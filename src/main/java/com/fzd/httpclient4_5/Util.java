package com.fzd.httpclient4_5;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by SRKJ on 2017/7/13.
 */
public class Util {

    private static final int maxTotal = 5;
    private static final int defaultMaxPerRoute = 1;
    private int connectTimeout = 5000;
    private int readTimeout = 5000;
    private static PoolingHttpClientConnectionManager cm;
    public static String cookie = "";
    static {
        ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
        LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", plainsf)
                .register("https", sslsf)
                .build();
        cm = new PoolingHttpClientConnectionManager(registry);
        //最大连接数
        cm.setMaxTotal(maxTotal);
        //每个路由基础的连接
        cm.setDefaultMaxPerRoute(defaultMaxPerRoute);
        //上面两句中的变量可以通过类的属性来确定
    }

    private RequestConfig getRequestConfig() {
        return RequestConfig.custom()
                .setConnectTimeout(connectTimeout)
                .setSocketTimeout(readTimeout)
                .build();
    }

    public static HttpClient getHttpClient() {
//        return HttpClients.custom().setConnectionManager(cm).build();
        return new DefaultHttpClient();
    }

    public static void operateResponse(HttpResponse response) {
        System.out.println("ResponseHeader Begin:------------------");
        System.out.println(response.getProtocolVersion());
        System.out.println(response.getStatusLine().getStatusCode());
        System.out.println(response.getStatusLine().getReasonPhrase());
        System.out.println(response.getStatusLine().toString());
        System.out.println(response.getLocale());
        Header[] headers = response.getAllHeaders();
        printHeader(headers);
        System.out.println("ResponseHeader End:------------------");
    }

    public static void printHeader(Header[] headers) {
        for(Header header : headers){
            System.out.println(header.getName() + " : " + header.getValue());
        }
    }

    /**
     * 发送 get请求
     * @param httpUrl
     */
    public static Map<String,Object> sendHttpGet(String httpUrl,HttpClient httpClient) throws IOException {
        HttpGet httpGet = new HttpGet(httpUrl);// 创建get请求
        return sendHttpGet(httpGet,httpClient);
    }


    /**
     * 发送Get请求
     * @param
     * @return
     */
    private static Map<String,Object> sendHttpGet(HttpGet httpGet, HttpClient httpClient) throws IOException {
        setHeaders(httpGet);
        System.out.println("RequestHeader Begin:------------------");
        Util.printHeader(httpGet.getAllHeaders());
        System.out.println("RequestHeader End:------------------");
        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        StringBuffer sb = new StringBuffer();
        if (entity != null) {
            InputStream instream = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    instream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("content",sb.toString());
        map.put("headers",response.getAllHeaders());
        return map;
    }

    public static HttpRequestBase setHeaders(HttpRequestBase httpRequestBase){
//        httpGet.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
//        httpGet.setHeader("Accept-Encoding","gzip, deflate");
//        httpGet.setHeader("Accept-Language","zh-CN,zh;q=0.8");
//        httpGet.setHeader("Cache-Control","max-age=0");
//        httpGet.setHeader("Connection","keep-alive");
//        httpRequestBase.setHeader("Cookie","JSESSIONID=i9Y_tpy8KaUg3ZJAsX4ZM8YPK91z-VfRUrJWPE8f-ZF0R52kzCgh!-1791012468;xwt=r_xwt_108");
        //        httpGet.setHeader("Host","www.sc.10086.cn");
//        httpGet.setHeader("Referer", "http://www.sc.10086.cn/service/my/SC_MY_INDEX.html?ts=1499943057474");
//        httpGet.setHeader("Upgrade-Insecure-Requests","1");
//        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");
//        httpRequestBase.setHeader("Cookie",cookie);
//        httpRequestBase.setHeader("Cookie","JSESSIONID=HpF4wF-tqXP3ISIKFO1IqR5WIvnRUk5Pok_j1Po5XX7DYMKR-v-K!2060932554;xwt=r_xwt_107");
        return httpRequestBase;
    }

}
