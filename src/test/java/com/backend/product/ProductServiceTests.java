package com.backend.product;

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

import java.util.Date;
import java.util.Optional;

import javax.persistence.OptimisticLockException;

import com.backend.product.ProductService;
import com.backend.user.UserService;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

//TODO:: Junit doesn't help in testing @Retryable
@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTests {

	private ProductService productService;

	@Mock
	private ProductRepository productRepository;

	@Mock
	private Product product;

	@Before
	public void init() {
		productService = new ProductService(productRepository);
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void testBuyAvailableProduct() throws Exception {

		//Product(name, availability, price)
		when(product.getQuantity()).thenReturn(100);
		Optional<Product> optionalProduct = Optional.of(product);

		when(productRepository.findById(any())).thenReturn(optionalProduct);

		assertEquals(12, productService.buyProduct(20, 20, 20, 12));
	}

	@Test
	public void testBuyUnAvailableProduct() throws Exception {

		expectedException.expect(NotFoundException.class);
		expectedException.expectMessage("Requested quantity is not available");

		when(product.getQuantity()).thenReturn(8);
		Optional<Product> optionalProduct = Optional.of(product);

		when(productRepository.findById(any())).thenReturn(optionalProduct);
		productService.buyProduct(20, 20, 20, 12);

	}

	@Test
	public void testBuyInvalidProduct() throws Exception {

		expectedException.expect(NotFoundException.class);
		expectedException.expectMessage("Product not found");

		when(productRepository.findById(any())).thenReturn(Optional.empty());
		productService.buyProduct(20, 20, 20, 12);
	}

	@Test
	public void testOptimisticLockException() throws Exception {
		expectedException.expect(OptimisticLockException.class);

		when(product.getQuantity()).thenReturn(100);
		Optional<Product> optionalProduct = Optional.of(product);

		when(productRepository.findById(any())).thenReturn(optionalProduct);
		when(productRepository.saveAndFlush(any())).thenThrow(new OptimisticLockException());
		productService.buyProduct(20, 20, 20, 12);
	}

}
