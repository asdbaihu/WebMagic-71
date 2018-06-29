package com.fzd.httpclient;

import java.io.IOException;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;

/**
 * 最简单的HTTP客户端,用来演示通过GET或者POST方式访问某个页面
 *
 * @authorLiudong
 */

public class SimpleClient {
    static String url = "https://12366.sc-l-tax.gov.cn/sso/loginsc/queryUserxx.do?userName=91510100633142770A";

    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 100; i++) {
//        System.out.println(url.charAt(87));
            HttpClient client = new HttpClient();
            // 设置代理服务器地址和端口
            //client.getHostConfiguration().setProxy("proxy_host_addr",proxy_port);
            // 使用 GET 方法 ，如果服务器需要通过 HTTPS 连接，那只需要将下面 URL 中的 http 换成 https
            HttpMethod method = new GetMethod(url);
//            method.setRequestHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
//            method.setRequestHeader("accept-encoding", "gzip, deflate, br");
//            method.setRequestHeader("accept-language", "zh-CN,zh;q=0.8");
//            method.setRequestHeader("cache-control", "max-age=0");
//            method.setRequestHeader("referer", "https://shop115016762.taobao.com/?spm=a230r.7195193.1997079397.2.XMRCB7");
//            method.setRequestHeader("upgrade-insecure-requests", "1");
//            method.setRequestHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
//            method.setRequestHeader("cookie", "t=74dc760f70781accbc15b9591f61bc4d; UM_distinctid=162a314dbabadd-003f6698928fb5-3a614f0b-1fa400-162a314dbac510; cna=wT8uE4q3lhECAXZ0az8uGcYL; thw=cn; miid=748152201439510464; hng=CN%7Czh-CN%7CCNY%7C156; cookie2=3ee2b3880f5149dc22ba53a5bd191b79; v=0; _tb_token_=37b611395e833; tk_trace=oTRxOWSBNwn9dPy4KVJVbutfzK5InlkjwbWpxHegXyGxPdWTLVRjn23RuZzZtB1ZgD6Khe0jl%2BAoo68rryovRBE2Yp933GccTPwH%2FTbWVnqEfudSt0ozZPG%2BkA1iKeVv2L5C1tkul3c1pEAfoOzBoBsNsJyRf0AcIQyLAECRl4H9IdpnDiUtmTcvtmchZCusxhl5k738Wnn5iLAmo1%2B8cOR25hPTBWvY8N7tqRvFtDzybMMEblSWRt8BG0r7uPh4vK0Wcms5UgWo%2FDAIeihXE7WHtpPH%2BeZW4wxSNtDbc5qLfC4KrvlOf1Zqno8eyy64Gf8KbL0Y%2FVoqg4hrtA4o; ali_ab=218.88.28.39.1525659011168.9; uc3=nk2=25XcZzP5&id2=UonYuCff1bKy4g%3D%3D&vt3=F8dBz44lKNpLJrOKZko%3D&lg2=WqG3DMC9VAQiUQ%3D%3D; existShop=MTUyNTY1OTM4Mg%3D%3D; lgc=%5Cu4ED8%5Cu732A%5Cu5FB7; tracknick=%5Cu4ED8%5Cu732A%5Cu5FB7; dnk=%5Cu4ED8%5Cu732A%5Cu5FB7; sg=%E5%BE%B715; csg=27853d4e; cookie1=Vyh1zqk072gHvkZ5aTB5bn99eCovx4hr9YgGTQEeF9I%3D; unb=1839378211; skt=19cdaed6cbbda85f; _cc_=VFC%2FuZ9ajQ%3D%3D; tg=0; _l_g_=Ug%3D%3D; _nk_=%5Cu4ED8%5Cu732A%5Cu5FB7; cookie17=UonYuCff1bKy4g%3D%3D; mt=ci=2_1; x=e%3D1%26p%3D*%26s%3D0%26c%3D0%26f%3D0%26g%3D0%26t%3D0%26__ll%3D-1%26_ato%3D0; uc2=wuf=https%3A%2F%2Ftrade.tmall.com%2Fdetail%2ForderDetail.htm%3Fspm%3Da1z09.2.0.0.67002e8dkQmZjb%26bizOrderId%3D137808524882371182; _m_h5_tk=f6676785dcd9aa9343a47aedafcf29f1_1525662246098; _m_h5_tk_enc=9f83555a9ad79a4324a8d6b4477312f4; uc1=cookie14=UoTeO8tqQ9lM0g%3D%3D&lng=zh_CN&cookie16=U%2BGCWk%2F74Mx5tgzv3dWpnhjPaQ%3D%3D&existShop=false&cookie21=U%2BGCWk%2F7p4mBoUyS4E9C&tag=8&cookie15=WqG3DMC9VAQiUQ%3D%3D&pas=0; enc=4xFBlJU627EBQ92CnClt2I9wTqWC%2FaAwLyO%2BH4Q8hxGacvGyjxXRRmy23BpVnjgbXN5%2B5VYyLpPq9z5xeqn82g%3D%3D; swfstore=241423; isg=BKWllNAkE0vSTHcxaRaUaGoytGEfSlnzHul_M6eKVVzrvsUwbjJpRDN0TCLIvnEs; whl=-1%260%260%261525661962922");
//            method.setRequestHeader(":authority", "rate.taobao.com");
//            method.setRequestHeader(":method","GET");
//            method.setRequestHeader(":path","/user-rate-UvCI0OFkWMFIbONTT.htm?spm=a1z10.1-c.0.0.4ee648cfqkrIPN");
//            method.setRequestHeader(":scheme","https");
            //        使用POST方法
//        HttpMethod method = new PostMethod("http://java.sun.com ");
            client.executeMethod(method);

            //打印服务器返回的状态
            System.out.println(method.getStatusLine());
//            System.out.print(method.getResponseBodyAsString());
//            client.executeMethod(method);
//            System.out.println(method.getStatusLine());
            //打印返回的信息
            System.out.println(method.getResponseBodyAsString());
//            System.out.println(new String(method.getResponseBodyAsString().getBytes("gb2312"), "utf-8"));
            //释放连接
            method.releaseConnection();
        }
    }
}