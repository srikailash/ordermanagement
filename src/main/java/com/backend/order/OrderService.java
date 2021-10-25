package com.backend.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

import com.backend.product.ProductService;
import com.backend.user.UserService;

@Service
public class OrderService {

	private final OrderRepository orderRepository;

	private final ProductService productService;

	private final UserService userService;

	private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    public OrderService(
		OrderRepository orderRepository, 
		ProductService productService, 
		UserService userService) {
        this.orderRepository = orderRepository;
		this.productService = productService;
		this.userService = userService;
	}

	public String addOrder(Order order) throws Exception {		
		orderRepository.save(order);
		return "";
	}

	//A - All or none transactions happen - YES - Inventory is rolled back when no balance is reduced
	//C - One valid state to another  - YES - With read locks, there are no lost updates
	//I - Concurrent transactions act as if they are serial - NO, one transaction can make another transaction fail/rollback
	//D - NO, Mysql is a single point of failure for current system 
	//TODO: Retry for DataAccessException
	//Reason for saving and flush changes is to make sure it's safe against failures
	public Order placeOrder(Integer userId, Integer requestedQuantity, Integer productId) throws Exception {

		//Step-1 : begin Input validations
		//Validations have been done in the controller
		//Step-1 : end

		Boolean inventoryLocked = false;
		Integer orderId = -1;
		Order order = new Order(userId, requestedQuantity, productId, "Received");	//This has to be improved to use DAOs so it can be better tested

		try {

			//step-2 begin : Creates new order entry with status 'Received'
			// offloading id generation to mysql by persisting order to DB
			orderRepository.saveAndFlush(order);			
			orderId = order.getId();
			Double unitPrice = 0.0;
			//step-2 end

			//step-3 begin : Calls Product Inventory service to lock the inventory
			this.productService.buyProduct( order.getId(), userId, productId, requestedQuantity);
			inventoryLocked = true;
			order.setStatus("Requested quantity locked");
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

			String logMessage = "orderService.placeOrder " + orderId + " productId : " + productId.toString() + " for quantity: " + requestedQuantity.toString() + " " + e.getMessage();
			logger.info(logMessage);

			if(inventoryLocked == true) {

				//Rollback begin : Rollback locked inventory back to Product
				try {
					this.productService.addInventory(productId, requestedQuantity);
				}
				catch(Exception rollbackException) {
					//TODO: Following log message has to be carefully monitored to not loose inventory
					logMessage = "orderService.addInventory request for orderId : " + orderId + " productId : " + productId.toString() + " for quantity: " + requestedQuantity.toString();
					logger.info(logMessage);
				}
				//Rollback end

			}

			try {
				order.setStatus(e.getMessage());
				orderRepository.saveAndFlush(order);
			}
			catch(Exception saveException) {
				logMessage = "orderService.saveAndFlush order failed for orderId : " + orderId + " productId : " + productId.toString() + " for quantity: " + requestedQuantity.toString();
				logger.info(logMessage);
			}

			throw e;
		}

	}

	// Update a order
	public String updateOrder(Integer id, Order order) throws Exception {
			// order.setId(id);
		orderRepository.saveAndFlush(order);
		return "Updated";
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
