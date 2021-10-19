//handles implementation for JPA
//handles all CRUD operations with mysql datastore
package com.backend.user;

import org.springframework.data.jpa.repository.JpaRepository;
 
public interface UserRepository extends JpaRepository<User, Integer> {

}
