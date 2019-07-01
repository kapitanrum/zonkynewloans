package com.github.zonky.utils;

import com.github.zonky.backend.entity.Loan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Utils for calling zonky API.
 */
public class ZonkyUtils {

    //loggers
    private static final Logger LOG_ZONKY = LoggerFactory.getLogger("zonky-loans");

    private ZonkyUtils() {
        //Util class - cannot create instance
    }

    /**
     * Simple pretty print loan list to logger.
     *
     * @param loanList List of loans
     */
    public static void logLoans(List<Loan> loanList) {
        StringBuilder formattedLoansString = new StringBuilder();
        for (Loan loan : loanList) {
            String loanString = String.join(System.getProperty("line.separator"),
                    "",
                    "------------------------------------------------------------------------------------------------",
                    String.format("ID: %d | Name: %s:", loan.getId(), loan.getName()),
                    "------------------------------------------------------------------------------------------------",
                    JsonUtils.prettyPrint(loan),
                    "",
                    "");
            formattedLoansString.append(loanString);
        }
        LOG_ZONKY.info(formattedLoansString.toString());
    }


}
