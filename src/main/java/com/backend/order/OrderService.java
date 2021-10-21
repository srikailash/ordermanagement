package com.backend.order;

import org.springframework.stereotype.Service;

import java.util.Collection;

import com.backend.product.ProductService;
import com.backend.user.UserService;

@Service
public class OrderService {

	private final OrderRepository orderRepository;

	private final ProductService productService;

	private final UserService userService;

    public OrderService(
		OrderRepository orderRepository, 
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
			return "failed";
		}
	}

	public Order placeOrder(Integer userId, Integer requestedQuantity, Integer productId) throws Exception {

		//Step-1 : begin Input validations
		//Validations have been done in the controller
		//Step-1 : end

		//step-2 begin Initialize order - Creates new order request with status 'Received'
		Order order = new Order(userId, requestedQuantity, productId, "Received");
		orderRepository.saveAndFlush(order);
		Double unitPrice = 0.0;
		//step-2 end

		try {

			//step-3 begin : Calls Product Inventory service to lock the inventory
			this.productService.buyProduct( order.getId(), userId, productId, requestedQuantity);
			order.setStatus("Requested quantity locked");
			orderRepository.saveAndFlush(order);

			unitPrice =  this.productService.getPrice(productId);
			//step-3 end

			//step-4 begin Calls user payment service to reduce balance
			this.userService.makePurchase(order.getId(), userId, productId, requestedQuantity * unitPrice);
			order.setTotalPrice(requestedQuantity * unitPrice);
			order.setQuantity(requestedQuantity);			
			order.setStatus("Order completed");
			orderRepository.saveAndFlush(order);
			//step-4 end

			return order;
		}
		catch(Exception e) {

			if(order.getStatus() == "Requested quantity locked") {
				//Rollback begin : Rollback locked inventory back to Product
				this.productService.addInventory(productId, requestedQuantity);
				order.setStatus("Insufficient balance");
				//Rollback end
			}

			order.setStatus(e.getMessage());
			orderRepository.saveAndFlush(order);

			throw e;
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

	public Collection<Order> getOrderByOrderIdAndUserId(Integer orderId, Integer userId) throws Exception {
		return orderRepository.findOrderFromUser(orderId, userId);
	}

	public Collection<Order> getOrdersByUserId(Integer userId, Integer limit, Integer offset) throws Exception {
		return orderRepository.findOrdersFromUser(userId, limit, offset);
	}

	public Collection<Order> getOrdersForProductId(Integer productId, Integer limit, Integer offset) throws Exception {
		return orderRepository.findOrdersForProduct(productId, limit, offset);
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
