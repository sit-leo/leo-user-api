package app.leo.user.services;

import app.leo.user.models.User;
import app.leo.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  @Autowired UserRepository userRepository;

  public User findByUsername(String username) {
      return this.userRepository.findByUsername(username);
  }
}
