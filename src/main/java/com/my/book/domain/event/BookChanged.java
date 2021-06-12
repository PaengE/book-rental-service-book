package com.my.book.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BookChanged {

    private String title;
    private String description;
    private String author;
    private String publicationDate;
    private String classification;
    private Boolean rented;
    private String eventType;
    private Long rentCnt;
    private Long bookId;
}
