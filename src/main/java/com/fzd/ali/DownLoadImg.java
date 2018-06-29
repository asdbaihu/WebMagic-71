package com.fzd.ali;

import com.fzd.httpclient4_5.HttpUtil;
import io.netty.handler.codec.base64.Base64Encoder;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by FZD on 2018/5/29.
 * Description:
 */
public class DownLoadImg {
    public static void main(String... args) throws IOException {
        for(int i = 0; i < 1; i++) {
            String url = "//pin.aliyun.com/get_img?sessionid=fcf6aba025e7135f5b31354cbacd056b&identity=sm-searchweb2&type=number";
            HttpGet httpGet = new HttpGet("https:" + url);
            CloseableHttpResponse response = HttpUtil.httpClient.execute(httpGet);
            //打印服务器返回的状态
            int code = response.getStatusLine().getStatusCode();
            if (code > 400) {
                System.out.println(code);
//                break;
            }
            HttpEntity entity = response.getEntity();
            byte[] data = EntityUtils.toByteArray(entity);

            BASE64Decoder base64Decoder = new BASE64Decoder();
            BASE64Encoder encoder = new BASE64Encoder();
            String base64 = encoder.encode(data);
            data = base64Decoder.decodeBuffer(base64);
            FileOutputStream fos = new FileOutputStream("C:\\Users\\SRKJ\\Desktop\\number\\"+i+".jpg");
            fos.write(data);
            fos.close();
        }
    }
}
