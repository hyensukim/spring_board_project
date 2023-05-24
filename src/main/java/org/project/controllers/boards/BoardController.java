package org.project.controllers.boards;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.project.commons.CommonException;
import org.project.commons.MemberUtil;
import org.project.entities.BoardEntity;
import org.project.models.board.BoardDataSaveService;
import org.project.models.board.config.BoardConfigInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardFormValidator formValidator;
    private final BoardDataSaveService saveService;
    private final BoardConfigInfoService configInfoService;
    private final HttpServletResponse response;
    private final MemberUtil memberUtil;

    private BoardEntity board; // 게시판 설정

    /**
     * 게시글 목록
     * @param bId
     * @return
     */
    @GetMapping("/list/{bId}")
    public String list(@PathVariable String bId, Model model) {
        commonProcess(bId, "list",model);
        return "board/list";
    }

    /**
     * 게시글 작성
     *
     * @param bId
     * @return
     */
    @GetMapping("/write/{bId}")
    public String write(@PathVariable String bId, Model model,
                        @ModelAttribute BoardDataForm boardDataForm) {
        commonProcess(bId,"write",model);
        boardDataForm.setBId(bId);
        if(memberUtil.isLogin()){
            boardDataForm.setPoster(memberUtil.getMember().getUsername());
        }
        return "board/write";
    }

    /**
     * 게시글 수정
     * @param id
     * @return
     */
    @GetMapping("/{id}/update")
    public String update(@PathVariable Long id, Model model) {
        commonProcess(null,"update",model);
        return "board/update";
    }

    @PostMapping("/save")
    public String save(@Valid BoardDataForm boardDataForm, Errors errors, Model model){
        Long id = boardDataForm.getId();
        String mode =id == null ? "write" : "update";
        commonProcess(boardDataForm.getBId(),mode,model);
        formValidator.validate(boardDataForm,errors);
        if(errors.hasErrors()){
            return "board/" + mode;
        }
        saveService.save(boardDataForm);
        // 작성후 이동 설정 - 목록, 글보기

        String location = board.getLocationAfterWriting();
        String url = "redirect:/board/";
        url += location.equals("view") ? "view/" + boardDataForm.getId() : "list/" + boardDataForm.getBId();
        return url;
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, Model model) {
        commonProcess(null,"view",model);
        return "board/view";
    }

    private void commonProcess(String bId, String action, Model model){
        /**
         * 게시판 설정 조회 -bId
         * action - write/update 공통 스크립트, 공통 CSS
         *        - 에디터 사용 -> 에디터 스크립트 추가
         *        - 에디터 미사용 -> 에디터 스크립트 미추가
         *        - write, list, view - 권한 체크
         *        - update - 본인이 게시글만 수정 가능
         *                 - 회원 - 회원번호
         *                 - 비회원 - 비회원 비밀번호
         *                 - 관리자 - 모두 가능
         */
        board = configInfoService.get(bId,action);
        List<String> addCss = new ArrayList<>();
        List<String> addScript = new ArrayList<>();

        // 공통 스타일 CSS
        addCss.add("board/style");
        addCss.add(String.format("board/%s_style",board.getSkin())); // 스킨별 스타일

        // 글작성, 글수정 시 필요한 자바스크립트 추가
        if(action.equals("write") || action.equals("update")){
            if(board.isUseEditor()){ // 에디터 사용 경우
                addScript.add("ckeditor/ckeditor");
            }
            addScript.add("board/form");
        }

        // 공통 필요 속성 추가
        model.addAttribute("board",board); // 게시판 설정
        model.addAttribute("addCss",addCss);
        model.addAttribute("addScript",addScript);
    }

    @ExceptionHandler({CommonException.class})
    public String errorHandler(CommonException e, Model model){
        e.printStackTrace();
        String message = e.getMessage();
        HttpStatus status = e.getStatus();
        response.setStatus(status.value());
        String script = String.format("alert('%s');history.back()",message);
        model.addAttribute("script",script);

        return"commons/execute_script";

    }
}
