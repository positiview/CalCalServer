package com.example.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
public class AdminMemberDTO extends User implements UserDetails {

    private String loginId;
    private String password;

    public AdminMemberDTO(
            String loginId,
            String password,
            Collection<? extends GrantedAuthority> authorities) {
        super(loginId, password, authorities != null ? authorities : Collections.emptyList());
        this.loginId = loginId;
        this.password = password;
    }

}
