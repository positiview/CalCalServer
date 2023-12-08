package com.example.service;

import com.example.entity.AdminMember;
import com.example.model.AdminMemberDTO;
import com.example.repository.AdminMemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AdminMemberService implements UserDetailsService  {

    private AdminMemberRepository adminMemberRepository;

    public AdminMember saveMember(AdminMember adminMember) {
        validateDuplicateMember(adminMember);
        return adminMemberRepository.save(adminMember);
    }

    // 회원 중복 체크
    private void validateDuplicateMember(AdminMember adminMember) {
        Optional<AdminMember> findMember = adminMemberRepository.findByLoginId(adminMember.getLoginId());
        // .isPresent() - Optional 객체가 값을 가지고 있다면 true, 없다면 false
        if (findMember.isPresent()) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Optional<AdminMember> member = adminMemberRepository.findByLoginId(loginId);

        // 해당 회원이 없으면 exception
        if (!member.isPresent()) {
            // 해당 예외가 적용되지 않아 변경함
            // throw new UsernameNotFoundException(loginId);
            throw new InternalAuthenticationServiceException(loginId);
        }

        AdminMemberDTO dto = new AdminMemberDTO(
                member.get().getLoginId(),
                member.get().getPassword(),
                Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"))
        );

        return dto;
    }
}