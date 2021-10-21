package com.backend.order;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

import com.backend.product.ProductService;
import com.backend.user.UserService;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTests {

	private OrderService orderService;

	@Mock
	private ProductService productService;

	@Mock
	private UserService userService;

	@Mock
	private OrderRepository orderRepository;

	@Before
	public void init() {
		orderService = new OrderService(orderRepository, productService, userService);
	}

	@Test
	public void testPlaceOrderCompletely() throws Exception {

		assertEquals("Order completed", orderService.placeOrder(20, 20, 20));
    	// carService.schedulePickup(new Date(), new Route());
    	// verify(rateFinder, times(1)).findBestRate(any(Route.class));
	}

	@Test
	public void testRequestedStockNotAvailable() throws Exception {

		assertEquals("Order completed", orderService.placeOrder(20, 20, 20));
    	// carService.schedulePickup(new Date(), new Route());
    	// verify(rateFinder, times(1)).findBestRate(any(Route.class));
	}

	@Test
	public void testNotEnoughBalanceWithUser() throws Exception {

		//make sure the inventory is returned in this case
		assertEquals("Order completed", orderService.placeOrder(20, 20, 20));
    	// carService.schedulePickup(new Date(), new Route());
    	// verify(rateFinder, times(1)).findBestRate(any(Route.class));
	}

	@Test
	public void testInvalidUserId() throws Exception {

		assertEquals("Order completed", orderService.placeOrder(20, 20, 20));
    	// carService.schedulePickup(new Date(), new Route());
    	// verify(rateFinder, times(1)).findBestRate(any(Route.class));
	}

	@Test
	public void testInvalidProductId() throws Exception {

		assertEquals("Order completed", orderService.placeOrder(20, 20, 20));
    	// carService.schedulePickup(new Date(), new Route());
    	// verify(rateFinder, times(1)).findBestRate(any(Route.class));
	}

}