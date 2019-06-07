package app.leo.user.controllers;


import app.leo.user.DTO.UserDTO;
import app.leo.user.models.User;
import app.leo.user.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("*")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/user")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO){
        User user = userService.registerNewUserAccount(userDTO);
        ModelMapper modelMapper = new ModelMapper();
        UserDTO response = modelMapper.map(user,UserDTO.class);
        return  new ResponseEntity<UserDTO>(response, HttpStatus.CREATED);
    }


}
