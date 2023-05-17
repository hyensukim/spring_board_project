package org.project.models.member;

import org.project.commons.CommonException;
import org.springframework.http.HttpStatus;

public class LoginValidationException extends CommonException {

    private String field;

    public LoginValidationException(String errorCode) {
        super(bundleValidation.getString(errorCode), HttpStatus.BAD_REQUEST);
    }

    public LoginValidationException(String field, String errorCode) {
        this(errorCode);
        this.field = field;
    }

    public String getField() {
        return field;
    }

}
