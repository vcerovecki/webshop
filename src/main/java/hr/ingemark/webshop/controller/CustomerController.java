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
import hr.ingemark.webshop.model.Customer;
import hr.ingemark.webshop.model.common.CustomResponse;
import hr.ingemark.webshop.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService iCustomerService;

	@PostMapping()
	public void createCustomer(@Valid @RequestBody Customer pCustomer) throws CustomException {
		iCustomerService.save(pCustomer);
	}
	
	@PutMapping("/{id}")
	public void updateCustomer(@Validated @RequestBody Customer pCustomer, @PathVariable("id") Long pId) throws CustomException {
		iCustomerService.update(pId, pCustomer);
	}
	
	@DeleteMapping("/{id}")
	public void deleteCustomer(@PathVariable("id") Long id) throws CustomException {
		iCustomerService.delete(id);
	}
	
	@GetMapping()
	public ResponseEntity<CustomResponse> getAllCustomers(){
		return new ResponseEntity<CustomResponse>(iCustomerService.getAll(), HttpStatus.OK);
	}
	
}
