package com.example.entity;


import com.example.model.AdminMemberDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@Setter
public class AdminMember {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true)
    private String loginId;

    private String password;

    // 관리자 회원가입 Method
    public static AdminMember createMember(AdminMemberDTO adminMemberDTO,
                                           PasswordEncoder passwordEncoder) {
        AdminMember member = new AdminMember();
        member.setLoginId(adminMemberDTO.getLoginId());

        // 비밀번호 암호화
        String password = passwordEncoder.encode(adminMemberDTO.getPassword());
        member.setPassword(password);

        return member;
    }
}
