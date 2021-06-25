package com.my.book.service.criteria;

import com.my.book.web.rest.InStockBookResource;
import java.io.Serializable;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.my.book.domain.InStockBook} entity. This class is used
 * in {@link InStockBookResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /in-stock-books?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */

@Getter
@Setter
@NoArgsConstructor
public class InStockBookCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;
    private StringFilter title;
    private StringFilter description;
    private StringFilter author;
    private StringFilter publisher;
    private LongFilter isbn;
    private LocalDateFilter publicationDate;

    public InStockBookCriteria(InStockBookCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.author = other.author == null ? null : other.author.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.publisher = other.publisher == null ? null : other.publisher.copy();
        this.isbn = other.isbn == null ? null : other.isbn.copy();
        this.publicationDate = other.publicationDate == null ? null : other.publicationDate.copy();
    }

    @Override
    public InStockBookCriteria copy() {
        return new InStockBookCriteria(this);
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public StringFilter title() {
        if (title == null) {
            title = new StringFilter();
        }
        return title;
    }

    public StringFilter author() {
        if (author == null) {
            author = new StringFilter();
        }
        return author;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public StringFilter publisher() {
        if (publisher == null) {
            publisher = new StringFilter();
        }
        return publisher;
    }

    public LongFilter isbn() {
        if (isbn == null) {
            isbn = new LongFilter();
        }
        return isbn;
    }

    public LocalDateFilter publicationDate() {
        if (publicationDate == null) {
            publicationDate = new LocalDateFilter();
        }
        return publicationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final InStockBookCriteria that = (InStockBookCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(author, that.author) &&
            Objects.equals(description, that.description) &&
            Objects.equals(publisher, that.publisher) &&
            Objects.equals(isbn, that.isbn) &&
            Objects.equals(publicationDate, that.publicationDate)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, description, publisher, isbn, publicationDate);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InStockBookCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (author != null ? "author=" + author + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (publisher != null ? "publisher=" + publisher + ", " : "") +
            (isbn != null ? "isbn=" + isbn + ", " : "") +
            (publicationDate != null ? "publicationDate=" + publicationDate + ", " : "") +
            "}";
    }
}
