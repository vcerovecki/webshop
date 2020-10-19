package hr.ingemark.webshop.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import hr.ingemark.webshop.model.common.ExchangeRate;

@PropertySource("classpath:application.properties")
@Component
public class HnbRestClient {

	@Autowired
	private RestTemplate iRestTemplate;
	
	@Value("${hnb.rest.endpoint}")
	private String iUri;
	
	public ExchangeRate[] fetchEurExchangeRate() {
		return iRestTemplate.getForObject(iUri, ExchangeRate[].class);
	}
	
}
