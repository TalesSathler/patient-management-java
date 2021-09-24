package com.scp.patientmanagement.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "role")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "role_id", nullable = false)
    private long roleId;

    @Column(name = "role_name", nullable = false)
    private String roleName;

    @Override
    public String getAuthority() {
        return this.roleName;
    }
}
