package com.backend.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import javax.transaction.Transactional;

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
	public String updateProduct(Integer id, Product product) {
		try {
			productRepository.save(product);
			return "Updated";
		}catch(Exception e) {
			return "Failed";
		}
	}

	// Get all products
	public Iterable<Product> getAllProducts(){
		return productRepository.findAll();
	}

	// Get single product by Id
	public Optional<Product> getProduct( Integer id) {
		return productRepository.findById(id);
	}

	// Delete a Student
	public String deleteProduct(Integer id) {
		try{
			productRepository.deleteById(id);
			return "Deleted";
		}catch(Exception e) {
			return "Failed";
		}
	}

	// Buying of product
	@Transactional
	public Double buyProduct(Integer id, Integer reqQuantity) {
		try{
			Optional<Product> optionalProduct = productRepository.findById(id);

			if(optionalProduct.isPresent()) {

				Product product = optionalProduct.get();
				Integer availableQuantity = product.getQuantity();
				if(availableQuantity > reqQuantity) {
					product.setQuantity(availableQuantity - reqQuantity);
					productRepository.save(product);
				}
				else {
					System.out.println("Throwing product out of stock exception");
					throw new Exception("Product out of stock");
				}
			}
			else {
				System.out.println("Product not present in inventory exception");
				throw new Exception("Product not present");
			}

			return 100.0;
		}catch(Exception e) {
			return 0.0;
		}
	}


	@Transactional
	public Double addInventory(Integer id, Integer quantity) {
		try{
			Optional<Product> optionalProduct = productRepository.findById(id);

			if(optionalProduct.isPresent()) {
				Product product = optionalProduct.get();
				Integer availableQuantity = product.getQuantity();
				product.setQuantity(availableQuantity + quantity);
				productRepository.save(product);
			}
			else {
				System.out.println("Product not present in inventory exception");
				throw new Exception("Product not present");
			}
			return 100.0;
		}catch(Exception e) {
			return 0.0;
		}
	}
}

