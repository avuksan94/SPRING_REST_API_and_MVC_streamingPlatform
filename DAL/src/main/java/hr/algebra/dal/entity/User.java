package hr.algebra.dal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

@Entity
@Table(name="user")
public class User {
    @Id
    @NotEmpty(message = "Username is required")
    @Column(name = "username")
    private String username;
    @Column(name = "createdAt")
    private LocalDateTime createdAt;
    @Column(name = "deletedAt")
    private LocalDateTime deletedAt;
    @NotEmpty(message = "First name is required")
    @Column(name = "firstName")
    private String firstName;
    @NotEmpty(message = "Last name is required")
    @Column(name = "lastName")
    private String lastName;
    @NotEmpty(message = "Email name is required")
    @Column(name = "email")
    private String email;
    @NotEmpty(message = "Password is required")
    @Column(name = "password")
    private String password;
    @NotEmpty(message = "Phone number is required")
    @Column(name = "phone")
    private String phone;
    @Column(name = "isConfirmed")
    private boolean isConfirmed;
    @Column(name = "securityToken")
    private String securityToken;
    @Column(name = "countryOfResidenceId")
    private int countryOfResidenceId;

    public User() {
    }

    public User(String username, LocalDateTime createdAt, LocalDateTime deletedAt, String firstName, String lastName, String email, String password, String phone, boolean isConfirmed, String securityToken, int countryOfResidenceId) {
        this.username = username;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.isConfirmed = isConfirmed;
        this.securityToken = securityToken;
        this.countryOfResidenceId = countryOfResidenceId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }

    public int getCountryOfResidenceId() {
        return countryOfResidenceId;
    }

    public void setCountryOfResidenceId(int countryOfResidenceId) {
        this.countryOfResidenceId = countryOfResidenceId;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", createdAt=" + createdAt +
                ", deletedAt=" + deletedAt +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", isConfirmed=" + isConfirmed +
                ", securityToken='" + securityToken + '\'' +
                ", countryOfResidenceId=" + countryOfResidenceId +
                '}';
    }
}
