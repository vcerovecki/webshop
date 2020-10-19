package hr.ingemark.webshop.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hr.ingemark.webshop.dto.OrderDto;
import hr.ingemark.webshop.exception.CustomException;
import hr.ingemark.webshop.model.common.CustomResponse;
import hr.ingemark.webshop.service.OrderService;

@RestController()
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService iorderService;
	
	@PostMapping
	public ResponseEntity<CustomResponse> createOrder(@Valid @RequestBody OrderDto pOrderDto) throws CustomException {
		return new ResponseEntity<CustomResponse>(iorderService.save(pOrderDto), HttpStatus.OK);
	}
	
	@GetMapping()
	public ResponseEntity<CustomResponse> getAll(){
		return new ResponseEntity<CustomResponse>(iorderService.getAll(), HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CustomResponse> updateOrder(@Valid @RequestBody OrderDto pOrderDto, @PathVariable("id") Long pId) throws CustomException{
		return new ResponseEntity<CustomResponse>(iorderService.update(pId, pOrderDto), HttpStatus.OK);
	}
	
	@DeleteMapping("/id")
	public ResponseEntity<CustomResponse> deleteOrder(@PathVariable("id") Long pId) throws CustomException{
		return new ResponseEntity<CustomResponse>(iorderService.delete(pId), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CustomResponse> getById(@PathVariable("id") Long pId) throws CustomException{
		return new ResponseEntity<CustomResponse>(iorderService.getById(pId), HttpStatus.OK);
	}
	
	@GetMapping("/{id}/finalize")
	public ResponseEntity<CustomResponse> finalizeOrder(@PathVariable("id") Long pId) throws CustomException{
		return new ResponseEntity<CustomResponse>(iorderService.finalizeOrder(pId), HttpStatus.OK);
	}
	
}
