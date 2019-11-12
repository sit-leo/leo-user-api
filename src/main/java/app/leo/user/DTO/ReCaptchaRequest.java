package app.leo.user.DTO;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class ReCaptchaRequest {
	MultiValueMap<String, String> reCaptChaRequest;

	public ReCaptchaRequest(String recaptchaSecret, String recaptcha) {
		reCaptChaRequest = new LinkedMultiValueMap<String, String>();
		reCaptChaRequest.add("secret", recaptchaSecret);
		reCaptChaRequest.add("response", recaptcha);
	}

	public MultiValueMap<String, String> getReCaptChaRequest() {
		return reCaptChaRequest;
	}

	public void setReCaptChaRequest(MultiValueMap<String, String> reCaptChaRequest) {
		this.reCaptChaRequest = reCaptChaRequest;
	}
}
