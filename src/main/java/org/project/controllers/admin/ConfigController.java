package org.project.controllers.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.project.commons.configs.ConfigInfoService;
import org.project.commons.configs.ConfigSaveService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log
@Controller
@RequestMapping("/admin/config")
@RequiredArgsConstructor
public class ConfigController {

    private final ConfigSaveService saveService;
    private final ConfigInfoService infoService;
    private String code = "siteConfig";

    @GetMapping
    public String config(Model model){
        commonProcess(model);
        ConfigForm configForm = infoService.get(code, ConfigForm.class);
        model.addAttribute("configForm",configForm == null ? new ConfigForm() : configForm);
        return "admin/config";
    }

    @PostMapping
    public String configPs(ConfigForm configForm, Model model){
        commonProcess(model);
        saveService.save(code,configForm);

        model.addAttribute("message","설정이 저장되었습니다.");

        return "admin/config";
    }

    // 컨트롤러 공통 처리 편의 기능
    private void commonProcess(Model model){
        String title = "사이트 관리";
        String menuCode="config";
        model.addAttribute("pageTitle",title);
        model.addAttribute("title",title);
        model.addAttribute("menuCode",menuCode); // 선택된 메뉴 항목 hover 처리 위한 변수
    }
}
