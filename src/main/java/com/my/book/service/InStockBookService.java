package com.my.book.service;

import com.my.book.domain.InStockBook;
import com.my.book.web.rest.dto.InStockBookDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.my.book.domain.InStockBook}.
 */
public interface InStockBookService {
    /**
     * Save a inStockBook.
     *
     * @param inStockBook the entity to save.
     * @return the persisted entity.
     */
    InStockBook save(InStockBook inStockBook);

    /**
     * Partially updates a inStockBook.
     *
     * @param inStockBookDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<InStockBookDTO> partialUpdate(InStockBookDTO inStockBookDTO);

    /**
     * Get all the inStockBooks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<InStockBook> findAll(Pageable pageable);

    /**
     * Get the "id" inStockBook.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InStockBook> findOne(Long id);

    /**
     * Delete the "id" inStockBook.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
