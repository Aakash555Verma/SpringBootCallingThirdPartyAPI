package login.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import login.dtos.FakeStoreLoginRequestDto;
import login.dtos.FakeStoreLoginResponseDto;

@Service
public class AuthService implements IAuthService {

	@Autowired
	private RestTemplateBuilder restTemplateBuilder;

	public MultiValueMap<String, String> login(FakeStoreLoginRequestDto fakeStoreLoginRequestDto) {
		try {
			RestTemplate restTemplate = restTemplateBuilder.build();

			String url = "https://fakestoreapi.com/auth/login";

			HttpEntity<FakeStoreLoginRequestDto> request = new HttpEntity<>(fakeStoreLoginRequestDto);

			ResponseEntity<FakeStoreLoginResponseDto> response = restTemplate.postForEntity(url, request,
					FakeStoreLoginResponseDto.class);

			String token = response.getBody().getToken();

			MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
			headers.add(HttpHeaders.SET_COOKIE, token);

			return headers;
		} catch (HttpClientErrorException ex) {
			return null; // invalid credentials
		}
	}

	@Override
	public void delete(Long loginId) {
		RestTemplate restTemplate = restTemplateBuilder.build();
		String url = "https://fakestoreapi.com/auth/{loginId}";
		restTemplate.delete(url, loginId);
	}
}
