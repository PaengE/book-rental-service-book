package com.my.book.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.my.book.domain.Book;
import com.my.book.repository.BookRepository;
import com.my.book.service.BookQueryService;
import com.my.book.service.BookService;
import com.my.book.service.criteria.BookCriteria;
import com.my.book.web.rest.dto.BookDTO;
import com.my.book.web.rest.dto.BookInfoDTO;
import com.my.book.web.rest.errors.BadRequestAlertException;
import com.my.book.web.rest.mapper.BookMapper;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.my.book.domain.Book}.
 */
@RestController
@RequestMapping("/api")
public class BookResource {

    private final Logger log = LoggerFactory.getLogger(BookResource.class);

    private static final String ENTITY_NAME = "bookBook";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BookService bookService;
    private final BookRepository bookRepository;
    private final BookQueryService bookQueryService;
    private final BookMapper bookMapper;

    public BookResource(BookService bookService, BookRepository bookRepository, BookQueryService bookQueryService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookRepository = bookRepository;
        this.bookQueryService = bookQueryService;
        this.bookMapper = bookMapper;
    }

    /**
     * {@code POST  /books} : Create a new book.
     *
     * @param bookDTO the bookDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bookDTO, or with status {@code 400 (Bad Request)} if the book has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/books")
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO) throws URISyntaxException {
        log.debug("REST request to save Book : {}", bookDTO);
        if (bookDTO.getId() != null) {
            throw new BadRequestAlertException("A new book cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BookDTO result = bookMapper.toDto(bookService.save(bookMapper.toEntity(bookDTO)));
        return ResponseEntity
            .created(new URI("/api/books/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /books/:id} : Updates an existing book.
     *
     * @param id      the id of the bookDTO to save.
     * @param bookDTO the bookDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bookDTO,
     * or with status {@code 400 (Bad Request)} if the bookDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bookDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/books/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable(value = "id", required = false) final Long id, @RequestBody BookDTO bookDTO)
        throws URISyntaxException {
        log.debug("REST request to update Book : {}, {}", id, bookDTO);
        if (bookDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bookDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bookRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BookDTO result = bookMapper.toDto(bookService.save(bookMapper.toEntity(bookDTO)));
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bookDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /books} : get all the books.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of books in body.
     */
    @GetMapping("/books")
    public ResponseEntity<List<BookDTO>> getAllBooks(BookCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Books by criteria: {}", criteria);
        Page<BookDTO> page = bookQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /books/count} : count all the books.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/books/count")
    public ResponseEntity<Long> countBooks(BookCriteria criteria) {
        log.debug("REST request to count Books by criteria: {}", criteria);
        return ResponseEntity.ok().body(bookQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /books/:id} : get the "id" book.
     *
     * @param id the id of the bookDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bookDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/books/{id}")
    public ResponseEntity<BookDTO> getBook(@PathVariable Long id) {
        log.debug("REST request to get Book : {}", id);
        Optional<BookDTO> bookDTO = Optional.ofNullable(bookMapper.toDto(bookService.findOne(id).get()));
        return ResponseUtil.wrapOrNotFound(bookDTO);
    }

    /**
     * {@code DELETE  /books/:id} : delete the "id" book.
     *
     * @param id the id of the bookDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) throws ExecutionException, InterruptedException, JsonProcessingException {
        log.debug("REST request to delete Book : {}", id);

        //  ?????? ????????? ???????????? ???????????? ?????? ????????? ??????
        bookService.delete(id);

        //  HTTP ???????????? ??????
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * ?????? ?????? ?????? API
     *
     * @param bookId
     * @return
     */
    @GetMapping("/books/bookInfo/{bookId}")
    public ResponseEntity<BookInfoDTO> findBookInfo(@PathVariable("bookId") Long bookId) {
        Book book = bookService.findBookInfo(bookId);
        BookInfoDTO bookInfoDTO = new BookInfoDTO(bookId, book.getTitle());
        log.debug(bookInfoDTO.toString());
        return ResponseEntity.ok().body(bookInfoDTO);
    }

    /**
     * ?????? ?????? ?????? ??????
     *
     * @param bookDTO
     * @param inStockId
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws JsonProcessingException
     * @throws URISyntaxException
     */
    @PostMapping("/books/{inStockId}")
    public ResponseEntity<BookDTO> registerBook(@RequestBody BookDTO bookDTO, @PathVariable Long inStockId)
        throws ExecutionException, InterruptedException, JsonProcessingException, URISyntaxException {
        if (bookDTO.getId() != null) {
            throw new BadRequestAlertException("A new book cannot already have an ID", ENTITY_NAME, "idexists");
        }

        // ?????? ????????? ???????????? ???????????? ?????? ????????? ??????
        Book newBook = bookService.registerNewBook(bookMapper.toEntity(bookDTO), inStockId);

        // DTO ??????
        BookDTO result = bookMapper.toDto(newBook);

        // HTTP ??????????????? DTO??? ?????? ??????
        return ResponseEntity
            .created(new URI("/api/books/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * ?????? ?????? ?????? ??????
     *
     * @param bookDTO
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws JsonProcessingException
     */
    @PutMapping("/books")
    public ResponseEntity<BookDTO> updateBook(@RequestBody BookDTO bookDTO)
        throws ExecutionException, InterruptedException, JsonProcessingException {
        log.debug("REST request to update Book : {}", bookDTO);
        if (bookDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        // ?????? ????????? ???????????? ???????????? ?????? ????????? ??????
        Book updateBook = bookService.updateBook(bookMapper.toEntity(bookDTO));

        // DTO ??????
        BookDTO result = bookMapper.toDto(updateBook);

        // HTTP ??????????????? DTO??? ?????? ??????
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bookDTO.getId().toString()))
            .body(result);
    }
}
