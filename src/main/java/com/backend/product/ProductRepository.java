//handles implementation for JPA
package com.backend.product;

import java.util.List;
 
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
 
public interface ProductRepository extends CrudRepository<Product, Long> {
    @Query(
        value = "SELECT * FROM Product",
        nativeQuery = true)
    public List<Product> listAllProducts();
}


