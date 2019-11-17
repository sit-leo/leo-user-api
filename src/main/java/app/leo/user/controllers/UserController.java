package app.leo.user.controllers;

import javax.validation.Valid;

import app.leo.user.DTO.*;
import app.leo.user.exceptions.WrongRoleException;
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

    @Autowired
    private ModelMapper modelMapper;

    private final String APPLICANT_ROLE = "applicant";
    private final String RECRUITER_ROLE = "recruiter";
    private final String ORGANIZER_ROLE = "organizer";
    private final String ADMIN_ROLE = "admin";

    @PostMapping("/user/applicant")
    public ResponseEntity<UserDTO> createApplicantUser(@RequestBody @Valid ApplicantUserCreateRequest applicantUserCreateRequest){
        applicantUserCreateRequest.getUser().setRole(APPLICANT_ROLE);
        recaptchaAdapter.isValidReCaptcha(applicantUserCreateRequest.getRecaptcha());
        User user = userService.registerNewUserAccount(applicantUserCreateRequest.getUser());
        ApplicantProfileDTO applicantProfileDTO = applicantUserCreateRequest.getApplicantProfile();
        applicantProfileDTO.setUserId(user.getId());
        profileAdapter.createApplicantProfile(applicantProfileDTO);
        UserDTO response = modelMapper.map(user,UserDTO.class);


        return  new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/user/recruiter")
    public ResponseEntity<UserDTO> createRecruiterUser(@RequestBody @Valid RecruiterUserCreateRequest recruiterUserCreateRequest){
        recruiterUserCreateRequest.getUser().setRole(RECRUITER_ROLE);
        recaptchaAdapter.isValidReCaptcha(recruiterUserCreateRequest.getRecaptcha());
        User user = userService.registerNewUserAccount(recruiterUserCreateRequest.getUser());
        RecruiterProfileDTO recruiterProfile = recruiterUserCreateRequest.getRecruiterProfile();
        recruiterProfile.setUserId(user.getId());
        profileAdapter.createRecruiterProfile(recruiterProfile);
        UserDTO response = modelMapper.map(user,UserDTO.class);
        return  new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/user/organizer")
    public ResponseEntity<UserDTO> createOrganizerUser(@RequestAttribute("user") UserDTO adminUser,
                                                       @RequestAttribute("token") String token,
                                                       @RequestBody @Valid OrganizerUserCreateRequest organizerUserCreateRequest
                                                       ){
        if(adminUser.getRole().equals(ADMIN_ROLE)) {
            UserDTO newUser = organizerUserCreateRequest.getUser();
            newUser.setRole(ORGANIZER_ROLE);
            User newUserAccount = userService.registerNewUserAccount(newUser);
            OrganizationProfileDTO organizationProfileDTO = organizerUserCreateRequest.getOrganizationProfileDTO();
            organizationProfileDTO.setUserId(newUserAccount.getId());
            OrganizationProfileDTO organizationProfile = profileAdapter.createOrganizationProfile(organizationProfileDTO,token);
            matchManagementAdapter.createOrganization(organizationProfile);
            UserDTO response = modelMapper.map(newUserAccount, UserDTO.class);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        throw new WrongRoleException("You're not admin");
    }

	@PutMapping("/change-password")
	public ResponseEntity<UserDTO> changePassword(
			@RequestBody @Valid ChangePasswordDTO password,
			@RequestAttribute(name = "user") UserDTO user
	){
		User changedPasswordUser = userService.changePassword(password, user.getUserId());
		UserDTO response = modelMapper.map(changedPasswordUser,UserDTO.class);
		return  new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PostMapping("/user/admin")
    public ResponseEntity<UserDTO> createAdminUser(@RequestBody @Valid AdminUserCreateRequest adminUserCreateRequest,
                                                   @RequestAttribute("user") User user){
        if(user.getRole().equals(ADMIN_ROLE)) {
            UserDTO newUser = adminUserCreateRequest.getUserDTO();
            newUser.setRole(ADMIN_ROLE);
            User newUserAccount = userService.registerNewUserAccount(newUser);
            AdminProfileDTO adminProfileDTO = adminUserCreateRequest.getAdminProfileDTO();
            adminProfileDTO.setUserId(newUserAccount.getId());
            profileAdapter.createAdminProfile(adminProfileDTO);
            newUser = modelMapper.map(newUserAccount, UserDTO.class);
            return new ResponseEntity<>(newUser, HttpStatus.OK);
        }
        throw new WrongRoleException("You are not admin");
    }
}
