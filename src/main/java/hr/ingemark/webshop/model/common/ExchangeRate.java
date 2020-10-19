package hr.ingemark.webshop.model.common;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import hr.ingemark.webshop.util.JsonDoubleDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRate implements Serializable{

	@JsonProperty(value = "Broj tečajnice")
	private Long id;
	@JsonProperty(value = "Datum primjene")
	@JsonFormat(pattern = "DD.MM.YYYY")
	private Date date;
	@JsonProperty(value = "Država")
	private String state;
	@JsonProperty(value = "Šifra valute")
	private String code;
	@JsonProperty(value = "Valuta")
	private String currency;
	@JsonProperty(value = "Jedinica")
	private String unit;
	@JsonProperty(value = "Kupovni za devize")
	@JsonDeserialize(using = JsonDoubleDeserializer.class)
	private Double purchaseExchange;
	@JsonProperty(value = "Srednji za devize")
	@JsonDeserialize(using = JsonDoubleDeserializer.class)
	private Double middleExchange;
	@JsonProperty(value = "Prodajni za devize")
	@JsonDeserialize(using = JsonDoubleDeserializer.class)
	private Double sellingExchange;
	
}
