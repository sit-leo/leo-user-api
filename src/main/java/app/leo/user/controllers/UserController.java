package app.leo.user.controllers;


import app.leo.user.DTO.*;
import app.leo.user.adapters.MatchManagementAdapter;
import app.leo.user.adapters.ProfileAdapter;
import app.leo.user.models.User;
import app.leo.user.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@CrossOrigin("*")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileAdapter profileAdapter;

    @Autowired
    private MatchManagementAdapter matchManagementAdapter;

    @PostMapping("/user/applicant")
    public ResponseEntity<UserDTO> createApplicantUser(@RequestBody @Valid ApplicantUserCreateRequest applicantUserCreateRequest){
        User user = userService.registerNewUserAccount(applicantUserCreateRequest.getUser());
        ApplicantProfileDTO applicantProfileDTO = applicantUserCreateRequest.getApplicantProfile();
        applicantProfileDTO.setUserId(user.getId());
        profileAdapter.createApplicantProfile(applicantProfileDTO);
        ModelMapper modelMapper = new ModelMapper();
        UserDTO response = modelMapper.map(user,UserDTO.class);

        return  new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/user/recruiter")
    public ResponseEntity<UserDTO> createRecruiterUser(@RequestBody @Valid RecruiterUserCreateRequest recruiterUserCreateRequest){
        User user = userService.registerNewUserAccount(recruiterUserCreateRequest.getUser());
        RecruiterProfileDTO recruiterProfile = recruiterUserCreateRequest.getRecruiterProfile();
        recruiterProfile.setUserId(user.getId());
        profileAdapter.createRecruiterProfile(recruiterProfile);
        ModelMapper modelMapper = new ModelMapper();
        UserDTO response = modelMapper.map(user,UserDTO.class);

        return  new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/user/organizer")
    public ResponseEntity<UserDTO> createOrganizerUser(@RequestBody @Valid OrganizerUserCreateRequest organizerUserCreateRequest ){
        User user = userService.registerNewUserAccount(organizerUserCreateRequest.getUser());
        OrganizationProfileDTO organizationProfileDTO = organizerUserCreateRequest.getOrganizationProfileDTO();
        organizationProfileDTO.setUserId(user.getId());
        OrganizationProfileDTO  organizationProfile = profileAdapter.createOrganizationProfile(organizationProfileDTO);
        matchManagementAdapter.createOrganization(organizationProfile);
        ModelMapper modelMapper = new ModelMapper();
        UserDTO response = modelMapper.map(user,UserDTO.class);
        return  new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
