package com.swr.learn.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swr.learn.dto.GroupDto;
import com.swr.learn.model.Group;
import com.swr.learn.service.GroupServiceImpl;

@RestController
@RequestMapping("/group")
public class GroupController {

	@Autowired
	private GroupServiceImpl serviceImpl;

	@GetMapping("/")
	public String getHomePage() {
		return "Welcome To Home..";
	}

	@GetMapping("/findAll")
	public Collection<Group> findAllGroup() {
		return serviceImpl.findAllGroup();
	}

	@PostMapping("/save")
	public Group saveUserGroup(@RequestBody GroupDto groupDto) {
		return serviceImpl.saveUserGroup(groupDto);
	}
	
	@GetMapping("/find/{Id}")
	public Group findGroupById(@PathVariable Long Id) {
		return serviceImpl.findGroupById(Id);
	}

}
