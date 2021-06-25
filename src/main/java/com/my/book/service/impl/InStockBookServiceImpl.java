package com.my.book.service.impl;

import com.my.book.domain.InStockBook;
import com.my.book.repository.InStockBookRepository;
import com.my.book.service.InStockBookService;
import com.my.book.web.rest.dto.InStockBookDTO;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InStockBookServiceImpl implements InStockBookService {

    private final Logger log = LoggerFactory.getLogger(InStockBookServiceImpl.class);

    private final InStockBookRepository inStockBookRepository;

    public InStockBookServiceImpl(InStockBookRepository inStockBookRepository) {
        this.inStockBookRepository = inStockBookRepository;
    }

    @Override
    public InStockBook save(InStockBook inStockBook) {
        log.debug("Request to save InStockBook : {}", inStockBook);
        return inStockBookRepository.save(inStockBook);
    }

    @Override
    public Page<InStockBook> findAll(Pageable pageable) {
        log.debug("Request to get all InStockBooks");
        return inStockBookRepository.findAll(pageable);
    }

    @Override
    public Optional<InStockBook> findOne(Long id) {
        log.debug("Request to get InStockBook : {}", id);

        return Optional.empty();
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete InStockBook : {}", id);
    }

    @Override
    public Page<InStockBookDTO> findByTitle(String title, Pageable pageable) {
        return null;
    }
}
