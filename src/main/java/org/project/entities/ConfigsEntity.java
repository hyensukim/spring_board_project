package org.project.entities;

import jakarta.persistence.*;
import lombok.Data;

/**
 * 관리자 페이지 설정 관련 데이터 저장 엔티티
 */
@Entity @Data
public class ConfigsEntity {
    @Id
    @Column(name="code_", length=45)
    private String code;

    @Lob
    @Column(name="value_", columnDefinition = "LONGTEXT")
    private String value;
}
