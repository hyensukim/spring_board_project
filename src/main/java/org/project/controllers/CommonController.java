package org.project.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.project.commons.CommonException;
import org.project.commons.MemberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice("org.project.controllers")
public class CommonController {

    @ExceptionHandler(Exception.class)
    public String errorHandler(Exception e, Model model, HttpServletResponse response, HttpServletRequest request){
        int status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        if(e instanceof CommonException){
            CommonException commonException = (CommonException) e;
            status = commonException.getStatus().value();
        }

        response.setStatus(status);
        StringBuffer URL = request.getRequestURL();
        model.addAttribute("status",status);
        model.addAttribute("exception",e);
        model.addAttribute("path",URL);
        model.addAttribute("message",e.getMessage());

        e.printStackTrace();

        return "templates/error/common";
    }
}
