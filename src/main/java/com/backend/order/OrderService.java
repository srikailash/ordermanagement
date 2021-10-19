package com.backend.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import javax.persistence.OptimisticLockException;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

import com.backend.product.ProductService;
import com.backend.user.UserService;

@Service
public class OrderService {

	@Autowired
	private final OrderRepository orderRepository;

	@Autowired
	private final ProductService productService;

	@Autowired
	private final UserService userService;

    public OrderService(OrderRepository orderRepository, 
		ProductService productService, 
		UserService userService) {
        this.orderRepository = orderRepository;
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
	@Retryable(maxAttempts=3,value=OptimisticLockException.class,backoff=@Backoff(delay = 2000))
	public String placeOrder(Integer userId, Integer requestedQuantity, Integer productId) throws Exception {

		//Let's make this step wise
		//Step-1 : Input validations
		//Step-2 : Initialize order - Creates new order request with status 'Received'
		//Step-3 : Calls Product Inventory service to lock the inventory
		//Step-4 : Calls user payment service to reduce balance
		//Step-5 : saves and flushes order table

		Order order = new Order(userId, requestedQuantity, productId, "Received");
		orderRepository.saveAndFlush(order);
		Integer placedQuantity = 0;
		Double productPrice = 0.0;

		try {

			placedQuantity = this.productService.buyProduct(productId, requestedQuantity);
			order.setPlacedQuantity(placedQuantity);
			if(placedQuantity == requestedQuantity) {
				order.setStatus("Requested quantity available and locked");
			}
			else {
				order.setStatus("Partial quantity available and locked");
			}
			orderRepository.saveAndFlush(order);

			productPrice =  this.productService.getPrice(productId);
			this.userService.makePurchase(userId, placedQuantity * productPrice);
			order.setTotalPrice(placedQuantity * productPrice);
			if(placedQuantity == requestedQuantity) {
				order.setStatus("Requested quantity placed");
			}
			else {
				order.setStatus("Partial quantity placed");
			}
			orderRepository.saveAndFlush(order);

			return order.getStatus();
		}
		catch(Exception e) {

			if(e.getMessage() == "Insufficient balance") {
				//TODO: Let the user know how many units they can purchase with their current balance
				//return back the inventory if there are problems changing balance
				//TODO: Have auditor to make sure all locked inventory is returned
				this.productService.addInventory(productId, placedQuantity);

				//set placed quantity to 0
				order.setPlacedQuantity(0);
			}

			order.setStatus(e.getMessage());
			orderRepository.saveAndFlush(order);

			return e.getMessage();
		}
	}

	// Update a order
	public String updateOrder(Integer id, Order order) {
		try {
			// order.setId(id);
			orderRepository.saveAndFlush(order);
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
