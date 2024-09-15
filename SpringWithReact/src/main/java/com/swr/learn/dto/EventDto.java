package com.swr.learn.dto;

import java.util.Set;

public class EventDto {
	private Long eventId;
	private String date;
	private String title;
	private String description;
	private Set<UserDto> attendees;

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<UserDto> getAttendees() {
		return attendees;
	}

	public void setAttendees(Set<UserDto> attendees) {
		this.attendees = attendees;
	}

}
