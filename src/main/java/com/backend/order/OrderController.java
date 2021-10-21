//Controller is the API layer
package com.backend.order;

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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.nio.file.AccessDeniedException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.backend.ErrorResponse;

// Code optimizations
// 1. Exception Handler reduces the number of try-catch blocks by handling all exceptions before responding
// 2. All exception based business logic can be handled at a single place

@RestController
@RequestMapping(path="api/v1/orders")
public class OrderController {

    private final OrderService orderService;
    
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

	//Place a new order
	@PostMapping(path="/place")
	public ResponseEntity<Object> placeNewOrder (
		@RequestBody com.fasterxml.jackson.databind.JsonNode payload, 
		@RequestHeader Integer userId) throws Exception {
		
		if(payload.get("userId") == null || payload.get("quantity") == null || payload.get("product") == null) {
			throw new HttpRequestMethodNotSupportedException("userId or product or quantity missing in body");
		}

		if(userId != payload.get("userId").intValue()) {
			throw new AccessDeniedException("Unauthorized access");
		}		

		Order order = orderService.placeOrder(
			payload.get("userId").intValue(),
			payload.get("quantity").intValue(), 
			payload.get("product").intValue()
		);

		Map<String, Object> res = new HashMap<String, Object>();
		res.put("id", order.getId());
		res.put("status", order.getStatus());
		res.put("code", 200);

		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	//Get order of the user with userId
	@GetMapping(path="/{id}")
	public ResponseEntity<Object> getOrderByIdAndUserId(
		@PathVariable(name = "id") Integer orderId, 
		@RequestHeader Integer userId) throws Exception {

			Collection<Order> order = orderService.getOrderByOrderIdAndUserId(orderId, userId);
			return new ResponseEntity<>(order, HttpStatus.OK);

	}

	//Get orders for a user
	@GetMapping(path="/user")
	public ResponseEntity<Object> getOrdersByUserId(
		@RequestParam(value = "userId", required = true) Integer id,
		@RequestParam(value = "limit", required = true) Integer limit,
		@RequestParam(value = "offset", required = true) Integer offset,
		@RequestHeader Integer userId) throws Exception {

			if(userId != id) {
				throw new AccessDeniedException("Unauthorized access");
			}

			if(limit < 0 || limit > 100) {
				throw new HttpRequestMethodNotSupportedException("Limit needs to be between 0 and 100");
			}

			if(offset < 0 || offset > 100) {
				throw new HttpRequestMethodNotSupportedException("Offset needs to be between 0 and 100");
			}

			Collection<Order> order = orderService.getOrdersByUserId(userId, limit, offset);
			return new ResponseEntity<>(order, HttpStatus.OK);

	}

	//Get orders for a user
	@GetMapping(path="/admin/product")
	public ResponseEntity<Object> getOrdersForProductId(
		@RequestParam(value = "productId", required = true) Integer productId,
		@RequestParam(value = "limit", required = true) Integer limit,
		@RequestParam(value = "offset", required = true) Integer offset) throws Exception {

			if(limit < 0 || limit > 100) {
				throw new HttpRequestMethodNotSupportedException("Limit needs to be between 0 and 100");
			}

			if(offset < 0 || offset > 100) {
				throw new HttpRequestMethodNotSupportedException("Offset needs to be between 0 and 100");
			}

			Collection<Order> order = orderService.getOrdersForProductId(productId, limit, offset);
			return new ResponseEntity<>(order, HttpStatus.OK);

	}

	@PostMapping(path="/admin/update/{id}")
	public @ResponseBody String updateOrder(@PathVariable(name = "id") Integer id, @RequestBody 
        Order order) throws Exception {
        return orderService.updateOrder(id, order);
	}

	//Returns a collection of Orders
	@DeleteMapping(path="/admin/product/{id}")
	public @ResponseBody String getAllOrdersForAProductId(@PathVariable(name = "id") Integer productId) throws Exception {
		// return studentService.deleteStudent(id);
        return orderService.deleteOrder(productId);
	}	

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> notFound(Exception ex){
		System.out.println(ex.getClass());
		ErrorResponse error = new ErrorResponse(ex);
		return new ResponseEntity<ErrorResponse>(
			new ErrorResponse(ex) , error.getCode());
    }

}
