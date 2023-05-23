package org.project.models.board.config;

import lombok.RequiredArgsConstructor;
import org.project.commons.MemberUtil;
import org.project.commons.constants.Role;
import org.project.entities.BoardEntity;
import org.project.repositories.BoardRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardConfigInfoService {

    private final BoardRepository repository;
    private final MemberUtil memberUtil; // 로그인 정보 필요

    public BoardEntity get(String bId, String location){ // 프론트, 접근 권한 체크
        return get(bId,false, location);
    }

    public BoardEntity get(String bId, boolean isAdmin){
        return get(bId, isAdmin, null);
    }

    /**
     * 게시판 설정 조회
     * @param bId
     * @param isAdmin : true - 권한 체크 X
     *                : false - 권한 체크, lacation으로 목록, 보기 , 쓰기, 답글, 댓글
     * @param location : 기능 위치(list,view,write,reply,comment)
     * @return
     */
    public BoardEntity get(String bId, boolean isAdmin, String location){

        BoardEntity board = repository.findById(bId).orElseThrow(BoardConfigNotExistException::new);

        if(!isAdmin){ // 권한 체크
            accessCheck(board,location);
        }

        return board;
    }


    /**
     * 접근 권한 체크 - 공통 기능은 분리해서 메서드를 별도로 정의하는 것이 유용하다.
     * @param board
     */
    private void accessCheck(BoardEntity board, String location){
        Role role = Role.ALL;
        if(location.equals("list")){ // 목록 접근 권한
            role = board.getLiAccessRole();
        }else if(location.equals("view")){ // 보기 접근 권한
            role = board.getVAccessRole();
        }else if(location.equals("write")){ // 쓰기 접근 권한
            role = board.getWAccessRole();

            if(!memberUtil.isLogin()) {board.setGuest(true);}//비회원 권한 부여

        }else if(location.equals("reply")){ // 답글 접근 권한
            role = board.getReplyAccessRole();
        }else if(location.equals("comment")){ // 댓글 접근 권한
            role = board.getCommentAccessRole();
        }

        if((role == Role.MEMBER && !memberUtil.isLogin())
        || (role == Role.ADMIN && !memberUtil.isAdmin())){
            throw new BoardNotAllowAccessException();
        }
    }
}
