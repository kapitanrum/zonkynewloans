package com.github.zonky.utils;

import com.github.zonky.backend.entity.Photo;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class JsonUtilsTest {

    /**
     * JSON validation
     *
     * @result Must return {@code true} if JSON is valid, otherwise must return {@code false}
     */
    @Test
    public void isJsonValid() {
        //valid JSON
        assertTrue(JsonUtils.isJsonValid("[{\"a\":\"a\"},{\"b\":\"b\"}]"));
        //invalid JSON
        assertFalse(JsonUtils.isJsonValid("[{\"a\":\"a\"]"));
    }

    /**
     * Tranforming JSON String array to list of objects.
     */
    @Test
    public void transformJsonArray() {
        List<Photo> photoList = JsonUtils.transformJsonArray(
                "[ " +
                        "  { " +
                        "    \"name\" : \"testA.jpg\"," +
                        "    \"url\" : \"/loans/photo/testA\"" +
                        "  }," +
                        "  { " +
                        "    \"name\" : \"testA.jpg\"," +
                        "    \"url\" : \"/loans/photo/testA\"" +
                        "  }" +
                        "]"
                , Photo.class
        );

        assertEquals(2, photoList.size());
    }

    /**
     * Print object to JSON string.
     */
    @Test
    public void pretyPrint() {
        //formatting JSON Object with TWO properties
        Photo photo = new Photo();
        photo.setName("name");
        photo.setUrl("url");
        assertEquals(4, JsonUtils.prettyPrint(photo).split("\r\n|\r|\n").length);

        //formatting JSON Object with ONE property
        photo = new Photo();
        photo.setName("name");
        assertEquals(3, JsonUtils.prettyPrint(photo).split("\r\n|\r|\n").length);

    }
}