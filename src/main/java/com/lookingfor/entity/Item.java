package com.lookingfor.entity;



@Entity
public class Item {
	
	
	@id;
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String 	name;
	private Integer category_id;
	private String 	found_date;
	private String 	name_tag;
	private Integer location_id;
	private Boolean found_yn;
	private String 	pickup_date;
	private String 	pickup_person_name;
	private String 	description;
	private String 	user_id;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCategory_id() {
		return category_id;
	}
	public void setCategory_id(Integer category_id) {
		this.category_id = category_id;
	}
	public String getFound_date() {
		return found_date;
	}
	public void setFound_date(String found_date) {
		this.found_date = found_date;
	}
	public String getName_tag() {
		return name_tag;
	}
	public void setName_tag(String name_tag) {
		this.name_tag = name_tag;
	}
	public Integer getLocation_id() {
		return location_id;
	}
	public void setLocation_id(Integer location_id) {
		this.location_id = location_id;
	}
	public Boolean getFound_yn() {
		return found_yn;
	}
	public void setFound_yn(Boolean found_yn) {
		this.found_yn = found_yn;
	}
	public String getPickup_date() {
		return pickup_date;
	}
	public void setPickup_date(String pickup_date) {
		this.pickup_date = pickup_date;
	}
	public String getPickup_person_name() {
		return pickup_person_name;
	}
	public void setPickup_person_name(String pickup_person_name) {
		this.pickup_person_name = pickup_person_name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	
	
	
}


////////

/*
package com.jaksim.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class JaksimMeetings {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer meetingId;
	private String title;
	private String category;
	private String missionTask;
	private String coverUrl;
	private String meetingDetail;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	@OneToMany(mappedBy = "meeting",  cascade=CascadeType.ALL )
	private List<MissionDays> missionDays;
	
	
	public Integer getMeetingId() {
		return meetingId;
	}
	public void setMeetingId(Integer meetingId) {
		this.meetingId = meetingId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getMissionTask() {
		return missionTask;
	}
	public void setMissionTask(String missionTask) {
		this.missionTask = missionTask;
	}
	public String getCoverUrl() {
		return coverUrl;
	}
	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}
	public String getMeetingDetail() {
		return meetingDetail;
	}
	public void setMeetingDetail(String meetingDetail) {
		this.meetingDetail = meetingDetail;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	public List<MissionDays> getMissionDays() {
		return missionDays;
	}
	public void setMissionDays(List<MissionDays> missionDays) {
		this.missionDays = missionDays;
	}
	
	
}

*/