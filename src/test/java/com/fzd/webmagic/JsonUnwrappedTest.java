package com.fzd.webmagic;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by SRKJ on 2017/7/28.
 */
public class JsonUnwrappedTest {
    @Test
    public void jsonUnwrapped() throws Exception {
        TestPOJO testPOJO = new TestPOJO();
        testPOJO.setId(111);
        TestName testName = new TestName();
        testName.setFirstName("张");
        testName.setSecondName("三");
        testPOJO.setName(testName);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = objectMapper.writeValueAsString(testPOJO);
        //如果没有@JsonUnwrapped，序列化后将为{"id":111,"name":{"firstName":"张","secondName":"三"}}
        //因为在name属性上加了@JsonUnwrapped，所以name的子属性firstName和secondName将不会包含在name中。
        Assert.assertEquals("{\"id\":111,\"firstName\":\"张\",\"secondName\":\"三\"}",jsonStr);
        String jsonStr2 = "{\"id\":111,\"firstName\":\"张\",\"secondName\":\"三\"}";
        TestPOJO testPOJO2 = objectMapper.readValue(jsonStr2,TestPOJO.class);
        Assert.assertEquals(111,testPOJO2.getId());
        Assert.assertEquals("张",testPOJO2.getName().getFirstName());
        Assert.assertEquals("三",testPOJO2.getName().getSecondName());
    }

    public static class TestPOJO{
        private int id;
        @JsonUnwrapped
        private TestName name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public TestName getName() {
            return name;
        }

        public void setName(TestName name) {
            this.name = name;
        }
        //getters、setters省略
    }
    public static class TestName{
        private String firstName;
        private String secondName;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getSecondName() {
            return secondName;
        }

        public void setSecondName(String secondName) {
            this.secondName = secondName;
        }
        //getters、setters省略
    }
}
