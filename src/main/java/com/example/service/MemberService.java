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
        memberEntity.setGender(memberDTO.getGender());
        memberEntity.setLength(memberDTO.getLength());
        memberEntity.setWeight(memberDTO.getWeight());
        memberEntity.setAge(memberDTO.getAge());


        memberRepository.save(memberEntity);
    }
    public boolean updateMemberData(MemberDTO memberDTO) {
        MemberEntity member = memberRepository.findByEmail(memberDTO.getEmail());

        if (member != null) {
            member.setPhone(memberDTO.getPhone());
            member.setPassword(memberDTO.getPassword());
            member.setGender(memberDTO.getGender());
            member.setLength(memberDTO.getLength());
            member.setWeight(memberDTO.getWeight());
            member.setAge(memberDTO.getAge());
            memberRepository.save(member);
            return true;
        } else {
            // 회원 정보가 없는 경우
            return false;
        }
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

    public MemberDTO getMember(String email) {
        MemberEntity member =

    }
}
