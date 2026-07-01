package org.financeData.services;

import java.util.List;

import org.financeData.dtos.RealTimeCashFlowResult;
import org.financeData.dtos.RealTimeNewsResult;
import org.financeData.exceptions.ExternalServiceException;
import org.financeData.models.CashFlow;
import org.financeData.models.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Service
public class CompanyStockService implements IStockService {

	@Autowired
	private RestTemplateBuilder restTemplateBuilder;

	@Value("${company.key}")
	private String key;
	@Value("${company.value}")
	private String value;

	public List<News> getStockNews(String symbol) {
		try {
			RestTemplate restTemplate = restTemplateBuilder.build();

			HttpHeaders headers = new HttpHeaders();
			headers.set(key, value);

			HttpEntity<String> entity = new HttpEntity<>(headers);

			ResponseEntity<RealTimeNewsResult> response = restTemplate.exchange(
					"https://real-time-finance-data.p.rapidapi.com/stock-news?symbol={symbol}", HttpMethod.GET, entity,
					RealTimeNewsResult.class, symbol);

			RealTimeNewsResult body = response.getBody();

			if (body == null || body.getData() == null) {
				throw new ExternalServiceException("Invalid response from external API");
			}

			return body.getData().getNews();

		} catch (HttpClientErrorException | HttpServerErrorException ex) {
			throw new ExternalServiceException("External API error: " + ex.getStatusCode());

		} catch (ResourceAccessException ex) {
			throw new ExternalServiceException("External API is unreachable");

		} catch (Exception ex) {
			throw new ExternalServiceException("Something went wrong while fetching stock news");
		}
	}

	public List<CashFlow> getCompanyCashFlow(String symbol) {
		try {
			RestTemplate restTemplate = restTemplateBuilder.build();

			HttpHeaders headers = new HttpHeaders();
			headers.set(key, value);

			HttpEntity<String> entity = new HttpEntity<>(headers);

			ResponseEntity<RealTimeCashFlowResult> response = restTemplate.exchange(
					"https://real-time-finance-data.p.rapidapi.com/company-cash-flow?symbol={symbol}", HttpMethod.GET,
					entity, RealTimeCashFlowResult.class, symbol);

			RealTimeCashFlowResult body = response.getBody();

			if (body == null || body.getData() == null) {
				throw new ExternalServiceException("Invalid response from Cash Flow API");
			}

			return body.getData().getCash_flow();

		} catch (HttpClientErrorException | HttpServerErrorException ex) {
			throw new ExternalServiceException("Cash Flow API error: " + ex.getStatusCode());

		} catch (ResourceAccessException ex) {
			throw new ExternalServiceException("Cash Flow API is unreachable");

		} catch (Exception ex) {
			throw new ExternalServiceException("Something went wrong while fetching cash flow data");
		}
	}

}
