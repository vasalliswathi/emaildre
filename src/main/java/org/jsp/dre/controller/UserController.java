package org.jsp.dre.controller;

import org.jsp.dre.entity.User;
import org.jsp.dre.repository.UserRepository;
import org.jsp.dre.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

	@Autowired
	private UserService service;


    UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
	
	
	@PostMapping
	public ResponseEntity<?>  saveUser(@RequestBody User user){
		return service.saveUser(user);
	}
	
	@GetMapping
	public ResponseEntity<?> findAllUsers(){
		return service.findAllUsers();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findUserById(@PathVariable int id){
		return service.findUserById(id);
	}


	@GetMapping("/match/{id}/{top}")
	public ResponseEntity<?> findMatch(@PathVariable int id,@PathVariable int top){
		return service.findMatch(id,top);
		
	}
}
