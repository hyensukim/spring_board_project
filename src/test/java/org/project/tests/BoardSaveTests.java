package org.project.tests;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.project.controllers.admin.BoardForm;
import org.project.controllers.boards.BoardDataForm;
import org.project.controllers.member.JoinForm;
import org.project.entities.BoardEntity;
import org.project.entities.MemberEntity;
import org.project.models.board.BoardDataSaveService;
import org.project.models.board.config.BoardConfigInfoService;
import org.project.models.board.config.BoardConfigSaveService;
import org.project.models.member.MemberSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc // 통합테스트
@TestPropertySource(locations="classpath:application-test.properties")
@DisplayName("게시글 등록, 수정 테스트")
public class BoardSaveTests {
    @Autowired
    private BoardDataSaveService saveService;

    @Autowired
    private BoardConfigSaveService configSaveService;

    private BoardConfigInfoService configInfoService;

    @Autowired
    private MemberSaveService memberSaveService;

    private BoardEntity board;

    private JoinForm joinForm;

    @BeforeEach
    @Transactional
    void init(){
        // 게시판 설정 추가
        BoardForm boardForm = new BoardForm();
        boardForm.setBId("freeTalk");
        boardForm.setBName("자유게시판");
        configSaveService.save(boardForm);

        board = configInfoService.get(boardForm.getBId(), true);

        //회원 가입 추가
        joinForm = JoinForm.builder()
                .memberId("user01")
                .memberPw("aA123456")
                .memberPwRe("aA123456")
                .email("user01@test.org")
                .memberNm("사용자01")
                .mobile("01000000000")
                .termsAgrees(new boolean[]{true})
                .build();
        memberSaveService.save(joinForm);
    }

    private BoardDataForm getBoardDataForm(){
        return BoardDataForm.builder()
                .bId(board.getBId())
                .guestPw("12345678")
                .poster("비회원")
                .subject("제목")
                .content("내용")
                .category(board.getCategory() == null ? null : board.getCategories()[0])
                .build();
    }

    // 임시로 로그인 상태를 유지하기 위한 설정
    // @WithMockUser(username="user01",password="aA123456")
    private BoardDataForm getMemberBoardDataForm(){
        return BoardDataForm.builder()
                .bId(board.getBId())
                .poster(joinForm.getMemberNm())
                .subject("제목")
                .content("내용")
                .category(board.getCategory() == null ? null : board.getCategories()[0])
                .build();
    }

    @Test
    @DisplayName("게시글 성공 시 예외 없음")
    void registerSuccessTest(){
        assertDoesNotThrow(()->{
            saveService.save(getBoardDataForm());
        });
    }

}
