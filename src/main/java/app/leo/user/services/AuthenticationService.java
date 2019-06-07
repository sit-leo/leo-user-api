package app.leo.user.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean passwordIsCorrect(String userPassword,String requestPassword){
        return passwordEncoder.matches(requestPassword,userPassword);
    }

}
