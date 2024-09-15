package com.swr.learn.dto;

import java.util.Set;

public class GroupDto {
	private Long groupId;
	private String groupName;
	private String city;
	private String country;
	private UserDto user;
	private Set<EventDto> events;

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public Set<EventDto> getEvents() {
		return events;
	}

	public void setEvents(Set<EventDto> events) {
		this.events = events;
	}
}
