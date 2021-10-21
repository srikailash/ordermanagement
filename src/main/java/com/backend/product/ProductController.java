//Controller is the API layer
package com.backend.product;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@RestController
@RequestMapping(path="api/v1/product")
public class ProductController {

    @Autowired
    private final ProductService productService;
    
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

	// Add new Product
	@PostMapping(path="/add")
	public @ResponseBody String addNewProduct (@RequestBody Product product) throws Exception {
		return productService.addProduct(product);
	}

	// Get all Products
	@GetMapping(path="/all")
	public @ResponseBody Iterable<Product> getAllProducts() {
        return productService.getAllProducts();
	}

	// Get single Product by Id
	@GetMapping(path="/{id}")
	public @ResponseBody Optional<Product> getProductById(@PathVariable(name = "id") Integer id) {
		return productService.getProduct(id);
	}

	// Update a Product
	@PostMapping(path="/update/{id}")
	public @ResponseBody String updateProduct(@PathVariable(name = "id") Integer id, @RequestBody 
        Product Product) {
        return productService.updateProduct(id, Product);
	}

    @PostMapping(value="/stock/{id}")
    public void processStock(@PathVariable(name = "id") Integer id,
		@RequestBody com.fasterxml.jackson.databind.JsonNode payload) throws Exception {
		productService.addInventory(id, payload.get("quantity").intValue());
    }	
	
	// Delete a Product
	@DeleteMapping(path="/delete/{id}")
	public @ResponseBody String deleteProduct(@PathVariable(name = "id") Integer id) {
		// return studentService.deleteStudent(id);
        return productService.deleteProduct(id);
	}
}
