package org.financeData.services;

import java.time.Duration;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalAPIUtil {

	private static final Logger log = LoggerFactory.getLogger(ExternalAPIUtil.class);

	@Autowired
	private RestTemplateBuilder restTemplateBuilder;

	/**
	 * Generic method to call any external API
	 */
	public <T, R> ResponseEntity<T> callApi(String url, HttpMethod method, R requestBody, Map<String, String> headers,
			Class<T> responseType) {

		// Build RestTemplate with timeout (per request safe)
		RestTemplate restTemplate = restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(10))
				.setReadTimeout(Duration.ofSeconds(20)).build();

		HttpHeaders httpHeaders = new HttpHeaders();

		// Add headers if present
		if (headers != null && !headers.isEmpty()) {
			headers.forEach(httpHeaders::set);
		}

		// Default content type
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<R> entity = new HttpEntity<>(requestBody, httpHeaders);

		try {
			log.info("Calling API: {} | Method: {}", url, method);

			ResponseEntity<T> response = restTemplate.exchange(url, method, entity, responseType);
			
			/**
			 * IMPORTANT :
			 * 
			 * Check other external methods here.
			 */

			log.info("Response Status: {}", response.getStatusCode());
			return response;

		} catch (HttpClientErrorException e) {
			log.error("Client Error (4xx): {}", e.getResponseBodyAsString());
			throw new RuntimeException("Client Error: " + e.getResponseBodyAsString(), e);

		} catch (HttpServerErrorException e) {
			log.error("Server Error (5xx): {}", e.getResponseBodyAsString());
			throw new RuntimeException("Server Error: " + e.getResponseBodyAsString(), e);

		} catch (ResourceAccessException e) {
			log.error("Timeout / Connection Error: {}", e.getMessage());
			throw new RuntimeException("Connection Timeout/Error", e);

		} catch (Exception e) {
			log.error("Unexpected Error: {}", e.getMessage());
			throw new RuntimeException("Unexpected error while calling API", e);
		}
	}
}
