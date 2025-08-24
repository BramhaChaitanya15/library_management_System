package com.connect.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.connect.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("select u from User u where u.userId = :uid")
	public User getUserById(@Param("uid") int uid);

	//getting books for search results with book title
	public List<User> findByNameContaining(String keywords);
	
	//getting books for search results with author name
	public List<User> findByEmailContaining(String keywords);

	//getting books for search results with ISBN
	public List<User> findByPhoneNumberContaining(String keywords);
	
}
