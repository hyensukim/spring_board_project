package org.project.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity @Data
@Builder @NoArgsConstructor
@AllArgsConstructor
public class BoardDataEntity extends BaseEntity{

    @Id @GeneratedValue
    private Long id;//게시글 번호

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="bId")
    private BoardEntity board; // 게시판 정보
    
    private String gId; // 회원당 업로드한 파일 묶음 시 참고번호

    private String poster; // 작성자

    private String guestPw; // 비회원 전용 비밀번호

    private String category; // 분류

    private String subject; // 제목

    private String content; // 내용
    
    private int hit; // 조회수
    
    private String ua; //User-Agent : 클라이언트 브라우저 정보

    private String ip; // 작성자 IP 주소
    
    private int commentCnt; // 댓글 갯수

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="userNo")
    private MemberEntity member; // 작성회원 정보
}
