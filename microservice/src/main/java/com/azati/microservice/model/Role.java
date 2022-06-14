package com.azati.microservice.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

import static java.lang.String.valueOf;

@Entity
@Getter
@Setter
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    @ManyToMany(mappedBy ="roles",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<User> users;

    @Override
    public String getAuthority() {
        return valueOf(getRoleName());
    }
}
