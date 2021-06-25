package com.my.book.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookChanged {

    // 재고 도서명
    private String title;

    // 도서 설명
    private String description;

    // 저자
    private String author;

    // 출판일
    private String publicationDate;

    // 분류
    private String classification;

    // 대출 여부
    private Boolean rented;

    // 이벤트 유형
    private String eventType;

    // 총 대출 횟수
    private Long rentCnt;

    // 재고 도서 일련번호
    private Long bookId;
}
