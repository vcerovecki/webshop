package hr.ingemark.webshop.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.ingemark.webshop.client.HnbRestClient;
import hr.ingemark.webshop.dto.OrderDto;
import hr.ingemark.webshop.dto.OrderItemDto;
import hr.ingemark.webshop.enums.StatusEnum;
import hr.ingemark.webshop.exception.CustomException;
import hr.ingemark.webshop.model.Customer;
import hr.ingemark.webshop.model.Order;
import hr.ingemark.webshop.model.OrderItem;
import hr.ingemark.webshop.model.Product;
import hr.ingemark.webshop.model.common.CustomResponse;
import hr.ingemark.webshop.model.common.ExchangeRate;
import hr.ingemark.webshop.repository.CustomerRepository;
import hr.ingemark.webshop.repository.OrderItemRepository;
import hr.ingemark.webshop.repository.OrderRepository;
import hr.ingemark.webshop.repository.ProductRepository;
import hr.ingemark.webshop.service.OrderService;
import hr.ingemark.webshop.util.CreateResponseUtil;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository iOrderRepository;

	@Autowired
	private CustomerRepository iCustomerRepository;

	@Autowired
	private ProductRepository iProductRepostiory;

	@Autowired
	private OrderItemRepository iOrderItemRepository;

	@Autowired
	private HnbRestClient iHnbRestClient;

	@Override
	public CustomResponse save(OrderDto pOrderDto) throws CustomException {
		Optional<Customer> tCustomerData = iCustomerRepository.findById(pOrderDto.getCustomerId());
		Order.OrderBuilder tOrderBuilder = Order.builder();
		if (tCustomerData.isPresent()) {
			tOrderBuilder.customer(tCustomerData.get());
		} else {
			throw new CustomException(101, "Customer (ID = " + pOrderDto.getCustomerId() + ") doesn't exist.");
		}
		Order tOrder = tOrderBuilder.id(pOrderDto.getId()).status(StatusEnum.DRAFT).build();
		Order tSavedOrder = iOrderRepository.save(tOrder);
		return CreateResponseUtil.createResponse(203, "Order successfully saved.", tSavedOrder);
	}

	@Override
	public CustomResponse getAll() {
		List<Order> tOrders = iOrderRepository.findAll();
		return CreateResponseUtil.createResponse(202, "Orders successfully fetched.", tOrders);
	}

	@Override
	public CustomResponse getById(Long pId) throws CustomException {
		Optional<Order> tOrderData = iOrderRepository.findById(pId);
		if (tOrderData.isPresent()) {
			return CreateResponseUtil.createResponse(202, "Order successfully fetched.", tOrderData.get());
		}
		throw new CustomException(101, "Order (ID = " + pId + ") doesn't exist.");
	}

	@Override
	public CustomResponse update(Long pId, OrderDto pOrderDto) throws CustomException {
		Optional<Order> tOrderData = iOrderRepository.findById(pId);
		if (tOrderData.isPresent()) {
			Order tNewOrder = tOrderData.get();
			if(tNewOrder.getStatus().equals(StatusEnum.SUBMITTED)) {
				throw new CustomException(500, "Order (ID = " + tNewOrder.getId() + ") is already submitted!");
			}
			Optional<Customer> tCustomerData = iCustomerRepository.findById(pOrderDto.getCustomerId());
			if (tCustomerData.isPresent()) {
				tNewOrder.setCustomer(tCustomerData.get());
			} else {
				throw new CustomException(13, "Customer (ID = " + pOrderDto.getCustomerId() + ") doesn't exist.");
			}
			List<OrderItem> tOrderItems = new ArrayList<OrderItem>();
			if (pOrderDto.getItems() != null) {
				tOrderItems = createOrderItems(pOrderDto, tNewOrder);
			}
			if (!tOrderItems.isEmpty()) {
				tNewOrder.setOrderItems(tOrderItems);
			}
			iOrderRepository.save(tNewOrder);
			return CreateResponseUtil.createResponse(21, "Order successfully updated.", tNewOrder);
		} else {
			throw new CustomException(101, "Order (ID = " + pId + ") doesn't exist.");
		}
	}

	private List<OrderItem> createOrderItems(OrderDto pOrderDto, Order pOrder) throws CustomException {
		List<OrderItem> tOrderItems = new ArrayList<OrderItem>();
		for (OrderItemDto tOrderItemDto : pOrderDto.getItems()) {
			if (checkProduct(tOrderItemDto.getProduct())) {
				OrderItem tOrderItem = new OrderItem();
				Order tOrder = new Order();
				tOrder.setId(pOrder.getId());
				Product tProduct = new Product();
				tProduct.setId(tOrderItemDto.getProduct());
				tOrderItem.setOrder(tOrder);
				tOrderItem.setProduct(tProduct);
				if (pOrder.getOrderItems().contains(tOrderItem)) {
					int tExistingIndex = pOrder.getOrderItems().indexOf(tOrderItem);
					OrderItem tExisting = pOrder.getOrderItems().get(tExistingIndex);
					tExisting.setQuantity(tOrderItemDto.getQuantity());
					iOrderItemRepository.save(tExisting);
				} else {
					tOrderItems.add(new OrderItem(tOrder, tProduct, tOrderItemDto.getQuantity()));
				}
			}
		}
		return tOrderItems;
	}

	private boolean checkProduct(Long product) {
		return iProductRepostiory.existsById(product);
	}

	@Override
	public CustomResponse delete(Long pId) throws CustomException {
		Optional<Order> tOrderData = iOrderRepository.findById(pId);
		if (tOrderData.isPresent()) {
			iOrderRepository.deleteById(pId);
			return CreateResponseUtil.createResponse(201, "Order successfully deleted!");
		} else {
			throw new CustomException(101, "Order (ID = " + pId + ") doesn't exist.");
		}
	}

	@Override
	public CustomResponse finalizeOrder(Long pId) throws CustomException {
		Optional<Order> tOrderData = iOrderRepository.findById(pId);
		if (tOrderData.isPresent()) {
			Order tOrder = tOrderData.get();
			Double tTotalHrk = 0.0;
			Double tTotalEur = 0.0;
			for (OrderItem tOrderItem : tOrder.getOrderItems()) {
				tTotalHrk += iProductRepostiory.findById(tOrderItem.getProduct().getId()).get().getPriceHrk()
						* tOrderItem.getQuantity();
			}
			tOrder.setTotalPriceHrk(tTotalHrk);
			tTotalEur = claculateTotalEur(tTotalHrk);
			tOrder.setTotalPriceEur(tTotalEur);
			tOrder.setStatus(StatusEnum.SUBMITTED);
			iOrderRepository.save(tOrder);
			return CreateResponseUtil.createResponse(301, "Order is finalized.", tOrder);
		}
		throw new CustomException(101, "Order (ID = " + pId + ") doesn't exist.");
	}

	private Double claculateTotalEur(Double pTotalHrk) {
		ExchangeRate[] tExchangeRate = iHnbRestClient.fetchEurExchangeRate();
		return pTotalHrk * tExchangeRate[0].getMiddleExchange();

	}

}
