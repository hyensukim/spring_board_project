package org.project.tests;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.project.controllers.admin.BoardForm;
import org.project.controllers.boards.BoardDataForm;
import org.project.entities.BoardDataEntity;
import org.project.entities.BoardEntity;
import org.project.models.board.BoardDataInfoService;
import org.project.models.board.BoardDataSaveService;
import org.project.models.board.config.BoardConfigInfoService;
import org.project.models.board.config.BoardConfigSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
@AutoConfigureMockMvc
@Transactional
public class BoardViewTests {

    private BoardEntity board;

    private BoardDataEntity boardData;
    private Long id; // 게시글 번호

    private BoardConfigSaveService configSaveService;

    @Autowired
    private BoardDataSaveService boardDataSaveService;
    @Autowired
    private BoardConfigInfoService configInfoService;

    @Autowired
    private BoardDataInfoService boardDataInfoService;

    @BeforeEach
    void init(){
        // 게시판 설정 추가
        BoardForm boardForm = new BoardForm();
        boardForm.setBId("freeTalk");
        boardForm.setBName("자유게시판");
        configSaveService.save(boardForm);
        board = configInfoService.get(boardForm.getBId(), true);

        //테스트용 기본 게시글 추가
        BoardDataForm boardDataForm = BoardDataForm.builder()
                .bId(board.getBId())
                .gId(UUID.randomUUID().toString())
                .subject("제목")
                .content("내용")
                .category(board.getCategory() == null ? null : board.getCategories()[0])
                .build();
        boardDataSaveService.save(boardDataForm);
        id = boardDataForm.getId();
    }

    @Test
    @DisplayName("게시글 조회 성공 시 예외 없음")
    void getBoardDataSuccessTest(){
        assertDoesNotThrow(()->{
            boardDataInfoService.get(id);
        });
    }
}
