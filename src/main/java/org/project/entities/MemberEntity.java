package org.project.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
@Table(indexes = { //인덱스 설정
        @Index(name="idx_member_memberNm", columnList = "memberNm"),
        @Index(name="idx_member_email", columnList = "email")
})
public class MemberEntity extends BaseEntity{

    @Id @GeneratedValue
    private Long memberNo; // 회원번호

    @Column(length=40, nullable = false, unique = true)
    private String memberId; // 회원 아이디

    @Column(length=65, nullable = false)
    private String memberPw; // 회원 비밀번호

    @Column(length=40, nullable = false)
    private String memberNm; // 회원명

    @Column(length=100, nullable = false)
    private String email; // 회원 이메일

    @Column(length=11)
    private String mobile; // 회원 연락처(11자 고정)

    @Lob
    private String termsAgree; // 회원 정보 약관 동의 내역
}
