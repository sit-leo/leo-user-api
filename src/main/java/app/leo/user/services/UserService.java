package app.leo.user.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import app.leo.user.DTO.ChangePasswordDTO;
import app.leo.user.DTO.UserDTO;
import app.leo.user.exceptions.UsernameDuplicateException;
import app.leo.user.exceptions.WrongPasswordException;
import app.leo.user.models.User;
import app.leo.user.repositories.UserRepository;

@Service
public class UserService {
  @Autowired
  UserRepository userRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private AuthenticationService authenticationService;

  public User findByUsername(String username) {
      return this.userRepository.findByUsername(username);
  }
  public User registerNewUserAccount(UserDTO requestedUserDTO){
        User user = new User();
        boolean usernameIsExist = userRepository.findByUsername(requestedUserDTO.getUsername())!= null;
        if(usernameIsExist) {
            throw new UsernameDuplicateException("Username already exist");
        }
        user.setUsername(requestedUserDTO.getUsername());
        user.setPassword(passwordEncoder.encode(requestedUserDTO.getPassword()));
        user.setRole(requestedUserDTO.getRole());
        return userRepository.save(user);
  }

  public User changePassword(ChangePasswordDTO password, long id) {
    User user = userRepository.findById(id);

    if (!authenticationService.passwordIsCorrect(user.getPassword(), password.getCurrentPassword())) {
      throw new WrongPasswordException();
    };

    user.setPassword(passwordEncoder.encode(password.getNewPassword()));
    return userRepository.save(user);
  }
}
