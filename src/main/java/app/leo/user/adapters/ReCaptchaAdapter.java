package app.leo.user.adapters;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import app.leo.user.DTO.ReCaptchaRequest;
import app.leo.user.DTO.ReCaptchaResponse;
import app.leo.user.exceptions.InvalidReCaptchaException;

@Service
public class ReCaptchaAdapter {

	@Value("${recaptcha.secret}")
	private String recaptchaSecret;

	@Value("${recaptcha.api.url}")
	private String recaptchaApiUrl;

	public void isValidReCaptcha(String recaptcha) {
		ReCaptchaRequest reCaptChaRequest = new ReCaptchaRequest(recaptchaSecret, recaptcha);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.add("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
		HttpEntity<MultiValueMap> entity = new HttpEntity<>(reCaptChaRequest.getReCaptChaRequest(), headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<ReCaptchaResponse> responseEntity = restTemplate.exchange(recaptchaApiUrl, HttpMethod.POST, entity, ReCaptchaResponse.class);
		ReCaptchaResponse reCaptchaResponse = responseEntity.getBody();
		if (reCaptchaResponse.isSuccess()) {
			return;
		}
		throw new InvalidReCaptchaException();
	}
}
