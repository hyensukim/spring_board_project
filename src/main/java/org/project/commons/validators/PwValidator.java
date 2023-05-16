package org.project.commons.validators;

public interface PwValidator {

    default boolean alphaCheck(String pw, boolean caseIncentive){ // 2번째 매개변수 : true - 대소문자 구분 X, false - 대소문자 구분 O
        /**
         *  비밀번호 복잡도 변경
         */
        return false;
    }
}
