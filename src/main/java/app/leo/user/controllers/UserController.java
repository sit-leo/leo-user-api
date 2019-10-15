package app.leo.user.controllers;


import app.leo.user.DTO.ApplicantProfileDTO;
import app.leo.user.DTO.ApplicantUserCreateRequest;
import app.leo.user.DTO.UserDTO;
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
    UserService userService;

    @Autowired
    ProfileAdapter profileAdapter;

    @PostMapping("/user/applicant")
    public ResponseEntity<UserDTO> createApplicantUser(@RequestBody @Valid ApplicantUserCreateRequest applicantUserCreateRequest){
        User user = userService.registerNewUserAccount(applicantUserCreateRequest.getUser());
        ApplicantProfileDTO applicantProfileDTO = applicantUserCreateRequest.getApplicantProfile();
        applicantProfileDTO.setUserId(user.getId());
        profileAdapter.createApplicantProfile(applicantProfileDTO);
        ModelMapper modelMapper = new ModelMapper();
        UserDTO response = modelMapper.map(user,UserDTO.class);

        return  new ResponseEntity<UserDTO>(response, HttpStatus.CREATED);
    }


}
