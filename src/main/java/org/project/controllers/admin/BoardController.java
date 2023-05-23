package org.project.controllers.admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.commons.MenuDetail;
import org.project.commons.Menus;
import org.project.commons.constants.Role;
import org.project.entities.BoardEntity;
import org.project.models.board.config.BoardConfigInfoService;
import org.project.models.board.config.BoardConfigListService;
import org.project.models.board.config.BoardConfigSaveService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller("AdminBoardController")
@RequestMapping("admin/board")
@RequiredArgsConstructor
public class BoardController {

    private final HttpServletRequest request;
    private final BoardConfigSaveService configSaveService;
    private final BoardConfigInfoService configInfoService;
    private final BoardConfigListService configListService;

    /**
     * 게시판 목록
     *
     * @return
     */
    @GetMapping
    public String index(@ModelAttribute BoardSearch boardSearch, Model model){
        commonProcess(model, "게시판 목록");

        Page<BoardEntity> data = configListService.gets(boardSearch);
        model.addAttribute("items",data.getContent());

        return "admin/board/index";
    }

    /**
     *  게시판 등록
     * @param model
     * @return
     */
    @GetMapping("/register")
    public String register(@ModelAttribute BoardForm boardForm, Model model){
        commonProcess(model, "게시판 등록");
        return "admin/board/config";
    }

    @GetMapping("/{bId}/update")
    public String update(@PathVariable String bId,Model model)  {
        commonProcess(model, "게시판 수정");

        BoardEntity board = configInfoService.get(bId,true);
        BoardForm boardForm = new ModelMapper().map(board,BoardForm.class);
        boardForm.setMode("update");
        boardForm.setLiAccessRole(board.getLiAccessRole().toString());
        boardForm.setVAccessRole(board.getVAccessRole().toString());
        boardForm.setWAccessRole(board.getWAccessRole().toString());
        boardForm.setReplyAccessRole(board.getReplyAccessRole().toString());
        boardForm.setCommentAccessRole(board.getCommentAccessRole().toString());

        model.addAttribute("boardForm",boardForm);

        return "admin/board/config";
    }

    @PostMapping("/save")
    public String save(@Valid BoardForm boardForm, Errors errors, Model model){
        String mode = boardForm.getMode();
        commonProcess(model,mode != null && mode.equals("update") ? "게시판 수정" : "게시판 등록");

        try{
            configSaveService.save(boardForm,errors);
        }catch(Exception e){
            errors.reject("BoardConfigErrors",e.getMessage());
        }

        if(errors.hasErrors()){
            return "admin/board/config";
        }
        return "redirect:/admin/board"; // 게시판 목록으로
    }

    private void commonProcess(Model model, String title){
        model.addAttribute("pageTitle",title);
        model.addAttribute("title",title);
        model.addAttribute("menuCode","board");

        ///서브 메뉴 처리
        String subMenuCode = Menus.subMenuCode(request);
        subMenuCode = title.equals("게시판 수정") ? "register" : subMenuCode;
        model.addAttribute("subMenuCode",subMenuCode);
        List<MenuDetail> submenus = Menus.gets("board");
        model.addAttribute("submenus",submenus);

    }
}
