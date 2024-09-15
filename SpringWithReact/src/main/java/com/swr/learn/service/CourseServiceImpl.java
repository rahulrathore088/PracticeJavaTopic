package com.swr.learn.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swr.learn.dto.CourseDto;
import com.swr.learn.model.Courses;
import com.swr.learn.repository.CourseRepository;

@Component
public class CourseServiceImpl {

	@Autowired
	private CourseRepository repository;

	public Long addCourse(CourseDto courseDto) {
		Courses courses = new Courses();
		BeanUtils.copyProperties(courseDto, courses);
		courses = repository.save(courses);
		return courses.getId();
	}

	public List<Courses> getAllCourse() {
		return repository.findAll();
	}

}
