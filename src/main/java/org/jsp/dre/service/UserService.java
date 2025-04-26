package org.jsp.dre.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jsp.dre.dao.UserDao;
import org.jsp.dre.dto.MatchingUser;
import org.jsp.dre.entity.User;
import org.jsp.dre.responsestructure.ResponseStructure;
import org.jsp.dre.util.SortByAge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserDao dao;
	
	@Autowired
	private EmailService emailService;

	public ResponseEntity<?> saveUser(User user) {

		User savedUser=dao.saveUser(user);
		emailService.sendEmail(savedUser);
		
		ResponseStructure rs =ResponseStructure.builder().status(HttpStatus.OK.value()).message("saved user successsfully").body(savedUser).build();
		ResponseEntity re=ResponseEntity.status(HttpStatus.OK).body(rs);
		
		return re;
	}

	public ResponseEntity<?> findAllUsers() {
		List<User> users = dao.findAllUsers();
		
		ResponseStructure rs=ResponseStructure.builder().status(HttpStatus.OK.value()).message("All Users Found Successfully").body(users).build();
		ResponseEntity re=ResponseEntity.status(HttpStatus.OK).body(rs);
		return re;
	}

	public ResponseEntity<?> findUserById(int id) {
		
		Optional<User> optional=dao.findUserById(id);
		
		if(optional.isEmpty()) {
			//exception
			ResponseStructure rs=ResponseStructure.builder().status(HttpStatus.OK.value()).message("User Not Found").body(id).build();
			ResponseEntity re=ResponseEntity.status(HttpStatus.OK).body(rs);
			return re;
		}
		
		User u = optional.get();
		
		ResponseStructure rs=ResponseStructure.builder().status(HttpStatus.OK.value()).message("User Found Successfully Done").body(u).build();
		ResponseEntity re=ResponseEntity.status(HttpStatus.OK).body(rs);
		return re;
	}

	public ResponseEntity<?> findMatch(int id, int top) {
		Optional<User> optional = dao.findUserById(id);
		if(optional.isEmpty()) {
			throw new RuntimeException("Invalid user Id,Unable to find TOP matches");
		}
		User user = optional.get();
		
		String gf= null;
		if(user.getGender().equalsIgnoreCase("MALE")) {
			gf="FEMALE";
		}
		else {
			gf="MALE";
		}
		
		List<User> users = dao.findByGender(gf);
		
		List<MatchingUser> matchingUser=new ArrayList<>();
		for(User u:users) {
			
			MatchingUser mu=new MatchingUser();
			mu.setId(u.getId());
			mu.setName(u.getName());
			mu.setEmail(u.getEmail());
			mu.setPhone(u.getPhone());
			mu.setPassword(u.getPassword());
			mu.setAge(u.getAge());
			mu.setIntrests(u.getIntrests());
			mu.setGender(u.getGender());
			
			int ad=user.getAge()-u.getAge();
			
			int aad=Math.abs(ad);
			
			mu.setAgeDifference(aad);
			
			int mic=0;
			List<String> intrests1 = user.getIntrests();
			List<String> intrests2 = u.getIntrests();
			for(String i:intrests1) {
				
				if(intrests2.contains(i)) {
					mic++;
				}
			}
			
			mu.setMatchingIntrestCount(mic);
			
			matchingUser.add(mu);
		}
		
		Collections.sort(matchingUser, new SortByAge());
		
		int c=0;
		List<MatchingUser> result=new ArrayList<>();
		for(MatchingUser mu:matchingUser) {
			if(top==0)
				break;
			else {
				result.add(mu);
				top--;
			}
		}
		ResponseStructure rs= ResponseStructure.builder().status(HttpStatus.OK.value()).message("Top matching Users Found").body(result).build();
		ResponseEntity re=ResponseEntity.status(HttpStatus.OK).body(rs);
		
		return re;
	}



	
}
