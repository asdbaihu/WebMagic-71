package com.fzd.httpclient;
import java.io.IOException;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.*;
import org.apache.http.*;

/**
 *提交参数演示
 *该程序连接到一个用于查询手机号码所属地的页面
 *以便查询号码段1330227所在的省份以及城市
 *@authorLiudong
 */

public class SimpleHttpClient {

    public static void main(String[] args) throws IOException {
        HttpClient client = new HttpClient();
        client.getHostConfiguration().setHost( "www.gsxt.gov.cn" , 80, "http" );
        HttpMethod method = getPostMethod();    // 使用 POST 方式提交数据
        client.executeMethod(method);   //打印服务器返回的状态
        System.out.println(method.getStatusLine());   //打印结果页面
        String response=new String(method.getResponseBodyAsString().getBytes("UTF-8"));

        //打印返回的信息
        System.out.println(response);
        method.releaseConnection();
    }

    /**

     * 使用 GET 方式提交数据
     *@return
     */

    private static HttpMethod getGetMethod(){
        return new GetMethod("/simcard.php?simcard=1330227");
    }



    /**
     * 使用 POST 方式提交数据
     *@return
     */

    private static HttpMethod getPostMethod(){
        /**
         * searchword:深圳市鸿华通交通设施工程有限公司
         token:129110725
         tab:
         geetest_challenge:447727925b35735629937a400d4f9583jp
         geetest_validate:68ae783f90cbace1956de68d0aa9478f
         geetest_seccode:68ae783f90cbace1956de68d0aa9478f|jordan
         */
        PostMethod post = new PostMethod( "/corp-query-search-1.html" );
        NameValuePair searchword = new NameValuePair( "searchword" , "深圳市鸿华通交通设施工程有限公司" );
        NameValuePair token = new NameValuePair("token","129110725");
        NameValuePair tab = new NameValuePair("tab","");
        NameValuePair geetest_challenge = new NameValuePair("geetest_challenge","447727925b35735629937a400d4f9583jp");
        NameValuePair geetest_validate = new NameValuePair("geetest_validate","68ae783f90cbace1956de68d0aa9478f");
        NameValuePair geetest_seccode = new NameValuePair("geetest_seccode","68ae783f90cbace1956de68d0aa9478f|jordan");
        post.setRequestBody( new NameValuePair[] { searchword,token,tab,geetest_challenge,geetest_validate,geetest_seccode});
        return post;
    }
}