package org.project.controllers.boards;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.project.entities.MemberEntity;

import java.util.UUID;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class BoardDataForm {

    private Long id;//게시글 번호
    @NotBlank
    private String bId; // 게시판 정보
    @NotBlank
    private String gId = UUID.randomUUID().toString(); // 회원당 업로드한 파일 묶음 시 참고번호
    @NotBlank
    private String poster; // 작성자

    private String guestPw; // 비회원 전용 비밀번호

    private String category; // 분류
    @NotBlank
    private String subject; // 제목
    @NotBlank
    private String content; // 내용

}
