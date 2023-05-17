package org.project.models.member;

import lombok.RequiredArgsConstructor;
import org.project.entities.MemberEntity;
import org.project.repositories.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberInfoService implements UserDetailsService {

    private final MemberRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        MemberEntity member = repository.findByMemberId(username);
        if(member == null)
            throw new UsernameNotFoundException(username);
        return MemberInfo.builder()
                .memberNo(member.getMemberNo())
                .memberId(member.getMemberId())
                .memberPw(member.getMemberPw())
                .memberNm(member.getMemberNm())
                .email(member.getEmail())
                .mobile(member.getMobile())
                .build();
    }
}
