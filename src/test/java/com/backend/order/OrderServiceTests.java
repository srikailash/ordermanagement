package com.backend.order;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.internal.runners.statements.ExpectException;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javassist.NotFoundException;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
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

	@Captor
    ArgumentCaptor<Integer> orderIdArg;
 
    @Captor
    ArgumentCaptor<Integer> userIdArg;

	@Captor
	ArgumentCaptor<Integer> productIdArg;

	@Captor
	ArgumentCaptor<Integer> reqQuantityArg;

	@Captor
	ArgumentCaptor<Integer> userIdToUserServiceArg;
	
	@Captor
	ArgumentCaptor<Integer> productIdToUserServiceArg;

	@Captor
	ArgumentCaptor<Double> priceArg;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Before
	public void init() {
		orderService = new OrderService(orderRepository, productService, userService);
	}

	@Test
	public void testPlaceOrderCompletely() throws Exception {

		String orderResponse = orderService.placeOrder(123, 456, 789).getStatus();
        Mockito.verify(productService, times(1))
		.buyProduct(
			orderIdArg.capture(),
			userIdArg.capture(), 
			productIdArg.capture(), 
			reqQuantityArg.capture()
		);
	
		assertEquals(userIdArg.getValue(), 123);
		assertEquals(reqQuantityArg.getValue(), 456);
		assertEquals(productIdArg.getValue(), 789);

        Mockito.verify(userService, times(1))
		.makePurchase(
			orderIdArg.capture(),
			userIdToUserServiceArg.capture(),
			productIdToUserServiceArg.capture(),
			priceArg.capture()
		);

		assertEquals(userIdToUserServiceArg.getValue(), 123);
		assertEquals(productIdToUserServiceArg.getValue(), 789);

		assertEquals("Order completed", orderResponse);
	}

	@Test
	public void testRequestedStockNotAvailable() throws Exception {
		expectedException.expect(NotFoundException.class);
		expectedException.expectMessage("Requested quantity is not available");
		when(productService.buyProduct(any(), any(), any(), any())).thenThrow(new NotFoundException("Requested quantity is not available"));
		orderService.placeOrder(123, 456, 789).getStatus();
	}

	@Test
	public void testNotEnoughBalanceWithUser() throws Exception {
		expectedException.expect(NotFoundException.class);
		expectedException.expectMessage("Not enough balance");
		when(userService.makePurchase(any(), any(), any(), any())).thenThrow(new NotFoundException("Not enough balance"));
		orderService.placeOrder(123, 456, 789).getStatus();
	}

	@Test
	public void testReturnLockedInventoryForNotEnoughBalance() throws Exception {
		expectedException.expect(NotFoundException.class);
		expectedException.expectMessage("Not enough balance");

		when(userService.makePurchase(any(), any(), any(), any())).thenThrow(new NotFoundException("Not enough balance"));
		orderService.placeOrder(123, 456, 789).getStatus();

		Mockito.verify(productService, times(1))
		.addInventory(
			productIdArg.capture(),
			reqQuantityArg.capture()
		);
		assertEquals(reqQuantityArg.getValue(), 456);
		assertEquals(productIdArg.getValue(), 789);
	}
}
