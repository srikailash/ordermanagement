//handles implementation for JPA
package com.backend.order;

import java.util.List;
 
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
 
public interface OrderRepository extends CrudRepository<Order, Long> {
    @Query(
        value = "SELECT * FROM Order", 
        nativeQuery = true)
    public List<Order> listAllOrders();
}
