package com.example.service;


import com.example.entity.MemberEntity;
import com.example.model.MemberDTO;
import com.example.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MemberService {

    private MemberRepository memberRepository;


    public void saveMember(MemberDTO memberDTO){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setEmail(memberDTO.getEmail());
        memberEntity.setPhone(memberDTO.getPhone());
        memberEntity.setPassword(memberDTO.getPassword());
        memberEntity.setPassword2(memberDTO.getPassword2());

        memberRepository.save(memberEntity);
    }

    public List<MemberEntity> findAllMembers() {
        return memberRepository.findAll();
    }

    public boolean login(String email, String password) {
        MemberEntity member = memberRepository.findByEmail(email);

        if (member != null && member.getPassword().equals(password)) {
            // 로그인 성공
            return true;
        } else {
            // 로그인 실패
            return false;
        }
    }
}
