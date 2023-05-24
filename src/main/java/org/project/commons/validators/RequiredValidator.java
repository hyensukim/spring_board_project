package org.project.commons.validators;

/**
 * 필수 데이터 검증 기능 인터페이스
 */

public interface RequiredValidator {
    default void requiredCheck(String str, RuntimeException e){
        if(str ==null || str.isBlank()){
            throw e;
        }
    }
}
