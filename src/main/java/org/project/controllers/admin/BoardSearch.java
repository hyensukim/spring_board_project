package org.project.controllers.admin;

import lombok.Data;

/**
 * 게시판 설정 검색
 */
@Data
public class BoardSearch {
    private int page = 1;
    private int limit = 20; //한페이지당 20개
    private String sOpt; // 검색 조건
    private String sKey; // 검색 키워드
}
