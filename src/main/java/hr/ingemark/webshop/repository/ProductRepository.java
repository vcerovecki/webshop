package hr.ingemark.webshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.ingemark.webshop.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	boolean existsByCode(String pCode);
	
	Optional<Product> findByCode(String code);
	
}
