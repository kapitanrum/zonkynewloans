package com.github.zonky.service;

import com.github.zonky.backend.entity.Loan;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Marketplace service for getting information from ZONKY api.
 */
public interface MarketplaceService {

    /**
     * Get loans before lastPublishedDateTime.
     *
     * @param lastPublishedDateTime Last date-time published loan for get.
     *                              If passed <code>null</code> then is set like current date minus one day.
     * @return List of loans. If not found any loan, then return empty list.
     */
    List<Loan> getNewLoans(final ZonedDateTime lastPublishedDateTime);


}
