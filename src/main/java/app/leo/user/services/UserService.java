package app.leo.user.services;

import app.leo.user.DTO.UserDTO;
import app.leo.user.exceptions.UsernameDuplicateException;
import app.leo.user.models.User;
import app.leo.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  @Autowired
  UserRepository userRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;

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
}
