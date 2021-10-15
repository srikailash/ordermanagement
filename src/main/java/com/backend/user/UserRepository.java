//handles implementation for JPA
//handles all CRUD operations with mysql datastore
package com.backend.user;

import java.util.List;
 
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
 
public interface UserRepository extends CrudRepository<User, Long> {
    @Query(
        value = "SELECT * FROM User", 
        nativeQuery = true)
    public List<User> listAllUsers();
}



