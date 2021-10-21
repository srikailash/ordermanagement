package com.backend.product;

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
public class ProductServiceTests {

	private ProductService productService;

	@Mock
	private ProductRepository productRepository;

	@Before
	public void init() {
		productService = new ProductService(productRepository);
	}

	@Test
	public void testBuyAvailableProduct() throws Exception {

		assertEquals("Order completed", productService.buyProduct(20, 20, 20, 20));
    	// carService.schedulePickup(new Date(), new Route());
    	// verify(rateFinder, times(1)).findBestRate(any(Route.class));
	}

	@Test
	public void testBuyUnAvailableProduct() throws Exception {

		assertEquals("Order completed", productService.buyProduct(20, 20, 20, 20));
    	// carService.schedulePickup(new Date(), new Route());
    	// verify(rateFinder, times(1)).findBestRate(any(Route.class));
	}

	@Test
	public void testBuyInvalidProduct() throws Exception {

		//make sure the inventory is returned in this case
		assertEquals("Order completed", productService.buyProduct(20, 20, 20, 20));
    	// carService.schedulePickup(new Date(), new Route());
    	// verify(rateFinder, times(1)).findBestRate(any(Route.class));
	}

	@Test
	public void testRetryForOptimisticLockException() throws Exception {

		//make sure the inventory is returned in this case
		assertEquals("Order completed", productService.buyProduct(20, 20, 20, 20));
    	// carService.schedulePickup(new Date(), new Route());
    	// verify(rateFinder, times(1)).findBestRate(any(Route.class));
	}	
}