package com.fzd.mobile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fzd.httpclient4_5.Util;
import com.fzd.util.DesCrypt;
import org.apache.commons.logging.Log;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by SRKJ on 2017/7/13.
 */
public class Login {
    private static HttpClient httpClient = Util.getHttpClient();
    private static final String charset = "UTF-8";
    private static String Cookie;
    private static Scanner scanner = new Scanner(System.in);
    private static DesCrypt desCrypt = new DesCrypt();
    private static String mobile = "18482173967";
    private static final String dispatcher = "http://www.sc.10086.cn/service/actionDispatcher.do";
    private static HttpClientContext localcontext = new HttpClientContext();

    static{
        try{
            CookieStore cookieStore = new BasicCookieStore();
            localcontext.setCookieStore(cookieStore);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String... arg) {
//        try {
//            System.out.println("GetSession Begin:");
////            Cookie = getSessionId("http://www.sc.10086.cn/login.html?url=my/",httpClient);
////            Util.cookie = Cookie;
//            System.out.println(Cookie);
//            VerCode verCode = getSmsImgVerifyCode(dispatcher);
//            System.out.println("请输入图片验证码：");
//            String imgVerifyCode = scanner.nextLine();
//            imgVerifyCode = desCrypt.encode(imgVerifyCode);
//            System.out.println("加密后图片验证码：" + imgVerifyCode);
//            sendSms("http://www.sc.10086.cn/service/sms.do",imgVerifyCode);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        callRecords(dispatcher);
//        Login login = new Login();
        sendSms2();
//        login.login2("984219");
    }

    private static void login(String url, String verifyCode) {
        HttpPost httpPost = new HttpPost(url);
        //设置参数
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("reqUrl", "SCLogin"));
        list.add(new BasicNameValuePair("busiNum", "SCLogin"));
        list.add(new BasicNameValuePair("operType", "0"));
        list.add(new BasicNameValuePair("mobile", mobile));
        list.add(new BasicNameValuePair("password", "dz8zdrPc7go="));
        list.add(new BasicNameValuePair("verifyCode", verifyCode));
        list.add(new BasicNameValuePair("loginFormTab", ""));
        list.add(new BasicNameValuePair("passwordType", "2"));
        list.add(new BasicNameValuePair("url", "index.html?ts=" + System.currentTimeMillis()));
        if (list.size() > 0) {
            UrlEncodedFormEntity entity = null;
            try {
                entity = new UrlEncodedFormEntity(list, charset);
                httpPost.setEntity(entity);
                httpPost.setHeader("Cookie", "sasa");
                HttpResponse response = httpClient.execute(httpPost);
                if (response != null) {
                    HttpEntity resEntity = response.getEntity();
                    if (resEntity != null) {
                        String result = EntityUtils.toString(resEntity, charset);
                        System.out.println(result);
                        getPersonInfo(dispatcher);
                    }
                }
                Util.operateResponse(response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private static VerCode getSmsImgVerifyCode(String url,String key) {
        HttpPost httpPost = new HttpPost(url);
        //设置参数
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("reqUrl", "SC_VerCode"));
        list.add(new BasicNameValuePair("busiNum", "SC_VerCode"));
        list.add(new BasicNameValuePair("key", ""));
        VerCode verCode = null;
        if (list.size() > 0) {
            UrlEncodedFormEntity entity = null;
            try {
                entity = new UrlEncodedFormEntity(list, charset);
                httpPost.setEntity(entity);
                HttpResponse response = httpClient.execute(httpPost);
                if (response != null) {
                    HttpEntity resEntity = response.getEntity();
                    if (resEntity != null) {
                        String result = EntityUtils.toString(resEntity, charset);
                        verCode = JSON.parseObject(result, VerCode.class);
                    }
                }
                for (Header header : response.getAllHeaders()) {
                    StringBuffer cookie = new StringBuffer();
                    if (header.getName().equals("Set-Cookie")) {
                        cookie.append(header.getValue());
                        cookie.append(";");
                    }
                    Util.cookie = cookie.toString();
                }
//                Util.operateResponse(response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return verCode;
    }


    private static void sendSms(String url, String imgVerifyCode) {
        HttpPost httpPost = new HttpPost(url);
        //设置参数
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("busiNum", "SCLoginSMS"));
        list.add(new BasicNameValuePair("mobile", mobile));
        list.add(new BasicNameValuePair("smsType", "1"));
        list.add(new BasicNameValuePair("passwordType", "2"));
        list.add(new BasicNameValuePair("imgVerCode", imgVerifyCode));
        httpPost = (HttpPost) Util.setHeaders(httpPost);
        Util.printHeader(httpPost.getAllHeaders());
        if (list.size() > 0) {
            UrlEncodedFormEntity entity = null;
            try {
                entity = new UrlEncodedFormEntity(list, charset);
                httpPost.setEntity(entity);
                HttpResponse response = httpClient.execute(httpPost);
                if (response != null) {
                    HttpEntity resEntity = response.getEntity();
                    if (resEntity != null) {
                        String result = EntityUtils.toString(resEntity, charset);
                        System.out.println(result);
                        JSONObject jsonObject = JSON.parseObject(result);
                        String resultMsg = jsonObject.getString("resultMsg");
                        if ("".equals(resultMsg)) {
                            System.out.println("请输入短信验证码：");
                            String smsVerifyCode = scanner.nextLine();
                            smsVerifyCode = desCrypt.encode(smsVerifyCode);
                            System.out.println("加密后短信验证码：" + smsVerifyCode);
                            login("http://www.sc.10086.cn/service/actionDispatcher.do", smsVerifyCode);
                        } else {
                            VerCode verCode = getSmsImgVerifyCode("http://www.sc.10086.cn/service/actionDispatcher.do","LOGIN_SMS");
                            System.out.println("请重新输入图片验证码：");
                            imgVerifyCode = scanner.nextLine();
                            imgVerifyCode = desCrypt.encode(imgVerifyCode);
                            System.out.println("加密后图片验证码：" + imgVerifyCode);
                            sendSms("http://www.sc.10086.cn/service/sms.do", imgVerifyCode);
                        }
                    }
                    return;
                }
                Util.operateResponse(response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private static void getPersonInfo(String url) {
        HttpPost httpPost = new HttpPost(url);
        //设置参数
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("reqUrl", "SC_GetAccountInfo"));
        if (list.size() > 0) {
            UrlEncodedFormEntity entity = null;
            try {
                entity = new UrlEncodedFormEntity(list, charset);
                httpPost.setEntity(entity);
                HttpResponse response = httpClient.execute(httpPost);
                if (response != null) {
                    HttpEntity resEntity = response.getEntity();
                    if (resEntity != null) {
                        String result = EntityUtils.toString(resEntity, charset);
                        System.out.println(result);
                    }
                }
                Util.operateResponse(response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void callRecords(String url) {
//        HttpGet getImg



        HttpPost httpPost = new HttpPost(url);
        //设置参数
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("reqUrl", "SC_MY_XDCXQuery"));
        list.add(new BasicNameValuePair("detailBillType", "02"));
        list.add(new BasicNameValuePair("date", "201707"));
        httpPost = (HttpPost) Util.setHeaders(httpPost);
        Util.printHeader(httpPost.getAllHeaders());
        if (list.size() > 0) {
            UrlEncodedFormEntity entity = null;
            try {
                entity = new UrlEncodedFormEntity(list, charset);
                httpPost.setEntity(entity);
                HttpResponse response = httpClient.execute(httpPost);
                if (response != null) {
                    HttpEntity resEntity = response.getEntity();
                    if (resEntity != null) {
                        String result = EntityUtils.toString(resEntity, charset);
                        System.out.println(result);
                    }
                }
                Util.operateResponse(response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private static void sendSms2() {
        System.out.print("hashCode: " + httpClient.hashCode());
        HttpPost httpPost = new HttpPost("https://login.10086.cn/sendRandomCodeAction.action");
        //设置参数
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("userName", mobile));
        list.add(new BasicNameValuePair("type", "01"));
        list.add(new BasicNameValuePair("channelID", "12003"));
        httpPost = (HttpPost) Util.setHeaders(httpPost);
        Util.printHeader(httpPost.getAllHeaders());
        if (list.size() > 0) {
            UrlEncodedFormEntity entity = null;
            try {
                entity = new UrlEncodedFormEntity(list, charset);
                httpPost.setEntity(entity);
                HttpResponse response = httpClient.execute(httpPost);
                if (response != null) {
                    HttpEntity resEntity = response.getEntity();
                    if (resEntity != null) {
                        String result = EntityUtils.toString(resEntity, charset);
                        System.out.println(result);
//                        JSONObject jsonObject = JSON.parseObject(result);
//                        String resultMsg = jsonObject.getString("resultMsg");
//                        System.out.println(resultMsg);
                        String smsVerifyCode = scanner.nextLine();
                        Header[] headers = response.getAllHeaders();
                        for (Header header : headers) {
                            if (header.getName().equals("Set-Cookie")) {
                                login2(smsVerifyCode, header.getValue());
                            }
                        }
//                        if("".equals(resultMsg)){
//                            System.out.println("请输入短信验证码：");
//                            String smsVerifyCode = scanner.nextLine();
//                            smsVerifyCode = desCrypt.encode(smsVerifyCode);
//                            System.out.println("加密后短信验证码：" + smsVerifyCode);
//                            login("http://www.sc.10086.cn/service/actionDispatcher.do",smsVerifyCode);
//                        }else {
//                            VerCode verCode = getSmsImgVerifyCode("http://www.sc.10086.cn/service/actionDispatcher.do");
//                            System.out.println("请重新输入图片验证码：");
//                            imgVerifyCode = scanner.nextLine();
//                            imgVerifyCode = desCrypt.encode(imgVerifyCode);
//                            System.out.println ("加密后图片验证码：" + imgVerifyCode);
//                            sendSms("http://www.sc.10086.cn/service/sms.do",imgVerifyCode);
//                        }
                    }
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void login2(String verifyCode,String cookie) {
        System.out.print("hashCode: " + httpClient.hashCode());
        //设置参数
        StringBuffer sb = new StringBuffer("https://login.10086.cn/login.htm?");
        sb.append("accountType=" + "01");
        sb.append("&account=" + mobile);
        sb.append("&password=" + verifyCode);
        sb.append("&pwdType=" + "02");
        sb.append("&smsPwd=");
        sb.append("&inputCode=");
        sb.append("&backUrl="+"http://www.10086.cn/sc/index_280_280.html");
        sb.append("&rememberMe=" + 0);
        sb.append("&channelID=" + "12003");
        sb.append("&protocol=" + "https:");
        sb.append("&timestamp=" + System.currentTimeMillis());
        System.out.println(sb.toString());
        HttpGet httpGet = new HttpGet(sb.toString());
//        httpGet.setHeader("Cookie",cookie);
//        httpGet.setHeader("Cookie","cmccssotoken=c36a777baa2145ae83ffd887d40aab33@.10086.cn; userinfokey=%7b%22loginType%22%3a%2201%22%2c%22provinceName%22%3a%22280%22%2c%22pwdType%22%3a%2202%22%2c%22userName%22%3a%2218482173967%22%7d; c=c36a777baa2145ae83ffd887d40aab33; verifyCode=7eb8390d3542fcb29acd4a0e402d25ebdd3b0636; WT_FPC=id=23dfe8ebfb4d780d4271501031465356:lv=1501053105300:ss=1501053105300; CmLocation=280|280; CmProvid=bj; jsessionid-echd-cpt-cmcc-jt=1436DD9241BF90F4AA8EB3BC44176B8E; freelogin_userlogout=; CaptchaCode=DNwBRN; rdmdmd5=A6A250803B9BABDC03AC3F6ADD348B8E");
//        httpGet.setHeader("Accept","application/json, text/javascript, */*; q=0.01");
//        httpGet.setHeader("Accept-Encoding","gzip, deflate, br");
//        httpGet.setHeader("Accept-Language","zh-CN,zh;q=0.8");
//        httpGet.setHeader("Cache-Control","max-age=0");
//        httpGet.setHeader("Connection","keep-alive");
//        httpGet.setHeader("Host","login.10086.cn");
//        httpGet.setHeader("Referer", "https://login.10086.cn/html/login/login.html?channelID=12027&backUrl=http%3A%2F%2Fwww.10086.cn%2Fsc%2Findex_280_280.html");
//        httpGet.setHeader("X-Requested-With","XMLHttpRequest");
//        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");
        Util.printHeader(httpGet.getAllHeaders());
        try {
            HttpResponse response = httpClient.execute(httpGet,localcontext);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    String result = EntityUtils.toString(resEntity, charset);
                    System.out.println(result);
                    getPersonInfo(dispatcher);
                }
            }
            Util.operateResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
