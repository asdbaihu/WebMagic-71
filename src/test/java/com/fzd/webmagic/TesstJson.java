package com.fzd.webmagic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by SRKJ on 2017/7/28.
 */
public class TesstJson {
    @Test
    public void jsonIgnoreTest() throws Exception {
        TestPOJO testPOJO = new TestPOJO();
        testPOJO.setId(111);
        testPOJO.setName("myName");
        testPOJO.setCount(22);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = objectMapper.writeValueAsString(testPOJO);
        Assert.assertEquals("{\"id\":111}",jsonStr);

        String jsonStr2 = "{\"id\":111,\"name\":\"myName\",\"count\":22}";
        TestPOJO testPOJO2 = objectMapper.readValue(jsonStr2, TestPOJO.class);
        Assert.assertEquals(111,testPOJO2.getId());
        Assert.assertNull(testPOJO2.getName());
        Assert.assertEquals(0,testPOJO2.getCount());
    }

    public static class TestPOJO{
        private int id;
        @JsonIgnore
        private String name;
        private int count;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCount() {
            return count;
        }
        @JsonIgnore
        public void setCount(int count) {
            this.count = count;
        }
    }
}
