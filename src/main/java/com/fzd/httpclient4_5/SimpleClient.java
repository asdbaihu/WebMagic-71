package com.fzd.httpclient4_5;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 最简单的HTTP客户端,用来演示通过GET或者POST方式访问某个页面
 *
 * @authorLiudong
 */

public class SimpleClient {
    static String keyWords = "辽中县茨榆坨镇益春釉彩加工厂";
    static String url = "https://s.1688.com/company/company_search.htm?keywords=%C5%AE%D7%B0&n=y&spm=a260k.635.3262836.d102&sug=1_0";
    public static void main(String[] args) throws UnsupportedEncodingException, InterruptedException {
//        String url = "https://www.itslaw.com/api/v1/caseFiles?startIndex=0&countPerPage=20&sortType=2&conditions=";
//        url = url + URLEncoder.encode("searchWord+"+keyWords+"+1+"+keyWords,"utf-8");
        for (int i = 0; i < 1; i++) {
            try {

                CloseableHttpClient client = HttpClients.createDefault();
                CookieStore cookieStore = new BasicCookieStore();
                HttpClientContext context = new HttpClientContext();
                context.setCookieStore(cookieStore);
//                HttpGet httpGet1 = new HttpGet("https://rate.taobao.com/user-rate-UvFcYMmHyOmHGONTT.htm");
//                CloseableHttpResponse response1 = client.execute(httpGet1, context);
//                System.out.println(EntityUtils.toString(response1.getEntity(), "UTF-8"));
                HttpGet httpGet2 = new HttpGet(url);
                httpGet2.addHeader(":authority", "rate.taobao.com");
                httpGet2.addHeader(":httpGet2","GET");
                httpGet2.addHeader(":path","/user-rate-UvCx4Mm8YOmgG.htm");
                httpGet2.addHeader(":scheme","https");
                httpGet2.addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
                httpGet2.addHeader("accept-encoding", "gzip, deflate, br");
                httpGet2.addHeader("accept-language", "zh-CN,zh;q=0.8");
                httpGet2.addHeader("cache-control", "max-age=0");
                httpGet2.addHeader("referer", "https://shop115016762.taobao.com/?spm=a230r.7195193.1997079397.2.XMRCB7");
                httpGet2.addHeader("upgrade-insecure-requests", "1");
                httpGet2.addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
                httpGet2.addHeader("cookie", "uc2=wuf=https%3A%2F%2Ftrade.tmall.com%2Fdetail%2ForderDetail.htm%3Fspm%3Da1z09.2.0.0.67002e8dkQmZjb%26bizOrderId%3D137808524882371182; enc=4xFBlJU627EBQ92CnClt2I9wTqWC%2FaAwLyO%2BH4Q8hxGacvGyjxXRRmy23BpVnjgbXN5%2B5VYyLpPq9z5xeqn82g%3D%3D; cookie2=3369a42a5b6cbccf44a3597924714612; swfstore=136333; t=a1ca24204feb9d8f3d4c94c480724e83; v=0; _tb_token_=36e439ba5db9a; cna=wT8uE4q3lhECAXZ0az8uGcYL; UM_distinctid=16339cc730a362-09fd3670650bc-3a614f0b-1fa400-16339cc730b6fa; uc3=nk2=25XcZzP5&id2=UonYuCff1bKy4g%3D%3D&vt3=F8dBz44lKrhTeA9FAO8%3D&lg2=VFC%2FuZ9ayeYq2g%3D%3D; existShop=MTUyNTY4MzEwMg%3D%3D; lgc=%5Cu4ED8%5Cu732A%5Cu5FB7; tracknick=%5Cu4ED8%5Cu732A%5Cu5FB7; dnk=%5Cu4ED8%5Cu732A%5Cu5FB7; sg=%E5%BE%B715; csg=a2f957c7; cookie1=Vyh1zqk072gHvkZ5aTB5bn99eCovx4hr9YgGTQEeF9I%3D; unb=1839378211; skt=b441e407e790d3ae; _cc_=VFC%2FuZ9ajQ%3D%3D; tg=0; _l_g_=Ug%3D%3D; _nk_=%5Cu4ED8%5Cu732A%5Cu5FB7; cookie17=UonYuCff1bKy4g%3D%3D; mt=ci=2_1; thw=cn; cq=ccp%3D0; x=e%3D1%26p%3D*%26s%3D0%26c%3D0%26f%3D0%26g%3D0%26t%3D0%26__ll%3D-1%26_ato%3D0; uc1=cookie14=UoTeO8tne%2BiW7A%3D%3D&lng=zh_CN&cookie16=U%2BGCWk%2F74Mx5tgzv3dWpnhjPaQ%3D%3D&existShop=false&cookie21=WqG3DMC9Fb5mPLIQo9kR&tag=8&cookie15=W5iHLLyFOGW7aA%3D%3D&pas=0; whl=-1%260%260%261525687779548; isg=BB0dKZasG0ztFP9gXAO33KZFLPnXklEL5hH3m9_iPXSjlj_IrIphXOtExIqQVmlE");

                CloseableHttpResponse response = client.execute(httpGet2, context);
                //打印服务器返回的状态
                int code = response.getStatusLine().getStatusCode();
                if (code > 400) {
                    System.out.println(code);
//                break;
                }
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(response.getEntity(), "UTF-8");
                if(result.contains("宝贝与描述相符")) {
                    System.out.println("1：" + result.contains("宝贝与描述相符"));
                }else if( result.contains("发现您的网络环境有异常，为保证正常使用，请验证")){
                    System.out.println("2：" + result.contains("发现您的网络环境有异常，为保证正常使用，请验证"));
                }else{
                    System.out.println("3：" + result.contains("密码"));
                }
                response.close();
                client.close();
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            Thread.sleep(6000);
        }
    }
}