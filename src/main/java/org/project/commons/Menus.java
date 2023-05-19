package org.project.commons;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * 서브 메뉴 조회
 */
public class Menus {
    public static List<MenuDetail> gets(String code) {
        List<MenuDetail> menus = new ArrayList<>();

        // 게시판 하위 메뉴
        if (code.equals("board")) {
            menus.add(new MenuDetail("게시판 목록", "/admin/board","board"));
            menus.add(new MenuDetail("게시판 등록/수정", "/admin/board/register","register"));
            menus.add(new MenuDetail("게시글 관리", "/admin/board/posts","post"));
        }

        return menus;
    }

    public static String subMenuCode(HttpServletRequest request){

        String URI = request.getRequestURI();
        return URI.substring(URI.lastIndexOf('/')+1);
    }
}
