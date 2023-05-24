package org.project.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity @Data
@Builder @NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
        @Index(name="idx_boardData_category", columnList = "category DESC"),
        @Index(name="idx_boardData_createdAt", columnList = "createdAt DESC")
})
public class BoardDataEntity extends BaseEntity{

    @Id @GeneratedValue
    private Long id;//게시글 번호

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="bId")
    private BoardEntity board; // 게시판 정보

    @Column(length=65, nullable = false)
    private String gId = UUID.randomUUID().toString(); // 회원당 업로드한 파일 묶음 시 참고번호

    @Column(length=40, nullable = false)
    private String poster; // 작성자

    @Column(length=65)
    private String guestPw; // 비회원 전용 비밀번호

    @Column(length = 60)
    private String category; // 분류 -> 조회 많이하므로 인덱스 지정

    @Column(nullable = false)
    private String subject; // 제목

    @Lob
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String content; // 내용
    
    private int hit; // 조회수

    @Column(length = 125)
    private String ua; //User-Agent : 클라이언트 브라우저 정보

    @Column(length = 20)
    private String ip; // 작성자 IP 주소
    
    private int commentCnt; // 댓글 갯수

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="userNo")
    private MemberEntity member; // 작성회원 정보
}
