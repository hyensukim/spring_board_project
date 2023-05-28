package org.project.configs.interceptors;

import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.project.commons.configs.ConfigInfoService;
import org.project.controllers.admin.ConfigForm;
import org.project.repositories.ConfigsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.Map;

/**
 * 사이트 설정 유지
 */
@Component("siteConf") // 빈이름 : siteConf로 지정.
@RequiredArgsConstructor
public class SiteConfigInterceptor implements HandlerInterceptor {

    private final ConfigInfoService infoService;
    private final HttpServletRequest request;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        /*사이트 설정 조회*/
        Map<String,String> siteConfigs = infoService.get("siteConfig", new TypeReference<Map<String, String>>(){});
        request.setAttribute("siteConfig", siteConfigs);
        return true;
    }

    public String get(String name){
        Map<String,String> siteConfig = (Map<String, String>) request.getAttribute("siteConfig");
        String value = siteConfig == null ? "" : siteConfig.get(name); // key 로 value 조회
        return value;
    }

}
