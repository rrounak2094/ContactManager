package com.smart.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.smart.dao.ContactRepository;
import com.smart.dao.UserRepository;
import com.smart.entity.Contact;
import com.smart.entity.User;

@RestController
public class SearchController {
	
	@Autowired
	private UserRepository ur;
	@Autowired
	private ContactRepository cr;

	//search Handler
	@GetMapping("/search/{query}")
	public ResponseEntity<?> search(@PathVariable("query") String query, Principal p)
	{
	     System.out.println(query);
	     
	     User u=this.ur.getUserByUserName(p.getName());
	     
	    List<Contact> contacts= this.cr.findByNameContainingAndU(query,u);
	    
	    return ResponseEntity.ok(contacts);
	     
	}
	
}
