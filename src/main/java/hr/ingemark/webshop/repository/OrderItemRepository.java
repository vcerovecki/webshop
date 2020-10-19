package hr.ingemark.webshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.ingemark.webshop.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
