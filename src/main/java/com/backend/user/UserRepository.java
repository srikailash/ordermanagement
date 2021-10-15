//handles implementation for JPA
package com.backend.user;

import java.util.List;
 
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

 
public interface UserRepository extends CrudRepository<User, Long> {
     
    public List<User> findByItem(String item);
     
    @Query("SELECT * FROM User")
    public List<User> listAllUsers();
}
