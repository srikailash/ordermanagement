package com.backend.product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductService {

    @Autowired
	private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
	}

	// Add new product
	public String addProduct(Product product) throws Exception {
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

	// Get single product by Id
	public Double getPrice( Integer id) throws Exception{
		Optional<Product> product =  productRepository.findById(id);

		if(product.isPresent()) {
			return product.get().getPrice();
		}
		else {
			throw new Exception("Invalid product");
		}

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

	public Integer buyProduct(Integer id, Integer reqQuantity) throws Exception{

		Optional<Product> optionalProduct = productRepository.findById(id);
		if(optionalProduct.isPresent()) {

			Product product = optionalProduct.get();
			Integer availableQuantity = product.getQuantity();

			if(availableQuantity == 0) {
				throw new Exception("Product out of stock");
			}

			if(availableQuantity > reqQuantity) {
				product.setQuantity(availableQuantity - reqQuantity);
				productRepository.saveAndFlush(product);
				return reqQuantity;
			}
			else {
				//Partial fulfillment of the order
				product.setQuantity(0);
				productRepository.saveAndFlush(product);
				return availableQuantity;
			}

		}
		else {
			throw new Exception("Invalid product");
		}

	}

	public void addInventory(Integer id, Integer quantity) throws Exception {

		Optional<Product> optionalProduct = productRepository.findById(id);

		if(optionalProduct.isPresent()) {
			Product product = optionalProduct.get();
			Integer availableQuantity = product.getQuantity();
			product.setQuantity(availableQuantity + quantity);
			productRepository.saveAndFlush(product);
		}
		else {
			System.out.println("Product not present in inventory exception");
			throw new Exception("Product not present");
		}

	}

}
