package com.backend.user;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.internal.runners.statements.ExpectException;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javassist.NotFoundException;

import java.util.Date;
import java.util.Optional;

import com.backend.order.Order;
import com.backend.product.ProductService;
import com.backend.user.UserService;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


//TODO:: Junit doesn't help in testing @Retryable
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTests {

	private UserService userService;

	@Mock
	private UserRepository userRepository;

	@Before
	public void init() {
		userService = new UserService(userRepository);
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void testCompletePurchase() throws Exception {

		User user = new User("abc", 100.0);
		Optional<User> optionalUser = Optional.of(user);

		when(userRepository.findById(any())).thenReturn(optionalUser);

		assertEquals(true, userService.makePurchase(123, 4, 5, 10.0));
	}

	@Test
	public void testUnavailableBalance() throws Exception {

		expectedException.expect(NotFoundException.class);
		expectedException.expectMessage("Not enough balance");

		User user = new User("abc", 5.0);
		Optional<User> optionalUser = Optional.of(user);

		when(userRepository.findById(any())).thenReturn(optionalUser);

		userService.makePurchase(123, 4, 5, 10.0);
	}

	@Test
	public void testInvalidUser() throws Exception {

		expectedException.expect(NotFoundException.class);
		expectedException.expectMessage("User not found");

		when(userRepository.findById(any())).thenReturn(Optional.empty());
		userService.makePurchase(123, 4, 5, 10.0);
	}

}