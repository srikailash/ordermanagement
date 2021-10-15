package com.backend.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
	private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
	}

	// Add new student
	public String addProduct(Product product) {		
		try {
			productRepository.save(product);
			return "saved";
		} catch(Exception e) {
			return "failed";
		}
	}

	// Update a Product
	public String updateProduct(Long id, Product product) {
		try {
			product.setId(id);
			productRepository.save(product);
			return "Updated";
		}catch(Exception e) {
			return "Failed";
		}
	}

	// Get all students
	public Iterable<Product> getAllProducts(){
		return productRepository.findAll();
	}

	// Get single student by Id
	public Optional<Product> getProduct(Long id) {
		return productRepository.findById(id);
	}

	// Delete a Student
	public String deleteProduct(Long id) {
		try{
			productRepository.deleteById(id);
			return "Deleted";
		}catch(Exception e) {
			return "Failed";
		}
	}

}

