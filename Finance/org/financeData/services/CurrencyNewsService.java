package org.financeData.services;

import org.financeData.dtos.RealTimeNewsData;
import org.financeData.dtos.RealTimeNewsResult;
import org.financeData.exceptions.ExternalServiceException;
import org.financeData.models.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CurrencyNewsService implements ICurrencyService {

	@Autowired
	private RestTemplateBuilder restTemplateBuilder;

	@Value("${currency.key}")
	private String key;
	@Value("${currency.value}")
	private String value;

	public List<News> getCurrencyNews(String fromCurrency, String toCurrency) {
		RestTemplate restTemplate = restTemplateBuilder.build();

		HttpHeaders headers = new HttpHeaders();
		headers.set(key, value);

		HttpEntity<String> entity = new HttpEntity<>(headers);

		ResponseEntity<RealTimeNewsResult> response = restTemplate.exchange(
				"https://real-time-finance-data.p.rapidapi.com/currency-news?from_symbol={currency1}&to_symbol={currency2}",
				HttpMethod.GET, entity, RealTimeNewsResult.class, fromCurrency, toCurrency);

		if (response == null || response.getBody() == null)
			throw new ExternalServiceException("Invalid response from external API");

		RealTimeNewsResult realTimeNewsResult = response.getBody();
		RealTimeNewsData realTimeNewsData = realTimeNewsResult.getData();
		return realTimeNewsData.getNews();
	}

}
