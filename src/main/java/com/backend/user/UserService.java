//Service has all the business logic
package com.backend.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

import javax.persistence.OptimisticLockException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

	private final UserRepository userRepository;

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

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

	public Double getBalance(Integer id) throws Exception {

		Optional<User> optionalUser = userRepository.findById(id);

		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			return user.getBalance();
		}
		else {
			throw new Exception("user not found");
		}

	}
	
	@Retryable(maxAttempts=3,value=OptimisticLockException.class,backoff=@Backoff(delay = 2000))
	public Boolean makePurchase(Integer orderId, Integer userId, Integer productId, Double price) throws Exception {

		Optional<User> optionalUser = userRepository.findById(userId);

		String logMessage = "userService.makePurchase request for orderId : " + orderId.toString() + " productId : " + productId.toString() + " with price : " + price.toString();
		logger.info(logMessage);

		if(optionalUser.isPresent()) {

			User user = optionalUser.get();
			Double balance = user.getBalance();

			if(Double.compare(balance, price) > 0) {
				user.setBalance(balance - price);
				userRepository.saveAndFlush(user);
				return true;
			}
			else {
				throw new Exception("Insufficient balance");
			}

		}
		else {
			//This is redundant check since headers are validated for userId authorization
			throw new Exception("User not found");
		}

	}


}
