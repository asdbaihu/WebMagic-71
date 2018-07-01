package com.fzd.ali;

import com.alibaba.fastjson.JSON;
import com.fzd.ali.po.ImgVerifyResult;
import com.fzd.httpclient4_5.HttpUtil;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import sun.misc.BASE64Encoder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by FZD on 2018/6/13.
 * Description:
 */
public class ImgVerify2 {
    public static void main(String... args) throws IOException, InterruptedException {
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
        System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.commons.httpclient", "stdout");
//        String result = HttpUtil.doGet("");
//        List<String> imgBase64s = new ArrayList<>();
//
//        BASE64Encoder encoder = new BASE64Encoder();
//        for (int i = 0; i < 1; i++) {
//            FileInputStream in = new FileInputStream("C:\\Users\\SRKJ\\Desktop\\150x40\\" + i + ".jpg");
//            byte[] bytes = new byte[in.available()];
//            in.read(bytes);
//            String base64 = encoder.encode(bytes);
//            Map<String, Object> params = new HashMap<>();
////        base64 = "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";
//
//            params.put("strs", base64);
//            String result = HttpUtil.doPost("http://127.0.0.1:8000/captchar", params);
//            System.out.println(result);
//        }
        ImgVerify2 imgVerify = new ImgVerify2();
        Long start = System.currentTimeMillis();
        imgVerify.batchVerfiyCode();
        System.out.println("耗时：" + (System.currentTimeMillis() - start) / 1000);
    }

    private void batchVerfiyCode() throws IOException, InterruptedException {
        Search1688 search1688 = new Search1688();
        int i = 0;
        int success = 0;
        int fail = 0;
        FileOutputStream fos;
        while (i < 100) {
            byte[] data = search1688.getImg(
                    "//pin.aliyun.com/get_img?sessionid=e516e2769f5dce1c080d19461d8b3008&identity=sm-searchweb2&type=number"
            );
            BASE64Encoder encoder = new BASE64Encoder();
            String base64 = encoder.encode(data);

            Map<String, Object> params = new HashMap<>();
            params.put("strs", base64);
//            String verifyCode = HttpUtil.doPost("http://127.0.0.1:8000/captchar", params);
//                String verifyCode = HttpUtil.doPost("http:/127.0.0.1:8000/captchar", params);
            String verifyCode = HttpUtil.doPost("http://127.0.0.1:8000/number", params);
            try {
                ImgVerifyResult imgVerifyResult = JSON.parseObject(verifyCode, ImgVerifyResult.class);
                if (imgVerifyResult.getData() != null) {
                    String resultStr = submitImg(imgVerifyResult.getData());
                    if (resultStr.equals("")) {
                        success++;
                        fos = new FileOutputStream("C:\\Users\\SRKJ\\Desktop\\success\\" + imgVerifyResult.getData() + ".jpg");
                        fos.write(data);
                        fos.close();
                    } else {
                        fail++;
                        fos = new FileOutputStream("C:\\Users\\SRKJ\\Desktop\\fail\\" + imgVerifyResult.getData() + ".jpg");
                        fos.write(data);
                        fos.close();
                    }
                } else {
                    System.out.println(imgVerifyResult.getMsg());
                }
            } catch (Exception e) {
                e.printStackTrace();
                fos = new FileOutputStream("C:\\Users\\SRKJ\\Desktop\\exception.jpg");
                fos.write(data);
                fos.close();
                fail++;
                continue;
            }
            i++;
        }
        System.out.println("success：" + success + "\n" + "fail：" + fail);
    }

    public String submitImg(String checkCode) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        HttpPost httpPost = new HttpPost("https://sec.1688.com/query.htm");
        httpPost.addHeader("Cookie",
//                "UM_distinctid=1627131a18726f-00c2677feff5ed-3a614f0b-1fa400-1627131a188313; cna=wT8uE4q3lhECAXZ0az8uGcYL; __last_loginid__=%E4%BB%98%E7%8C%AA%E5%BE%B7; last_mid=b2b-1839378211; hng=CN%7Czh-CN%7CCNY%7C156; lid=%E4%BB%98%E7%8C%AA%E5%BE%B7; ali_apache_track=c_mid=b2b-1839378211|c_lid=%E4%BB%98%E7%8C%AA%E5%BE%B7|c_ms=1; _cn_slid_=faNQ3gTey1; ali_ab=218.88.26.197.1525773293068.7; ali_beacon_id=218.88.26.197.1525773937972.710014.7; JSESSIONID=9L78lO0l1-GhXZ1OzZdQrAf9dqT9-t8CixtQ-oDe94; cookie2=16ddc1a30821550a15825fb47817e8e6; t=fda5943929603be20bb78ddb7f857fef; _tb_token_=7beee8197e7be; __cn_logon__=true; __cn_logon_id__=%E4%BB%98%E7%8C%AA%E5%BE%B7; ali_apache_tracktmp=c_w_signed=Y; LoginUmid=xLJwvClELt816qnTW0KkZdo5sLWk%2Ftj0aq4l%2BV0yj5Yyp8JBi5lr6w%3D%3D; tbsnid=a6rIMybPxKbfjsezuQKj1%2Be7DbJN%2BS6QF528gS9gj5s6sOlEpJKl9g%3D%3D; userID=YuieXOHrx2M4%2BtXcIgqZqt20SUO8PSgz96JsiX9uRzM6sOlEpJKl9g%3D%3D; userIDNum=ZxPKNZE3L1psO6kAAHeQyQ%3D%3D; h_keys=\"%u6df1%u5733%u5e02%u9f99%u5c97%u533a%u8863%u6e58%u574a%u670d%u9970%u5546%u884c#%u5973%u88c5#%u6df1%u5733%u5e02%u5de8%u660a%u946b%u8d38%u6613%u6709%u9650%u516c%u53f8#%u6df1%u5733%u5e02%u5b9d%u5b89%u533a%u897f%u4e61%u552f%u7231%u8863%u670d%u88c5%u5382#%u6df1%u5733%u5e02%u5361%u95e8%u670d%u9970%u6709%u9650%u516c%u53f8#%u6df1%u5733%u5e02%u9f99%u5c97%u533a%u7f18%u83b1%u6052%u6f6e%u6d41%u670d%u9970%u5546%u884c#%u6fc2%u5ba0%ue5ca#%u6df1%u5733%u5e02%u653f%u9e3f%u5fae%u7535%u5b50%u6709%u9650%u516c%u53f8#%u7537%u88c5#%u5185%u8863\"; alicnweb=touch_tb_at%3D1528098002587%7Clastlogonid%3D%25E4%25BB%2598%25E7%258C%25AA%25E5%25BE%25B7%7ChomeIdttS%3D53164068340257168870485693372392195909%7ChomeIdttSAction%3Dtrue%7Chp_newbuyerguide%3Dtrue%7Cshow_inter_tips%3Dfalse; cookie1=Vyh1zqk072gHvkZ5aTB5bn99eCovx4hr9YgGTQEeF9I%3D; cookie17=UonYuCff1bKy4g%3D%3D; sg=%E5%BE%B715; csg=f0c39862; unb=1839378211; cn_tmp=\"Z28mC+GqtZ1CLMKfEwL7u2ICZVpZdxHRmLcLU9/oAa6a5WC6V3phTq4t0hL4hddyUTqFuE25/OIAoVl0iVZTXNN5QIYGkA/9M4EDpHKyZ9Dcxefk3pjESWGrH4nCeNLQg9Ujw7kt+gez0jXO4yhcErmYkmTZ8spspIQ0XIXG4Bki9a/4/UVG0MCBxDgPNBvWrm6r5z80AbG48zQ737g4ljlPfx8ODmIrLSd5ONba5hmJGZLTJv3BBw==\"; login=kFeyVBJLQQI%3D; _nk_=1nOtq11N8pk%3D; _tmp_ck_0=jTA9N8%2B4RDUuHZ33VyF66ctaKRDYKPc4AlrjnGPThT67rrYUGSdI3%2BQR25thb6ozaUAYdTM%2B8%2FNnTdpX9VSewdHe992eMEuU7%2Fw1ZZEj5yaQwNctLnzvm8Tkl28W4PMM0v5UtBNUXwgZui%2BgFz7XjVR5o1uT4hecz0Il9d%2Borv4MK4w9RKWsSedj4pW95a6BZWtat%2FXBA7yJ8QdfWXpESNGBCLm%2BXhr4mAXhnoOI5c3eSWNaT%2FKc8G7TwHKHcKx%2FC6hdk8G74QTG7eSLaGoA6LW5kGjfE3RvvO6U1%2FJooZuoCThdwZ5u1vVd%2BZ%2FQthos6VHxkk0kNxADVEjbA91EkNsd%2F5f6DtQfOQ7DxyDu2xVwkZyLWzq1UCoMT6Z1shrziKln2y3c4%2FF8NzuMnmC7vAY9icJqIGWi87Q8hOPaNFNr4EHnH6%2FuAlkY5wtFJP9DPdNZJ5Dw7JS0idk%2FG90bL9JOIIW3EDxyDqdFCPKq7W1W%2FcLcIR3qM2lEM%2FaRG6oFBbWwXd8voGUw4h8CviSA2fppXZgGt0DQpd6ymFDzr2I%3D; _csrf_token=1528100024200; _is_show_loginId_change_block_=b2b-1839378211_false; _show_force_unbind_div_=b2b-1839378211_false; _show_sys_unbind_div_=b2b-1839378211_false; _show_user_unbind_div_=b2b-1839378211_false; __rn_alert__=false; ad_prefer=\"2018/06/04 16:15:12\"; isg=BNfX-96iQDqAisUeoYkE-jmBZkvhtKv4OI9tCSkE8qYNWPaaNuw7zpV6vvjGsIP2"
                "f0b-1fa400-1627131a188313; cna=wT8uE4q3lhECAXZ0az8uGcYL; __last_loginid__=%E4%BB%98%E7%8C%AA%E5%BE%B7; last_mid=b2b-1839378211; hng=CN%7Czh-CN%7CCNY%7C156; lid=%E4%BB%98%E7%8C%AA%E5%BE%B7; ali_apache_track=c_mid=b2b-1839378211|c_lid=%E4%BB%98%E7%8C%AA%E5%BE%B7|c_ms=1; _cn_slid_=faNQ3gTey1; ali_ab=218.88.26.197.1525773293068.7; ali_beacon_id=218.88.26.197.1525773937972.710014.7; JSESSIONID=9L78NFkv1-AhXZ9ZsXjxmnDCXtv4-2wDa3uQ-3ODG4; cookie2=1ddb0b5e9320a118cf49fce485087548; t=fda5943929603be20bb78ddb7f857fef; _tb_token_=ee137403fee75; ali_apache_tracktmp=c_w_signed=Y; tbsnid=XlTa6LYLpQB6scYeuPlMLlfojLbKpiBG9TRo5nAMNbc6sOlEpJKl9g%3D%3D; __rn_alert__=false; ad_prefer=\"2018/06/05 14:18:05\"; h_keys=\"%u5973%u88c5#%u6df1%u5733%u5e02%u9f99%u5c97%u533a%u94ed%u51ef%u987a%u5236%u8863%u5382#%u6df1%u5733%u5e02%u5b9d%u5b89%u533a%u6c99%u4e95%u5c0f%u6e05%u6670%u670d%u88c5%u6279%u53d1%u57ce#%u60e0%u5dde%u5e02%u76c8%u73c8%u670d%u9970%u6709%u9650%u516c%u53f8#%u6df1%u5733%u5e02%u9f99%u5c97%u533a%u8863%u6e58%u574a%u670d%u9970%u5546%u884c#%u6df1%u5733%u5e02%u5de8%u660a%u946b%u8d38%u6613%u6709%u9650%u516c%u53f8#%u6df1%u5733%u5e02%u5b9d%u5b89%u533a%u897f%u4e61%u552f%u7231%u8863%u670d%u88c5%u5382#%u6df1%u5733%u5e02%u5361%u95e8%u670d%u9970%u6709%u9650%u516c%u53f8#%u6df1%u5733%u5e02%u9f99%u5c97%u533a%u7f18%u83b1%u6052%u6f6e%u6d41%u670d%u9970%u5546%u884c\"; cookie1=Vyh1zqk072gHvkZ5aTB5bn99eCovx4hr9YgGTQEeF9I%3D; cookie17=UonYuCff1bKy4g%3D%3D; sg=%E5%BE%B715; csg=1c50873c; __cn_logon__=true; __cn_logon_id__=%E4%BB%98%E7%8C%AA%E5%BE%B7; LoginUmid=xLJwvClELt816qnTW0KkZdo5sLWk%2Ftj0aq4l%2BV0yj5Yyp8JBi5lr6w%3D%3D; unb=1839378211; cn_tmp=\"Z28mC+GqtZ1CLMKfEwL7u2ICZVpZdxHRmLcLU9/oAa6a5WC6V3phTq4t0hL4hddyUTqFuE25/OIAoVl0iVZTXNN5QIYGkA/9M4EDpHKyZ9Dcxefk3pjESWGrH4nCeNLQg9Ujw7kt+gez0jXO4yhcErmYkmTZ8spspIQ0XIXG4Bki9a/4/UVG0MCBxDgPNBvWrm6r5z80AbG48zQ737g4ljlPfx8ODmIrfMsvOpYWxOji9xd5jsKo1Q==\"; login=kFeyVBJLQQI%3D; userID=YuieXOHrx2M4%2BtXcIgqZqt20SUO8PSgz96JsiX9uRzM6sOlEpJKl9g%3D%3D; _nk_=1nOtq11N8pk%3D; userIDNum=ZxPKNZE3L1psO6kAAHeQyQ%3D%3D; _csrf_token=1528180007983; _tmp_ck_0=mrTraeh7gUqFh%2Bp76eM0YiEU6G46lvRB7BfvhnBmFbAmpwAbCsbw7UV5ARbSo1uyTaRPVOLj17CbA31Ehx5eaSMBDPvFkQlWENy3Kz4TiUg%2FtVcmrc9xZ0l3jbWO05GHRlLHxxS0b0KQjixJpYxAGMZ92xlX7ntybia2ErYNYiePrPfxOYF0q5XH%2BXf%2F8SmsGjMA8cBcLQcRfequraqUsQlwLG7C8GbRuaz2e0iHR2b%2B6SU9pnxclIV%2FCkxalFPvL9E%2BxcSLbkuARnSw3f1TnraymJtP8OEeXt7Ic5xOcxNF4yJkOcn%2BM9B%2BeJzx3i4Xc%2FC%2BhCfSmypWzQFh9GE10xzglK%2F00136PfZMFGIp85QF26GgW1dtRovUKDaty6eSz95JICB%2B76Cb%2BbY9NVOSe5A8280KJA8NSP1WOITLzBOLMYEsbYdxTC0dcc3tzQvt2KPcLAwBo2jycQ58c0uWYHZ7Zty26%2Fv1P9CojZGjDXzZr%2FaeKZQ%2B4SOoLqTLvIyCbLs5tMUM0fedmC%2Ft0ogYt5CtzNB76T1L4MQK2GyKS8A%3D; alicnweb=touch_tb_at%3D1528185371845%7Clastlogonid%3D%25E4%25BB%2598%25E7%258C%25AA%25E5%25BE%25B7%7ChomeIdttS%3D53164068340257168870485693372392195909%7ChomeIdttSAction%3Dtrue%7Chp_newbuyerguide%3Dtrue%7Cshow_inter_tips%3Dfalse; isg=BGJi2Oy6TasBmFBdZNZZ6XTms-gEG2ZLpcgYCqz7k1WAfwH5kEeX3Mv9q7uD795lUM_distinctid=1627131a18726f-00c2677feff5ed-3a614"
        );
        httpPost.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        httpPost.addHeader("Accept-Encoding", "gzip, deflate, br");
        httpPost.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
        httpPost.addHeader("Connection", "keep-alive");
        httpPost.addHeader("Cache-Control", "max-age=0");
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.addHeader("Host", "sec.1688.com");
        httpPost.addHeader("Origin", "http://localhost:63343");
        httpPost.addHeader("Referer", "http://localhost:63343/First/taobao/query.html");
        httpPost.addHeader("Upgrade-Insecure-Requests", "1");
        httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");


        Map<String, Object> params = new HashMap<>();
        params.put("action", "QueryAction");
        params.put("event_submit_do_query", "ok");
        params.put("smPolicy", "searchweb2-company-anti_Spider-html-checkcode");
        params.put("smReturn", "https://s.1688.com/company/company_search.htm?keywords=%C5%AE%D7%B0&button_click=top&earseDirect=false&n=y");
        params.put("smApp", "searchweb2");
        params.put("smCharset", "GBK");
        params.put("smTag", "MjE4Ljg4LjI2LjE5NywxODM5Mzc4MjExLGQ4YWEwZjczY2RjYzRhMWRiMDM0YTIxOTMzNzQ0NGU5");
        params.put("smSign", "Lupm3LRqN1dpAhyZbfASRQ==");
        params.put("identity", "sm-searchweb2");
        params.put("captcha", "");
        params.put("checkcode", checkCode);
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
        return result;
    }

}
