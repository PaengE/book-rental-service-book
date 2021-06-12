package com.my.book.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.my.book.domain.Book;
import com.my.book.web.rest.dto.BookDTO;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.my.book.domain.Book}.
 */
public interface BookService {
    /**
     * Save a book.
     *
     * @param book the entity to save.
     * @return the persisted entity.
     */
    Book save(Book book);

    /**
     * Partially updates a book.
     *
     * @param bookDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BookDTO> partialUpdate(BookDTO bookDTO);

    /**
     * Get all the books.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BookDTO> findAll(Pageable pageable);

    /**
     * Get the "id" book.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BookDTO> findOne(Long id);

    /**
     * Delete the "id" book.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Book createBook(Book book) throws InterruptedException, ExecutionException, JsonProcessingException;

    Book updateBook(Book book) throws InterruptedException, ExecutionException, JsonProcessingException;

    void processChangeBookState(Long bookId, String bookStatus);

    Book findBookInfo(Long bookId);
}
