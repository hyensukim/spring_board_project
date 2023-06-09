package org.project.commons;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MenuDetail {
    private String name;
    private String url;
    private String code;
}
