package hr.algebra.dal.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class RoleId implements Serializable {
    @Column(name = "username")
    private String username;

    @Column(name = "role")
    private String role;

    public RoleId() {
    }

    public RoleId(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "RoleId{" +
                "username='" + username + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
