//handles implementation for JPA
//handles all CRUD operations with mysql datastore
package com.backend.product;

import org.springframework.data.jpa.repository.JpaRepository;
 
public interface ProductRepository extends JpaRepository<Product, Integer> {

}
