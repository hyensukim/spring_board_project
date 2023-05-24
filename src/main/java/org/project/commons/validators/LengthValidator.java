package org.project.commons.validators;

public interface LengthValidator {
    /**
     *
     * @param str
     * @param min 최소 문자 갯수, 0인 경우 확인 X
     * @param max 최대 문자 갯수, 0인 경우 확인 X
     */
    default void lengthCheck(String str, int min, int max, RuntimeException e){
        if(str == null || (min > 0 && str.length() < min) ||(max > 0 && str.length() > max)){
            throw e;
        }
    }

    default void lengthCheck(String str, int min, RuntimeException e){
        lengthCheck(str,min,0,e);
    }

    default void lengthCheck(long num, int min, int max, RuntimeException e){
        if(num < min || num > max){
            throw e;
        }
    }

    default void lengthCheck(long num, int min, RuntimeException e){
        if(num <min){
            throw e;
        }
    }

}
