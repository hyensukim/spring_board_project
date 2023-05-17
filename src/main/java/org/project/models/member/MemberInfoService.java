package org.project.models.member;

import lombok.RequiredArgsConstructor;
import org.project.entities.MemberEntity;
import org.project.repositories.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberInfoService implements UserDetailsService {

    private final MemberRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        MemberEntity member = repository.findByMemberId(username);
        if(member == null)
            throw new UsernameNotFoundException(username);

        List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(member.getRole().toString()));

        return MemberInfo.builder()
                .memberNo(member.getMemberNo())
                .memberId(member.getMemberId())
                .memberPw(member.getMemberPw())
                .memberNm(member.getMemberNm())
                .email(member.getEmail())
                .mobile(member.getMobile())
                .role(member.getRole())
                .authorities(authorities)
                .build();
    }
}
