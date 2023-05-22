package org.project.controllers.admin;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardForm {

    private String mode; // update : 수정, 없으면 추가

    @NotBlank
    private String bId; // 게시판아이디

    @NotBlank
    private String bName; // 게시판이름

    private boolean bUse; // 사용여부

    private int rowsOfPage = 20; // 한페이지 당 게시글 수

    private boolean showList; // 게시글 하단 목록

    private String category;//게시판 분류

    private String liAccessRole = "ALL"; //글목록 접근권한

    private String vAccessRole = "ALL"; //글보기 접근권한

    private String wAccessRole = "ALL"; //글쓰기 접근권한

    private String replyAccessRole = "ALL"; //답글 접근권한

    private String commentAccessRole = "ALL"; //댓글 접근권한

    private boolean useEditor; // 에디터 사용여부

    private boolean useAttachFile;// 파일 첨부 사용여부

    private boolean useAttachImage; // 이미지 첨부 사용여부

    private String locationAfterWriting = "view"; // 글작성 후 이동 관련 설정 기능

    private boolean useReply;//답글 사용여부

    private boolean useComment;//댓글 사용여부

    private String skin = "default";//게시판 스킨
}
