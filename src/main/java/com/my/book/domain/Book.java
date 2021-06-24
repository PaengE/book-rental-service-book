package com.my.book.domain;

import com.my.book.domain.enumeration.BookStatus;
import com.my.book.domain.enumeration.Classification;
import com.my.book.domain.enumeration.Location;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Book.
 */
@Data
@Entity
@Table(name = "book")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "description")
    private String description;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "isbn")
    private Long isbn;

    @Column(name = "publication_date")
    private LocalDate publicationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "classification")
    private Classification classification;

    @Enumerated(EnumType.STRING)
    @Column(name = "book_status")
    private BookStatus bookStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "location")
    private Location location;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Book id(Long id) {
        this.id = id;
        return this;
    }

    public Book title(String title) {
        this.title = title;
        return this;
    }

    public Book author(String author) {
        this.author = author;
        return this;
    }

    public Book description(String description) {
        this.description = description;
        return this;
    }

    public Book publisher(String publisher) {
        this.publisher = publisher;
        return this;
    }

    public Book isbn(Long isbn) {
        this.isbn = isbn;
        return this;
    }

    public Book publicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
        return this;
    }

    public Book classification(Classification classification) {
        this.classification = classification;
        return this;
    }

    public Book bookStatus(BookStatus bookStatus) {
        this.bookStatus = bookStatus;
        return this;
    }

    public Book location(Location location) {
        this.location = location;
        return this;
    }
}
