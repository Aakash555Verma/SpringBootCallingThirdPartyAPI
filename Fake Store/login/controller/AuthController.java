package login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import login.dtos.FakeStoreLoginRequestDto;
import login.services.IAuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private IAuthService authService;

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody FakeStoreLoginRequestDto requestDto) {

		MultiValueMap<String, String> headers = authService.login(requestDto);

		if (headers == null) {
			return new ResponseEntity<>("login failed", null, HttpStatus.UNAUTHORIZED);
		}

		return new ResponseEntity<>("login successful", headers, HttpStatus.OK);
	}
}
