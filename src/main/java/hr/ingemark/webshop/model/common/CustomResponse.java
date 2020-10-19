package hr.ingemark.webshop.model.common;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import hr.ingemark.webshop.model.WebShopEntity;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class CustomResponse {

	private int code;
	private String message;
	private WebShopEntity entity;
	private List<WebShopEntity> entities;
	
	public CustomResponse(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	
	public CustomResponse(int code, String message, List<WebShopEntity> entities) {
		super();
		this.code = code;
		this.message = message;
		this.entities = entities;
	}

	public CustomResponse(int code, String message, WebShopEntity entity) {
		super();
		this.code = code;
		this.message = message;
		this.entity = entity;
	}
	
}
