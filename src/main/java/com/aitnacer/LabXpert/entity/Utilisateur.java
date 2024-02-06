package com.aitnacer.LabXpert.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


@Getter
@Setter
@ToString(callSuper = true)
@Entity
@Table(name = "Utilisateur")
@NoArgsConstructor
public class Utilisateur extends UtilisateurInfo implements UserDetails {
    @NotNull(message = "The username should not be null!")
    private String userName;
    @NotNull(message = "The password should not be null!")
    @NotBlank(message = "The password should not be blank!")
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Builder
    public Utilisateur(Long id, String nom, String prenom, String Adresse, String telephone, EnumSexe sexe, boolean deleted, String userName, String password, UserRole role) {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
