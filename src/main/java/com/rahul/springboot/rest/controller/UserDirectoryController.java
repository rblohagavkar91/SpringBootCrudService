package com.rahul.springboot.rest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rahul.springboot.rest.dao.UserDirectoryRepository;
import com.rahul.springboot.rest.exception.ResourceNotFoundException;
import com.rahul.springboot.rest.model.UserInfo;

@RestController
public class UserDirectoryController {

	@Autowired
	private UserDirectoryRepository userRepository;
	
	@RequestMapping(method=RequestMethod.POST,value = "/userdetails/addUser")
	public UserInfo addUser(@RequestBody UserInfo userInfo)
	{
		return userRepository.save(userInfo);
	}
	
	@RequestMapping("/userdetails")
	public List<UserInfo> getUsers()
	{	
		List<UserInfo> allUsers = new ArrayList<>();
		userRepository.findAll().forEach(allUsers :: add);
		return allUsers;
	}
	
	@RequestMapping("/userdetails/{id}")
	public ResponseEntity<UserInfo> getUserInfo(@PathVariable(value = "id") String id)
			throws ResourceNotFoundException {
		
		UserInfo user = userRepository.findByuserId(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + id));
		return ResponseEntity.ok().body(user);
	}
	
	@RequestMapping(method=RequestMethod.PUT,value = "/userdetails/update/{id}")
	public ResponseEntity<UserInfo> updateUserDetails(@PathVariable String id, @RequestBody UserInfo userInfo) throws ResourceNotFoundException {
		UserInfo user = userRepository.findByuserId(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + id));
		
		user.setEmailId(userInfo.getEmailId());
		user.setUserId(userInfo.getUserId());
		user.setDepartment(userInfo.getDepartment());
		final UserInfo updatedEmployee = userRepository.save(userInfo);
		return ResponseEntity.ok(updatedEmployee);
	}

	
	@RequestMapping(method=RequestMethod.DELETE,value = "/userdetails/delete/{id}")
	public Map<String, Boolean> deleteUser(@PathVariable String id) throws ResourceNotFoundException {
		UserInfo user = userRepository.findByuserId(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + id));
		userRepository.delete(user);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	
}
