package org.project.entities;

import jakarta.persistence.*;
import lombok.Data;

/**
 * 관리자 페이지 설정 관련 데이터 저장 엔티티
 */
@Entity @Data
public class ConfigsEntity {
    @Id
    private String code;
    @Lob
    private String value;
}
