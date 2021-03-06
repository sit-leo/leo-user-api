package app.leo.user.adapters;

import app.leo.user.DTO.AdminProfileDTO;
import app.leo.user.DTO.ApplicantProfileDTO;
import app.leo.user.DTO.OrganizationProfileDTO;
import app.leo.user.DTO.RecruiterProfileDTO;
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
        String url = profileApiUrl + "/profile/applicant/create";
        HttpHeaders headers = new HttpHeaders();
        headers.add("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity<ApplicantProfileDTO> entity = new HttpEntity<>(applicantProfileDTO,headers);
        restTemplate.exchange(url, HttpMethod.POST,entity,ApplicantProfileDTO.class);
    }

    public void createRecruiterProfile(RecruiterProfileDTO recruiterProfileDTO) {
        RestTemplate restTemplate = new RestTemplate();
        String url = profileApiUrl + "/profile/recruiter/create";
        HttpHeaders headers = new HttpHeaders();
        headers.add("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity<RecruiterProfileDTO> entity = new HttpEntity<>(recruiterProfileDTO, headers);
        restTemplate.exchange(url, HttpMethod.POST, entity, RecruiterProfileDTO.class);
    }

    public OrganizationProfileDTO createOrganizationProfile(OrganizationProfileDTO organizationDTO,String token) {
        RestTemplate restTemplate = new RestTemplate();
        String url = profileApiUrl + "/profile/organizer/create";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization",token);
        headers.add("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity<OrganizationProfileDTO> entity = new HttpEntity<>(organizationDTO, headers);
        ResponseEntity<OrganizationProfileDTO> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, OrganizationProfileDTO.class);
        return responseEntity.getBody();
    }

    public AdminProfileDTO createAdminProfile(AdminProfileDTO adminDTO){
        RestTemplate restTemplate = new RestTemplate();
        String url = profileApiUrl + "/profile/admin/create";
        HttpHeaders headers = new HttpHeaders();
        headers.add("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity<AdminProfileDTO> entity = new HttpEntity<>(adminDTO, headers);
        ResponseEntity<AdminProfileDTO> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, AdminProfileDTO.class);
        return responseEntity.getBody();
    }

    public Long getProfileIdByUserId( String role,long userId){
        RestTemplate restTemplate=new RestTemplate();
        String url = profileApiUrl + "/profile/id/" + userId + "/" + role;
        HttpHeaders headers = new HttpHeaders();
        headers.add("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url,HttpMethod.GET,entity,Long.class);
        return responseEntity.getBody();
    }
}
