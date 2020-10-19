package hr.ingemark.webshop.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "ORDER_ITEM")
@Table(name = "ORDER_ITEM", uniqueConstraints = @UniqueConstraint(columnNames = {"PRODUCT_ID", "ORDER_ID"}))
public class OrderItem implements WebShopEntity, Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@ManyToOne(optional = false)
	private Product product;
	@ManyToOne(optional = false)
	@JsonIgnore
	private Order order;
	
	@Column(name = "QUANTITY")
	private Double quantity;

	public OrderItem(Order order, Product product, Double quantity) {
		super();
		this.order = order;
		this.product = product;
		this.quantity = quantity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public boolean equals(Object obj) {
		OrderItem tObj = (OrderItem) obj;
		return (this.getProduct().getId().equals(tObj.getProduct().getId()) && this.getOrder().getId().equals(tObj.getOrder().getId()));
	}
	
	@Override
	public int hashCode() {
		return this.getOrder().getId().hashCode() * this.getProduct().getId().hashCode();
	}
	
}
