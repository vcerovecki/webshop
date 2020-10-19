package hr.ingemark.webshop.service;

import hr.ingemark.webshop.dto.OrderDto;
import hr.ingemark.webshop.exception.CustomException;
import hr.ingemark.webshop.model.common.CustomResponse;

public interface OrderService extends WebShopService<OrderDto> {
	
	public CustomResponse finalizeOrder(Long pId) throws CustomException;
	
}
