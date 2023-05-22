package org.project.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.project.commons.constants.Role;

@Entity @Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class BoardEntity extends BaseMemberEntity{

    @Id @Column(length=30)
    private String bId; // 게시판아이디

    @Column(length=60, nullable = false)
    private String bName; // 게시판이름

    private boolean bUse; // 사용여부

    private int rowsOfPage = 20; // 한페이지 당 게시글 수

    private boolean showList; // 게시글 하단 목록

    @Lob
    private String category;//게시판 분류

    @Enumerated(EnumType.STRING)
    @Column(length=10, nullable = false)
    private Role liAccessRole = Role.ALL; //글목록 접근권한

    @Enumerated(EnumType.STRING)
    @Column(length=10, nullable = false)
    private Role vAccessRole = Role.ALL; //글보기 접근권한

    @Enumerated(EnumType.STRING)
    @Column(length=10, nullable = false)
    private Role wAccessRole = Role.ALL; //글쓰기 접근권한

    @Enumerated(EnumType.STRING)
    @Column(length=10, nullable = false)
    private Role replyAccessRole = Role.ALL; //답글 접근권한

    @Enumerated(EnumType.STRING)
    @Column(length=10, nullable = false)
    private Role commentAccessRole = Role.ALL; //댓글 접근권한

    private boolean useEditor; // 에디터 사용여부

    private boolean useAttachFile;// 파일 첨부 사용여부

    private boolean useAttachImage; // 이미지 첨부 사용여부

    @Column(length=10, nullable=false)
    private String locationAfterWriting = "view"; // 글작성 후 이동 관련 설정 기능

    private boolean useReply;//답글 사용여부

    private boolean useComment;//댓글 사용여부

    @Column(length=20, nullable=false)
    private String skin = "default";//게시판 스킨

    /**
     * 게시판 분류 목록
     */
    public String[] getCategories(){
        if(category == null){return null;}
        String[] categories = category.replaceAll("\\r","").trim().split("\\n");
        return categories;
    }
}
