package com.backend.product;

import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Optional;

import javax.persistence.OptimisticLockException;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.annotation.Backoff;

import org.springframework.transaction.annotation.Transactional;

import javassist.NotFoundException;

@Service
@Transactional
public class ProductService {

	private final ProductRepository productRepository;

	private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

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

	public Collection<Product> findProductsLike(String q, Integer limit, Integer offset) throws Exception{
		return productRepository.findProductsLike(q, limit, offset);
	}

	// Get single product by Id
	public Product findProductById( Integer id) throws Exception{
		Optional<Product> optionalProduct =  productRepository.findById(id);

		if(optionalProduct.isPresent()) {
			return optionalProduct.get();
		}
		else {
			throw new NotFoundException("Product not found");
		}

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

	@Retryable(maxAttempts=3,value=OptimisticLockException.class,backoff=@Backoff(delay = 2000))
	public Integer buyProduct(Integer orderId, Integer userId, Integer productId, Integer reqQuantity) throws Exception{

		String logMessage = "productService.buyProduct request for orderId : " + orderId.toString() + " productId : " + productId.toString() + " for quantity: " + reqQuantity.toString();
		logger.info(logMessage);

		Optional<Product> optionalProduct = productRepository.findById(productId);
		if(optionalProduct.isPresent()) {

			Product product = optionalProduct.get();
			Integer availableQuantity = product.getQuantity();

			if(availableQuantity > reqQuantity) {
				product.setQuantity(availableQuantity - reqQuantity);
				productRepository.saveAndFlush(product);
				return reqQuantity;
			}
			else {
				throw new NotFoundException("Requested quantity is not available");
			}
		}
		else {
			throw new NotFoundException("Product not found");
		}

	}

	@Retryable(maxAttempts=3,value=OptimisticLockException.class,backoff=@Backoff(delay = 2000))
	public void addInventory(Integer id, Integer quantity) throws Exception {

		Optional<Product> optionalProduct = productRepository.findById(id);

		if(optionalProduct.isPresent()) {
			Product product = optionalProduct.get();
			Integer availableQuantity = product.getQuantity();
			product.setQuantity(availableQuantity + quantity);
			productRepository.saveAndFlush(product);
		}
		else {
			throw new NotFoundException("Product not found");
		}

	}

}

