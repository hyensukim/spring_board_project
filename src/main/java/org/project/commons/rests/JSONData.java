package org.project.commons.rests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

// JSON 형태로 예외처리를 위한 커맨드 객체
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class JSONData<T> {
    private boolean success; // true -> 전송
    private HttpStatus status = HttpStatus.OK; // 200
    private String message;
    private T data;
}