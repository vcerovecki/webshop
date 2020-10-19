package hr.ingemark.webshop.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import hr.ingemark.webshop.enums.StatusEnum;
import hr.ingemark.webshop.util.JsonDoubleSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "WEBSHOP_ORDER")
@Builder
public class Order implements WebShopEntity, Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@ManyToOne(optional = true)
	private Customer customer;
	
	@OneToMany(fetch = FetchType.EAGER, cascade =  CascadeType.ALL, mappedBy = "order")
	private List<OrderItem> orderItems;
	
	@Column(name = "STATUS")
	private StatusEnum status;
	
	@Column(name = "TOTAL_PRICE_HRK")
	@JsonSerialize(using = JsonDoubleSerializer.class)
	private Double totalPriceHrk;
	
	@Column(name = "TOTAL_PRICE_EUR")
	@JsonSerialize(using = JsonDoubleSerializer.class)
	private Double totalPriceEur;
	
}
