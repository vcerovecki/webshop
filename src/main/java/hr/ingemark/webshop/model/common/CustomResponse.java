package hr.ingemark.webshop.model.common;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import hr.ingemark.webshop.model.WebShopEntity;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class CustomResponse {

	private MessageType messageType;
	private WebShopEntity entity;
	private List<WebShopEntity> entities;
	
	public CustomResponse(int code, String message) {
		super();
		MessageType tMessageType = new MessageType(code, message);
		this.messageType = tMessageType;
	}
	
	public CustomResponse(int code, String message, List<WebShopEntity> entities) {
		super();
		MessageType tMessageType = new MessageType(code, message);
		this.messageType = tMessageType;
		this.entities = entities;
	}

	public CustomResponse(int code, String message, WebShopEntity entity) {
		super();
		MessageType tMessageType = new MessageType(code, message);
		this.messageType = tMessageType;
		this.entity = entity;
	}
	
}
