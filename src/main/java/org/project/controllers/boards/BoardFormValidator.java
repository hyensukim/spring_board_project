package org.project.controllers.boards;

import lombok.RequiredArgsConstructor;
import org.project.commons.MemberUtil;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class BoardFormValidator implements Validator {

    private final MemberUtil memberUtil;

    @Override
    public boolean supports(Class<?> clazz) {
        return BoardDataForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BoardDataForm boardDataForm = (BoardDataForm) target;
        /** 비회원 - 비밀번호 체크
         * - 필수
         * */

        String guestPw = boardDataForm.getGuestPw();
        if(!memberUtil.isLogin()){
            if(guestPw == null || guestPw.isBlank()){
                errors.rejectValue("guestPw","NotBlank");
            }
        }

        if(guestPw != null && guestPw.length() < 6){
            errors.rejectValue("guestPw","Size");
        }
    }
}
