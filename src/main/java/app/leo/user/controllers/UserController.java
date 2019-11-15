package app.leo.user.controllers;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import app.leo.user.DTO.ApplicantProfileDTO;
import app.leo.user.DTO.ApplicantUserCreateRequest;
import app.leo.user.DTO.ChangePasswordDTO;
import app.leo.user.DTO.OrganizationProfileDTO;
import app.leo.user.DTO.OrganizerUserCreateRequest;
import app.leo.user.DTO.RecruiterProfileDTO;
import app.leo.user.DTO.RecruiterUserCreateRequest;
import app.leo.user.DTO.UserDTO;
import app.leo.user.adapters.MatchManagementAdapter;
import app.leo.user.adapters.ProfileAdapter;
import app.leo.user.adapters.ReCaptchaAdapter;
import app.leo.user.models.User;
import app.leo.user.services.UserService;


@CrossOrigin("*")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileAdapter profileAdapter;

    @Autowired
    private MatchManagementAdapter matchManagementAdapter;

    @Autowired
    private ReCaptchaAdapter recaptchaAdapter;

    @PostMapping("/user/applicant")
    public ResponseEntity<UserDTO> createApplicantUser(@RequestBody @Valid ApplicantUserCreateRequest applicantUserCreateRequest){
        applicantUserCreateRequest.getUser().setRole("applicant");
        recaptchaAdapter.isValidReCaptcha(applicantUserCreateRequest.getRecaptcha());
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
        recruiterUserCreateRequest.getUser().setRole("recruiter");
        recaptchaAdapter.isValidReCaptcha(recruiterUserCreateRequest.getRecaptcha());
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
        UserDTO newUser = organizerUserCreateRequest.getUser();
        newUser.setRole("organizer");
        User user = userService.registerNewUserAccount(newUser);
        OrganizationProfileDTO organizationProfileDTO = organizerUserCreateRequest.getOrganizationProfileDTO();
        organizationProfileDTO.setUserId(user.getId());
        OrganizationProfileDTO  organizationProfile = profileAdapter.createOrganizationProfile(organizationProfileDTO);
        matchManagementAdapter.createOrganization(organizationProfile);
        ModelMapper modelMapper = new ModelMapper();
        UserDTO response = modelMapper.map(user,UserDTO.class);
        return  new ResponseEntity<>(response, HttpStatus.CREATED);
    }

	@PutMapping("/change-password")
	public ResponseEntity<UserDTO> changePassword(
			@RequestBody @Valid ChangePasswordDTO password,
			@RequestAttribute(name = "user") UserDTO user
	){
		User changedPasswordUser = userService.changePassword(password, user.getUserId());
		ModelMapper modelMapper = new ModelMapper();
		UserDTO response = modelMapper.map(changedPasswordUser,UserDTO.class);
		return  new ResponseEntity<>(response, HttpStatus.CREATED);
	}
}
