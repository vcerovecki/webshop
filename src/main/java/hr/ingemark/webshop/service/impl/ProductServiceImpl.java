package hr.ingemark.webshop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.ingemark.webshop.exception.CustomException;
import hr.ingemark.webshop.model.Product;
import hr.ingemark.webshop.model.common.CustomResponse;
import hr.ingemark.webshop.repository.ProductRepository;
import hr.ingemark.webshop.service.ProductService;
import hr.ingemark.webshop.util.CreateResponseUtil;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository iProductRepository;

	@Override
	public CustomResponse save(Product entity) throws CustomException {
		if(iProductRepository.existsByCode(entity.getCode())) {
			throw new CustomException(102, "Product (code = " + entity.getCode() + ") already exists.");
		}
		Product tProduct = iProductRepository.save(entity);
		return CreateResponseUtil.createResponse(203, "Product successfully created.", tProduct);
	}

	@Override
	public CustomResponse update(Long pId, Product entity) throws CustomException {
		Optional<Product> tProductData = iProductRepository.findByCode(entity.getCode());
		if(tProductData.isPresent()) {
			Product tNewProduct = tProductData.get();
			if(tNewProduct.getId() != pId) {
				throw new CustomException(102, "Product (code = " + entity.getCode() + ") already exists.");
			}
			tNewProduct.setCode(entity.getCode());
			tNewProduct.setDescription(entity.getDescription());
			tNewProduct.setName(entity.getName());
			tNewProduct.setPriceHrk(entity.getPriceHrk());
			tNewProduct.setAvailable(entity.isAvailable());
			Product tUpdatedProduct = iProductRepository.save(tNewProduct);
			return CreateResponseUtil.createResponse(200, "Product successfully updated.", tUpdatedProduct);
		} else {
			throw new CustomException(101, "Product (ID = " + pId + ") doesn't exist.");
		}
	}

	@Override
	public CustomResponse delete(Long pId) throws CustomException {
		Optional<Product> tProductData = iProductRepository.findById(pId);
		if(tProductData.isPresent()) {
			iProductRepository.deleteById(pId);
			return CreateResponseUtil.createResponse(201, "Product successfully deleted!");
		} else {
			throw new CustomException(101, "Product (ID = " + pId + ") doesn't exist.");
		}
	}

	@Override
	public CustomResponse getAll() {
		List<Product> tProducts = iProductRepository.findAll();
		return CreateResponseUtil.createResponse(202, "Products successfully fetched.", tProducts);
	}

	@Override
	public CustomResponse getById(Long pId) throws CustomException {
		Optional<Product> tProductData = iProductRepository.findById(pId);
		if(tProductData.isPresent()) {
			return CreateResponseUtil.createResponse(202, "Product successfully fetched.", tProductData.get());
		}
		throw new CustomException(101, "Product (ID = " + pId + ") doesn't exist.");
	}

}
