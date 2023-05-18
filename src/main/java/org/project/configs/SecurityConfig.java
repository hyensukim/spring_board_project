package org.project.configs;

import jakarta.servlet.http.HttpServletResponse;
import org.project.commons.MemberUtil;
import org.project.models.member.LoginFailureHandler;
import org.project.models.member.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * 스프링 시큐리티 설정
 */

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        // 로그인 & 로그아웃
        http.formLogin()
                .loginPage("/member/login")
                .usernameParameter("memberId")
                .passwordParameter("memberPw")
                .successHandler(new LoginSuccessHandler())
                .failureHandler(new LoginFailureHandler())
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                .logoutSuccessUrl("/member/login");

        // 웹페이지 접근 권한 제한(인증, 인가)
        http.authorizeHttpRequests()
                .requestMatchers("/mypage/**").authenticated() // 마이페이지 - 회원 전용
//                .requestMatchers("/admin/**").hasAuthority("ADMIN") // 관리자페이지 - 관리자 전용
                .anyRequest().permitAll();

        // 인증, 인가 후처리 설정
        http.exceptionHandling()
                .authenticationEntryPoint(
                        (req,resp,e)->{
                            String URI = req.getRequestURI();
                            if(URI.indexOf("/admin") != -1){ // 관리자 페이지 접근 시 에러 발생 처리
                                resp.sendError(HttpServletResponse.SC_UNAUTHORIZED,"NOT AUTHORIZED");
                            }else{ // 회원 페이지 접근 시 로그인 화면 리다이렉션 처리
                                String redirectURL = req.getContextPath() + "/member/login";
                                resp.sendRedirect(redirectURL);
                            }
                        });
        http.headers().frameOptions().sameOrigin(); // iframe 하나의 도메인에서 처리되도록 하기 위한 설정
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return w-> w.ignoring().requestMatchers("/css/**","/js/**","/images/**","/errors/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
