//handles implementation for JPA
//handles all CRUD operations with mysql datastore
package com.backend.product;

import org.springframework.data.repository.CrudRepository;
 
public interface ProductRepository extends CrudRepository<Product, Long> {

}
