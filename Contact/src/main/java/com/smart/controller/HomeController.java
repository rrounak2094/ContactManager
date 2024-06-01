package com.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.dao.UserRepository;
import com.smart.entity.User;
import com.smart.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
public class HomeController {
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private UserRepository ur;
	
  @RequestMapping("/home")	
  public String home(Model m)
  {
	  m.addAttribute("title", "Smart Contact Manager");
	  return "home";
  }

  @RequestMapping("/about")	
  public String about(Model m)
  {
	  m.addAttribute("title", "About Smart Contact Manager");
	  return "about";
  }
  
  @RequestMapping("/signup")	
  public String signup(Model m)
  {
	  m.addAttribute("title", "signup-Smart Contact Manager");
	  m.addAttribute("user", new User());
	  return "signup";
  }
  
  //handler for register user
  @RequestMapping(value="/do_register", method=RequestMethod.POST)
  public String registerUser(@Valid @ModelAttribute("user") User user,
		                     BindingResult res,
		                     @RequestParam(value="agree",defaultValue="false") boolean agree,
		                     Model m,
		                     HttpSession session)
  {
	  try {
		  
		  if(!agree)
		  {
			  System.out.println("You have not agreed the terma and conditions");
			  throw new Exception("You have not agreed the terma and conditions");
		  }
		  
		  if(res.hasErrors())
		  {
			  System.out.println("Error : "+res.toString());
			  m.addAttribute("user",user);
			  return "signup";
		  }
		  
		  user.setRole("ROLE_USER");
		  user.setEnabled(true);
		  user.setPwd(pe.encode(user.getPwd()));
		  
		  System.out.println(agree);
		  System.out.println(user);
		  
		  //save in the database
		  User result=this.ur.save(user);
		  
		  m.addAttribute("user", new User());
		  
		  session.setAttribute("message", new Message("Successfully Registered!!","alert-success"));//when we retrun on signup page then we can see the details in the fields
          
		  return "signup";
		
	} catch (Exception e) {
		
		e.printStackTrace();
		m.addAttribute("user", user);
		session.setAttribute("message", new Message("Something went wrong!!"+e.getMessage(),"alert-danger"));
		return "signup";
	}	  
	 // return "signup";
  }
  
  //handler for custom login
  @GetMapping("/logi")
  public String customLogin(Model m)
  {
	  m.addAttribute("title", "login-Smart Contact Manager");
	  return "login";
  }

}