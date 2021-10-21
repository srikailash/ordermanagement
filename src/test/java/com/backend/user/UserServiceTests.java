package com.backend.user;

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
public class UserServiceTests {

	private UserService userService;

	@Mock
	private UserRepository userRepository;

	@Before
	public void init() {
		userService = new UserService(userRepository);
	}

	@Test
	public void testCompletePurchase() throws Exception {

		assertEquals("Order completed", userService.makePurchase(20, 20, 20, 20.0));
    	// carService.schedulePickup(new Date(), new Route());
    	// verify(rateFinder, times(1)).findBestRate(any(Route.class));
	}

	@Test
	public void testUnavailableBalance() throws Exception {

		assertEquals("Order completed", userService.makePurchase(20, 20, 20, 20.0));
    	// carService.schedulePickup(new Date(), new Route());
    	// verify(rateFinder, times(1)).findBestRate(any(Route.class));
	}

	@Test
	public void testInvalidUser() throws Exception {

		//make sure the inventory is returned in this case
		assertEquals("Order completed", userService.makePurchase(20, 20, 20, 20.0));
    	// carService.schedulePickup(new Date(), new Route());
    	// verify(rateFinder, times(1)).findBestRate(any(Route.class));
	}

}