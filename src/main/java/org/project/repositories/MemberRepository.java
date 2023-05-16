package org.project.repositories;

import org.project.entities.MemberEntity;
import org.project.entities.QMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MemberRepository extends JpaRepository<MemberEntity,Long>,
        QuerydslPredicateExecutor<MemberEntity> {

    MemberEntity findByMemberId(String memberId); // 아이디로 회원 조회

    // 회원 중복 검증 기능(회원 아이디)
    default boolean isExists(String memberId){
        QMemberEntity member = QMemberEntity.memberEntity;
        return exists(member.memberId.eq(memberId));
    }

}
