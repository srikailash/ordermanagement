//handles implementation for JPA
//handles all CRUD operations with mysql datastore
package com.backend.product;

import org.springframework.data.repository.PagingAndSortingRepository;
 
public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {

}
