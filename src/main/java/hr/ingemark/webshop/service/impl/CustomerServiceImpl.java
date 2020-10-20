package hr.ingemark.webshop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.ingemark.webshop.model.Customer;
import hr.ingemark.webshop.model.common.CustomResponse;
import hr.ingemark.webshop.repository.CustomerRepository;
import hr.ingemark.webshop.service.CustomerService;
import hr.ingemark.webshop.util.CreateResponseUtil;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository iCustomerRepository;
	
	@Override
	public CustomResponse save(Customer entity) {
		Customer tCustomer = iCustomerRepository.save(entity);
		return CreateResponseUtil.createResponse(10, "Customer successfully created.", tCustomer);
	}

	@Override
	public CustomResponse update(Long pId, Customer entity) {
		Optional<Customer> tCustomerData = iCustomerRepository.findById(pId);
		if(tCustomerData.isPresent()) {
			Customer tNewCustomer = tCustomerData.get();
			tNewCustomer.setEmail(entity.getEmail());
			tNewCustomer.setFirstName(entity.getFirstName());
			tNewCustomer.setLastName(entity.getLastName());
			Customer tUpdatedCustomer = iCustomerRepository.save(tNewCustomer);
			return CreateResponseUtil.createResponse(11, "Customer successfully updated.", tUpdatedCustomer);
		} else {
			return CreateResponseUtil.createResponse(13, "Customer (ID = " + pId + ") doesn't exist.");
		}
	}

	@Override
	public CustomResponse delete(Long pId) {
		Optional<Customer> tCustomerData = iCustomerRepository.findById(pId);
		if(tCustomerData.isPresent()) {
			iCustomerRepository.deleteById(pId);
			return CreateResponseUtil.createResponse(14, "Customer successfully deleted!");
		} else {
			return CreateResponseUtil.createResponse(13, "Customer (ID = " + pId + ") doesn't exist.");
		}
	}

	@Override
	public CustomResponse getAll() {
		List<Customer> tCustomers = iCustomerRepository.findAll();
		return CreateResponseUtil.createResponse(12, "Customers successfully fetched.", tCustomers);
	}

	@Override
	public CustomResponse getById(Long pId) {
		Optional<Customer> tCustomerData = iCustomerRepository.findById(pId);
		if(tCustomerData.isPresent()) {
			return CreateResponseUtil.createResponse(12, "Customer successfully fetched.", tCustomerData.get());
		}
		return CreateResponseUtil.createResponse(13, "Customer (ID = " + pId + ") doesn't exist.");
	}

}
