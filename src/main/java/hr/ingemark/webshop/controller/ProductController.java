package hr.ingemark.webshop.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hr.ingemark.webshop.exception.CustomException;
import hr.ingemark.webshop.model.Product;
import hr.ingemark.webshop.model.common.CustomResponse;
import hr.ingemark.webshop.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private ProductService iProductService;

	@PostMapping()
	public ResponseEntity<CustomResponse> createProduct(@Valid @RequestBody Product pProduct) throws CustomException {
		return new ResponseEntity<CustomResponse>(iProductService.save(pProduct), HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CustomResponse> updateProduct(@Validated @RequestBody Product pProduct, @PathVariable("id") Long pId) throws CustomException {
		return new ResponseEntity<CustomResponse>(iProductService.update(pId, pProduct), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<CustomResponse> deleteProduct(@PathVariable("id") Long id) throws CustomException {
		return new ResponseEntity<CustomResponse>(iProductService.delete(id), HttpStatus.OK);
	}
	
	@GetMapping()
	public ResponseEntity<CustomResponse> getAllProducts(){
		return new ResponseEntity<CustomResponse>(iProductService.getAll(), HttpStatus.OK);
	}
	
}
