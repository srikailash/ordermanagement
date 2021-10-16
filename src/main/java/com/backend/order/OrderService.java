package com.backend.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class OrderService {

	@Autowired
	private final OrderRepository orderRepository;

	@Autowired
    private OrderItemsRepository orderItemsRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
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

	public String placeOrder(Order order) {
		try {

			OrderItems orderItem1 = new OrderItems(1, 100L, 20, 200.0);
			OrderItems orderItem2 = new OrderItems(1, 100L, 20, 200.0);
			// Order order = new Order(123, 200.0);

			orderItemsRepository.save(orderItem1);
			orderItemsRepository.save(orderItem2);

			// orderRepository.save(order);
			return "saved";
		} catch(Exception e) {
			return "failed";
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
