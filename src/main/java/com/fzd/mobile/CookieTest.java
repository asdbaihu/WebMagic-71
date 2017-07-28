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
    private static HttpClientHelper httpClientHelper = HttpClientHelper.getInstance();
    private static HttpClient httpClient = httpClientHelper.getConnection();
    private static final String charset = "UTF-8";
    private static String Cookie;
    private static Scanner scanner = new Scanner(System.in);
    private static DesCrypt desCrypt = new DesCrypt();
    private static String mobile = "18408249626";
    private static final String dispatcher = "http://www.sc.10086.cn/service/actionDispatcher.do";
    private static HttpClientContext localcontext = null;
    private static String channelID = "12027";

    static {
        try{
            CookieStore cookieStore = new BasicCookieStore();
            localcontext = HttpClientContext.create();
            localcontext.setCookieStore(cookieStore);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String... arg) {
        try {
//
            HttpClientHelper.getInstance().doGet("http://www1.10086.cn/service/sso/checkuserloginstatus.jsp?callback=checkuserloginstatuscallback&_=1501122383927");
            List<org.apache.http.cookie.Cookie> cookies = HttpClientHelper.getInstance().getCookieStore().getCookies();
            System.out.println("CookieStore:" + cookies);
            HttpClientHelper.getInstance().doGet("http://www1.10086.cn/service/sso/checkuserloginstatus.jsp?callback=checkuserloginstatuscallback&_=1501122383927");
//            System.out.println("CookieStore:" + cookies);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
