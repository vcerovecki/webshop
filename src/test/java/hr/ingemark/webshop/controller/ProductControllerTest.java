package hr.ingemark.webshop.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import hr.ingemark.webshop.WebshopApplication;
import hr.ingemark.webshop.dto.OrderDto;
import hr.ingemark.webshop.dto.OrderItemDto;
import hr.ingemark.webshop.exception.CustomException;
import hr.ingemark.webshop.model.Customer;
import hr.ingemark.webshop.model.Order;
import hr.ingemark.webshop.model.Product;
import hr.ingemark.webshop.model.common.CustomResponse;
import hr.ingemark.webshop.service.CustomerService;
import hr.ingemark.webshop.service.OrderService;
import hr.ingemark.webshop.service.ProductService;

@SpringBootTest(classes = WebshopApplication.class)
@ActiveProfiles("test")
class ProductControllerTest {
	
	@Autowired
	private ProductService iProductService;
	
	@Autowired
	private OrderService iOrderService;
	
	@Autowired
	private CustomerService iCustomerService;

	@Test
	void whenInsert_thenReturnProduct() throws CustomException {
		byte[] tByteArr = new byte[10];
		new Random().nextBytes(tByteArr);
		String tRandomString = new String(tByteArr, Charset.forName("UTF-8"));
		Product tProduct = Product.builder()
			.isAvailable(true)
			.code(tRandomString)
			.name(tRandomString)
			.description(tRandomString)
			.priceHrk(5.0)
			.build();
		CustomResponse tSaveResponse = iProductService.save(tProduct);
		
		CustomResponse tFetcheResponse = iProductService.getById(((Product)tSaveResponse.getEntity()).getId());
		
		assertTrue(tSaveResponse.getMessageType().getCode() == 203, "Proct is not inserted");
		assertTrue(tFetcheResponse.getMessageType().getCode() == 202, "Product is not inserted!");
	}
	
	@Test
	void whenSaveWithExistingCode_thenThrowException() throws CustomException {
		byte[] tByteArr = new byte[10];
		new Random().nextBytes(tByteArr);
		String tRandomString = new String(tByteArr, Charset.forName("UTF-8"));
		Product tProduct = Product.builder()
			.isAvailable(true)
			.code(tRandomString)
			.name(tRandomString)
			.description(tRandomString)
			.priceHrk(5.0)
			.build();
		iProductService.save(tProduct);
		
		Throwable exception = assertThrows(CustomException.class, () -> iProductService.save(tProduct));
	    assertEquals(102, ((CustomException)exception).getCode());
	}
	
	@Test
	void whenUpdateWithExistingCode_thenThrowException() throws CustomException {
		byte[] tByteArr = new byte[10];
		new Random().nextBytes(tByteArr);
		String tRandomString = new String(tByteArr, Charset.forName("UTF-8"));
		Product tProduct = Product.builder()
			.isAvailable(true)
			.code(tRandomString)
			.name(tRandomString)
			.description(tRandomString)
			.priceHrk(5.0)
			.build();
		iProductService.save(tProduct);
		String tFirstProductCode = tProduct.getCode();
		
		new Random().nextBytes(tByteArr);
		tRandomString = new String(tByteArr, Charset.forName("UTF-8"));
		tProduct.setCode(tRandomString);
		tProduct.setId(null);
		CustomResponse tResponse = iProductService.save(tProduct);
		
		tProduct.setCode(tFirstProductCode);
		
		
		Long tProductId = ((Product)tResponse.getEntity()).getId();
		
		Throwable exception = assertThrows(CustomException.class, () -> iProductService.update(tProductId, tProduct));
	    assertEquals(102, ((CustomException)exception).getCode());
	}
	
	@Test
	void whenUnavailableProductAddedToOrder_thenThrowException() throws CustomException {
		byte[] tByteArr = new byte[10];
		new Random().nextBytes(tByteArr);
		String tRandomString = new String(tByteArr, Charset.forName("UTF-8"));
		Product tProduct = Product.builder()
			.isAvailable(false)
			.code(tRandomString)
			.name(tRandomString)
			.description(tRandomString)
			.priceHrk(5.0)
			.build();
		CustomResponse tResponse = iProductService.save(tProduct);
		Long tProductId = ((Product)tResponse.getEntity()).getId();
		
		Customer tCustomer = Customer.builder()
				.firstName("tester")
				.lastName("tester")
				.email("tester")
				.build();
		
		CustomResponse tResponseCustomer = iCustomerService.save(tCustomer);
		Long tCustomerId = ((Customer)tResponseCustomer.getEntity()).getId();
		
		OrderDto tOrderDto = new OrderDto();
		tOrderDto.setCustomerId(tCustomerId);
		tOrderDto.setStatus("DRAFT");
		CustomResponse tResponseOrder = iOrderService.save(tOrderDto);
		Long tOrderId = ((Order)tResponseOrder.getEntity()).getId();
		OrderItemDto tOrderItemDto = new OrderItemDto();
		tOrderItemDto.setProduct(tProductId);
		tOrderItemDto.setQuantity(5.0);
		List<OrderItemDto> tItems = new ArrayList<OrderItemDto>();
		tItems.add(tOrderItemDto);
		tOrderDto.setItems(tItems);
		
		Throwable exception = assertThrows(CustomException.class, () -> iOrderService.update(tOrderId, tOrderDto));
	    assertEquals(103, ((CustomException)exception).getCode());
	}

}
