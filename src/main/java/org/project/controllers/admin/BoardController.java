package org.project.controllers.admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.project.commons.MenuDetail;
import org.project.commons.Menus;
import org.project.models.board.config.BoardConfigSaveService;
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
    /**
     * 게시판 목록
     *
     * @return
     */
    @GetMapping
    public String index(Model model){
        commonProcess(model, "게시판 목록");
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
        model.addAttribute("subMenuCode",subMenuCode);
        List<MenuDetail> submenus = Menus.gets("board");
        model.addAttribute("submenus",submenus);

    }
}
