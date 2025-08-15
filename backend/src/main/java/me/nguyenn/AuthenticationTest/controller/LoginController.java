package me.nguyenn.AuthenticationTest.controller;

import me.nguyenn.AuthenticationTest.service.DataCollectService;
import me.nguyenn.AuthenticationTest.service.LoginService;
import me.nguyenn.AuthenticationTest.dto.LoginRequest;
import me.nguyenn.AuthenticationTest.dto.LoginResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class LoginController {

    private final LoginService loginService;
    private final DataCollectService dataCollectService;

    @Autowired
    public LoginController(LoginService loginService, DataCollectService dataCollectService) {
        this.loginService = loginService;
        this.dataCollectService = dataCollectService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        // gọi service để xác thực và lấy token
        String token = loginService.login(email, password);
        if (token == null) {
            // login thất bại trả 401
            return ResponseEntity.status(401).build();
        }

        // trả về token cho client
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/register")
    public String register(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        try {
            loginService.register(email, password);
        }
        catch (Exception e) {
            return "Duplicated email";
        }

        return "OK";
    }

    @GetMapping("/get-data")
    public String getData(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            if (loginService.validate(token) == null) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }

            return dataCollectService.getData();
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
}
