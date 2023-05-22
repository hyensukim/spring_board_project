package org.project.models.board.config;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.project.controllers.admin.BoardSearch;
import org.project.entities.BoardEntity;
import org.project.entities.QBoardEntity;
import org.project.repositories.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import static org.springframework.data.domain.Sort.Order.desc;

/**
 * 게시판 설정 목록(리스트 조회)
 */
@Service
@RequiredArgsConstructor
public class BoardConfigListService {

    private final BoardRepository repository;

    public Page<BoardEntity> gets(BoardSearch boardSearch){
        QBoardEntity board = QBoardEntity.boardEntity;
        BooleanBuilder andBuilder = new BooleanBuilder();

        int page = boardSearch.getPage();
        int limit = boardSearch.getLimit();
        page = page < 1 ? 1 : page;
        limit = limit < 1 ? 20 : limit;

        /** 검색 조건 처리 S */
        String sOpt = boardSearch.getSOpt();
        String sKey = boardSearch.getSKey();
        if(sOpt != null && !sOpt.isBlank() &&  sKey != null &&!sKey.isBlank()){
            sKey = sKey.trim();
            sOpt = sOpt.trim();

            if(sOpt.equals("all")){ // 통합 검색 - bId, bName
                BooleanBuilder orBuilder = new BooleanBuilder();
                orBuilder.or(board.bId.contains(sKey))
                        .or(board.bName.contains(sKey));
                andBuilder.and(orBuilder);
            }else if(sOpt.equals("bId")){ // 게시판 아이디 - bId
                andBuilder.and(board.bId.contains(sKey));
            }else if(sOpt.equals("bName")){ // 게시판 이름 - bName
                andBuilder.and(board.bName.contains(sKey));
            }
        }
        /** 검색 조건 처리 E */

        Pageable pageable = PageRequest.of(page -1, limit, Sort.by(desc("createdAt")));
        Page<BoardEntity> data = repository.findAll(andBuilder, pageable);

        return data;

    }
}
