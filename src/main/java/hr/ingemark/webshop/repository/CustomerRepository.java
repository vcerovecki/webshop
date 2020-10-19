package hr.ingemark.webshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.ingemark.webshop.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
