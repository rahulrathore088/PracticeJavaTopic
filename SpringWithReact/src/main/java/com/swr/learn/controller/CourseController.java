package com.swr.learn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swr.learn.dto.CourseDto;
import com.swr.learn.model.Courses;
import com.swr.learn.service.CourseServiceImpl;

@RestController
@RequestMapping("/course")
@CrossOrigin(origins="http://localhost:3000")
public class CourseController {

	@Autowired
	private CourseServiceImpl courseServiceImpl;

	@PostMapping("/addCourse")
	public Long addCourse(@RequestBody CourseDto dto) {
		return courseServiceImpl.addCourse(dto);
	}
	
	@GetMapping("/allCourse")
	public List<Courses> getAllCourse(){
		return courseServiceImpl.getAllCourse();
	}

}
