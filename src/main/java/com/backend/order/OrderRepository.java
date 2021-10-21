//handles implementation for JPA
//handles all CRUD operations with mysql datastore
package com.backend.order;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
 
public interface OrderRepository extends JpaRepository<Order, Integer> {
    
    boolean existsById(Integer orderId); //Checks if there are any records by name

    @Query(value = "SELECT * FROM Orders WHERE id = :orderId AND user_id = :userId", nativeQuery = true)
    Collection<Order> findOrderFromUser(Integer orderId, Integer userId) throws Exception;

    @Query(value="SELECT * FROM Orders WHERE user_id = :userId limit :limit offset :offset", nativeQuery = true)
    public Collection<Order> findOrdersFromUser(Integer userId, int limit, int offset);

    @Query(value="SELECT * FROM Orders WHERE product_id = :productId limit :limit offset :offset", nativeQuery = true)
    public Collection<Order> findOrdersForProduct(Integer productId, int limit, int offset);
}
