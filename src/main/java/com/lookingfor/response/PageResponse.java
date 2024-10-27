package com.lookingfor.response;

import java.util.List;


//응답할 때 목록 리스트와 전체 게시물 갯수 등의 추가적인 정보를 함께 담아서 응답하기 위한 객체
public class PageResponse<T> {
	private List<T> list; // 실제 리스트 담아서 주자
	private int currentPage;
	private int totalPages;
	private boolean hasNext;
	private boolean hasPrevious;
	private long totalElements;
	
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public boolean isHasNext() {
		return hasNext;
	}
	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}
	public boolean isHasPrevious() {
		return hasPrevious;
	}
	public void setHasPrevious(boolean hasPrevious) {
		this.hasPrevious = hasPrevious;
	}
	public long getTotalElements() {
		return totalElements;
	}
	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

}
