package com.lp.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lp.model.Actor;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<Actor, Long> {
	public Actor findByUserName(String userName);
	public void deleteByUserName(String userName);

}
