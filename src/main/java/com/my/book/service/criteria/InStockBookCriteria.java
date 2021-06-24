package com.my.book.service.criteria;

import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.Filter;

/**
 * Criteria class for the {@link com.my.book.domain.Book} entity. This class is used
 * in {@link com.my.book.web.rest.BookResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /books?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
public class InStockBookCriteria implements Serializable, Criteria {

    @Override
    public InStockBookCriteria copy() {
        return null;
    }
}
