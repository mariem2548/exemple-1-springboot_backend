package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exection.ResourceNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
@CrossOrigin("*")

@RestController
@RequestMapping("api/v1/")
public class UserController {
    @Autowired
	private UserRepository userRepository;
	// get all users
        @GetMapping("/users")
    	public List<User> getAllUsers(){
    	return userRepository.findAll();
    	
    }
        //save users
    	@PostMapping(value = "/saveUsers")
    	public User createUser(@RequestBody User user) {

    		return userRepository.save(user);
    	}
    	//get users by id
    	@GetMapping("/users/{userId}")
    	public ResponseEntity<User> getUserById(@PathVariable int userId) {
    		User user = userRepository.findById(userId)
    				.orElseThrow(()-> new ResourceNotFoundException("user not exist "));
    		return ResponseEntity.ok(user);
    		
    	}
    	
    	
    	//update users
    	@PutMapping("/users/{userId}")
    	public ResponseEntity<User> updateUser(@PathVariable int userId, @RequestBody User userDetails){
    		User user = userRepository.findById(userId)
    				.orElseThrow(()-> new ResourceNotFoundException("user not exist "));
    		user.setUserNom(userDetails.getUserNom());
    		user.setUserPrenom(userDetails.getUserPrenom());
    		
    		User updateUser = userRepository.save(user);
    		return ResponseEntity.ok(updateUser);

    	}
    	
    	// delete user
    	@DeleteMapping("/users/{userId}")
    	public ResponseEntity< Map<String, Boolean>> deleteUser(@PathVariable int userId){
    		User user = userRepository.findById(userId)
    				.orElseThrow(()-> new ResourceNotFoundException("user not exist "));
    	userRepository.delete(user);
    	Map <String, Boolean> response = new HashMap<>();
    	response.put("deleted", Boolean.TRUE);
    	return  ResponseEntity.ok(response);
    		
    	}
}
