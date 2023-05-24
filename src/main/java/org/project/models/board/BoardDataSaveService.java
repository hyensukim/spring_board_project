package org.project.models.board;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.project.commons.MemberUtil;
import org.project.controllers.boards.BoardDataForm;
import org.project.entities.BoardDataEntity;
import org.project.entities.BoardEntity;
import org.project.models.board.config.BoardConfigInfoService;
import org.project.repositories.BoardDataRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardDataSaveService {

    private final MemberUtil memberUtil;
    private final BoardValidator boardValidator;
    private final BoardConfigInfoService configInfoService;
    private final BoardDataRepository repository;
    private final HttpServletRequest request;
    private final PasswordEncoder passwordEncoder;

    public void save(BoardDataForm boardDataForm){
        boardValidator.check(boardDataForm);

        // 게시글 저장 처리 - 추가/수정 (id로 구분)

        /**
         * 1. 게시판 설정 - 글 작성/수정 권한 검증
         *              - 수정 : 본인이 작성한 글인지 검증
         * 2. 게시글 저장 및 조회하여 수정
         * 3. 회원정보 - 게시글 등록시에만 저장(유일한 값으로 저장하기 위함)
         * 4.
         */
        Long id = boardDataForm.getId();
        BoardEntity board = configInfoService.get(boardDataForm.getBId(), id == null ? "write" : "update");

        BoardDataEntity boardData = null;
        if( id == null){ // 게시글 추가
            String ip = request.getRemoteAddr();
            String ua = request.getHeader("User-Agent");
            boardData = BoardDataEntity.builder()
                    .gId(boardDataForm.getGId())
                    .board(board)
                    .category(boardDataForm.getCategory())
                    .poster(boardDataForm.getPoster())
                    .subject(boardDataForm.getSubject())
                    .content(boardDataForm.getContent())
                    .ip(ip)
                    .ua(ua)
                    .build();
//            boardData.setBoard(board);
            if(memberUtil.isLogin()){
                boardData.setMember(memberUtil.getEntity());
            }else{
                boardData.setGuestPw((passwordEncoder.encode(boardDataForm.getGuestPw())));
            }

        }else{ // 게시글 수정

        }

        boardData = repository.saveAndFlush(boardData);
        boardDataForm.setId(boardData.getId());

    }
}
