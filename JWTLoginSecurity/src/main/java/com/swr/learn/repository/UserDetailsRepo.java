package com.swr.learn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.swr.learn.entites.User;

@Repository
public interface UserDetailsRepo extends JpaRepository<User, Long> {

	User findByUserName(String username);

}
