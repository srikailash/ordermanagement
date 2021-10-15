//handles implementation for JPA
//handles all CRUD operations with mysql datastore
package com.backend.order;

import org.springframework.data.repository.CrudRepository;
 
public interface OrderRepository extends CrudRepository<Order, Long> {

}
