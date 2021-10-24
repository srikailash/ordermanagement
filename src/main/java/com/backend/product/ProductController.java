//Controller is the API layer
package com.backend.product;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;
import java.util.Optional;

import com.backend.ErrorResponse;

@RestController
@RequestMapping(path="api/v1/products")
public class ProductController {

    private final ProductService productService;
    
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

	// Add new Product
	@PostMapping(path="admin/add")
	public @ResponseBody String addNewProduct (@RequestBody Product product) throws Exception {
		return productService.addProduct(product);
	}

	// Get all Products
	@GetMapping(path="/all")
	public @ResponseBody Iterable<Product> getAllProducts() throws Exception {
        return productService.getAllProducts();
	}

	//Get orders for a user
	@GetMapping(path="/search")
	public ResponseEntity<Object> getProductsLike(
		@RequestParam(value = "q", required = true) String q,
		@RequestParam(value = "limit", required = true) Integer limit,
		@RequestParam(value = "offset", required = true) Integer offset) throws Exception {

			
			if(!q.matches("[a-zA-Z]*")) {
				throw new HttpRequestMethodNotSupportedException("without a valid query string is");
			}

			if(limit < 0 || limit > 100) {
				throw new HttpRequestMethodNotSupportedException("without Limit between 0 and 100 is");
			}

			if(offset < 0 || offset > 100) {
				throw new HttpRequestMethodNotSupportedException("without Offset between 0 and 100 is");
			}

			Collection<Product> products = productService.findProductsLike(q, limit, offset);
			return new ResponseEntity<>(products, HttpStatus.OK);

	}
	
	// Get single Product by Id
	@GetMapping(path="/{id}")
	public ResponseEntity<Object> getProductById(@PathVariable(name = "id") Integer id) throws Exception {
		Product product = productService.findProductById(id);
		return new ResponseEntity<>(product, HttpStatus.OK);
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

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> notFound(Exception ex){
		System.out.println(ex.getClass());
		ErrorResponse error = new ErrorResponse(ex);
		return new ResponseEntity<ErrorResponse>(
			new ErrorResponse(ex) , error.getCode());
    }		
}
