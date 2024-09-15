package com.swr.learn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.swr.learn.model.Courses;

@Repository
public interface CourseRepository extends JpaRepository<Courses, Long>{

}
