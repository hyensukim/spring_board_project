    package org.project.commons.restcontrollers;

    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.ExceptionHandler;
    import org.springframework.web.bind.annotation.RestControllerAdvice;

    @RestControllerAdvice("org.project.restcontrollers")
    public class CommonRestController {
        @ExceptionHandler(Exception.class)
        public String errorHandler(Exception e, Model model){
            return "templates/error/common";
        }
    }
