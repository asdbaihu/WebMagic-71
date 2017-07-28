package com.fzd.webmagic;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by SRKJ on 2017/7/28.
 */
public class JsonPropertyTest {
    @Test
    public void jsonPropertyTest() throws Exception {
        TestPOJO testPOJO = new TestPOJO();
        testPOJO.wahaha(111);
        testPOJO.setFirstName("myName");

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = objectMapper.writeValueAsString(testPOJO);
        Assert.assertEquals("{\"id\":111,\"first_name\":\"myName\"}",jsonStr);

        String jsonStr2 = "{\"id\":111,\"first_name\":\"myName\"}";
        TestPOJO testPOJO2 = objectMapper.readValue(jsonStr2, TestPOJO.class);
        Assert.assertEquals(111, testPOJO2.wahaha());
        Assert.assertEquals("myName", testPOJO2.getFirstName());
    }

    public static class TestPOJO{
        @JsonProperty//注意这里必须得有该注解，因为没有提供对应的getId和setId函数，而是其他的getter和setter，防止遗漏该属性
        private int id;
        @JsonProperty("first_name")
        private String firstName;

        public int wahaha() {
            return id;
        }

        public void wahaha(int id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }
    }
}
