package hr.ingemark.webshop.dto;

import java.util.List;

import lombok.Data;

@Data
public class OrderDto {

	private Long id;
	private Long customerId;
	private List<OrderItemDto> items;
	private String status;
	private Double totalPriceHrk;
	private Double totalPriceEur;
	
}
