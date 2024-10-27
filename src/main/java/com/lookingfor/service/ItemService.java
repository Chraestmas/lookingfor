package com.lookingfor.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;
import org.springframework.stereotype.Service;

import com.lookingfor.dto.ItemDTO;
import com.lookingfor.repository.ItemRepository;

@Service
public class ItemService {
	
	ItemRepository ir;
	
	@Autowired
	public ItemService(ItemRepository ir) {
		this.ir = ir;
	}
	
	public PageResponse<ItemDTO> getMeetings(int page, int size, String keyword, String category) {
		
		String findPattern = "%" + keyword + "%";// ex "%모임%"
		
		Sort s = Sort.by(Sort.Order.desc("createdAt"));
		PageRequest pr = PageRequest.of(page-1, size, s);
		
		Page<Item> res = null;
		if(category.equals("ALL")) {
			if(keyword == null || keyword.equals("")) {
				// category X, 제목 X
				res = ir.findAllBy(pr);
			}else {
				// category X, 제목 O
				res = ir.findAllByTitleLike(findPattern, pr);
			}
		}else {
			
			if(keyword == null || keyword.equals("")) {
				//cate O 제목 X
				res = ir.findAllByCategory(category, pr);
			}else {
				// cate O 제목 O
				res = ir.findAllByTitleLikeAndCategory(findPattern, category, pr);
			}
		}
		
		// res.getContent() --> 리스트<JaksimMeetings>  --> 리스트<JaksimMeetingsDTO>
		// JaksimMeetings를 DTO로 바꿔주고 PageResponse에다 담아서 Controller로 전달
		List<ItemDTO> li = new ArrayList<>();
		for(Item jm : res.getContent()) {
			ItemDTO jmdto = new ItemDTO();
			jmdto.setCategory(jm.getCategory());
			jmdto.setCoverUrl(jm.getCoverUrl());
			jmdto.setMeetingId(jm.getMeetingId());
			jmdto.setMissionTask(jm.getMissionTask());
			jmdto.setTitle(jm.getTitle());
			/*
			List<MissionDaysDTO> mdList = new ArrayList<>();
			for( MissionDays md : jm.getMissionDays()) {
				MissionDaysDTO mddto = new MissionDaysDTO();
				mddto.setDayOfWeek(md.getDayOfWeek());
				mdList.add(mddto);
			}
			
			jmdto.setMissionDays(mdList);
			li.add(jmdto);
			*/
		}
		
		PageResponse<ItemDTO> pageMeetings = new PageResponse<>();
		pageMeetings.setList(li);
		pageMeetings.setCurrentPage(page);
		pageMeetings.setHasNext(page < res.getTotalPages());
		pageMeetings.setHasPrevious(page > 1);
		pageMeetings.setTotalElements(res.getTotalElements());
		pageMeetings.setTotalPages(res.getTotalPages());
		
		return pageMeetings;
		
	}

}

/*
package com.jaksim.service;

@Service
public class JaksimMeetingsService {
	
	JaksimMeetingsRepository jmr;
	MissionDaysRepository mdr;
	MeetingMembersRepository mmr;
	MissionCompletionsRepository mcr;
	
	@Autowired
	public JaksimMeetingsService(JaksimMeetingsRepository jmr,
			MissionDaysRepository mdr, 
			MeetingMembersRepository mmr,
			MissionCompletionsRepository mcr) {
		this.jmr = jmr;
		this.mdr = mdr;
		this.mmr = mmr;
		this.mcr = mcr;
	}
	
	@Transactional
	public void deleteMeeting(int id) {
		// id가 존재하는 meetingId인지 검사
		Optional<JaksimMeetings> opMeeting = jmr.findById(id);
		if(!opMeeting.isPresent()) {
			// 해당 meeting id가 존재하지 않음
			return;
		}
		// meeting을 외래키로 사용하고 있는 테이블에서 먼저 삭제 진행
		mdr.deleteByMeeting_MeetingId(id);
		mmr.deleteByMeeting_MeetingId(id);
		mcr.deleteByMeeting_MeetingId(id);
		
		
		jmr.deleteById(id);
		
	}
	
	// 모임 수정 메소드
	@Transactional
	public void updateMeeting(int id, JaksimMeetingsDTO jmdto) {
		// id가 존재하는 meetingId인지 검사
		Optional<JaksimMeetings> opMeeting = jmr.findById(id);
		if(!opMeeting.isPresent()) {
			// 해당 meeting id가 존재하지 않음
			return;
		}
		
		JaksimMeetings jmEntity = new JaksimMeetings();
		jmEntity.setMeetingId(id);
		jmEntity.setCategory(jmdto.getCategory());
		jmEntity.setCoverUrl(jmdto.getCoverUrl());
		jmEntity.setMeetingDetail(jmdto.getMeetingDetail());
		jmEntity.setMissionTask(jmdto.getMissionTask());
		jmEntity.setTitle(jmdto.getTitle());
		jmEntity.setUpdatedAt(LocalDateTime.now());
		jmEntity.setCreatedAt(jmdto.getCreatedAt());
		
		
		JaksimMeetings savedMeeting = jmr.save(jmEntity); // 메소드 실행 결과는 실제로 데이터베이스에 생성된 JaksimMeetings 객체가 된다.

		// missionDays 테이블에서 기존에 요일을 삭제하고, 새로운 요일을 추가해야 한다.
		mdr.deleteByMeeting_MeetingId(id); // meeting_id로 요일 행 삭제
		
		// 새로운 요일 추가
		List<MissionDays> tmp = new ArrayList<>();
		
		// MissionDaysDTO가 요소로 들어있는 리스트를 MissionDays 엔터티가 요소로 들어있는 리스트 tmp로 변환
		for(MissionDaysDTO mddto : jmdto.getMissionDays()) {
			MissionDays mdEntity = new MissionDays();
			mdEntity.setDayOfWeek(mddto.getDayOfWeek());
			mdEntity.setMeeting(savedMeeting);
			tmp.add(mdEntity);
		}
		
		
		mdr.saveAll(tmp);
		
		
		
		
	
		
		
	}
	
	
	// 모임 생성 메소드
	@Transactional
	public CreateResponse<Integer> createMeeting(JaksimMeetingsDTO jmdto) {
		// dto를 entity로 변환
		JaksimMeetings jmEntity = new JaksimMeetings();
		jmEntity.setTitle(jmdto.getTitle());
		jmEntity.setCategory(jmdto.getCategory());
		jmEntity.setMeetingDetail(jmdto.getMeetingDetail());
		jmEntity.setCoverUrl(jmdto.getCoverUrl());
		jmEntity.setMissionTask(jmdto.getMissionTask());
		jmEntity.setCreatedAt(LocalDateTime.now());
		jmEntity.setUpdatedAt(LocalDateTime.now());
		
		
//		// MissionDays를 추가할 때 모임id가 없기 때문에 데이터 생성과정에서 오류가 발생한다.
//		// 따라서 MissionDays는 모임을 먼저 생성하고, 생성한 id를 받아와서 직접 추가해줘야한다.
//		List<MissionDays> tmp = new ArrayList<>();
//		
//		// MissionDaysDTO가 요소로 들어있는 리스트를 MissionDays 엔터티가 요소로 들어있는 리스트 tmp로 변환
//		for(MissionDaysDTO mddto : jmdto.getMissionDays()) {
//			MissionDays mdEntity = new MissionDays();
//			mdEntity.setDayOfWeek(mddto.getDayOfWeek());
//			tmp.add(mdEntity);
//		}
//		
//		jmEntity.setMissionDays(tmp);
		
		JaksimMeetings savedMeeting = jmr.save(jmEntity); // 메소드 실행 결과는 실제로 데이터베이스에 생성된 JaksimMeetings 객체가 된다.
		List<MissionDays> tmp = new ArrayList<>();
		
		// MissionDaysDTO가 요소로 들어있는 리스트를 MissionDays 엔터티가 요소로 들어있는 리스트 tmp로 변환
		for(MissionDaysDTO mddto : jmdto.getMissionDays()) {
			MissionDays mdEntity = new MissionDays();
			mdEntity.setDayOfWeek(mddto.getDayOfWeek());
			mdEntity.setMeeting(savedMeeting);
			tmp.add(mdEntity);
		}
		
		
		mdr.saveAll(tmp);
		// 만들어진 meetingId 조회
		// savedMeeting.getMeetingId();
		
		// 모임 만든사람을 meeting_members 테이블에 저장
		
		
		MeetingMembers mmEntity = new MeetingMembers();
		mmEntity.setLeader(true);
		mmEntity.setMeeting(savedMeeting);
		
		JaksimUsers juEntity = new JaksimUsers();
		juEntity.setUserEmail(jmdto.getLeaderEmail());
		mmEntity.setUser(juEntity);
		
		
		MeetingMembersId mmid = new MeetingMembersId();
		mmid.setMeetingId(savedMeeting.getMeetingId());
		mmid.setUserEmail(jmdto.getLeaderEmail());
		
		mmEntity.setId(mmid);
		
		
		
		mmr.save( mmEntity );
		
		
		CreateResponse<Integer> cr = new CreateResponse<>();
		cr.setCreatedId(savedMeeting.getMeetingId());
		cr.setMessage("모임 생성 성공!");
		return cr;
		
	}
	
	
	public JaksimMeetingsDTO getMeetingById(int id) {
		Optional<JaksimMeetings> res =  jmr.findById(id);
		if(res.isPresent()) {
			JaksimMeetings tmp = res.get();
			
			JaksimMeetingsDTO jmdto = new JaksimMeetingsDTO();
			jmdto.setMeetingId(tmp.getMeetingId());
			jmdto.setCoverUrl(tmp.getCoverUrl());
			jmdto.setMeetingDetail(tmp.getMeetingDetail());
			
			List<MissionDaysDTO> days = new ArrayList<>();
			for(MissionDays md : tmp.getMissionDays()) {
				MissionDaysDTO mddto = new MissionDaysDTO();
				mddto.setDayOfWeek(md.getDayOfWeek());
				mddto.setMissionDayId(md.getMissionDayId());
				
				days.add(mddto);
			}
			
			jmdto.setMissionDays(days);
			jmdto.setMissionTask(tmp.getMissionTask());
			
			return jmdto;
			
		}
		// 게시글 조회 결과가 없을때
		return null;
		
		
	}
	
	
	public PageResponse<JaksimMeetingsDTO> getMeetings(int page, int size, String keyword, String category) {
		
		String findPattern = "%" + keyword + "%";// ex "%모임%"
		
		Sort s = Sort.by(Sort.Order.desc("createdAt"));
		PageRequest pr = PageRequest.of(page-1, size, s);
		
		Page<JaksimMeetings> res = null;
		if(category.equals("ALL")) {
			if(keyword == null || keyword.equals("")) {
				// category X, 제목 X
				res = jmr.findAllBy(pr);
			}else {
				// category X, 제목 O
				res = jmr.findAllByTitleLike(findPattern, pr);
			}
		}else {
			
			if(keyword == null || keyword.equals("")) {
				//cate O 제목 X
				res = jmr.findAllByCategory(category, pr);
			}else {
				// cate O 제목 O
				res = jmr.findAllByTitleLikeAndCategory(findPattern, category, pr);
			}
		}
		
		// res.getContent() --> 리스트<JaksimMeetings>  --> 리스트<JaksimMeetingsDTO>
		// JaksimMeetings를 DTO로 바꿔주고 PageResponse에다 담아서 Controller로 전달
		List<JaksimMeetingsDTO> li = new ArrayList<>();
		for(JaksimMeetings jm : res.getContent()) {
			JaksimMeetingsDTO jmdto = new JaksimMeetingsDTO();
			jmdto.setCategory(jm.getCategory());
			jmdto.setCoverUrl(jm.getCoverUrl());
			jmdto.setMeetingId(jm.getMeetingId());
			jmdto.setMissionTask(jm.getMissionTask());
			jmdto.setTitle(jm.getTitle());
			
			List<MissionDaysDTO> mdList = new ArrayList<>();
			for( MissionDays md : jm.getMissionDays()) {
				MissionDaysDTO mddto = new MissionDaysDTO();
				mddto.setDayOfWeek(md.getDayOfWeek());
				mdList.add(mddto);
			}
			
			jmdto.setMissionDays(mdList);
			li.add(jmdto);
			
		}
		
		PageResponse<JaksimMeetingsDTO> pageMeetings = new PageResponse<>();
		pageMeetings.setList(li);
		pageMeetings.setCurrentPage(page);
		pageMeetings.setHasNext(page < res.getTotalPages());
		pageMeetings.setHasPrevious(page > 1);
		pageMeetings.setTotalElements(res.getTotalElements());
		pageMeetings.setTotalPages(res.getTotalPages());
		
		return pageMeetings;
		
	}
	
}








*/