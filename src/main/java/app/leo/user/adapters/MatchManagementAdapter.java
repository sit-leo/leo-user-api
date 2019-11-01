package app.leo.user.adapters;

import app.leo.user.DTO.Organization;
import app.leo.user.DTO.OrganizationProfileDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MatchManagementAdapter {

    @Value("${match.management.api.url}")
    private String matchManagementUrl;

    public Organization createOrganization(OrganizationProfileDTO organizationProfileDTO ){
        RestTemplate restTemplate = new RestTemplate();
        String url = matchManagementUrl + "/create/organization";
        HttpHeaders headers = new HttpHeaders();
        headers.add("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        Organization organization = new Organization(organizationProfileDTO.getName(),organizationProfileDTO.getDescription(),organizationProfileDTO.getId());
        HttpEntity<Organization> entity = new HttpEntity<>(organization, headers);
        ResponseEntity<Organization> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, Organization.class);
        return responseEntity.getBody();
    }
}
