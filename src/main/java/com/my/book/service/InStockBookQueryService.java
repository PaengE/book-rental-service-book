package com.my.book.service;

import com.my.book.domain.InStockBook;
import com.my.book.domain.InStockBook_;
import com.my.book.repository.InStockBookRepository;
import com.my.book.service.criteria.BookCriteria;
import com.my.book.service.criteria.InStockBookCriteria;
import com.my.book.web.rest.dto.InStockBookDTO;
import com.my.book.web.rest.mapper.InStockBookMapper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link InStockBook} entities in the database.
 * The main input is a {@link InStockBookCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link InStockBookDTO} or a {@link Page} of {@link InStockBookDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InStockBookQueryService extends QueryService<InStockBook> {

    private final Logger log = LoggerFactory.getLogger(InStockBookQueryService.class);

    private final InStockBookRepository inStockBookRepository;

    private final InStockBookMapper inStockBookMapper;

    public InStockBookQueryService(InStockBookRepository inStockBookRepository, InStockBookMapper inStockBookMapper) {
        this.inStockBookRepository = inStockBookRepository;
        this.inStockBookMapper = inStockBookMapper;
    }

    /**
     * Return a {@link List} of {@link InStockBookDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<InStockBookDTO> findByCriteria(InStockBookCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<InStockBook> specification = createSpecification(criteria);
        return inStockBookMapper.toDto(inStockBookRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link InStockBookDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<InStockBookDTO> findByCriteria(InStockBookCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<InStockBook> specification = createSpecification(criteria);
        return inStockBookRepository.findAll(specification, page).map(inStockBookMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InStockBookCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<InStockBook> specification = createSpecification(criteria);
        return inStockBookRepository.count(specification);
    }

    /**
     * Function to convert {@link InStockBookCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<InStockBook> createSpecification(InStockBookCriteria criteria) {
        Specification<InStockBook> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), InStockBook_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), InStockBook_.title));
            }
            if (criteria.getAuthor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAuthor(), InStockBook_.author));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), InStockBook_.description));
            }
            if (criteria.getPublisher() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPublisher(), InStockBook_.publisher));
            }
            if (criteria.getIsbn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIsbn(), InStockBook_.isbn));
            }
            if (criteria.getPublicationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPublicationDate(), InStockBook_.publicationDate));
            }
        }
        return specification;
    }
}
