package com.github.zonky.backend.repository;

import com.github.zonky.backend.entity.Loan;
import com.github.zonky.utils.Page;

import java.util.Map;

/**
 * Repository for marketplace methods
 */
public interface MarketplaceRepository {

    /**
     * Get paged, filtered, ordered loans from zonky api.
     *
     * @param first   First row
     * @param size    Page size
     * @param filters Filter map - Key is param name with operator, value is value :) - for details see <a href="https://zonky.docs.apiary.io/#introduction/pagination,-sorting-and-filtering/filtering">Zonky API documentation - filtering</>
     * @param sort    Sort order - for details see <a href="https://zonky.docs.apiary.io/#introduction/pagination,-sorting-and-filtering/sorting">Zonky API documentation - sorting</>
     * @return Page wrapping object for list of loans and total size
     */
    Page<Loan> getLoans(Integer first, Integer size, Map<String, String> filters, String sort);
}
