package com.my.book.web.rest.dto;

import com.my.book.domain.enumeration.Source;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * A DTO for the {@link com.my.book.domain.InStockBook} entity.
 */
@Getter
@Setter
@ToString
public class InStockBookDTO implements Serializable {

    private Long id;

    private String title;

    private String description;

    private String author;

    private String publisher;

    private Long isbn;

    private LocalDate publicationDate;

    private Source source;
}
