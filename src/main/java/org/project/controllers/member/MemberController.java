package org.project.controllers.member;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.project.models.member.MemberSaveService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberSaveService saveService;
    private final JoinValidator joinValidator;

    @GetMapping("/join")
    public String join(Model model){ // @ModelAttribute 사용 시 Model 없이도 템플릿에서 사용 가능
        commonProcess(model);
        JoinForm joinForm = new JoinForm();
        model.addAttribute("joinForm",joinForm);
        return "member/join";
    }

    @PostMapping("/join")
    public String joinPs(@Valid JoinForm joinForm, Errors errors,Model model){
        commonProcess(model);
        joinValidator.validate(joinForm,errors);

        if(errors.hasErrors()){
            return "member/join";
        }

        saveService.save(joinForm);
        return "redirect:/member/login";
    }

    @GetMapping("/login")
    public String login() {

        return "member/login";
    }

    private void commonProcess(Model model){
        model.addAttribute("pageTitle","회원가입");
    }

}
