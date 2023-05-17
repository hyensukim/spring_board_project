package org.project.commons.validators;

import java.util.regex.Pattern;

public interface PwValidator {

    /**
     *  비밀번호 복잡성 체크 -알파벳 체크
     *
     * @param pw
     * @param caseIncentive
     *  - false : 소문자 + 대문자가 반드시 포함되는 패턴
     *  - true : 대소문자 상관없이 포함되는 패턴
     * @return
     */
    default boolean alphabetCheck(String pw, boolean caseIncentive){
        if(caseIncentive) {// 대소문자 구분 X 체크
            Pattern pattern = Pattern.compile("[a-zA-Z]+",Pattern.CASE_INSENSITIVE);
            return pattern.matcher(pw).find();
        }
        // 대소문자 각각 체크
        Pattern pattern1 = Pattern.compile("[a-z]+");
        Pattern pattern2 = Pattern.compile("[A-Z]+");
        return pattern1.matcher(pw).find() &&
                pattern2.matcher(pw).find();
    }

    /**
     * 숫자가 포함된 패턴 유무 체크
     * @param pw
     * @return
     */
    default boolean numberCheck(String pw){
        Pattern pattern = Pattern.compile("\\d+");
        return pattern.matcher(pw).find();
    }

    /**
     * 특수문자 포함된 패턴 유무 체크
     * @param pw
     * @return
     */
    default boolean specialCharsCheck(String pw){
        Pattern pattern = Pattern.compile("[`~!@#$%^&*()-_+=]+");
        return pattern.matcher(pw).find();
    }
}
