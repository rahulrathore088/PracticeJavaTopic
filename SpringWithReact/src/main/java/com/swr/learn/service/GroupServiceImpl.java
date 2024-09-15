package com.swr.learn.service;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swr.learn.dto.EventDto;
import com.swr.learn.dto.GroupDto;
import com.swr.learn.dto.UserDto;
import com.swr.learn.model.Event;
import com.swr.learn.model.Group;
import com.swr.learn.model.User;
import com.swr.learn.repository.GroupRepository;

@Component
public class GroupServiceImpl {

	@Autowired
	private GroupRepository repository;

	public Group saveUserGroup(GroupDto groupDto) {
		Group grp = new Group();
		BeanUtils.copyProperties(groupDto, grp, "events");
		UserDto userDto = groupDto.getUser();
		User user = new User();
		BeanUtils.copyProperties(userDto, user);
		grp.setUser(user);
		Set<Event> events = new HashSet<>();
		for (EventDto dto : groupDto.getEvents()) {
			Event event = new Event();
			event.setAttendees(Collections.singleton(user));
			event.setDate(Instant.parse(dto.getDate()));
			event.setDescription(dto.getDescription());
			event.setTitle(dto.getTitle());
			events.add(event);
		}
		grp.setEvents(events);
		return repository.save(grp);
	}

	public Collection<Group> findAllGroup() {
		return repository.findAll();
	}

	public Group findGroupById(Long id) {
		Optional<Group> opt =  repository.findById(id);
		if(opt.isPresent()) {
			return opt.get();
		}
		return null;
	}

}
