package com.fzd.ali;

import com.alibaba.fastjson.JSON;
import com.fzd.ali.po.ImgVerifyResult;
import com.fzd.httpclient4_5.HttpUtil;
import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by FZD on 2018/6/19.
 * Description:
 */
public class TestImgThread {


    public  void test(){
        int index = 0;String name = "12";
        BASE64Encoder encoder = new BASE64Encoder();
        for(int i = index; i < (index + 5); i++){
            FileInputStream in = null;
            try {
                in = new FileInputStream("C:\\Users\\SRKJ\\Desktop\\150x40\\" + i + ".jpg");
                byte[] bytes = new byte[in.available()];
                in.read(bytes);
                String base64 = encoder.encode(bytes);
                Map<String, Object> params = new HashMap<>();
                params.put("strs", base64);
                synchronized (this) {
                    String result = HttpUtil.doPost("http://127.0.0.1:8000/captchar", params);
                    ImgVerifyResult imgVerifyResult = JSON.parseObject(result, ImgVerifyResult.class);
                    System.out.println("name:" + name + "  code:" + imgVerifyResult.getData() + " index:" + i);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
