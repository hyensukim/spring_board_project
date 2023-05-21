package org.project.models.member;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.commons.constants.Role;
import org.project.controllers.member.JoinForm;
import org.project.entities.MemberEntity;
import org.project.repositories.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 회원 정보 추가, 수정
 * - 수정 시는 비밀번호 값이 있을때만 수정
 */

@Service
@RequiredArgsConstructor
public class MemberSaveService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    
    // 회원 정보 추가
    public void save(JoinForm joinForm){
        
        MemberEntity member = new ModelMapper().map(joinForm, MemberEntity.class);
        member.setRole(Role.MEMBER);
        member.setMemberPw(passwordEncoder.encode(member.getMemberPw()));//비번 해시화
        memberRepository.saveAndFlush(member);
        
    }
    
}
