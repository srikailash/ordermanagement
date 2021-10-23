//Controller is the API layer
package com.backend.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.bind.annotation.PathVariable;

import java.nio.file.AccessDeniedException;
import java.util.Optional;
import java.util.concurrent.Callable;

import com.backend.ErrorResponse;

@RestController
@RequestMapping(path="api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

	// Add new user
	@PostMapping(path="/admin/add")
	public @ResponseBody String addNewUser (@RequestBody User user) {
		return userService.addUser(user);
	}

	// Get single user by Id
	@GetMapping(path="/{id}")
	public @ResponseBody Optional<User> getUserById(
		@PathVariable(name = "id") Integer id,
		@RequestHeader Integer userId) throws Exception {

		if(userId != id) {
			throw new AccessDeniedException("Unauthorized access");
		}

		return userService.getUser(id);
	}

	// // Update a user
	// @PostMapping(path="/update/{id}")
	// public @ResponseBody String updateUser(@PathVariable(name = "id") Integer id, @RequestBody 
    //     User user) {
    //     return userService.updateUser(id, user);
	// }

	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	public @ResponseBody String updateUser(
		@PathVariable(name = "id") Integer id,
		@RequestBody com.fasterxml.jackson.databind.JsonNode payload,
		@RequestHeader Integer userId) throws Exception {
		
		if(userId != id) {
			throw new AccessDeniedException("Unauthorized access");
		}

		userService.updateUserName(id, payload.get("name").textValue());
		return "user name updated";
	}

	@GetMapping("/get/{id}")
	public Callable<Optional<User>> testTimeout(@PathVariable(name = "id") Integer id) {
		return () -> {
			return userService.getUser(id);
		};
	}

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> notFound(Exception ex){
		System.out.println(ex.getClass());
		ErrorResponse error = new ErrorResponse(ex);
		return new ResponseEntity<ErrorResponse>(
			new ErrorResponse(ex) , error.getCode());
    }	

}
