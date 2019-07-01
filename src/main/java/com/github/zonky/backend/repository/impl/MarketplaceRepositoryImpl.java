package com.github.zonky.backend.repository.impl;

import com.github.zonky.backend.entity.Loan;
import com.github.zonky.backend.repository.MarketplaceRepository;
import com.github.zonky.utils.JsonUtils;
import com.github.zonky.utils.Page;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Implementation {@link MarketplaceRepository}
 */
@Repository
public class MarketplaceRepositoryImpl extends AbstractZonkyRepository implements MarketplaceRepository {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractZonkyRepository.class);

    /**
     * Endpoint for list of loans
     */
    protected static final String MARKETPLACE_ZONKY_URL = BASIC_ZONKY_URL + "loans/marketplace";

    public Page<Loan> getLoans(Integer first, Integer size, Map<String, String> filters, String sort) {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(MARKETPLACE_ZONKY_URL + convertFilterToUrlString(filters));
        MultivaluedMap<String, Object> myHeaders = new MultivaluedHashMap<>();
        if (first != null) {
            myHeaders.add(HEADER_PAGE, (int) first / size);
        }
        if (size != null) {
            myHeaders.add(HEADER_SIZE, size);
        }
        if (sort != null && !sort.trim().isEmpty()) {
            myHeaders.add(HEADER_ORDER, sort);
        }
        Invocation.Builder invocationBuilder = webTarget.request(MEDIA_TYPES).headers(myHeaders);
        Response response = invocationBuilder.get();
        String jsonInput = response.readEntity(String.class);
        LOG.debug("status: " + response.getStatus());
        LOG.debug("headers: " + response.getHeaders());
        LOG.debug("body:" + jsonInput);

        List<Loan> loanList = JsonUtils.transformJsonArray(jsonInput, Loan.class);
        //read total rows from response header
        int totalRows = NumberUtils.toInt(Objects.toString(response.getHeaders().get(HEADER_TOTAL), null),loanList.size());
        return new Page<>(loanList, totalRows);
    }
}
