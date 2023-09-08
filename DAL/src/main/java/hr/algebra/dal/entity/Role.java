package hr.algebra.dal.entity;

import hr.algebra.dal.embedded.RoleId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name="roles")
public class Role implements Comparable<Role> {
    @EmbeddedId
    private RoleId roleId;
    public Role() {}

    public Role(String username, String role) {
        this.roleId = new RoleId(username, role);
    }

    public String getUsername() {
        return roleId.getUsername();
    }

    public void setUsername(String username) {
        roleId.setUsername(username);
    }

    public String getRole() {
        return roleId.getRole();
    }

    public void setRole(String role) {
        roleId.setRole(role);
    }

    @Override
    public String toString() {
        return "Role{" +
                "userId=" + getUsername() +
                ", role='" + getRole() + '\'' +
                '}';
    }

    @Override
    public int compareTo(Role o) {
        return this.getRole().compareTo(o.getRole());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role1 = (Role) o;
        return Objects.equals(getUsername(), role1.getUsername()) &&
                Objects.equals(getRole(), role1.getRole());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getRole());
    }
}
