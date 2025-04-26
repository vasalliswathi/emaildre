package org.jsp.dre.dao;

import java.util.List;
import java.util.Optional;

import org.jsp.dre.entity.User;
import org.jsp.dre.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

	@Autowired
	private UserRepository repo;

	public User saveUser(User user) {
		
		return repo.save(user);
	}

	public List<User> findAllUsers() {
		
		return repo.findAll();
		
	}

	public Optional<User> findUserById(int id) {
	
		return repo.findById(id);
	}

	public List<User> findByGender(String gf) {
		
		return repo.findByGender(gf);
	}



	
}
