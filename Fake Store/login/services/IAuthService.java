package login.services;

import org.springframework.util.MultiValueMap;

import login.dtos.FakeStoreLoginRequestDto;

public interface IAuthService {
    MultiValueMap<String, String> login(FakeStoreLoginRequestDto fakeStoreLoginRequestDto);

	void delete(Long loginId);
}
