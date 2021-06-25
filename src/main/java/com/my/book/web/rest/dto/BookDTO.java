package com.my.book.web.rest.dto;

import com.my.book.domain.enumeration.BookStatus;
import com.my.book.domain.enumeration.Classification;
import com.my.book.domain.enumeration.Location;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;
import lombok.*;

/**
 * A DTO for the {@link com.my.book.domain.Book} entity.
 */

@Getter
@Setter
@ToString
public class BookDTO implements Serializable {

    private Long id;
    private String title;
    private String author;
    private String description;
    private String publisher;
    private Long isbn;
    private LocalDate publicationDate;
    private Classification classification;
    private BookStatus bookStatus;
    private Location location;
}
