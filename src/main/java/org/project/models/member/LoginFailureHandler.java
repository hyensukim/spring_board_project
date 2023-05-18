package org.project.models.member;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String memberId = request.getParameter("memberId");
        String memberPw = request.getParameter("memberPw");

        session.removeAttribute("memberId");
        session.removeAttribute("requiredMemberId");
        session.removeAttribute("requiredMemberPw");
        session.removeAttribute("global");

        session.setAttribute("memberId", memberId);

        try {
            if (memberId == null || memberId.isBlank()) {
                throw new LoginValidationException("requiredMemberId", "NotBlank.memberId");
            }
            if (memberPw == null || memberPw.isBlank()) {
                throw new LoginValidationException("requiredMemberPw", "NotBlank.memberPw");
            }
            throw new LoginValidationException("global", "Validation.login.fail");

        } catch (LoginValidationException e) {
            session.setAttribute(e.getField(), e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/member/login");
    }
}
