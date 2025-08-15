package me.nguyenn.AuthenticationTest.dto;

public class LoginRequest {
    private String email;
    private String password;

    // Constructor không tham số cần cho Jackson (JSON mapping)
    public LoginRequest() {}

    // Constructor đầy đủ (tuỳ chọn)
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getter và Setter
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
