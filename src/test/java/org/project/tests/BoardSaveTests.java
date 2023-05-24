package org.project.tests;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.project.commons.configs.ConfigSaveService;
import org.project.controllers.admin.BoardForm;
import org.project.controllers.admin.ConfigForm;
import org.project.controllers.boards.BoardDataForm;
import org.project.controllers.member.JoinForm;
import org.project.entities.BoardEntity;
import org.project.models.board.BoardDataSaveService;
import org.project.models.board.BoardValidationException;
import org.project.models.board.config.BoardConfigInfoService;
import org.project.models.board.config.BoardConfigSaveService;
import org.project.models.member.MemberSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcConfigurer;
import org.springframework.test.web.servlet.setup.MockMvcConfigurerAdapter;

import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc // 통합테스트
@TestPropertySource(locations="classpath:application-test.properties")
@DisplayName("게시글 등록, 수정 테스트")
@Transactional
public class BoardSaveTests {
    @Autowired
    private BoardDataSaveService saveService;

    @Autowired
    private BoardConfigSaveService configSaveService;
    @Autowired
    private BoardConfigInfoService configInfoService;

    @Autowired
    private MemberSaveService memberSaveService;

    @Autowired
    private ConfigSaveService siteConfigSaveService;

    private BoardEntity board;

    private JoinForm joinForm;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    @Transactional
    void init(){
        //사이트 설정 추가
        siteConfigSaveService.save("siteConfig",new ConfigForm());

        // 게시판 설정 추가
        BoardForm boardForm = new BoardForm();
        boardForm.setBId("freeTalk");
        boardForm.setBName("자유게시판");
        configSaveService.save(boardForm);

        board = configInfoService.get(boardForm.getBId(), true);

        //회원 가입 추가
        joinForm = JoinForm.builder()
                .memberId("user01")
                .memberPw("aA!123456")
                .memberPwRe("aA!123456")
                .email("user01@test.org")
                .memberNm("사용자01")
                .mobile("01000000000")
                .termsAgrees(new boolean[]{true})
                .build();
        memberSaveService.save(joinForm);
    }


    private BoardDataForm getBoardDataForm(){
        BoardDataForm boardDataForm = getCommonBoardDataForm();
        boardDataForm.setGuestPw("12345678");
        return boardDataForm;
    }

    // 임시로 로그인 상태를 유지하기 위한 설정
    private BoardDataForm getCommonBoardDataForm(){
        return BoardDataForm.builder()
                .bId(board.getBId())
//                .gId(UUID.randomUUID().toString())
                .poster(joinForm.getMemberNm())
                .subject("제목")
                .content("내용")
                .category(board.getCategory() == null ? null : board.getCategories()[0])
                .build();
    }

    @Test
    @DisplayName("게시글 등록(비회원) 성공시 예외 없음")
    @WithAnonymousUser
    void registerGuestSuccessTest() {
        assertDoesNotThrow(() -> {
            saveService.save(getBoardDataForm());
        });
    }

    @Test
    @DisplayName("게시글 등록(회원) 성공시 예외 없음")
    @WithMockUser(username="user01",password="aA!123456")
    @Disabled
    void registerMemberSuccessTest() {
        assertDoesNotThrow(() -> {
            saveService.save(getCommonBoardDataForm());
        });
    }

    @Test
    @DisplayName("필수 항목 검증(비회원) -bId, gId, poster, subject, content, guestPw(자리수는 6자리 이상)" +
            ", BoardValidationException 발생")
    @WithAnonymousUser
    void requiredFieldsGuestTest(){
        commonRequiredFieldsTest();

        assertThrows(BoardValidationException.class,()->{
           BoardDataForm boardDataForm = getBoardDataForm();
           boardDataForm.setGuestPw(null);
           saveService.save(boardDataForm);
        });

        assertThrows(BoardValidationException.class,()->{
            BoardDataForm boardDataForm = getBoardDataForm();
            boardDataForm.setGuestPw("                       ");
            saveService.save(boardDataForm);
        });
        assertThrows(BoardValidationException.class,()->{
            BoardDataForm boardDataForm = getBoardDataForm();
            boardDataForm.setGuestPw("1234");
            saveService.save(boardDataForm);
        });

    }

    @Test
    @DisplayName("필수 항목 검증(회원) - bId,gId,poster,subject,content,BoarValidationException 발생")
    @WithMockUser(username="user01", password="aA!123456")
    void requiredFieldsMemberTest(){
        commonRequiredFieldsTest();
    }

    @Test
    @DisplayName("통합테스트 - 비회원 게시글 작성 유효성 검사")
    void requiredFieldsGuestControllerTest() throws Exception{
        BoardDataForm boardDataForm = getBoardDataForm();
        mockMvc.perform(post("/board/save")
                .param("bId",boardDataForm.getBId())
                .param("gId",boardDataForm.getGId())
                        .with(csrf().asHeader()))
                .andDo(print());
    }


    // 회원 / 비회원 공통 유효성 검사 체크 기능
    private void commonRequiredFieldsTest(){
        assertAll(
                //bId == null
                () -> assertThrows(BoardValidationException.class,()->{
                    BoardDataForm boardDataform = getCommonBoardDataForm();
                    boardDataform.setBId("    ");
                    saveService.save(boardDataform);
                }),
                () -> assertThrows(BoardValidationException.class,()->{
                    BoardDataForm boardDataform = getCommonBoardDataForm();
                    boardDataform.setBId(null);
                    saveService.save(boardDataform);
                }),
                //gId
                () -> assertThrows(BoardValidationException.class,()->{
                    BoardDataForm boardDataform = getCommonBoardDataForm();
                    boardDataform.setGId("    ");
                    saveService.save(boardDataform);
                }),
                () -> assertThrows(BoardValidationException.class,()->{
                    BoardDataForm boardDataform = getCommonBoardDataForm();
                    boardDataform.setBId(null);
                    saveService.save(boardDataform);
                }),
                // poster
                () -> assertThrows(BoardValidationException.class,()->{
                    BoardDataForm boardDataform = getCommonBoardDataForm();
                    boardDataform.setPoster("    ");
                    saveService.save(boardDataform);
                }),
                () -> assertThrows(BoardValidationException.class,()->{
                    BoardDataForm boardDataform = getCommonBoardDataForm();
                    boardDataform.setPoster(null);
                    saveService.save(boardDataform);
                }),
                () -> assertThrows(BoardValidationException.class,()->{
                    BoardDataForm boardDataform = getCommonBoardDataForm();
                    boardDataform.setSubject("    ");
                    saveService.save(boardDataform);
                }),
                () -> assertThrows(BoardValidationException.class,()->{
                    BoardDataForm boardDataform = getCommonBoardDataForm();
                    boardDataform.setSubject(null);
                    saveService.save(boardDataform);
                }),
                () -> assertThrows(BoardValidationException.class,()->{
                    BoardDataForm boardDataform = getCommonBoardDataForm();
                    boardDataform.setContent("    ");
                    saveService.save(boardDataform);
                }),
                () -> assertThrows(BoardValidationException.class,()->{
                    BoardDataForm boardDataform = getCommonBoardDataForm();
                    boardDataform.setContent(null);
                    saveService.save(boardDataform);
                })
        );
    }
}
