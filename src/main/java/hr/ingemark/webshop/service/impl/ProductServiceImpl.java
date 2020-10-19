package hr.ingemark.webshop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public CustomResponse save(Product entity) {
		Product tProduct = iProductRepository.save(entity);
		return CreateResponseUtil.createResponse(30, "Product successfully created.", tProduct);
	}

	@Override
	public CustomResponse update(Long pId, Product entity) {
		Optional<Product> tProductData = iProductRepository.findById(pId);
		if(tProductData.isPresent()) {
			Product tNewProduct = tProductData.get();
			tNewProduct.setCode(entity.getCode());
			tNewProduct.setDescription(entity.getDescription());
			tNewProduct.setName(entity.getName());
			tNewProduct.setPriceHrk(entity.getPriceHrk());
			tNewProduct.setAvailable(entity.isAvailable());
			Product tUpdatedProduct = iProductRepository.save(tNewProduct);
			return CreateResponseUtil.createResponse(31, "Product successfully updated.", tUpdatedProduct);
		} else {
			return CreateResponseUtil.createResponse(33, "Product (ID = " + pId + ") doesn't exist.");
		}
	}

	@Override
	public CustomResponse delete(Long pId) {
		Optional<Product> tProductData = iProductRepository.findById(pId);
		if(tProductData.isPresent()) {
			iProductRepository.deleteById(pId);
			return CreateResponseUtil.createResponse(34, "Product successfully deleted!");
		} else {
			return CreateResponseUtil.createResponse(33, "Product (ID = " + pId + ") doesn't exist.");
		}
	}

	@Override
	public CustomResponse getAll() {
		List<Product> tProducts = iProductRepository.findAll();
		return CreateResponseUtil.createResponse(32, "Products successfully fetched.", tProducts);
	}

	@Override
	public CustomResponse getById(Long pId) {
		Optional<Product> tProductData = iProductRepository.findById(pId);
		if(tProductData.isPresent()) {
			return CreateResponseUtil.createResponse(32, "Product successfully fetched.", tProductData.get());
		}
		return CreateResponseUtil.createResponse(33, "Product (ID = " + pId + ") doesn't exist.");
	}

}
