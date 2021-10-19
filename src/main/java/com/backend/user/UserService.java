//Service has all the business logic
package com.backend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
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
	public String updateUser(Integer id, User user) {
		try {
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
	public Optional<User> getUser(Integer id) {
		return userRepository.findById(id);
	}

	// Delete a Student
	public String deleteUser(Integer id) {
		try{
			userRepository.deleteById(id);
			return "Deleted";
		}catch(Exception e) {
			return "Failed";
		}
	}

	public Double getBalance(Integer id) {

		Optional<User> optionalUser = userRepository.findById(id);

		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			return user.getBalance();
		}
		else {
			System.out.println("Unknown user");
			return 0.0;
			// throw new Exception("user not found");
		}

	}

	public Boolean makePurchase(Integer id, Double price) throws Exception {

		Optional<User> optionalUser = userRepository.findById(id);

		if(optionalUser.isPresent()) {

			User user = optionalUser.get();
			Double balance = user.getBalance();

			if(Double.compare(balance, price) > 0) {
				user.setBalance(balance - price);
				userRepository.saveAndFlush(user);
				return true;
			}
			else {
				System.out.println("Insufficient balance");
				throw new Exception("Insufficient balance");
			}

		}
		else {
			//This is redundant check since headers are validated for userId authorization
			throw new Exception("Problems connecting to userId");
		}

	}


}
