package hr.ingemark.webshop.service;

import hr.ingemark.webshop.exception.CustomException;
import hr.ingemark.webshop.model.common.CustomResponse;

public interface WebShopService<T> {

	public CustomResponse save(T entity) throws CustomException;
	
	public CustomResponse update(Long pId, T entity) throws CustomException;
	
	public CustomResponse delete(Long pId) throws CustomException;
	
	public CustomResponse getAll();
	
	public CustomResponse getById(Long pId) throws CustomException;
	
}
