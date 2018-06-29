package com.fzd.ali;

import com.fzd.httpclient4_5.HttpUtil;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * 最简单的HTTP客户端,用来演示通过GET或者POST方式访问某个页面
 *
 * @authorLiudong
 */

public class Search1688 {
    static String url = "https://s.1688.com/company/company_search.htm?keywords=%C5%AE%D7%B0&button_click=top&earseDirect=false&n=y";
    static Search1688 search1688 = new Search1688();
    public static void main(String[] args) throws IOException, InterruptedException {
//        search1688.crawl();
        search1688.getImg("//pin.aliyun.com/get_img?sessionid=fcf6aba025e7135f5b31354cbacd056b&identity=sm-searchweb2&type=default");
        search1688.submitImg();

    }

    private void crawl() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            try {
                HttpGet httpGet = new HttpGet(url);
                httpGet.addHeader("cookie","UM_distinctid=1627131a18726f-00c2677feff5ed-3a614f0b-1fa400-1627131a188313; cna=wT8uE4q3lhECAXZ0az8uGcYL; __last_loginid__=%E4%BB%98%E7%8C%AA%E5%BE%B7; last_mid=b2b-1839378211; hng=CN%7Czh-CN%7CCNY%7C156; lid=%E4%BB%98%E7%8C%AA%E5%BE%B7; ali_apache_track=c_mid=b2b-1839378211|c_lid=%E4%BB%98%E7%8C%AA%E5%BE%B7|c_ms=1; _cn_slid_=faNQ3gTey1; ali_ab=218.88.26.197.1525773293068.7; alisw=swIs1200%3D1%7C; ali_beacon_id=218.88.26.197.1525773937972.710014.7; _is_show_loginId_change_block_=b2b-1839378211_false; _show_force_unbind_div_=b2b-1839378211_false; _show_sys_unbind_div_=b2b-1839378211_false; _show_user_unbind_div_=b2b-1839378211_false; __rn_alert__=false; alicnweb=touch_tb_at%3D1527833518058%7Clastlogonid%3D%25E4%25BB%2598%25E7%258C%25AA%25E5%25BE%25B7%7ChomeIdttS%3D53164068340257168870485693372392195909%7ChomeIdttSAction%3Dtrue%7Chp_newbuyerguide%3Dtrue%7Cshow_inter_tips%3Dfalse; h_keys=\"%u5973%u88c5#%u6fc2%u5ba0%ue5ca#%u6df1%u5733%u5e02%u653f%u9e3f%u5fae%u7535%u5b50%u6709%u9650%u516c%u53f8#%u7537%u88c5#%u5185%u8863#%u978b%u9774#%u7bb1%u5305#%u914d%u9970#%u8fd0%u52a8%u670d%u9970\"; JSESSIONID=YK1Zsxl-AiXZ0Krzyn6F9vMQY8-ExLLhtQ-4mNf3; cookie2=18ce27680cdf0c6775490c4469f6e490; t=fda5943929603be20bb78ddb7f857fef; _tb_token_=33d5d51ebee77; cookie1=Vyh1zqk072gHvkZ5aTB5bn99eCovx4hr9YgGTQEeF9I%3D; cookie17=UonYuCff1bKy4g%3D%3D; sg=%E5%BE%B715; csg=6116ecb2; __cn_logon__=true; __cn_logon_id__=%E4%BB%98%E7%8C%AA%E5%BE%B7; ali_apache_tracktmp=c_w_signed=Y; LoginUmid=xLJwvClELt816qnTW0KkZdo5sLWk%2Ftj0aq4l%2BV0yj5Yyp8JBi5lr6w%3D%3D; unb=1839378211; tbsnid=DmtszC%2FChh6lsepbVNWOCmvr9KxX0qmQEaXfbs3Sj1s6sOlEpJKl9g%3D%3D; cn_tmp=\"Z28mC+GqtZ1CLMKfEwL7u2ICZVpZdxHRmLcLU9/oAa6a5WC6V3phTq4t0hL4hddyUTqFuE25/OIAoVl0iVZTXNN5QIYGkA/9M4EDpHKyZ9Dcxefk3pjESWGrH4nCeNLQg9Ujw7kt+gez0jXO4yhcErmYkmTZ8spspIQ0XIXG4Bki9a/4/UVG0MCBxDgPNBvWrm6r5z80AbG48zQ737g4ljlPfx8ODmIrpb1PY8iGTHNO7NlYr0IrRw==\"; login=kFeyVBJLQQI%3D; userID=YuieXOHrx2M4%2BtXcIgqZqt20SUO8PSgz96JsiX9uRzM6sOlEpJKl9g%3D%3D; _nk_=1nOtq11N8pk%3D; userIDNum=ZxPKNZE3L1psO6kAAHeQyQ%3D%3D; _tmp_ck_0=3z%2FWkVuAy5OtahrxgM8tAJ3xbNUexF3sgE%2F6%2Bzc%2FaQrVbo5MIbhiDwwv3yWVH9WM465RfWOW0Lf9dos8pPtJQhPZ7d%2Bsr6iFHlpbDqZoMymXRBLD%2F8qv8QBh0DjzzULug3xqsMkhSLQ0VD%2FF93yEI3g%2B8NydFSzqxzIU4U1WsOMBcnIOFs8e2SQxxGSFIOV%2BaXa%2BuecuVXOck3W1bVoLE7KncaSvfDAtLyRbqxZz34GCMPEs1ttPcRDEWWWYL0bE16MY3J2xyTXVLUVKZMV7RF3%2BhHI6YL5rRe%2Bp8lvFvGuwNa1KwPdPc2jR9jFxw1Zzg3gB1aC6wgQk%2F%2FSRvYm3Y0KskuQJlmyFbZjfcroHtzVqeGPwRPXBPUM4DtBeWRa6hqykI5MKQaM%2BSGe78ovwDMHBRogni014z13dmywlj%2B6bQHcYnn2DjV1hCW1NGN3JZ9JymuivVlx3tGFI6dQMHuiIkDJLyz7VR16BPu29Nm%2FnjQCbeBBlbiNDPZUnMbYOXACdWZtyKTk%3D; _csrf_token=1527834464999; ad_prefer=\"2018/06/01 14:27:46\"; isg=BD8_ygXNWGZ6zF3WeYEsMuEZzhMJjJNBEAfVQdEM0O414F9i2fQjFr3yJrAeuGs-");
                //httpGet.addHeader("cookie", "UM_distinctid=1627131a18726f-00c2677feff5ed-3a614f0b-1fa400-1627131a188313; cna=wT8uE4q3lhECAXZ0az8uGcYL; __last_loginid__=%E4%BB%98%E7%8C%AA%E5%BE%B7; last_mid=b2b-1839378211; hng=CN%7Czh-CN%7CCNY%7C156; lid=%E4%BB%98%E7%8C%AA%E5%BE%B7; ali_apache_track=c_mid=b2b-1839378211|c_lid=%E4%BB%98%E7%8C%AA%E5%BE%B7|c_ms=1; _cn_slid_=faNQ3gTey1; ali_ab=218.88.26.197.1525773293068.7; alisw=swIs1200%3D1%7C; ali_beacon_id=218.88.26.197.1525773937972.710014.7; _alizs_area_info_=1001; _alizs_user_type_=purchaser; _alizs_cate_info_=70; _m_h5_tk=f17e53be3be90271ef85b3c925fc774e_1525839510967; _m_h5_tk_enc=b31009b7df4fc7b30c3e6db3272ed272; h_keys=\"%u5973%u88c5#%u673a%u68b0#%u6fc2%u5ba0%ue5ca#%u516c%u53f8#%u6bdb%u7ebf#%u5e7f%u5dde%u6f6e%u6d41#%u884c#%u5e97#das1\"; __rn_alert__=false; JSESSIONID=A1zYKLe-tHgZp1ATg5qdyIsrB4-6wQAirQ-aOG1; cookie2=11b5a43c96ade1fc0ac5fa8946a51e75; t=fda5943929603be20bb78ddb7f857fef; _tb_token_=4b731de5a5fe; alicnweb=touch_tb_at%3D1526016516985%7Clastlogonid%3D%25E4%25BB%2598%25E7%258C%25AA%25E5%25BE%25B7%7ChomeIdttS%3D53164068340257168870485693372392195909%7ChomeIdttSAction%3Dtrue%7Chp_newbuyerguide%3Dtrue%7Cshow_inter_tips%3Dfalse; cookie1=Vyh1zqk072gHvkZ5aTB5bn99eCovx4hr9YgGTQEeF9I%3D; cookie17=UonYuCff1bKy4g%3D%3D; sg=%E5%BE%B715; csg=5a2acb1f; __cn_logon__=true; __cn_logon_id__=%E4%BB%98%E7%8C%AA%E5%BE%B7; ali_apache_tracktmp=c_w_signed=Y; LoginUmid=xLJwvClELt816qnTW0KkZdo5sLWk%2Ftj0aq4l%2BV0yj5Yyp8JBi5lr6w%3D%3D; unb=1839378211; tbsnid=T4XJLROZVFDhf%2Fnq0jOX7b6AiOAK1g1mPwasZocvvO06sOlEpJKl9g%3D%3D; cn_tmp=\"Z28mC+GqtZ1CLMKfEwL7u2ICZVpZdxHRmLcLU9/oAa6a5WC6V3phTq4t0hL4hddyUTqFuE25/OIAoVl0iVZTXNN5QIYGkA/9M4EDpHKyZ9Dcxefk3pjESWGrH4nCeNLQg9Ujw7kt+gez0jXO4yhcErmYkmTZ8spspIQ0XIXG4Bki9a/4/UVG0MCBxDgPNBvWrm6r5z80AbG48zQ737g4ljlPfx8ODmIrKU5jHUpdmpIB4ZjzaeXnww==\"; login=kFeyVBJLQQI%3D; userID=YuieXOHrx2M4%2BtXcIgqZqt20SUO8PSgz96JsiX9uRzM6sOlEpJKl9g%3D%3D; _nk_=1nOtq11N8pk%3D; userIDNum=ZxPKNZE3L1psO6kAAHeQyQ%3D%3D; _csrf_token=1526016930959; _is_show_loginId_change_block_=b2b-1839378211_false; _show_force_unbind_div_=b2b-1839378211_false; _show_sys_unbind_div_=b2b-1839378211_false; _show_user_unbind_div_=b2b-1839378211_false; ad_prefer=\"2018/05/11 13:54:28\"; _tmp_ck_0=\"kbtFEb0jsRLvhPCRfKOrsS%2BFYZwo6W9MWKu9pASiuAjzzORnyOqIzUJEco8CZTQi0PztuIdNRkolf7KICWNjuf8qc7b432%2FM8fT%2FCaM47qhe4Z9pWd31llXB1KUsSsFF8CODRC8gIiJImSidi1UYL6DCYtAEXjWPoK2y0sGNz%2BKjo9UtJ6PiG3xM93O43NvtM1oYfMFZJ7LhxiUw49RQurQunln8%2B%2BL4HkiZEKJ42rZH6%2Bdc2vEuPMtgJcK5D2weTSog6oUyRGfpHfQ3CBzx9Ni75WNYyI5Q6GMoRIbfFuQPvenOVtzxOADVv8CHsP1wP%2FszvzexbDRXsm8EBSw%2FzfbroWPdcPdBYrAA1DgaxRIn%2FccJXj0%2BpiXQTM%2BSeOnGFiSQVJSH9Kgn8e5DoMEfo%2F1rIDrW3C8CbCmxuKSKsf%2BqKtEYSa1Be0hB6tmgUNW4zeFXZpZM9BfFaxRC3WCJ%2FBa8JS966IlfWv5lpBIFNhvXvskxuNKrjsBpSk4PtSSqvsrFmkO71uQu77YkhYONAQ%3D%3D\"; isg=BODgV2QTrtYmqxKbsly7x_pMse5yQcQ027JaTFrxrPuOVYB_AvmUQ7Zn6f1VZXyL");
                String result = HttpUtil.doMethod(httpGet);
                if(result.contains("1688/淘宝会员（仅限会员名）请在此登录")) {
                    System.out.println("1688/淘宝会员（仅限会员名）请在此登录");
                } else if(result.contains("发现您的网络环境有异常，为保证正常使用，请验证")){
                    System.out.println("发现您的网络环境有异常，为保证正常使用，请验证");
                    search1688.getImg(HttpUtil.buildHtml(result.getBytes(), "utf-8").getDocument().select("#checkcodeImg").get(0).attr("src"));
                    search1688.submitImg();
                }else {
                    System.out.println("获取数据成功：" +  result.contains("您可以在阿里巴巴公司黄页搜索到关于女装生产商的工商注册年份、员工人数、年营业额、信用记录、相关女装产品的供求信息、交易记录等企业详情。"));
                }
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
//            Thread.sleep(6000);
        }
    }

    public byte[] getImg(String url) throws IOException {
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
        FileOutputStream fos = new FileOutputStream("C:\\Users\\SRKJ\\IdeaProjects\\WebMagic\\src\\main\\java\\com\\fzd\\ali\\a.jpg");
        fos.write(data);
        fos.close();
//        System.out.println("图片下载成功");
        return data;
    }

    public void submitImg() throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入图片验证码");
        String checkCode = scanner.nextLine();
        HttpPost httpPost = new HttpPost("https://sec.1688.com/query.htm");
        httpPost.addHeader("Cookie",
            "UM_distinctid=1627131a18726f-00c2677feff5ed-3a614f0b-1fa400-1627131a188313; cna=wT8uE4q3lhECAXZ0az8uGcYL; __last_loginid__=%E4%BB%98%E7%8C%AA%E5%BE%B7; last_mid=b2b-1839378211; hng=CN%7Czh-CN%7CCNY%7C156; lid=%E4%BB%98%E7%8C%AA%E5%BE%B7; ali_apache_track=c_mid=b2b-1839378211|c_lid=%E4%BB%98%E7%8C%AA%E5%BE%B7|c_ms=1; _cn_slid_=faNQ3gTey1; ali_ab=218.88.26.197.1525773293068.7; ali_beacon_id=218.88.26.197.1525773937972.710014.7; JSESSIONID=9L78lO0l1-GhXZ1OzZdQrAf9dqT9-t8CixtQ-oDe94; cookie2=16ddc1a30821550a15825fb47817e8e6; t=fda5943929603be20bb78ddb7f857fef; _tb_token_=7beee8197e7be; __cn_logon__=true; __cn_logon_id__=%E4%BB%98%E7%8C%AA%E5%BE%B7; ali_apache_tracktmp=c_w_signed=Y; LoginUmid=xLJwvClELt816qnTW0KkZdo5sLWk%2Ftj0aq4l%2BV0yj5Yyp8JBi5lr6w%3D%3D; tbsnid=a6rIMybPxKbfjsezuQKj1%2Be7DbJN%2BS6QF528gS9gj5s6sOlEpJKl9g%3D%3D; userID=YuieXOHrx2M4%2BtXcIgqZqt20SUO8PSgz96JsiX9uRzM6sOlEpJKl9g%3D%3D; userIDNum=ZxPKNZE3L1psO6kAAHeQyQ%3D%3D; h_keys=\"%u6df1%u5733%u5e02%u9f99%u5c97%u533a%u8863%u6e58%u574a%u670d%u9970%u5546%u884c#%u5973%u88c5#%u6df1%u5733%u5e02%u5de8%u660a%u946b%u8d38%u6613%u6709%u9650%u516c%u53f8#%u6df1%u5733%u5e02%u5b9d%u5b89%u533a%u897f%u4e61%u552f%u7231%u8863%u670d%u88c5%u5382#%u6df1%u5733%u5e02%u5361%u95e8%u670d%u9970%u6709%u9650%u516c%u53f8#%u6df1%u5733%u5e02%u9f99%u5c97%u533a%u7f18%u83b1%u6052%u6f6e%u6d41%u670d%u9970%u5546%u884c#%u6fc2%u5ba0%ue5ca#%u6df1%u5733%u5e02%u653f%u9e3f%u5fae%u7535%u5b50%u6709%u9650%u516c%u53f8#%u7537%u88c5#%u5185%u8863\"; alicnweb=touch_tb_at%3D1528098002587%7Clastlogonid%3D%25E4%25BB%2598%25E7%258C%25AA%25E5%25BE%25B7%7ChomeIdttS%3D53164068340257168870485693372392195909%7ChomeIdttSAction%3Dtrue%7Chp_newbuyerguide%3Dtrue%7Cshow_inter_tips%3Dfalse; cookie1=Vyh1zqk072gHvkZ5aTB5bn99eCovx4hr9YgGTQEeF9I%3D; cookie17=UonYuCff1bKy4g%3D%3D; sg=%E5%BE%B715; csg=f0c39862; unb=1839378211; cn_tmp=\"Z28mC+GqtZ1CLMKfEwL7u2ICZVpZdxHRmLcLU9/oAa6a5WC6V3phTq4t0hL4hddyUTqFuE25/OIAoVl0iVZTXNN5QIYGkA/9M4EDpHKyZ9Dcxefk3pjESWGrH4nCeNLQg9Ujw7kt+gez0jXO4yhcErmYkmTZ8spspIQ0XIXG4Bki9a/4/UVG0MCBxDgPNBvWrm6r5z80AbG48zQ737g4ljlPfx8ODmIrLSd5ONba5hmJGZLTJv3BBw==\"; login=kFeyVBJLQQI%3D; _nk_=1nOtq11N8pk%3D; _tmp_ck_0=jTA9N8%2B4RDUuHZ33VyF66ctaKRDYKPc4AlrjnGPThT67rrYUGSdI3%2BQR25thb6ozaUAYdTM%2B8%2FNnTdpX9VSewdHe992eMEuU7%2Fw1ZZEj5yaQwNctLnzvm8Tkl28W4PMM0v5UtBNUXwgZui%2BgFz7XjVR5o1uT4hecz0Il9d%2Borv4MK4w9RKWsSedj4pW95a6BZWtat%2FXBA7yJ8QdfWXpESNGBCLm%2BXhr4mAXhnoOI5c3eSWNaT%2FKc8G7TwHKHcKx%2FC6hdk8G74QTG7eSLaGoA6LW5kGjfE3RvvO6U1%2FJooZuoCThdwZ5u1vVd%2BZ%2FQthos6VHxkk0kNxADVEjbA91EkNsd%2F5f6DtQfOQ7DxyDu2xVwkZyLWzq1UCoMT6Z1shrziKln2y3c4%2FF8NzuMnmC7vAY9icJqIGWi87Q8hOPaNFNr4EHnH6%2FuAlkY5wtFJP9DPdNZJ5Dw7JS0idk%2FG90bL9JOIIW3EDxyDqdFCPKq7W1W%2FcLcIR3qM2lEM%2FaRG6oFBbWwXd8voGUw4h8CviSA2fppXZgGt0DQpd6ymFDzr2I%3D; _csrf_token=1528100024200; _is_show_loginId_change_block_=b2b-1839378211_false; _show_force_unbind_div_=b2b-1839378211_false; _show_sys_unbind_div_=b2b-1839378211_false; _show_user_unbind_div_=b2b-1839378211_false; __rn_alert__=false; ad_prefer=\"2018/06/04 16:15:12\"; isg=BNfX-96iQDqAisUeoYkE-jmBZkvhtKv4OI9tCSkE8qYNWPaaNuw7zpV6vvjGsIP2"
        );
        httpPost.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        httpPost.addHeader("Accept-Encoding", "gzip, deflate, br");
        httpPost.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
        httpPost.addHeader("Connection", "keep-alive");
        httpPost.addHeader("Cache-Control", "max-age=0");
        httpPost.addHeader("Content-Type","application/x-www-form-urlencoded");
        httpPost.addHeader("Host", "sec.1688.com");
        httpPost.addHeader("Origin", "http://localhost:63343");
        httpPost.addHeader("Referer", "http://localhost:63343/First/taobao/query.html");
        httpPost.addHeader("Upgrade-Insecure-Requests", "1");
        httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");


        Map<String, Object> params = new HashMap<>();
        params.put("action","QueryAction");
        params.put("event_submit_do_query","ok");
        params.put("smPolicy","searchweb2-company-anti_Spider-html-checkcode");
        params.put("smReturn","https://s.1688.com/company/company_search.htm?keywords=%C5%AE%D7%B0&button_click=top&earseDirect=false&n=y");
        params.put("smApp","searchweb2");
        params.put("smCharset","GBK");
        params.put("smTag","MjE4Ljg4LjI2LjE5NywxODM5Mzc4MjExLGQ4YWEwZjczY2RjYzRhMWRiMDM0YTIxOTMzNzQ0NGU5");
        params.put("smSign","Lupm3LRqN1dpAhyZbfASRQ==");
        params.put("identity","sm-searchweb2");
        params.put("captcha","");
        params.put("checkcode",checkCode);
        params.put("ua",
                "098#E1hvipvEvbQvUpCkvvvvvjiPPFdvAj1WPLSUzj1VPmPvljrnnLqO1jtEP2MvQjrmnOwCvvpvCvvviQhvCvvv9UUPvpvhvv2MMQhCvvOvUvvvphvEvpCWbbp8vvwzq2pI9CvpOhmv9UmvhCC9pUmv9UvphVCv9Umv9CvpOhmv9UmvhCC9pUmv9UvphVv23w0x9EkXJ5wI9UmvhCC9pUmv9UvphbyCvm9vvvvvphvvvvvv96CvpvLevvm2phCvhRvvvUnvphvppvvv96CvpCCvuphvmvvv9bhRp5HkkphvC99vvOC0psyCvvpvvvvv"
        );
        List<NameValuePair> pairList = new ArrayList<>(params.size());
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry
                    .getValue().toString());
            pairList.add(pair);
        }
        httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));
        String result = HttpUtil.doMethod(httpPost);
        System.out.println("验证码正确：" + result.equals(""));
//        crawl();
    }
}