package org.project.commons.validators;

public interface MobileFormCheck {

    default boolean mobileFormCheck(String mobile){
        /**
         *  연락처 형식
         *  - 01[0,1,6,7] - [0-9][3,4] - [0-9][4]
         *  - 숫자가 아닌 문자 모두 제거(숫자로 형식 통일화)
         *  - 패턴 생성 및 match()로 체크
         */

        //형식 통일
        mobile.replaceAll("\\D","");

        //패턴 체크
        String pattern = "^01[016]\\d{3,4}\\d{4}$";

        return mobile.matches(pattern);
    }
}
