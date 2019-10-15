package app.leo.user.adapters;

import app.leo.user.DTO.ApplicantProfileDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProfileAdapter {

    @Value("${profile.api.url}")
    private String profileApiUrl;

    public void createApplicantProfile(ApplicantProfileDTO applicantProfileDTO){
        RestTemplate restTemplate = new RestTemplate();
        System.out.println(profileApiUrl);
        String url = String.format(profileApiUrl + "/profile/applicant/create");
        HttpHeaders headers = new HttpHeaders();
        headers.add("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity<ApplicantProfileDTO> entity = new HttpEntity<ApplicantProfileDTO>(applicantProfileDTO,headers);
        restTemplate.exchange(url, HttpMethod.POST,entity,ApplicantProfileDTO.class);
    }
}
