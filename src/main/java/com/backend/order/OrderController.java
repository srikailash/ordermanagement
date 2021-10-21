//Controller is the API layer
package com.backend.order;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.backend.ErrorResponse;

// Code optimizations
// 1. Exception Handler reduces the number of try-catch blocks by handling all exceptions before responding
// 2. All exception based business logic can be handled at a single place

@RestController
@RequestMapping(path="api/v1/orders")
public class OrderController {

    @Autowired
    private final OrderService orderService;
    
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

	//Place a new order
	@PostMapping(path="/place")
	public @ResponseBody String placeNewOrder (
		@RequestBody com.fasterxml.jackson.databind.JsonNode payload, 
		@RequestHeader Integer user_id) throws Exception {

		return orderService.placeOrder(
			user_id,
			payload.get("requested_quantity").intValue(), 
			payload.get("product").intValue()
		);
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
	@GetMapping(path="/user/{id}")
	public ResponseEntity<Object> getOrdersByUserId(
		@PathVariable(name = "id") Integer id,
		@RequestHeader Integer userId) throws Exception {

			if(userId != id) {
				throw new Exception("Unauthorized access");
			}

			Collection<Order> order = orderService.getOrdersByUserId(userId);
			return new ResponseEntity<>(order, HttpStatus.OK);

	}

	//Returns a collection of orders
	//Sorted by date
	@GetMapping(path="/admin/all")
	public @ResponseBody Iterable<Order> getAllOrders() {
        return orderService.getAllOrders();
	}

	@PostMapping(path="/admin/update/{id}")
	public @ResponseBody String updateOrder(@PathVariable(name = "id") Integer id, @RequestBody 
        Order order) throws Exception {
        return orderService.updateOrder(id, order);
	}

	@DeleteMapping(path="/admin/delete/{id}")
	public @ResponseBody String deleteOrder(@PathVariable(name = "id") Integer id) throws Exception {
		// return studentService.deleteStudent(id);
        return orderService.deleteOrder(id);
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
