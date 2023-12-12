package com.example.service;


import com.example.entity.MemberEntity;
import com.example.model.MemberDTO;
import com.example.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public MemberDTO getMemberData(String email) {
        MemberEntity member = memberRepository.findByEmail(email);

        if (member != null) {
            MemberDTO memberDTO = new MemberDTO();
            memberDTO.setEmail(member.getEmail());
            memberDTO.setPhone(member.getPhone());
            memberDTO.setPassword(member.getPassword());
            memberDTO.setGender(member.getGender());
            memberDTO.setLength(member.getLength());
            memberDTO.setWeight(member.getWeight());
            memberDTO.setAge(member.getAge());

            return memberDTO;
        } else {
            // 회원 정보가 없는 경우
            return null;
        }
    }

    public Page<MemberEntity> getMemberList(Pageable pageable) {
        return memberRepository.findAll(pageable);
    }

    public Page<MemberEntity> getemailList(String keyword, Pageable pageable) {
        return memberRepository.getByemailLike(keyword, pageable);
    }

    public Page<MemberEntity> getphoneList(String keyword, Pageable pageable) {
        return memberRepository.getByphoneLike(keyword, pageable);
    }


}
