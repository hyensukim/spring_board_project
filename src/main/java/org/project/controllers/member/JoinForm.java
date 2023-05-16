package org.project.controllers.member;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class JoinForm {
    @NotBlank
    @Size(max=6, min=20)
    private String memberId;
    @NotBlank
    @Size(min=8)
    private String memberPw;
    @NotBlank
    private String memberPwRe;
    @NotBlank
    private String memberNm;
    @NotBlank
    @Email
    private String email;
    private String mobile;
    // 약관 동의 여러개 일 수 있으므로, 배열 형태로 생성 -> Bean Validation으로 검증불가 -> 별도로 검증
    private boolean[] termsAgrees;
}
