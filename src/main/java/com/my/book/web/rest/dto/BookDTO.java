package com.my.book.web.rest.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * A DTO for the {@link com.my.book.domain.Book} entity.
 */

@Data
public class BookDTO implements Serializable {

    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String author;

    private String description;
}
