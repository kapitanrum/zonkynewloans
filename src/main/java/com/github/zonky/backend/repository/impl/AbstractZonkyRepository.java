package com.github.zonky.backend.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.ws.rs.core.MediaType;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public abstract class AbstractZonkyRepository {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractZonkyRepository.class);

    //Target URI setting
    protected static final MediaType[] MEDIA_TYPES = new MediaType[]{MediaType.TEXT_PLAIN_TYPE, MediaType.APPLICATION_JSON_TYPE};
    protected static final String BASIC_ZONKY_URL = "https://api.zonky.cz/";

    //HTTP Header parameters
    protected static final String HEADER_PAGE = "X-Page";
    protected static final String HEADER_SIZE = "X-Size";
    protected static final String HEADER_ORDER = "X-Order";
    protected static final String HEADER_TOTAL = "X-Total";

    /**
     * Create filter string for URL.
     *
     * @param filters Map of filter configuration - <b>key</b> is filter name and <b>value</b> is filter value.
     * @return String with filter values encoded for URL.
     */
    protected static String convertFilterToUrlString(Map<String, String> filters) {
        if (CollectionUtils.isEmpty(filters)) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        result.append("?");

        for (Map.Entry<String, String> itemEntry : filters.entrySet()) {
            if (result.toString().length() > 1) {
                result.append("&");
            }
            try {
                result.append(itemEntry.getKey()).append("=").append(URLEncoder.encode(itemEntry.getValue(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                LOG.warn(String.format("Cannot transform filter '%s' with value '%s' to URL",
                        itemEntry.getKey(),
                        itemEntry.getValue()), e);
            }

        }
        return result.toString();
    }

}
