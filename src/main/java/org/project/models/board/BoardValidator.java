package org.project.models.board;

import org.project.commons.MemberUtil;
import org.project.commons.validators.LengthValidator;
import org.project.commons.validators.RequiredValidator;
import org.project.commons.validators.Validator;
import org.project.controllers.boards.BoardDataForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BoardValidator implements Validator<BoardDataForm>, RequiredValidator, LengthValidator {

    @Autowired
    private MemberUtil memberUtil;

    @Override
    public void check(BoardDataForm boardDataForm) {
        requiredCheck(boardDataForm.getBId(), new BoardValidationException(("BadRequest")));
        requiredCheck(boardDataForm.getGId(), new BoardValidationException(("BadRequest")));
        requiredCheck(boardDataForm.getPoster(), new BoardValidationException(("NotBlank.boardDataForm.poster")));
        requiredCheck(boardDataForm.getSubject(), new BoardValidationException(("NotBlank.boardDataForm.subject")));
        requiredCheck(boardDataForm.getContent(), new BoardValidationException("NotBlank.boardDataForm.content"));

        if(!memberUtil.isLogin()){
            requiredCheck(boardDataForm.getGuestPw(),new BoardValidationException("NotBlank.boardDataForm.guestPw"));
            lengthCheck(boardDataForm.getGuestPw(), 6, new BoardValidationException("Size.boardDataForm.guestPw"));
        }

    }
}
