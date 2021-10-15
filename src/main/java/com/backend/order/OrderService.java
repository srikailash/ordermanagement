package com.backend.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {

	@Autowired
	private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
	}
	
	// Add new student
	public String addOrder(Order order) {		
		try {
			orderRepository.save(order);
			return "saved";
		} catch(Exception e) {
			return "failed";
		}
	}

	// Update a order
	public String updateOrder(Long id, Order order) {
		try {
			order.setId(id);
			orderRepository.save(order);
			return "Updated";
		}catch(Exception e) {
			return "Failed";
		}
	}

	// Get all students
	public Iterable<Order> getAllOrders(){
		return orderRepository.findAll();
	}

	// Get single student by Id
	public Optional<Order> getOrder(Long id) {
		return orderRepository.findById(id);
	}

	// Delete a Student
	public String deleteOrder(Long id) {
		try{
			orderRepository.deleteById(id);
			return "Deleted";
		}catch(Exception e) {
			return "Failed";
		}
	}

}
