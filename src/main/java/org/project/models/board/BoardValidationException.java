package org.project.models.board;

import org.project.commons.CommonException;
import org.springframework.http.HttpStatus;

/**
 * 게시판 유효성 검사 관련 예외
 */
public class BoardValidationException extends CommonException {
    public BoardValidationException(String code) {
        super(bundleValidation.getString(code), HttpStatus.BAD_REQUEST);
    }
}
