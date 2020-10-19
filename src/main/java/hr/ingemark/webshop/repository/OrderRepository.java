package hr.ingemark.webshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.ingemark.webshop.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
