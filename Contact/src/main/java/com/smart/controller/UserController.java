package com.smart.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.dao.ContactRepository;
import com.smart.dao.UserRepository;
import com.smart.entity.Contact;
import com.smart.entity.User;
import com.smart.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private BCryptPasswordEncoder br;
	
	@Autowired
	private UserRepository ur;
	
	@Autowired
	private ContactRepository cr;
	
	@ModelAttribute
	public void commonData(Principal p,Model m)
	{
		 String username=p.getName(); 
		  System.out.println(username);
		  
		//get the user using user name
	     User user=ur.getUserByUserName(username); 
	     //user.setImageUrl(userP.jpg);
	     System.out.println(user);
	     
	     //send the data
	     m.addAttribute("user", user);
	}
	
	@RequestMapping("/index")
	public String dasdboard(Principal p,Model m)
	{
		 return "Normal user/user_dashboard"; //localhost:1218/user/index
	}
	
	//open add form handler
	@GetMapping("/add-contact")
	public String openAddContact(Model m)
	{
		m.addAttribute("title","Add contact");
		m.addAttribute("contact",new Contact());
		
		return "Normal user/add_contact_form";
	}
	
	//processing add contact form
	//@PostMapping("/process-contact")
	@RequestMapping(value = "/process-contact", method = RequestMethod.POST)
	public String processContact(@ModelAttribute @Valid Contact contact,
			//@RequestParam("profileImage") MultipartFile file,
			Principal p, Model m,HttpSession session)
	{
	
		
		try
		{
		System.out.println(contact);
		
		String name=p.getName();
		User user=this.ur.getUserByUserName(name);
		
		
		
		//processing and uploading file
		/*
		 * if(file.isEmpty()) { System.out.println("File is empty"); } else {
		 * contact.setprofileImage(file.getOriginalFilename()); File saveFile=new
		 * ClassPathResource("static/IMG").getFile(); Path
		 * path=Paths.get(saveFile.getAbsolutePath()+File.separator+file.
		 * getOriginalFilename());
		 * Files.copy(file.getInputStream(),path,StandardCopyOption.REPLACE_EXISTING);
		 * System.out.println("image uploaded"); }
		 */	//set the user object in contact 
		
		contact.setU(user);
		 user.getC().add(contact);
		 
		//add data in the database
		 this.ur.save(user);
		 
		 System.out.println("Added");
		 
		 //success message
		 session.setAttribute("message", new Message("your contact is  added!! Add more contacts","alert-success"));
		 
		}
		catch(Exception e)
		{
			System.out.println("Error : "+e.getMessage());
			e.printStackTrace();
			
			//error message
			session.setAttribute("message", new Message("Something went wrong please try again!!","alert-danger"));
		}
		 
		return "Normal user/add_contact_form";
	}
	
	//show contact handler
	//per page 5 contact show
	//current page=0[page]
	@GetMapping("show_contacts/{page}")
	public String showContacts(@PathVariable("page") Integer page,Model m, Principal p)
	{
		m.addAttribute("title","View All contacts");
		
		String username=p.getName();
		User user=this.ur.getUserByUserName(username);
		
		//Send all contact list by using contact repository
		Pageable pageable=PageRequest.of(page,5);
		
		Page<Contact> contacts=this.cr.findContactsByUser(user.getId(),pageable);
		
		m.addAttribute("contacts", contacts);
		m.addAttribute("CurrentPage", page);
		m.addAttribute("totalPages", contacts.getTotalPages());
		
		return "Normal user/show_contacts";
	}
	
	//handler for specific contact detail
	@GetMapping("/contact/{conid}")
	public String showCon(@PathVariable("conid") Integer conid,Model m, Principal p)
	{
		System.out.println(conid);
		
		Optional<Contact> optional=this.cr.findById(conid);
		Contact contact=optional.get();
		
		//put the restriction here like user can see only his/her contact only by url
		String username=p.getName();
		User user=this.ur.getUserByUserName(username);
		
		if(user.getId()==contact.getU().getId())
		{
			m.addAttribute("contact", contact);
		}
		
		return "Normal user/contact";
	}
	
	//delete contact handler
	@GetMapping("/delete/{conid}")
	public String deleteContact(@PathVariable("conid") Integer conid,Model m, HttpSession session,Principal p)
	{
		Optional<Contact> optional=this.cr.findById(conid);
		Contact contact=optional.get();
		
		User user=this.ur.getUserByUserName(p.getName());
		user.getC().remove(contact);
		this.ur.save(user);
		
		//this.cr.delete(contact);
		
		System.out.println("Deleted");
		
		session.setAttribute("message", new Message("Contact deleted successfully!!!","success"));
		
		return "redirect:/user/show_contacts/0";
	}
	
	//open update form handler
	@PostMapping("/update-contact/{conid}")
	public String updateContact(@PathVariable("conid") Integer conid,Model m)
	{
		m.addAttribute("title", "Update Contact");
		
		Contact contact=this.cr.findById(conid).get();
		
		m.addAttribute("contact", contact);
		
		return "Normal user/update_form";
	}

	
	//process the update contact form
	
	@PostMapping("/process-update")
	public String updateHandler(@ModelAttribute Contact contact,
			                    //@RequestParam("profileImage") MultipartFile file,
			                    Model m, HttpSession session, Principal p)
	{
		System.out.println(contact.getAbout());
		System.out.println(contact.getConid());
		
		try {
			//write image code......
			
			User user=this.ur.getUserByUserName(p.getName());
			
			contact.setU(user);
			
			this.cr.save(contact);
			
			session.setAttribute("message", new Message("Your Contact is updated successfully!!!","success"));
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/user/contact/"+contact.getConid();
	}
	
	//your profile handler
	@GetMapping("/profile")
	public String yourProfile(Model m)
	{
		m.addAttribute("title", "Profile Page");
		
		return "Normal user/profile";
	}
	
	//open setting handler
	@GetMapping("/setting")
	public String setting(Model m)
	{
		m.addAttribute("title", "Setting Page");
		
		return "Normal user/setting";
	}
	
	//handler for change password
	@PostMapping("/change-password")
	public String changePassword(@RequestParam("oldpwd") String oldpwd, @RequestParam("newpwd") String newpwd,
			Principal p, HttpSession session)
	{
		System.out.println("old---"+oldpwd+"new---"+newpwd);
		
		String username=p.getName();
		User currentUser=this.ur.getUserByUserName(username);
		System.out.println(currentUser.getPwd());
		
		if(this.br.matches(oldpwd, currentUser.getPwd()))
		{
			//change the pwd
			currentUser.setPwd(this.br.encode(newpwd));
			this.ur.save(currentUser);
			
			session.setAttribute("message", new Message("Your Password is changed successfully!!!","success"));
		}
		else
		{
			//error
			session.setAttribute("message", new Message("Please enter correct old password","danger"));
			return "redirect:/user/setting";
		}
		
		return "redirect:/user/index";
	}

}
	
	
	

