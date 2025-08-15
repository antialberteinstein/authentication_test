package me.nguyenn.AuthenticationTest.dto;

public class LoginResponse {
    private String token;

    // Constructor
    public LoginResponse() {}

    public LoginResponse(String token) {
        this.token = token;
    }

    // Getter và Setter
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
