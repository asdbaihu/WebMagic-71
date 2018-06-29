package com.fzd.mobile;
import com.fzd.httpclient4_5.Util;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by SRKJ on 2017/7/13.
 */
public class MobileClient {
    public static void main(String... arg){
//        try {
//            System.out.println(sendHttpGet("http://www.sc.10086.cn/login.html?url=my/SC_MY_INDEX.html"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        sendHttpGetByURI("");
//        try {
//            System.out.println(Util.sendHttpGet("http://www.sc.10086.cn/service/my/SC_MY_ZDCX.html"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        getSmsImgVerifyCode("http://www.sc.10086.cn/service/actionDispatcher.do",Util.getHttpClient(),"UTF-8");
    }



    /**
     * 发送 get请求
     * @param httpUrl
     */
    public static String sendHttpGetByURI(String httpUrl) {
//        HttpGet httpGet = new HttpGet(
//                "http://www.google.com/search?hl=en&q=httpclient&btnG=Google+Search&aq=f&oq=");
        URI uri = null;
        try {
            uri = URIUtils.createURI("http", "www.google.com", -1, "/search",
                    "q=httpclient&btnG=Google+Search&aq=f&oq=", null);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        HttpGet httpGet = new HttpGet(uri);
        System.out.println(httpGet.getURI());
        return null;
    }
}
