package com.smart.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.dao.UserRepository;
import com.smart.entity.User;
import com.smart.service.EmailService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ForgetController {
	
	Random random=new Random(1000);
	
	@Autowired
	private UserRepository ur;
	
	@Autowired
	private BCryptPasswordEncoder br;
	
	@Autowired
	private EmailService es;
	
	//email id form open handler
	@GetMapping("/forgot")
	public String  openForm()
	{
		return "forget_email_form";
	}

	@PostMapping("/send-otp")
	public String  sendOTP(@RequestParam("email") String email, HttpSession session)
	{
		System.out.println(email);
		
		//generate 4 digit otp
		
		int otp=random.nextInt(99999999);
		
		System.out.println(otp);
		
		//write code for send otp to email
		
		String subject="OTP from SCM";
		String message="<h1>OTP = "+otp+"</h1>";
		String to=email;
		
		boolean res=this.es.sendEmail(subject, message, to);
		
		if(res)
		{
			session.setAttribute("otp", otp);
			session.setAttribute("email",email);
			return  "verify_otp";
		}
		else
		{
			session.setAttribute("message", "check your email id");
			
			return "forget_email_form";
		}
		
	
	}

	@PostMapping("/verify-otp")
	public String verifyOtp(@RequestParam ("otp") int otp,HttpSession session)
	{
		int myotp=(int)session.getAttribute("otp");
		String email=(String)session.getAttribute("email");
		if(myotp==otp)
		{
		    User user=this.ur.getUserByUserName(email);	
		    
		    if(user==null)
		    {
		    	//send the error
		    	session.setAttribute("message", "user does not exist");
				
				return "forget_email_form";
		    }
		    else
		    {
		    	//send the change pwd form
		    	return "password_change_form";
		    }
			
			
		}
		else
		{
			session.setAttribute("message", "you have entered wrong OTP");
			return "verify_otp";
		}
	}
	
	// change pwd
	@PostMapping("/change-pwd")
	public String changepwd(@RequestParam("npwd") String npwd, HttpSession session)
	{
		String email=(String)session.getAttribute(npwd);
		User user=this.ur.getUserByUserName(email);
		user.setPwd(this.br.encode(npwd));
		this.ur.save(user);
		
		return "login";
	}
	
	
}
