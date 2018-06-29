package com.fzd.httpclient4_5;

import org.apache.http.HttpEntity;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * 最简单的HTTP客户端,用来演示通过GET或者POST方式访问某个页面
 *
 * @authorLiudong
 */

public class SimpleClient2 {
    static String[] url = {
            "http://www.qichacha.com/search?key=%E6%88%90%E9%83%BD%E6%95%B0%E8%9E%8D%E7%A7%91%E6%8A%80"};
    static String[] referer = {
            "http://www.qichacha.com/search?key=%E6%88%90%E9%83%BD%E6%95%B0%E8%9E%8D%E7%A7%91%E6%8A%80"};
    public static void main(String[] args) throws IOException, InterruptedException {

        CloseableHttpClient client = HttpClients.createDefault();
        CookieStore cookieStore = new BasicCookieStore();
        HttpClientContext context = new HttpClientContext();
        context.setCookieStore(cookieStore);
        for(int i = 0; i < url.length; i++){
            HttpGet httpGet = new HttpGet(url[i]);
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
//            httpGet.setHeader("Cookie","acw_sc__=5a2f6df898530a7efebcf5854f42614ae0e36b41;");
            CloseableHttpResponse response = client.execute(httpGet, context);
            //打印服务器返回的状态
            int code = response.getStatusLine().getStatusCode();
            if(code > 400) {
                System.out.println(code);
                break;
            }
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(response.getEntity(), "UTF-8");
            System.out.println(url[0] +"："+cookieStore.getCookies());
//            System.out.println(result);
            System.out.println(result.contains("企查查"));
//            //释放连接
            httpGet.releaseConnection();
            System.out.println(i);
        }
    }

    public static String getHost(String url){
        String rootUrl = url;
        if( url.contains( "http://" ) ) {
            int idx = url.indexOf( "/", "http://".length() );
            if(idx != -1){
                rootUrl = url.substring( 0, idx );
            }
            rootUrl = rootUrl.substring("http://".length());
        } else if( url.contains( "https://" ) ) {
            int idx = url.indexOf( "/", "https://".length() );
            if(idx != -1){
                rootUrl = url.substring( 0, idx );
            }
            rootUrl = rootUrl.substring("https://".length());
        }
        if(url.contains("?")){
            int end = rootUrl.indexOf("?");
            if(end != -1){
                rootUrl = rootUrl.substring(0, end);
            }

        }
        return rootUrl;
    }

}