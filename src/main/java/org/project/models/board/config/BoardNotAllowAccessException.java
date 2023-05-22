package org.project.models.board.config;

import org.project.commons.CommonException;
import org.springframework.http.HttpStatus;

public class BoardNotAllowAccessException extends CommonException {
    public BoardNotAllowAccessException(){
        super(bundleValidation.getString("Validation.board.NotAllowAccess"), HttpStatus.UNAUTHORIZED);
    }
}
