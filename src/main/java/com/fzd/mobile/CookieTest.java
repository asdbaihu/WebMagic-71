package com.fzd.mobile;

import com.fzd.httpclient4_5.HttpClientHelper;
import com.fzd.util.DesCrypt;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;

import java.util.List;
import java.util.Scanner;

/**
 * Created by SRKJ on 2017/7/27.
 */

public class CookieTest {

    public static void main(String... arg) {
        try {
            System.out.println(HttpClientHelper.getInstance().doGet("http://www.qichacha.com/search?key=成都数融科技有限公司"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
