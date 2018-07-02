package com.fzd.ali;

import com.fzd.httpclient4_5.HttpUtil;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by FZD on 2018/7/1.
 * Description:
 */
public class CrawlYellowPage {
    public static void main(String... args) throws IOException, InterruptedException {
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
        System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.commons.httpclient", "stdout");
        CrawlYellowPage crawl = new CrawlYellowPage();
        crawl.parseYellowPage(
                "https://corp.1688.com/page/index.htm?memberId=*xC-i2FvYvmgYMG8uOmkYOm8SMibZ8NTT&fromSite=company_site&tracelog=gsda_huangye"
        );
    }

    //解析黄页
    private Map<String, Object> parseYellowPage(String url) throws IOException, InterruptedException {
        for(int i = 0; i < 100; i++) {
            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader("Cookie",
                   "cna=/EyzE3NLhFsCAX1GT747oqB0; ali_ab=125.70.177.102.1529893377253.0; UM_distinctid=16434c07e4011c-0b6ed6a24c0176-5e452019-1fa400-16434c07e4318f; lid=%E4%BB%98%E7%8C%AA%E5%BE%B7; ali_apache_track=c_mid=b2b-1839378211|c_lid=%E4%BB%98%E7%8C%AA%E5%BE%B7|c_ms=1; last_mid=b2b-1839378211; __last_loginid__=%E4%BB%98%E7%8C%AA%E5%BE%B7; _cn_slid_=faNQ3gTey1; JSESSIONID=8L78OCuu1-9iXZJMlFi80moBzZF8-As2uJwQ-19r57; cookie2=13a1a06d3e7f360cdd492b7e9ffccd86; t=f3482f813646aed3d6aacd11fee4d15e; _tb_token_=e8e33e1433be4; ali_apache_tracktmp=c_w_signed=Y; tbsnid=5DvwRZEzsHazelbfQpO%2BGUZYSFjQNLPtReG1qqEwzN46sOlEpJKl9g%3D%3D; ali_beacon_id=125.70.177.102.1530238669534.021450.2; LoginUmid=JJTj%2B4v5rY8HL13Wj5e6Nu8pZOCziSL7ObaW1RwuC9vBlpK6JhHIZA%3D%3D; userID=YuieXOHrx2M4%2BtXcIgqZqt20SUO8PSgz96JsiX9uRzM6sOlEpJKl9g%3D%3D; userIDNum=ZxPKNZE3L1psO6kAAHeQyQ%3D%3D; h_keys=\"%u6210%u90fd%u542f%u7a0b%u8fbe%u5546%u8d38%u6709%u9650%u516c%u53f8#%u6210%u90fd%u5c1a%u598d%u7f8e%u5546%u8d38%u6709%u9650%u516c%u53f8#%u6210%u90fd%u5e02%u91d1%u725b%u533a%u946b%u610f%u670d%u88c5%u7ecf%u8425%u90e8#%u5973%u88c5#%u6210%u90fd%u767e%u5174%u670d%u9970%u6279%u53d1%u5546%u884c#%u6210%u90fd%u5e02%u6c38%u661f%u8fb0%u5149%u7535%u79d1%u6280%u6709%u9650%u516c%u53f8#%u56db%u5ddd%u5965%u83f2%u514b%u65af%u5efa%u8bbe%u5de5%u7a0b%u6709%u9650%u516c%u53f8%u5efa%u6750%u9500%u552e%u5206%u516c%u53f8#%u65b0%u90fd%u533a%u6b23%u68a6%u60a6%u670d%u9970%u7ecf%u8425%u90e8#%u6b66%u4faf%u533a%u8369%u4f73%u670d%u88c5%u7ecf%u8425%u90e8#%u8863%u820d\"; ad_prefer=\"2018/06/29 18:18:49\"; alicnweb=touch_tb_at%3D1530414109918%7ChomeIdttS%3D95179809422547921645983716339009199990%7ChomeIdttSAction%3Dtrue%7Clastlogonid%3D%25E4%25BB%2598%25E7%258C%25AA%25E5%25BE%25B7%7Cshow_inter_tips%3Dfalse; cookie1=Vyh1zqk072gHvkZ5aTB5bn99eCovx4hr9YgGTQEeF9I%3D; cookie17=UonYuCff1bKy4g%3D%3D; sg=%E5%BE%B715; csg=1906b729; __cn_logon__=true; __cn_logon_id__=%E4%BB%98%E7%8C%AA%E5%BE%B7; unb=1839378211; cn_tmp=\"Z28mC+GqtZ1CLMKfEwL7u2ICZVpZdxHRmLcLU9/oAa6a5WC6V3phTq4t0hL4hddyUTqFuE25/OIAoVl0iVZTXNN5QIYGkA/9M4EDpHKyZ9Dcxefk3pjESWGrH4nCeNLQg9Ujw7kt+gez0jXO4yhcErmYkmTZ8spspIQ0XIXG4Bki9a/4/UVG0MCBxDgPNBvWrm6r5z80AbG48zQ737g4ljlPfx8ODmIrhz93Pwp8oL77woNwWI9NzA==\"; login=kFeyVBJLQQI%3D; _nk_=1nOtq11N8pk%3D; _tmp_ck_0=Us6kfQ%2FxEONMz0Bru7vCThd6MIT2V0j5SxGOH3YycWKQ9Fam5F8XyNxqD%2Fvwqz5K1%2F147soj0UyqIlEwtFTmZuuEZV3rFqe%2BnmSFBfpIXm3vOMirj7IJ9Bh0txFwrrzNWM7eFf3zzY1I2Ez%2FayZYiichZEZAxx%2FyhMxxysLLlE1WkMvQ9WIq8MnI8T4Ocg0YLSXNn0Lz8t4xGQXjgyYM1yOrE456uyWjEQUirr1FN7hWp5WO422Z3AOce%2BowrdSoWhQG7TIXmHQqlGK2ZaxTiSgjXI17bY%2FbC7yryLFi5JiUNg9ezOBe1MDE%2BpCOg6ieXaCdeA2pFr6cSuWN%2BEnmC1izxLmJA%2FwqA%2FlZnh7pjRJtjHJfwTOfillIJIrR98AFB2vZ7mN3vQ4orCunoOoeYAvEO9xblw4PR1QqOfudIAAHnzrujtYBkHxy37zlIuJPeGEqiYS%2BmJrFN0ZrzihE3CQmx%2FeVV%2BLOPNWCXzfrwJ3QLUXI74g4Sp9yhxvwfxuNkFirH%2BjHdkpk3CHT3FhX8ex0UsT3Giq9BLtPSHIKEk4%3D; _csrf_token=1530416669883; CNZZDATA1000110779=1076705787-1530003600-https%253A%252F%252Fshop1394672810794.1688.com%252F%7C1530412967; _is_show_loginId_change_block_=b2b-1839378211_false; _show_force_unbind_div_=b2b-1839378211_false; _show_sys_unbind_div_=b2b-1839378211_false; _show_user_unbind_div_=b2b-1839378211_false; isg=BH9_Cq0gGI6TzByaUIIlan5oDlPJzNOAY-gZHhFMHy51IJ-iGTRjVv0yZrB7eKt-; __rn_alert__=false"
            );
            String result = HttpUtil.doMethod(httpGet);
            Document doc = HttpUtil.buildHtml(result.getBytes(), "gbk").getDocument();
            Elements contentInfoEl = doc.select(".content-info");
            if (contentInfoEl.size() != 0) {
                Map<String, Object> resultMap = new HashMap<>();
                for (Element td : contentInfoEl) {
                    resultMap.put(td.select(".title").text(), td.select(".info").text());
                }
            }else if (result.contains("发现您的网络环境有异常，为保证正常使用，请验证")) {
                System.out.println("发现您的网络环境有异常，为保证正常使用，请验证");
                getImg(HttpUtil.buildHtml(result.getBytes(), "utf-8").getDocument().select("#checkcodeImg").get(0).attr("src"));
                submitImg();
                continue;
            }else  if (result.contains("1688/淘宝会员（仅限会员名）请在此登录")) {
                System.out.println("1688/淘宝会员（仅限会员名）请在此登录");
                continue;
            }
            System.out.println(contentInfoEl.size());
        }
        return null;
    }




    public void getImg(String url) throws IOException {
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
        FileOutputStream fos = new FileOutputStream("F:\\IdeaProjects\\WebMagic\\WebMagic\\src\\main\\java\\com\\fzd\\ali\\a.jpg");
        fos.write(data);
        fos.close();
//        System.out.println("图片下载成功");
    }

    public void submitImg() throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入图片验证码");
        String checkCode = scanner.nextLine();
        HttpPost httpPost = new HttpPost("https://sec.1688.com/query.htm");
        httpPost.addHeader("Cookie",
//                "UM_distinctid=1627131a18726f-00c2677feff5ed-3a614f0b-1fa400-1627131a188313; cna=wT8uE4q3lhECAXZ0az8uGcYL; __last_loginid__=%E4%BB%98%E7%8C%AA%E5%BE%B7; last_mid=b2b-1839378211; hng=CN%7Czh-CN%7CCNY%7C156; lid=%E4%BB%98%E7%8C%AA%E5%BE%B7; ali_apache_track=c_mid=b2b-1839378211|c_lid=%E4%BB%98%E7%8C%AA%E5%BE%B7|c_ms=1; _cn_slid_=faNQ3gTey1; ali_ab=218.88.26.197.1525773293068.7; ali_beacon_id=218.88.26.197.1525773937972.710014.7; JSESSIONID=9L78NFkv1-AhXZ9ZsXjxmnDCXtv4-2wDa3uQ-3ODG4; cookie2=1ddb0b5e9320a118cf49fce485087548; t=fda5943929603be20bb78ddb7f857fef; _tb_token_=ee137403fee75; ali_apache_tracktmp=c_w_signed=Y; tbsnid=XlTa6LYLpQB6scYeuPlMLlfojLbKpiBG9TRo5nAMNbc6sOlEpJKl9g%3D%3D; __rn_alert__=false; cookie1=Vyh1zqk072gHvkZ5aTB5bn99eCovx4hr9YgGTQEeF9I%3D; cookie17=UonYuCff1bKy4g%3D%3D; sg=%E5%BE%B715; csg=1c50873c; __cn_logon__=true; __cn_logon_id__=%E4%BB%98%E7%8C%AA%E5%BE%B7; LoginUmid=xLJwvClELt816qnTW0KkZdo5sLWk%2Ftj0aq4l%2BV0yj5Yyp8JBi5lr6w%3D%3D; unb=1839378211; cn_tmp=\"Z28mC+GqtZ1CLMKfEwL7u2ICZVpZdxHRmLcLU9/oAa6a5WC6V3phTq4t0hL4hddyUTqFuE25/OIAoVl0iVZTXNN5QIYGkA/9M4EDpHKyZ9Dcxefk3pjESWGrH4nCeNLQg9Ujw7kt+gez0jXO4yhcErmYkmTZ8spspIQ0XIXG4Bki9a/4/UVG0MCBxDgPNBvWrm6r5z80AbG48zQ737g4ljlPfx8ODmIrfMsvOpYWxOji9xd5jsKo1Q==\"; login=kFeyVBJLQQI%3D; userID=YuieXOHrx2M4%2BtXcIgqZqt20SUO8PSgz96JsiX9uRzM6sOlEpJKl9g%3D%3D; _nk_=1nOtq11N8pk%3D; userIDNum=ZxPKNZE3L1psO6kAAHeQyQ%3D%3D; _csrf_token=1528180007983; _tmp_ck_0=mrTraeh7gUqFh%2Bp76eM0YiEU6G46lvRB7BfvhnBmFbAmpwAbCsbw7UV5ARbSo1uyTaRPVOLj17CbA31Ehx5eaSMBDPvFkQlWENy3Kz4TiUg%2FtVcmrc9xZ0l3jbWO05GHRlLHxxS0b0KQjixJpYxAGMZ92xlX7ntybia2ErYNYiePrPfxOYF0q5XH%2BXf%2F8SmsGjMA8cBcLQcRfequraqUsQlwLG7C8GbRuaz2e0iHR2b%2B6SU9pnxclIV%2FCkxalFPvL9E%2BxcSLbkuARnSw3f1TnraymJtP8OEeXt7Ic5xOcxNF4yJkOcn%2BM9B%2BeJzx3i4Xc%2FC%2BhCfSmypWzQFh9GE10xzglK%2F00136PfZMFGIp85QF26GgW1dtRovUKDaty6eSz95JICB%2B76Cb%2BbY9NVOSe5A8280KJA8NSP1WOITLzBOLMYEsbYdxTC0dcc3tzQvt2KPcLAwBo2jycQ58c0uWYHZ7Zty26%2Fv1P9CojZGjDXzZr%2FaeKZQ%2B4SOoLqTLvIyCbLs5tMUM0fedmC%2Ft0ogYt5CtzNB76T1L4MQK2GyKS8A%3D; CNZZDATA1000110779=1824680030-1525773874-https%253A%252F%252Flogin.1688.com%252F%7C1528184681; _is_show_loginId_change_block_=b2b-1839378211_false; _show_force_unbind_div_=b2b-1839378211_false; _show_sys_unbind_div_=b2b-1839378211_false; _show_user_unbind_div_=b2b-1839378211_false; ad_prefer=\"2018/06/05 16:29:21\"; h_keys=\"%u6df1%u5733%u5e02%u9f99%u5c97%u533a%u94ed%u51ef%u987a%u5236%u8863%u5382#%u5973%u88c5#%u6df1%u5733%u5e02%u5b9d%u5b89%u533a%u6c99%u4e95%u5c0f%u6e05%u6670%u670d%u88c5%u6279%u53d1%u57ce#%u60e0%u5dde%u5e02%u76c8%u73c8%u670d%u9970%u6709%u9650%u516c%u53f8#%u6df1%u5733%u5e02%u9f99%u5c97%u533a%u8863%u6e58%u574a%u670d%u9970%u5546%u884c#%u6df1%u5733%u5e02%u5de8%u660a%u946b%u8d38%u6613%u6709%u9650%u516c%u53f8#%u6df1%u5733%u5e02%u5b9d%u5b89%u533a%u897f%u4e61%u552f%u7231%u8863%u670d%u88c5%u5382#%u6df1%u5733%u5e02%u5361%u95e8%u670d%u9970%u6709%u9650%u516c%u53f8#%u6df1%u5733%u5e02%u9f99%u5c97%u533a%u7f18%u83b1%u6052%u6f6e%u6d41%u670d%u9970%u5546%u884c\"; alicnweb=touch_tb_at%3D1528188125843%7Clastlogonid%3D%25E4%25BB%2598%25E7%258C%25AA%25E5%25BE%25B7%7ChomeIdttS%3D53164068340257168870485693372392195909%7ChomeIdttSAction%3Dtrue%7Chp_newbuyerguide%3Dtrue%7Cshow_inter_tips%3Dfalse; isg=BCsr_wDd9GTkPSmaNWUQtnVluk_V6D9EBCtBjZ2ofmrBPEieIRTdE8PeknxSHJe6"
//                "f0b-1fa400-1627131a188313; cna=wT8uE4q3lhECAXZ0az8uGcYL; __last_loginid__=%E4%BB%98%E7%8C%AA%E5%BE%B7; last_mid=b2b-1839378211; hng=CN%7Czh-CN%7CCNY%7C156; lid=%E4%BB%98%E7%8C%AA%E5%BE%B7; ali_apache_track=c_mid=b2b-1839378211|c_lid=%E4%BB%98%E7%8C%AA%E5%BE%B7|c_ms=1; _cn_slid_=faNQ3gTey1; ali_ab=218.88.26.197.1525773293068.7; ali_beacon_id=218.88.26.197.1525773937972.710014.7; JSESSIONID=9L78NFkv1-AhXZ9ZsXjxmnDCXtv4-2wDa3uQ-3ODG4; cookie2=1ddb0b5e9320a118cf49fce485087548; t=fda5943929603be20bb78ddb7f857fef; _tb_token_=ee137403fee75; ali_apache_tracktmp=c_w_signed=Y; tbsnid=XlTa6LYLpQB6scYeuPlMLlfojLbKpiBG9TRo5nAMNbc6sOlEpJKl9g%3D%3D; __rn_alert__=false; ad_prefer=\"2018/06/05 14:18:05\"; h_keys=\"%u5973%u88c5#%u6df1%u5733%u5e02%u9f99%u5c97%u533a%u94ed%u51ef%u987a%u5236%u8863%u5382#%u6df1%u5733%u5e02%u5b9d%u5b89%u533a%u6c99%u4e95%u5c0f%u6e05%u6670%u670d%u88c5%u6279%u53d1%u57ce#%u60e0%u5dde%u5e02%u76c8%u73c8%u670d%u9970%u6709%u9650%u516c%u53f8#%u6df1%u5733%u5e02%u9f99%u5c97%u533a%u8863%u6e58%u574a%u670d%u9970%u5546%u884c#%u6df1%u5733%u5e02%u5de8%u660a%u946b%u8d38%u6613%u6709%u9650%u516c%u53f8#%u6df1%u5733%u5e02%u5b9d%u5b89%u533a%u897f%u4e61%u552f%u7231%u8863%u670d%u88c5%u5382#%u6df1%u5733%u5e02%u5361%u95e8%u670d%u9970%u6709%u9650%u516c%u53f8#%u6df1%u5733%u5e02%u9f99%u5c97%u533a%u7f18%u83b1%u6052%u6f6e%u6d41%u670d%u9970%u5546%u884c\"; cookie1=Vyh1zqk072gHvkZ5aTB5bn99eCovx4hr9YgGTQEeF9I%3D; cookie17=UonYuCff1bKy4g%3D%3D; sg=%E5%BE%B715; csg=1c50873c; __cn_logon__=true; __cn_logon_id__=%E4%BB%98%E7%8C%AA%E5%BE%B7; LoginUmid=xLJwvClELt816qnTW0KkZdo5sLWk%2Ftj0aq4l%2BV0yj5Yyp8JBi5lr6w%3D%3D; unb=1839378211; cn_tmp=\"Z28mC+GqtZ1CLMKfEwL7u2ICZVpZdxHRmLcLU9/oAa6a5WC6V3phTq4t0hL4hddyUTqFuE25/OIAoVl0iVZTXNN5QIYGkA/9M4EDpHKyZ9Dcxefk3pjESWGrH4nCeNLQg9Ujw7kt+gez0jXO4yhcErmYkmTZ8spspIQ0XIXG4Bki9a/4/UVG0MCBxDgPNBvWrm6r5z80AbG48zQ737g4ljlPfx8ODmIrfMsvOpYWxOji9xd5jsKo1Q==\"; login=kFeyVBJLQQI%3D; userID=YuieXOHrx2M4%2BtXcIgqZqt20SUO8PSgz96JsiX9uRzM6sOlEpJKl9g%3D%3D; _nk_=1nOtq11N8pk%3D; userIDNum=ZxPKNZE3L1psO6kAAHeQyQ%3D%3D; _csrf_token=1528180007983; _tmp_ck_0=mrTraeh7gUqFh%2Bp76eM0YiEU6G46lvRB7BfvhnBmFbAmpwAbCsbw7UV5ARbSo1uyTaRPVOLj17CbA31Ehx5eaSMBDPvFkQlWENy3Kz4TiUg%2FtVcmrc9xZ0l3jbWO05GHRlLHxxS0b0KQjixJpYxAGMZ92xlX7ntybia2ErYNYiePrPfxOYF0q5XH%2BXf%2F8SmsGjMA8cBcLQcRfequraqUsQlwLG7C8GbRuaz2e0iHR2b%2B6SU9pnxclIV%2FCkxalFPvL9E%2BxcSLbkuARnSw3f1TnraymJtP8OEeXt7Ic5xOcxNF4yJkOcn%2BM9B%2BeJzx3i4Xc%2FC%2BhCfSmypWzQFh9GE10xzglK%2F00136PfZMFGIp85QF26GgW1dtRovUKDaty6eSz95JICB%2B76Cb%2BbY9NVOSe5A8280KJA8NSP1WOITLzBOLMYEsbYdxTC0dcc3tzQvt2KPcLAwBo2jycQ58c0uWYHZ7Zty26%2Fv1P9CojZGjDXzZr%2FaeKZQ%2B4SOoLqTLvIyCbLs5tMUM0fedmC%2Ft0ogYt5CtzNB76T1L4MQK2GyKS8A%3D; alicnweb=touch_tb_at%3D1528185371845%7Clastlogonid%3D%25E4%25BB%2598%25E7%258C%25AA%25E5%25BE%25B7%7ChomeIdttS%3D53164068340257168870485693372392195909%7ChomeIdttSAction%3Dtrue%7Chp_newbuyerguide%3Dtrue%7Cshow_inter_tips%3Dfalse; isg=BGJi2Oy6TasBmFBdZNZZ6XTms-gEG2ZLpcgYCqz7k1WAfwH5kEeX3Mv9q7uD795lUM_distinctid=1627131a18726f-00c2677feff5ed-3a614"
                "cna=/EyzE3NLhFsCAX1GT747oqB0; ali_ab=125.70.177.102.1529893377253.0; UM_distinctid=16434c07e4011c-0b6ed6a24c0176-5e452019-1fa400-16434c07e4318f; lid=%E4%BB%98%E7%8C%AA%E5%BE%B7; ali_apache_track=c_mid=b2b-1839378211|c_lid=%E4%BB%98%E7%8C%AA%E5%BE%B7|c_ms=1; last_mid=b2b-1839378211; __last_loginid__=%E4%BB%98%E7%8C%AA%E5%BE%B7; _cn_slid_=faNQ3gTey1; JSESSIONID=8L78OCuu1-9iXZJMlFi80moBzZF8-As2uJwQ-19r57; cookie2=13a1a06d3e7f360cdd492b7e9ffccd86; t=f3482f813646aed3d6aacd11fee4d15e; _tb_token_=e8e33e1433be4; ali_apache_tracktmp=c_w_signed=Y; tbsnid=5DvwRZEzsHazelbfQpO%2BGUZYSFjQNLPtReG1qqEwzN46sOlEpJKl9g%3D%3D; ali_beacon_id=125.70.177.102.1530238669534.021450.2; LoginUmid=JJTj%2B4v5rY8HL13Wj5e6Nu8pZOCziSL7ObaW1RwuC9vBlpK6JhHIZA%3D%3D; userID=YuieXOHrx2M4%2BtXcIgqZqt20SUO8PSgz96JsiX9uRzM6sOlEpJKl9g%3D%3D; userIDNum=ZxPKNZE3L1psO6kAAHeQyQ%3D%3D; h_keys=\"%u6210%u90fd%u542f%u7a0b%u8fbe%u5546%u8d38%u6709%u9650%u516c%u53f8#%u6210%u90fd%u5c1a%u598d%u7f8e%u5546%u8d38%u6709%u9650%u516c%u53f8#%u6210%u90fd%u5e02%u91d1%u725b%u533a%u946b%u610f%u670d%u88c5%u7ecf%u8425%u90e8#%u5973%u88c5#%u6210%u90fd%u767e%u5174%u670d%u9970%u6279%u53d1%u5546%u884c#%u6210%u90fd%u5e02%u6c38%u661f%u8fb0%u5149%u7535%u79d1%u6280%u6709%u9650%u516c%u53f8#%u56db%u5ddd%u5965%u83f2%u514b%u65af%u5efa%u8bbe%u5de5%u7a0b%u6709%u9650%u516c%u53f8%u5efa%u6750%u9500%u552e%u5206%u516c%u53f8#%u65b0%u90fd%u533a%u6b23%u68a6%u60a6%u670d%u9970%u7ecf%u8425%u90e8#%u6b66%u4faf%u533a%u8369%u4f73%u670d%u88c5%u7ecf%u8425%u90e8#%u8863%u820d\"; ad_prefer=\"2018/06/29 18:18:49\"; alicnweb=touch_tb_at%3D1530414109918%7ChomeIdttS%3D95179809422547921645983716339009199990%7ChomeIdttSAction%3Dtrue%7Clastlogonid%3D%25E4%25BB%2598%25E7%258C%25AA%25E5%25BE%25B7%7Cshow_inter_tips%3Dfalse; cookie1=Vyh1zqk072gHvkZ5aTB5bn99eCovx4hr9YgGTQEeF9I%3D; cookie17=UonYuCff1bKy4g%3D%3D; sg=%E5%BE%B715; csg=1906b729; __cn_logon__=true; __cn_logon_id__=%E4%BB%98%E7%8C%AA%E5%BE%B7; unb=1839378211; cn_tmp=\"Z28mC+GqtZ1CLMKfEwL7u2ICZVpZdxHRmLcLU9/oAa6a5WC6V3phTq4t0hL4hddyUTqFuE25/OIAoVl0iVZTXNN5QIYGkA/9M4EDpHKyZ9Dcxefk3pjESWGrH4nCeNLQg9Ujw7kt+gez0jXO4yhcErmYkmTZ8spspIQ0XIXG4Bki9a/4/UVG0MCBxDgPNBvWrm6r5z80AbG48zQ737g4ljlPfx8ODmIrhz93Pwp8oL77woNwWI9NzA==\"; login=kFeyVBJLQQI%3D; _nk_=1nOtq11N8pk%3D; _tmp_ck_0=Us6kfQ%2FxEONMz0Bru7vCThd6MIT2V0j5SxGOH3YycWKQ9Fam5F8XyNxqD%2Fvwqz5K1%2F147soj0UyqIlEwtFTmZuuEZV3rFqe%2BnmSFBfpIXm3vOMirj7IJ9Bh0txFwrrzNWM7eFf3zzY1I2Ez%2FayZYiichZEZAxx%2FyhMxxysLLlE1WkMvQ9WIq8MnI8T4Ocg0YLSXNn0Lz8t4xGQXjgyYM1yOrE456uyWjEQUirr1FN7hWp5WO422Z3AOce%2BowrdSoWhQG7TIXmHQqlGK2ZaxTiSgjXI17bY%2FbC7yryLFi5JiUNg9ezOBe1MDE%2BpCOg6ieXaCdeA2pFr6cSuWN%2BEnmC1izxLmJA%2FwqA%2FlZnh7pjRJtjHJfwTOfillIJIrR98AFB2vZ7mN3vQ4orCunoOoeYAvEO9xblw4PR1QqOfudIAAHnzrujtYBkHxy37zlIuJPeGEqiYS%2BmJrFN0ZrzihE3CQmx%2FeVV%2BLOPNWCXzfrwJ3QLUXI74g4Sp9yhxvwfxuNkFirH%2BjHdkpk3CHT3FhX8ex0UsT3Giq9BLtPSHIKEk4%3D; _csrf_token=1530416669883; CNZZDATA1000110779=1076705787-1530003600-https%253A%252F%252Fshop1394672810794.1688.com%252F%7C1530412967; _is_show_loginId_change_block_=b2b-1839378211_false; _show_force_unbind_div_=b2b-1839378211_false; _show_sys_unbind_div_=b2b-1839378211_false; _show_user_unbind_div_=b2b-1839378211_false; isg=BH9_Cq0gGI6TzByaUIIlan5oDlPJzNOAY-gZHhFMHy51IJ-iGTRjVv0yZrB7eKt-; __rn_alert__=false"
        );


        Map<String, Object> params = new HashMap<>();
        params.put("action", "QueryAction");
        params.put("event_submit_do_query", "ok");
        params.put("smPolicy", "company.web-company__yellow_page-anti_Spider-seo-html-checkcode");
        params.put("smReturn", "https://corp.1688.com/page/index.htm?spm=a2615.7691481.1998738210.5.7eeb3ec6hNKcut&memberId=*xC-i2FIbvFv4vFk0vmHtwRNT&fromSite=company_site&tracelog=gsda_huangye");
        params.put("smApp", "company.web");
        params.put("smCharset", "GBK");
        params.put("smTag", "MTI1LjcwLjE3Ny4xNzYsMTgzOTM3ODIxMSwyZjY4NjBlYzM4MjI0MDFmODdmZmIxZWUxNGIzNmMyMQ==");
        params.put("smSign", "eSaOr+E5KYtjYeoUFg2J6w==");
        params.put("identity", "sm-company.web");
        params.put("captcha", "");
        params.put("checkcode", checkCode);
        params.put("ua",
                "098#E1hvUpvxvcZvUvCkvvvvvjiPPssp1jlhPFcyQjEUPmPwAj1Un2dvgj18RF5p6jrRRphvCvvvphvtvpvhvvCvp2yCvvpvvhCvRphvCvvvphvjvpvx4HdNzY1NmdzmSMUsoWe/STPU7gnuRphvCvvvphmCvpv44Hgpzzfw7Di4wkqNoReMSw7YZMuUCwF8sGgtvpvhvvCvpbyCvm9vvhCvvvvvvvvvBGwvvU2qvvCj1Qvvv3QvvhNjvvvmmvvvBGwvvvUUkphvC9hvpyPOz8yCvv9vvhhDLawRYQyCvhAC3i7xjwyzhb8ram56D46Od3ODNr3l53yfV7QEfJoK5ikOayL90EkO5C3d2ezOd34AdcOdYUoAdBuKfvDr1CkK5ivDNr3lHdoJ+3+uvphvC9vhphvvvvGCvvpvvPMMRphvCvvvphm5vpvhvvCCBUwCvvpv9hCv9phv2HiJwnMlzHi47D0PzQwCvCxI7rMNzj/PSw8MGFnUAfTMIG3f90Ijvpvx4HdNzYAN0qzmSMUsoWe/STPU7gnu9phv2HiJoRwNzHi47IxtzQ=="
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
