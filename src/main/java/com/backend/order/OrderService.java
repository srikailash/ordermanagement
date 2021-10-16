package com.backend.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import javax.transaction.Transactional;

import com.backend.product.ProductService;
import com.backend.user.UserService;

@Service
public class OrderService {

	@Autowired
	private final OrderRepository orderRepository;

	@Autowired
    private OrderItemsRepository orderItemsRepository;

	@Autowired
	private final ProductService productService;

	@Autowired
	private final UserService userService;


    public OrderService(OrderRepository orderRepository, 
		OrderItemsRepository orderItemsRepository,
		ProductService productService, 
		UserService userService) {
        this.orderRepository = orderRepository;
		this.orderItemsRepository = orderItemsRepository;
		this.productService = productService;
		this.userService = userService;
	}

	public String addOrder(Order order) {		
		try {
			orderRepository.save(order);
			return "saved";
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return "failed";
		}
	}

	@Transactional
	public String placeOrder() {
		try {
			this.productService.buyProduct(6, 30);
			this.userService.makePurchase(6, 23.0);
			return "purchased with balance";
		} catch(Exception e) {
			return "failed with insufficient balance";
		}
	}

	// Update a order
	public String updateOrder(Integer id, Order order) {
		try {
			// order.setId(id);
			orderRepository.save(order);
			return "Updated";
		}catch(Exception e) {
			return "Failed";
		}
	}

	// Get all students
	public Iterable<Order> getAllOrders(){
		try {
			orderRepository.findAll();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return orderRepository.findAll();
	}

	// Get single student by Id
	public Optional<Order> getOrder(Integer id) {
		return orderRepository.findById(id);
	}

	// Delete a Student
	public String deleteOrder(Integer id) {
		try{
			orderRepository.deleteById(id);
			return "Deleted";
		}catch(Exception e) {
			return "Failed";
		}
	}

}
