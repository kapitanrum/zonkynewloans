package com.github.zonky.service.impl;

import com.github.zonky.backend.entity.Loan;
import com.github.zonky.backend.repository.MarketplaceRepository;
import com.github.zonky.service.MarketplaceService;
import com.github.zonky.utils.Page;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyMap;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class MarketplaceServiceImplTest {

    @Inject
    private MarketplaceService marketplaceService;
    @MockBean
    private MarketplaceRepository marketplaceRepository;

    @Before
    public void setUp() {
        List<Loan> loans = new ArrayList<>();

        loans.add(Mockito.mock(Loan.class));
        loans.add(Mockito.mock(Loan.class));
        loans.add(Mockito.mock(Loan.class));

        Page<Loan> loanPageFirst = new Page<>(loans, 6);

        Page<Loan> loanPageSecond = new Page<>(loans, 6);

        when(marketplaceRepository.getLoans(anyInt(), anyInt(), anyMap(), anyString()))
                .thenAnswer(invocation -> {
                    int param = invocation.getArgument(0);
                    if (param > 0) {
                        return loanPageFirst;
                    }
                    return loanPageSecond;
                });
    }

    /**
     * Loading all paged loans.
     *
     * @Result Loan list size must be equals 6.
     */
    @Test
    public void getNewLoans() {
        List<Loan> loans = marketplaceService.getNewLoans(ZonedDateTime.now());
        Assert.assertEquals(6, loans.size());
    }

    @TestConfiguration
    static class MarketplaceServiceTestContextConfiguration {

        @Bean
        public MarketplaceService marketplaceService() {
            return new MarketplaceServiceImpl();
        }
    }
}