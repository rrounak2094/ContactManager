package com.smart.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smart.entity.Contact;
import com.smart.entity.User;

public interface ContactRepository extends JpaRepository<Contact,Integer>  {

	//implementing the pagination
	@Query("from Contact as c where c.u.id=:userId")
	public Page<Contact> findContactsByUser(@Param("userId") int userId,Pageable pageable);//pageable contain two information current page and contact per page
	
	
	//for search functionality
	//findByNameContaining()--> work on like keyword ex-we search li then we see those contact whose name contain li
	public List<Contact> findByNameContainingAndU(String name,User u);
	
	
	
}
