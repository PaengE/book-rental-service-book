package com.my.book.repository;

import com.my.book.domain.InStockBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface InStockBookRepository extends JpaRepository<InStockBook, Long>, JpaSpecificationExecutor<InStockBook> {}
