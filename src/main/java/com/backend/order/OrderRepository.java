//handles implementation for JPA
//handles all CRUD operations with mysql datastore
package com.backend.order;

import org.springframework.data.jpa.repository.JpaRepository;
 
public interface OrderRepository extends JpaRepository<Order, Integer> {

}
