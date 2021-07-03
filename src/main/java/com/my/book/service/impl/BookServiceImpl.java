package com.my.book.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.my.book.adaptor.BookProducer;
import com.my.book.domain.Book;
import com.my.book.domain.enumeration.BookStatus;
import com.my.book.domain.event.BookChanged;
import com.my.book.repository.BookRepository;
import com.my.book.service.BookService;
import com.my.book.service.InStockBookService;
import com.my.book.web.rest.mapper.BookMapper;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Book}.
 */
@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);

    private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final InStockBookService inStockBookService;
    private final BookProducer bookProducer;

    public BookServiceImpl(
        BookRepository bookRepository,
        BookMapper bookMapper,
        InStockBookService inStockBookService,
        BookProducer bookProducer
    ) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.inStockBookService = inStockBookService;
        this.bookProducer = bookProducer;
    }

    @Override
    public Book save(Book book) {
        log.debug("Request to save Book : {}", book);
        return bookRepository.save(book);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Book> findAll(Pageable pageable) {
        log.debug("Request to get all Books");
        return bookRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Book> findOne(Long id) {
        log.debug("Request to get Book : {}", id);
        return bookRepository.findById(id);
    }

    @Override
    public void delete(Long id) throws ExecutionException, InterruptedException, JsonProcessingException {
        log.debug("Request to delete Book : {}", id);
        sendBookCatalogEvent("DELETE_BOOK", id);
        bookRepository.deleteById(id);
    }

    @Override
    public Book createBook(Book book) throws InterruptedException, ExecutionException, JsonProcessingException {
        log.debug("Request to create Book : {}", book);
        Book createdBook = bookRepository.save(book);
        sendBookCatalogEvent("NEW_BOOK", createdBook.getId());
        return createdBook;
    }

    @Override
    public Book updateBook(Book book) throws InterruptedException, ExecutionException, JsonProcessingException {
        log.debug("Request to update Book : {}", book);
        Book updatedBook = bookRepository.save(book);
        sendBookCatalogEvent("UPDATE_BOOK", book.getId());
        return updatedBook;
    }

    @Override
    public void processChangeBookState(Long bookId, String bookStatus) {
        Book book = bookRepository.findById(bookId).get();
        book.setBookStatus(BookStatus.valueOf(bookStatus));
        bookRepository.save(book);
    }

    @Override
    @Transactional
    public Book findBookInfo(Long bookId) {
        log.debug("Request to findBookInfo Book : {}", bookId);
        return bookRepository.findById(bookId).get();
    }

    @Override
    public Book registerNewBook(Book book, Long inStockId) throws InterruptedException, ExecutionException, JsonProcessingException {
        Book newBook = bookRepository.save(book);
        inStockBookService.delete(inStockId);
        sendBookCatalogEvent("NEW_BOOK", newBook.getId());
        return newBook;
    }

    @Override
    public void sendBookCatalogEvent(String eventType, Long bookId)
        throws InterruptedException, ExecutionException, JsonProcessingException {
        // 재고 도서 정보 조회
        Book book = bookRepository.findById(bookId).get();

        // `도서변경됨` 이벤트 객체 생성
        BookChanged bookChanged = new BookChanged();

        // 신규 재고 도서이거나 재고 도서 수정인 경우
        if (eventType.equals("NEW_BOOK") || eventType.equals("UPDATE_BOOK")) {
            bookChanged.setBookId(book.getId());
            bookChanged.setBookId(book.getId());
            bookChanged.setAuthor(book.getAuthor());
            bookChanged.setClassification(book.getClassification().toString());
            bookChanged.setDescription(book.getDescription());
            bookChanged.setPublicationDate(book.getPublicationDate().format(fmt));
            bookChanged.setTitle(book.getTitle());
            bookChanged.setEventType(eventType);
            bookChanged.setRented(!book.getBookStatus().equals(BookStatus.AVAILABLE));
            bookChanged.setRentCnt((long) 0);
            // 이벤트 메시지 전송
            bookProducer.sendBookCreateEvent(bookChanged);
        } else if (eventType.equals("DELETE_BOOK")) {
            bookChanged.setEventType(eventType);
            bookChanged.setBookId(book.getId());
            // 이벤트 메시지 전송
            bookProducer.sendBookDeleteEvent(bookChanged);
        }
    }
}
