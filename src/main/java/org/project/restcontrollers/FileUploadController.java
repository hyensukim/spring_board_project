package org.project.restcontrollers;

import org.project.commons.CommonException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileUploadController {

    @GetMapping("file/upload")
    public void upload(){
        boolean result = true;
        if(result) {
            throw new CommonException("예외발생", HttpStatus.BAD_REQUEST);
        }
    }
}
