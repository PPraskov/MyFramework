package app.domain.entity;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role extends Identifier{


    private String role;

    public Role() {

    }

    @Column(name = "role")
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
