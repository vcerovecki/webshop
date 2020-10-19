package hr.ingemark.webshop.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "PRODUCT")
public class Product implements WebShopEntity, Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "CODE", columnDefinition = "CHAR(10)", unique = true)
	@Length(min = 10, max = 10, message = "Code must contain exactly 10 characters")
	@NotNull(message = "The code must be specified")
	private String code;
	
	@Column(name = "NAME")
	private String name;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "product")
	@JsonIgnore
	private Set<OrderItem> orderItems;
	
	@Column(name = "PRICE_HRK")
	@Min(value = 0, message = "The price must be at least 0.00")
	@NotNull(message = "The price must be specified")
	private Double priceHrk;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "IS_AVAILDABLE")
	@JsonProperty(value = "isAvailable")
	private boolean isAvailable;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(Set<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public Double getPriceHrk() {
		return priceHrk;
	}

	public void setPriceHrk(Double priceHrk) {
		this.priceHrk = priceHrk;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	
	
	
}
