//handles implementation for JPA
//handles all CRUD operations with mysql datastore
package com.backend.user;

import org.springframework.data.repository.CrudRepository;
 
public interface UserRepository extends CrudRepository<User, Integer> {

}
