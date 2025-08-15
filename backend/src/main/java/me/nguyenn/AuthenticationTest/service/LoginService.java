package me.nguyenn.AuthenticationTest.service;

import me.nguyenn.AuthenticationTest.model.User;
import me.nguyenn.AuthenticationTest.repository.UserRepository;
import me.nguyenn.AuthenticationTest.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;


    public String login(String email, String password) {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            return null;
        }

        if (!password.equals(user.getPassword())) {
            return null;
        }

        return JwtUtil.generateToken(email);
    }

    public void register(String email, String password) throws Exception {
        User user = userRepository.findByEmail(email);

        if (user != null) {
            throw new Exception();
        }

        user = new User();
        user.setEmail(email);
        user.setPassword(password);

        userRepository.save(user);
    }

    public String validate(String token) {
        return JwtUtil.validateToken(token);
    }

}
