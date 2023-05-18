    package org.project.restcontrollers;

    import org.project.commons.CommonException;
    import org.project.commons.rests.JSONData;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.ExceptionHandler;
    import org.springframework.web.bind.annotation.RestControllerAdvice;

    @RestControllerAdvice("org.project.restcontrollers")
    public class CommonRestController {
        @ExceptionHandler(Exception.class)
        public ResponseEntity<JSONData<Object>> errorHandler(Exception e){
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            if(e instanceof CommonException){
                CommonException commonException = (CommonException) e;
                status = commonException.getStatus();
            }

        JSONData<Object> jsonData = JSONData.builder()
                .success(false)
                .message(e.getMessage())
                .status(status)
                .build();

            return ResponseEntity.status(status).body(jsonData);
        }
    }
