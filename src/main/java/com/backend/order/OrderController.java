//Controller is the API layer
package com.backend.order;

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
@RequestMapping(path="api/v1/order")
public class OrderController {

    @Autowired
    private final OrderService orderService;
    
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

	// Add new Order
	@PostMapping(path="/add")
	public @ResponseBody String addNewOrder (@RequestBody Order order) {
		return orderService.addOrder(order);
	}

	// Add new Order
	@PostMapping(path="/place")
	public @ResponseBody String placeNewOrder (@RequestBody Order order) {
		return orderService.placeOrder(order);
	}	

	// Get all Orders
	@GetMapping(path="/all")
	public @ResponseBody Iterable<Order> getAllOrders() {
        return orderService.getAllOrders();
	}

	// Get single Order by Id
	@GetMapping(path="/{id}")
	public @ResponseBody Optional<Order> getOrderById(@PathVariable(name = "id") Integer id) {
		return orderService.getOrder(id);
	}

	// Update a Order
	@PostMapping(path="/update/{id}")
	public @ResponseBody String updateOrder(@PathVariable(name = "id") Integer id, @RequestBody 
        Order order) {
        return orderService.updateOrder(id, order);
	}

	// Delete a Order
	@DeleteMapping(path="/delete/{id}")
	public @ResponseBody String deleteOrder(@PathVariable(name = "id") Integer id) {
		// return studentService.deleteStudent(id);
        return orderService.deleteOrder(id);
	}
}
