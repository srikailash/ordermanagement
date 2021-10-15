//Controller is the API layer
package com.backend.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@RestController
@RequestMapping(path="api/v1/user")
public class UserController {

    @Autowired
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }

	// Add new user
	@PostMapping(path="/add")
	public @ResponseBody String addNewUser (@RequestBody User user) {
		return userService.addUser(user);
	}

	// Get all users
	@GetMapping(path="/all")
	public @ResponseBody Iterable<User> getAllUsers() {
        return userService.getAllUsers();
	}

	// Get single user by Id
	@GetMapping(path="/{id}")
	public @ResponseBody Optional<User> getUserById(@PathVariable(name = "id") Long id) {
		return userService.getUser(id);
	}

	// Update a user
	@PostMapping(path="/update/{id}")
	public @ResponseBody String updateUser(@PathVariable(name = "id") Long id, @RequestBody 
        User user) {
        return userService.updateUser(id, user);
	}

	// Delete a user
	@DeleteMapping(path="/delete/{id}")
	public @ResponseBody String deleteUser(@PathVariable(name = "id") Long id) {
		// return studentService.deleteStudent(id);
        return userService.deleteUser(id);
	}
}
