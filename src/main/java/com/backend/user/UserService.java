//Service has all the business logic
package com.backend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

	@Autowired
	private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
	}
	
	// Add new student
	public String addUser(User user) {		
		try {
			userRepository.save(user);
			return "saved";
		} catch(Exception e) {
			return "failed";
		}
	}

	// Update a User
	public String updateUser(Long id, User user) {
		try {
			user.setId(id);
			userRepository.save(user);
			return "Updated";
		}catch(Exception e) {
			return "Failed";
		}
	}

	// Get all students
	public Iterable<User> getAllUsers(){
		return userRepository.findAll();
	}

	// Get single student by Id
	public Optional<User> getUser(Long id) {
		return userRepository.findById(id);
	}

	// Delete a Student
	public String deleteUser(Long id) {
		try{
			userRepository.deleteById(id);
			return "Deleted";
		}catch(Exception e) {
			return "Failed";
		}
	}

}
