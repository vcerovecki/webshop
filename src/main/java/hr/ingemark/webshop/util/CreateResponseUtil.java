package hr.ingemark.webshop.util;

import java.util.List;

import hr.ingemark.webshop.model.WebShopEntity;
import hr.ingemark.webshop.model.common.CustomResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CreateResponseUtil {

	public static CustomResponse createResponse(int code, String pMessage, WebShopEntity pWebShopEntity) {
		return new CustomResponse(code, pMessage, pWebShopEntity);
	}
	
	public static CustomResponse createResponse(int code, String pMessage) {
		return new CustomResponse(code, pMessage);
	}
	
	public static CustomResponse createResponse(int code, String pMessage, List entities) {
		return new CustomResponse(code, pMessage, entities);
	}
	
}
