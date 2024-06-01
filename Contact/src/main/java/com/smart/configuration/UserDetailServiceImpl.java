package com.smart.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.smart.dao.UserRepository;
import com.smart.entity.User;

public class UserDetailServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository ur;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//fetching user data from database
		User user=ur.getUserByUserName(username);
		
		if(user==null)
		{
			throw new UsernameNotFoundException("Could not found the user");
		}
		
		CustomUserDetails cud=new CustomUserDetails(user);
		
		return cud;
	}

}
