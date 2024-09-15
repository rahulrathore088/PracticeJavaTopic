package com.swr.learn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.swr.learn.model.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
	Group findByGroupName(String groupName);
}
