package com.fzd.webmagic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by SRKJ on 2017/7/28.
 */
public class JsonIgnorePropertiesTest {
    @Test(expected = UnrecognizedPropertyException.class)
    public void JsonIgnoreProperties() throws Exception {
        TestPOJO testPOJO = new TestPOJO();
        testPOJO.setId(111);
        testPOJO.setName("myName");
        testPOJO.setAge(22);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = objectMapper.writeValueAsString(testPOJO);
        Assert.assertEquals("{\"id\":111}",jsonStr);//name和age被忽略掉了

        String jsonStr2 = "{\"id\":111,\"name\":\"myName\",\"age\":22,\"title\":\"myTitle\"}";
        TestPOJO testPOJO2 = objectMapper.readValue(jsonStr2, TestPOJO.class);
        Assert.assertEquals(111, testPOJO2.getId());
        Assert.assertNull(testPOJO2.getName());
        Assert.assertEquals(0,testPOJO2.getAge());
        String jsonStr3 = "{\"id\":111,\"name\":\"myName\",\"count\":33}";//这里有个未知的count属性，反序列化会报错
        objectMapper.readValue(jsonStr3, TestPOJO.class);
    }

    @JsonIgnoreProperties({"name","age","title"})
    public static class TestPOJO{
        private int id;
        private String name;
        private int age;

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

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        //getters、setters省略
    }
}
