package com.github.zonky.service.impl;

import com.github.zonky.backend.entity.Loan;
import com.github.zonky.backend.repository.MarketplaceRepository;
import com.github.zonky.service.MarketplaceService;
import com.github.zonky.utils.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Marketplace service implementation
 */
@Service
public class MarketplaceServiceImpl implements MarketplaceService {

    private static final Logger LOG = LoggerFactory.getLogger(MarketplaceServiceImpl.class);
    private static final int PAGE_SIZE = 20;
    private static final ZonedDateTime DEFAULT_LATEST_PUBLISHED_LOAN = ZonedDateTime.now().minusDays(1);

    @Inject
    private MarketplaceRepository marketplaceRepository;

    public List<Loan> getNewLoans(final ZonedDateTime lastPublishedDateTime) {
        final ZonedDateTime lastDateTime = lastPublishedDateTime == null ? DEFAULT_LATEST_PUBLISHED_LOAN : lastPublishedDateTime;
        List<Loan> result = new ArrayList<>();
        int first = 0;
        boolean latestDateTimeReached = false;

        //prepare filters
        Map<String, String> filters = new HashMap<>();
        filters.put(LoanFilterEnum.DATE_PUBLISHED.gt(), lastDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));

        //load all loans - may be on more pages
        while (!latestDateTimeReached) {
            Page<Loan> pagedLoans = marketplaceRepository.getLoans(first, PAGE_SIZE, filters, OrderEnum.DATE_PUBLISHED.descending());
            result.addAll(pagedLoans.getContent());
            latestDateTimeReached = result.size() == pagedLoans.getTotalCount();
            first += PAGE_SIZE;
        }
        return result;
    }

    /**
     * Filter fields definitions
     */
    private enum LoanFilterEnum {
        DATE_PUBLISHED("datePublished"),
        RATING("rating")
        ;

        private final String field;

        LoanFilterEnum(String field) {
            this.field = field;
        }

        /**
         * Filter operation - greater than (gt).
         *
         * @return ${field}__gt
         */
        String gt() {
            return field + "__gt";
        }

    }

    /**
     * Definitions of sort fields.
     */
    private enum OrderEnum {
        DATE_PUBLISHED("datePublished"),
        RATING("rating");

        private final String field;

        OrderEnum(String field) {
            this.field = field;
        }

        /**
         * Aascending order operator for field.
         */
        String ascending() {
            return "+" + field;
        }

        /**
         * Descending order operator for field.
         */
        String descending() {
            return "-" + field;
        }
    }
}