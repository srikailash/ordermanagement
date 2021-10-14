package com.backend.user;

import java.util.*;
import org.springframework.stereotype.Service;

@Service
public class UserService {


	public List<User> getUsers() {
		// return "Hello World-1!!";

		List l = new ArrayList<User >();
        
		l.add(new User("kailash"));

		return l;

	}
}

