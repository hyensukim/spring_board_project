package org.project.controllers.member;

import lombok.RequiredArgsConstructor;
import org.project.commons.validators.MobileFormCheck;
import org.project.repositories.MemberRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class JoinValidator implements Validator, MobileFormCheck {

    private final MemberRepository memberRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return JoinForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        /**
         * 1. 아이디 중복 여부 검증
         * 2. 비밀번호 복잡성(영대소문자, 숫자, 특수문자 포함)
         * 3. 비밀번호와 비밀번호 확인 일치
         * 4. 휴대전화번호(선택) - 입력된 경우 형식 체크
         * 5. 휴대전화번호가 입력된 경우 숫자만 추출해서 다시 커맨드 객체에 저장
         * 6. 필수 약관 동의 체크(필수 입력 검증)
         */

        JoinForm joinForm = (JoinForm)target;
        String id = joinForm.getMemberId();
        String pw = joinForm.getMemberPw();
        String pwRe = joinForm.getMemberPwRe();
        String mobile = joinForm.getMobile();
        boolean[] agrees = joinForm.getTermsAgrees(); // 필수 약관

        //1. 아이디 중복 체크
        if(id != null && !id.isBlank() && memberRepository.isExists(id)){
            errors.rejectValue("memberId","Validation.duplicate.memberId");
        }

        //3. 비밀번호와 비밀번호 확인 일치
        if(pw != null && !pw.isBlank() && pwRe != null && !pwRe.isBlank()
                && !pw.equals(pwRe)){
            errors.rejectValue("memberPwRe","Validation.incorrect.memberPwRe");
        }
        
        //4. 휴대전화번호(선택) - 입력된 경우 형식 체크
        //5. 휴대전화 번호가 입력된 경우 숫자만 추출해서 다시 커맨드 객체에 저장
        if(mobile !=null && !mobile.isBlank() ){
            if(!mobileFormCheck(mobile))
                errors.rejectValue("mobile","Validation.mobile");

            mobile = mobile.replaceAll("\\D","");
            joinForm.setMobile(mobile);
        }

        //6. 필수 약관 동의 체크
        if(agrees != null && agrees.length > 0){
            for(boolean agree : agrees){
                if(!agree){
                    errors.reject("Validation.joinForm.agree");
                    break;
                }
            }
        }

    }
}
