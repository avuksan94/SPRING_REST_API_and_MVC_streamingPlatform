package hr.algebra.bll.blModels;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.util.Set;

public class UserModel {
    @Id
    @NotEmpty(message = "Username is required")
    private String username;

    @NotEmpty(message = "First name is required")
    private String firstName;

    @NotEmpty(message = "Last name is required")
    private String lastName;
    @NotEmpty(message = "Email name is required")
    @Email(message = "Please provide a valid email address.")
    private String email;

    @NotEmpty(message = "Password is required")
    private String password;

    @NotEmpty(message = "Phone number is required")
    @Pattern(regexp = "^\\+(?:[0-9]●?){6,14}[0-9]$", message = "Please provide a valid phone number.")
    private String phone;
    private int countryOfResidenceId;
    private Set<String> roles;

    public UserModel() {
    }

    public UserModel(String username, String firstName, String lastName, String email, String password, String phone, int countryOfResidenceId, Set<String> roles) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.countryOfResidenceId = countryOfResidenceId;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public int getCountryOfResidenceId() {
        return countryOfResidenceId;
    }

    public void setCountryOfResidenceId(int countryOfResidenceId) {
        this.countryOfResidenceId = countryOfResidenceId;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", countryOfResidenceId=" + countryOfResidenceId +
                ", roles=" + roles +
                '}';
    }
}
