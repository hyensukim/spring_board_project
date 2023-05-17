package org.project.commons;

import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.project.commons.constants.Role;
import org.project.entities.MemberEntity;
import org.project.models.member.MemberInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberUtil {

    @Autowired
    private HttpSession session;

    /**
     * 로그인 회원 정보 조회 편의 기능 구현
     * @return
     */
    public boolean isLogin(){
        return getMember() != null;
    }

    public boolean isAdmin(){
        return isLogin() && getMember().getRole() == Role.ADMIN;
    }

    public MemberInfo getMember(){
        MemberInfo memberInfo = (MemberInfo) session.getAttribute("memberInfo");
        return memberInfo;
    }

    public MemberEntity getEntity(){
        if(isLogin()){
            MemberEntity member = new ModelMapper().map(getMember(),MemberEntity.class);
            return member;
        }
        return null;
    }
}
