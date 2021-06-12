package com.my.book.web.rest.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BookInfoDTO implements Serializable {

    private Long id;
    private String title;
}
