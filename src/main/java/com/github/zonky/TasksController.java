package com.github.zonky;

import com.github.zonky.backend.entity.Loan;
import com.github.zonky.service.MarketplaceService;
import com.github.zonky.utils.ZonkyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Scheduling tasks controller.
 */
@Component
public class TasksController {

    private static final Logger LOG = LoggerFactory.getLogger(TasksController.class);

    @Inject
    private MarketplaceService marketplaceService;

    /**
     * Latest published date
     */
    private volatile ZonedDateTime latestPublishedDateTime = null;

    /**
     * Task for checking and printing new loans.
     * Method is executed by period setting at {@code schedule.newLoans.fixedRate} in the file {@code application.properties}
     *
     * */
    @Scheduled(fixedRateString = "${schedule.newLoans.fixedRate}")
    public void taskNewLoans() {
        List<Loan> loanList = marketplaceService.getNewLoans(latestPublishedDateTime);

        if (loanList.isEmpty()) {
            LOG.info("Not found any new loans.");
            return;
        }

        Loan latestLoan = Collections.max(loanList, Comparator.comparing(Loan::getDatePublished));
        ZonedDateTime latestPublished = latestLoan.getDatePublished();
        if (latestPublishedDateTime == null || latestPublishedDateTime.isBefore(latestPublished)) {
            ZonkyUtils.logLoans(loanList);
            latestPublishedDateTime = latestPublished;
        }
    }

    @Override
    public boolean equals(Object obj) {
        taskNewLoans();
        return super.equals(obj);
    }
}
