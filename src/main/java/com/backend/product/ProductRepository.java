//handles implementation for JPA
//handles all CRUD operations with mysql datastore
package com.backend.product;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
 
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value="SELECT * FROM Products WHERE name like %:q% limit :limit offset :offset", nativeQuery = true)
    public Collection<Product> findProductsLike(String q, int limit, int offset);    
}
