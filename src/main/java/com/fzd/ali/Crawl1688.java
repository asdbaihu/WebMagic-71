package com.fzd.ali;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fzd.ali.po.*;
import com.fzd.concurrency.TestCrawl;
import com.fzd.httpclient4_5.HttpUtil;
import com.fzd.util.ElasticSearchUtils;
import org.apache.commons.lang.StringUtils;
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
import us.codecraft.webmagic.selector.Html;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by FZD on 2018/5/9.
 * Description:
 */
public class Crawl1688 {
    Search1688 search1688 = new Search1688();
    public static void main(String[] args) throws IOException, InterruptedException {
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
        System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.commons.httpclient", "stdout");
        Crawl1688 crawl1688 = new Crawl1688();
//        crawl1688.mixTitleArea();
//        crawl1688.parseYellowPage("https://corp.1688.com/page/index.htm?spm=a2615.7691481.1998738210.5.3f1d2b81HLqtAr&memberId=*xC-i2FvyMmg0vFHyMFcuvGIYMXbZ8NTT&fromSite=company_site&tracelog=gsda_huangye");
//        crawl1688.getDetail();
//        crawl1688.selleService();
//        crawl1688.getArea();
//        crawl1688.crawlTitle();
//        crawl1688.crawlArea();
//        crawl1688.testCrawl();
//        crawl1688.startCrawl();
        ExecutorService exec = Executors.newCachedThreadPool();
        for(int i =0; i < 10000; i++) {
           exec.execute(new TestCrawl());
        }
        exec.shutdown();
//        crawl1688.testCrawl();
    }

    private void mixTitleArea(){
        List<String> titleList = getTitle();
        AreaVo areaVo = getArea();
        int[] bizTypes = new int[]{1,2,4,8};
        Integer sum = 1;
        List<AreaTitle> areaTitles = new ArrayList<>();
        for(Prov prov : areaVo.getProvs()){
            for(String city : prov.getCity()){
                for(String keywords : titleList) {
                    for (int bizType : bizTypes) {
                        AreaTitle areaTitle = new AreaTitle(sum.toString(), prov.getName(), city, keywords, bizType, false, sum);
                        areaTitles.add(areaTitle);
                        sum++;
                    }
                }
            }
        }
        ElasticSearchUtils.batchAdd("area_title", "area_title", areaTitles);
        System.out.println(areaTitles.size());

    }

    private void startCrawl() throws IOException, InterruptedException {
        Crawl1688 crawl1688 = new Crawl1688();
        List<String> titleList = crawl1688.getTitle();
        AreaVo areaVo = crawl1688.getArea();
        String province = "广东";
        String city = "深圳";
        String itemTitle = "女装";
//        for(String itemTitle : titleList) {
            int beginPage = 1;
            Integer totalPage = 0;
            List<ShopBase> shopBases = new ArrayList<>();
            do {
                String result = crawl1688.crawlBase(itemTitle, "广东", "深圳", beginPage);
                if (result.contains("发现您的网络环境有异常，为保证正常使用，请验证")) {
                    System.out.println("发现您的网络环境有异常，为保证正常使用，请验证");
                    search1688.getImg(HttpUtil.buildHtml(result.getBytes(), "utf-8").getDocument().select("#checkcodeImg").get(0).attr("src"));
                    search1688.submitImg();
                    continue;
                }
                if (result.contains("1688/淘宝会员（仅限会员名）请在此登录")) {
                    System.out.println("1688/淘宝会员（仅限会员名）请在此登录");
                    continue;
                }
                Map<String, Object> resultMap = parseSearchBase(result);
                totalPage = (Integer) resultMap.get("totalPage");
                shopBases.addAll((Collection<? extends ShopBase>) resultMap.get("shopBases"));
                beginPage++;
                if(beginPage > totalPage)
                    break;
            } while (true);
            System.out.println("广东：" + "深圳。" + itemTitle + "：" + shopBases.size());
//            ElasticSearchUtils.batchAdd(ESTypes.ALIBABA, ESTypes.A1688, shopBases);
//        }
    }

    private void testCrawl() {
        String url = "https://s.1688.com/company/company_search.htm?keywords=%C5%AE%D7%B0&annualRevenue=8%2C1&province=%BD%AD%CB%D5%2C%D5%E3%BD%AD%2C%C9%CF%BA%A3&n=y&pageSize=30&offset=0&filt=y&beginPage=9&smToken=d6198886c105458b973da67edbfa8be9&smSign=UCcWuOL6GYtbm8KTuCnx7Q%3D%3D";
        String result = HttpUtil.doGet(url);
        Html html = HttpUtil.buildHtml(result.getBytes(), "UTF-8");
        System.out.println(html);
    }

    private String crawlBase(String keywords, String province, String city, Integer beginPage) throws IOException, InterruptedException {
        //https://s.1688.com/company/company_search.htm?keywords=女装&city=深圳&province=广东&n=y&filt=y
        //https://s.1688.com/company/company_search.htm?keywords=女装&province=江苏,浙江,上海&n=y&filt=y
        String url = "https://s.1688.com/company/company_search.htm";
        Map<String, Object> params = new HashMap<>();
        if (!StringUtils.isEmpty(keywords)) {
            params.put("keywords", URLEncoder.encode(keywords, "gbk"));
        }
        if (!StringUtils.isEmpty(province)) {
            params.put("province", URLEncoder.encode(province, "gbk"));
        }
        if (!StringUtils.isEmpty(city)) {
            params.put("city", URLEncoder.encode(city, "gbk"));
        }
        if (beginPage != null && beginPage > 0) {
            params.put("beginPage", beginPage);
            params.put("offset", 3);
        }
        params.put("n", "y");
        params.put("filt", "y");
        params.put("sortType", "pop");//人气
        params.put("biztype", "4");
        String result = HttpUtil.doGet(url, params);
        return result;
    }

    private Map<String, Object> parseSearchBase(String result){
        Html html = HttpUtil.buildHtml(result.getBytes(), "UTF-8");
        Document doc = html.getDocument();
        Elements shopLi = doc.select(".sm-company-list.fd-clr").select("li").select(".company-list-item");
        List<ShopBase> shopBases = new ArrayList<>();

        for (Element el : shopLi) {
            ShopBase shopBase = new ShopBase();
            shopBase.setShopId(el.attr("companyid"));
            shopBase.setItemId(el.attr("itemid"));
            shopBase.setMemberId(el.attr("memberid"));
            Elements shopA = el.select(".list-item-title-text");
            if (shopA != null && shopA.size() > 0) {
                Element a = shopA.get(0);
                shopBase.setName(a.attr("title"));
                shopBase.setSpmId(a.attr("data-spm-anchor-id"));
                shopBase.setUrl(a.attr("href"));
                shopBase.setCreditRemarkUrl(shopBase.getUrl() + "/page/creditdetail_remark.htm");
                shopBase.setBaseInfoUrl(shopBase.getUrl() + "/page/creditdetail.htm");
            }
            shopBases.add(shopBase);
        }
        Integer totalPage = 0;
        Elements totalPageEL = doc.select("#jumpto");
        if (totalPageEL != null && totalPageEL.size() > 0) {
            totalPage = Integer.parseInt(totalPageEL.get(0).attr("data-max"));
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("shopBases", shopBases);
        resultMap.put("totalPage", totalPage);
        return resultMap;
    }

    public void crawlCredit(String url, String memberId){
        CreditDetailContentProcessor processor = new CreditDetailContentProcessor();
        String result = HttpUtil.doGet(url);
        Document doc = HttpUtil.buildHtml(result.getBytes(), "utf-8").getDocument();
        processor.getCreditDetail(doc, memberId);
    }

    public void crawlCompanyBase(String url, String memberId) {
        CompanyBaseContentProcessor processor = new CompanyBaseContentProcessor();
        String result = HttpUtil.doGet(url);
        Document doc = HttpUtil.buildHtml(result.getBytes(), "utf-8").getDocument();
        processor.getCompanyBase(doc);
    }

    private AreaVo getArea(){
        ObjectMapper mapper = new ObjectMapper();
        AreaVo result = null;
        try {
            result = mapper.readValue(Crawl1688.class.getResource("/area.json"), AreaVo.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private List<String> getTitle(){
        ObjectMapper mapper = new ObjectMapper();
        List<String> result = null;
        try {
            result = mapper.readValue(Crawl1688.class.getResource("/title.txt"), List.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private AreaVo crawlArea() {
        ObjectMapper mapper = new ObjectMapper();
        AreaVo result = null;
        try {
            result = mapper.readValue(Crawl1688.class.getResource("/area.json"), AreaVo.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private List<String> crawlTitle() {
        String url = "https://www.1688.com/";
        List<String> goodsCategory = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            String result = HttpUtil.doGet(url);
            Html html = HttpUtil.buildHtml(result.getBytes(), "utf-8");
            Document doc = html.getDocument();
            Elements subNav = doc.select("#sub-nav");
            Elements aElements = subNav.select("a");
            for (Element element : aElements) {
                goodsCategory.add(element.text());
            }
            System.out.println(goodsCategory);
        }
        PrintWriter outputStream = null;
        try {
            File file = new File(Crawl1688.class.getResource("/title.txt").toURI());

            outputStream = new PrintWriter(new
                    FileOutputStream(file));
            outputStream.println(JSON.toJSON(goodsCategory));
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return goodsCategory;
    }

//    public static void getImg(String url) throws IOException {
//        HttpGet httpGet = new HttpGet("https:" + url);
//        CloseableHttpResponse response = HttpUtil.httpClient.execute(httpGet, HttpUtil.context);
//        //打印服务器返回的状态
//        int code = response.getStatusLine().getStatusCode();
//        if (code > 400) {
//            System.out.println(code);
////                break;
//        }
//        HttpEntity entity = response.getEntity();
//        byte[] data = EntityUtils.toByteArray(entity);
//        FileOutputStream fos = new FileOutputStream("C:\\Users\\SRKJ\\IdeaProjects\\WebMagic\\src\\main\\java\\com\\fzd\\ali\\a.jpg");
//        fos.write(data);
//        fos.close();
////        System.out.println("图片下载成功");
//    }
//
//    public static void submitImg() throws IOException, InterruptedException {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("请输入图片验证码");
//        String checkCode = scanner.nextLine();
//        HttpPost httpPost = new HttpPost("https://sec.1688.com/query.htm");
//        httpPost.addHeader("Cookie",
//                "UM_distinctid=1627131a18726f-00c2677feff5ed-3a614f0b-1fa400-1627131a188313; cna=wT8uE4q3lhECAXZ0az8uGcYL; __last_loginid__=%E4%BB%98%E7%8C%AA%E5%BE%B7; last_mid=b2b-1839378211; hng=CN%7Czh-CN%7CCNY%7C156; lid=%E4%BB%98%E7%8C%AA%E5%BE%B7; ali_apache_track=c_mid=b2b-1839378211|c_lid=%E4%BB%98%E7%8C%AA%E5%BE%B7|c_ms=1; _cn_slid_=faNQ3gTey1; ali_ab=218.88.26.197.1525773293068.7; ali_beacon_id=218.88.26.197.1525773937972.710014.7; _alizs_area_info_=1001; _alizs_user_type_=purchaser; _alizs_cate_info_=70; _m_h5_tk=f17e53be3be90271ef85b3c925fc774e_1525839510967; _m_h5_tk_enc=b31009b7df4fc7b30c3e6db3272ed272; __rn_alert__=false; JSESSIONID=A1zYKLe-tHgZp1ATg5qdyIsrB4-6wQAirQ-aOG1; cookie2=11b5a43c96ade1fc0ac5fa8946a51e75; t=fda5943929603be20bb78ddb7f857fef; _tb_token_=4b731de5a5fe; cookie1=Vyh1zqk072gHvkZ5aTB5bn99eCovx4hr9YgGTQEeF9I%3D; cookie17=UonYuCff1bKy4g%3D%3D; sg=%E5%BE%B715; csg=5a2acb1f; __cn_logon__=true; __cn_logon_id__=%E4%BB%98%E7%8C%AA%E5%BE%B7; ali_apache_tracktmp=c_w_signed=Y; LoginUmid=xLJwvClELt816qnTW0KkZdo5sLWk%2Ftj0aq4l%2BV0yj5Yyp8JBi5lr6w%3D%3D; unb=1839378211; tbsnid=T4XJLROZVFDhf%2Fnq0jOX7b6AiOAK1g1mPwasZocvvO06sOlEpJKl9g%3D%3D; cn_tmp=\"Z28mC+GqtZ1CLMKfEwL7u2ICZVpZdxHRmLcLU9/oAa6a5WC6V3phTq4t0hL4hddyUTqFuE25/OIAoVl0iVZTXNN5QIYGkA/9M4EDpHKyZ9Dcxefk3pjESWGrH4nCeNLQg9Ujw7kt+gez0jXO4yhcErmYkmTZ8spspIQ0XIXG4Bki9a/4/UVG0MCBxDgPNBvWrm6r5z80AbG48zQ737g4ljlPfx8ODmIrKU5jHUpdmpIB4ZjzaeXnww==\"; login=kFeyVBJLQQI%3D; userID=YuieXOHrx2M4%2BtXcIgqZqt20SUO8PSgz96JsiX9uRzM6sOlEpJKl9g%3D%3D; _nk_=1nOtq11N8pk%3D; userIDNum=ZxPKNZE3L1psO6kAAHeQyQ%3D%3D; _csrf_token=1526016930959; _is_show_loginId_change_block_=b2b-1839378211_false; _show_force_unbind_div_=b2b-1839378211_false; _show_sys_unbind_div_=b2b-1839378211_false; _show_user_unbind_div_=b2b-1839378211_false; h_keys=\"%u5973%u88c5#%u978b#%u5973%u88c532#%u673a%u68b0#%u6fc2%u5ba0%ue5ca#%u516c%u53f8#%u6bdb%u7ebf#%u5e7f%u5dde%u6f6e%u6d41#%u884c\"; alicnweb=touch_tb_at%3D1526025779984%7Clastlogonid%3D%25E4%25BB%2598%25E7%258C%25AA%25E5%25BE%25B7%7ChomeIdttS%3D53164068340257168870485693372392195909%7ChomeIdttSAction%3Dtrue%7Chp_newbuyerguide%3Dtrue%7Cshow_inter_tips%3Dfalse; ad_prefer=\"2018/05/11 16:19:58\"; _tmp_ck_0=\"LUGYM4%2BaK%2FUqPCTKdCDFhq3pUnnO2wD0XkVvTZzE7QgJ0Ft96ue%2BJ7B0VyFjIlkeOBK19bJW5Mwfg8bm%2FsnczR9VR7zQzD97LN8XX4EZe5vEhGdlrPhxWFCPn18vskq9sesY81tbF842h0tDDnxug6BJGznZaUlBqhssWEQXuNUT%2Fk8xcCFjMQcLC6wyKIJpikWRkEWaXTco8JZC6mI5w8jMsVGWruEIMNJInwB3Z%2B9hj4oF7AkINtKsed%2B4aXt%2FRrdlev%2FnV2y7fkhr6nIqsmWLQIwUwWutfsf8XIxH7%2Bec3fxvNDy0QOdIvPL4CWt9Yp8LdbZKSU7jmLLL5xjkk3EpiCIgdxfSjvqTNrxDTMlWObMi8gXbEHXIUyim6E7VNEx7Ln%2Fi4DJzKjg7mS7KhthIhVp0Kl%2Bxmk1ecchW%2FRtXjWXxvP2qTMPjfroCHC%2B%2Fd%2FLzDQZAw%2BxitF324yCUTfR0TPvzDmh5NmR2OSbHc%2FGglEcD37qcfx%2BMxHB%2FRiStKPOmB8a8UNmJKWzwbmEHkg%3D%3D\"; isg=BNvb5uQ0BZeLinmqBfVApiXVaj-FGO81VLvxnc0Y-1rxrPqOVYD8A6tlQgwijEeq"
//        );
//        httpPost.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
//        httpPost.addHeader("Accept-Encoding", "gzip, deflate, br");
//        httpPost.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
//        httpPost.addHeader("Connection", "keep-alive");
//        httpPost.addHeader("Cache-Control", "max-age=0");
//        httpPost.addHeader("Content-Type","application/x-www-form-urlencoded");
//        httpPost.addHeader("Host", "sec.1688.com");
//        httpPost.addHeader("Origin", "http://localhost:63343");
//        httpPost.addHeader("Referer", "http://localhost:63343/First/taobao/query.html");
//        httpPost.addHeader("Upgrade-Insecure-Requests", "1");
//        httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
//
//
//        Map<String, Object> params = new HashMap<>();
//        params.put("action","QueryAction");
//        params.put("event_submit_do_query","ok");
//        params.put("smPolicy","searchweb2-company-anti_Spider-html-checkcode");
//        params.put("smReturn","https://s.1688.com/company/company_search.htm?keywords=%C5%AE%D7%B0&button_click=top&earseDirect=false&n=y");
//        params.put("smApp","searchweb2");
//        params.put("smCharset","GBK");
//        params.put("smTag","MjE4Ljg4LjI2LjE5NywxODM5Mzc4MjExLGQ4YWEwZjczY2RjYzRhMWRiMDM0YTIxOTMzNzQ0NGU5");
//        params.put("smSign","Lupm3LRqN1dpAhyZbfASRQ==");
//        params.put("identity","sm-searchweb2");
//        params.put("captcha","");
//        params.put("checkcode",checkCode);
//        params.put("ua",
//                "098#E1hvipvEvbQvUpCkvvvvvjiPPFdvAj1WPLSUzj1VPmPvljrnnLqO1jtEP2MvQjrmnOwCvvpvCvvviQhvCvvv9UUPvpvhvv2MMQhCvvOvUvvvphvEvpCWbbp8vvwzq2pI9CvpOhmv9UmvhCC9pUmv9UvphVCv9Umv9CvpOhmv9UmvhCC9pUmv9UvphVv23w0x9EkXJ5wI9UmvhCC9pUmv9UvphbyCvm9vvvvvphvvvvvv96CvpvLevvm2phCvhRvvvUnvphvppvvv96CvpCCvuphvmvvv9bhRp5HkkphvC99vvOC0psyCvvpvvvvv"
//        );
//        List<NameValuePair> pairList = new ArrayList<>(params.size());
//        for (Map.Entry<String, Object> entry : params.entrySet()) {
//            NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry
//                    .getValue().toString());
//            pairList.add(pair);
//        }
//        httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));
//        String result = HttpUtil.doMethod(httpPost);
//        System.out.println("验证码正确：" + result.equals(""));
////        crawl();
//    }

    private String selleService(){
        HttpGet httpPost = new HttpGet(
                "https://kinecat.1688.com/event/app/winport_bsr/getBsrData.htm"
        );
        httpPost.addHeader("Cookie",
//             "UM_distinctid=1627131a18726f-00c2677feff5ed-3a614f0b-1fa400-1627131a188313; cna=wT8uE4q3lhECAXZ0az8uGcYL; __last_loginid__=%E4%BB%98%E7%8C%AA%E5%BE%B7; last_mid=b2b-1839378211; hng=CN%7Czh-CN%7CCNY%7C156; lid=%E4%BB%98%E7%8C%AA%E5%BE%B7; ali_apache_track=c_mid=b2b-1839378211|c_lid=%E4%BB%98%E7%8C%AA%E5%BE%B7|c_ms=1; _cn_slid_=faNQ3gTey1; ali_ab=218.88.26.197.1525773293068.7; ali_beacon_id=218.88.26.197.1525773937972.710014.7; _uab_collina=152654884002745930298915; JSESSIONID=9L78lO0l1-GhXZ1OzZdQrAf9dqT9-t8CixtQ-oDe94; cookie2=16ddc1a30821550a15825fb47817e8e6; t=fda5943929603be20bb78ddb7f857fef; _tb_token_=7beee8197e7be; _umdata=0823A424438F76AB0D906A6B7CF2CA633BEAE0AA1526E4592B3622F5EB409468FF41029D23C5EC7CCD43AD3E795C914C1BC4DEB22555B7B57D7CC596D828FF2C; ali_apache_tracktmp=c_w_signed=Y; tbsnid=a6rIMybPxKbfjsezuQKj1%2Be7DbJN%2BS6QF528gS9gj5s6sOlEpJKl9g%3D%3D; _is_show_loginId_change_block_=b2b-1839378211_false; _show_force_unbind_div_=b2b-1839378211_false; _show_sys_unbind_div_=b2b-1839378211_false; _show_user_unbind_div_=b2b-1839378211_false; __rn_alert__=false; CNZZDATA1257121278=526688878-1525831332-https%253A%252F%252Fshop3388t654913l9.1688.com%252F%7C1528101517; ad_prefer=\"2018/06/04 17:06:08\"; h_keys=\"%u60e0%u5dde%u5e02%u76c8%u73c8%u670d%u9970%u6709%u9650%u516c%u53f8#%u6df1%u5733%u5e02%u9f99%u5c97%u533a%u8863%u6e58%u574a%u670d%u9970%u5546%u884c#%u5973%u88c5#%u6df1%u5733%u5e02%u5de8%u660a%u946b%u8d38%u6613%u6709%u9650%u516c%u53f8#%u6df1%u5733%u5e02%u5b9d%u5b89%u533a%u897f%u4e61%u552f%u7231%u8863%u670d%u88c5%u5382#%u6df1%u5733%u5e02%u5361%u95e8%u670d%u9970%u6709%u9650%u516c%u53f8#%u6df1%u5733%u5e02%u9f99%u5c97%u533a%u7f18%u83b1%u6052%u6f6e%u6d41%u670d%u9970%u5546%u884c#%u6fc2%u5ba0%ue5ca#%u6df1%u5733%u5e02%u653f%u9e3f%u5fae%u7535%u5b50%u6709%u9650%u516c%u53f8#%u7537%u88c5\"; alicnweb=touch_tb_at%3D1528103758701%7Clastlogonid%3D%25E4%25BB%2598%25E7%258C%25AA%25E5%25BE%25B7%7ChomeIdttS%3D53164068340257168870485693372392195909%7ChomeIdttSAction%3Dtrue%7Chp_newbuyerguide%3Dtrue%7Cshow_inter_tips%3Dfalse; cookie1=Vyh1zqk072gHvkZ5aTB5bn99eCovx4hr9YgGTQEeF9I%3D; cookie17=UonYuCff1bKy4g%3D%3D; sg=%E5%BE%B715; csg=fee18fdd; __cn_logon__=true; __cn_logon_id__=%E4%BB%98%E7%8C%AA%E5%BE%B7; LoginUmid=xLJwvClELt816qnTW0KkZdo5sLWk%2Ftj0aq4l%2BV0yj5Yyp8JBi5lr6w%3D%3D; unb=1839378211; cn_tmp=\"Z28mC+GqtZ1CLMKfEwL7u2ICZVpZdxHRmLcLU9/oAa6a5WC6V3phTq4t0hL4hddyUTqFuE25/OIAoVl0iVZTXNN5QIYGkA/9M4EDpHKyZ9Dcxefk3pjESWGrH4nCeNLQg9Ujw7kt+gez0jXO4yhcErmYkmTZ8spspIQ0XIXG4Bki9a/4/UVG0MCBxDgPNBvWrm6r5z80AbG48zQ737g4ljlPfx8ODmIrhObyRtIGp8r/MNdSbI2pug==\"; login=kFeyVBJLQQI%3D; userID=YuieXOHrx2M4%2BtXcIgqZqt20SUO8PSgz96JsiX9uRzM6sOlEpJKl9g%3D%3D; _nk_=1nOtq11N8pk%3D; userIDNum=ZxPKNZE3L1psO6kAAHeQyQ%3D%3D; _tmp_ck_0=jTA9N8%2B4RDUuHZ33VyF66fH8ELAIDdWw1zmFy8oLErXwhxPIe21PIfVOZNxMeuVX%2F1MlSerfAxs6cv%2Bze9lbf%2BRVlJwiQns864G%2BciO%2BA%2BRaJjtSVVtk9VpwuMXZxLmQ8oXxUKxW0KmRr3qXJ73o38ymqZiV6fER8zrUsxBbagOxFMqD3YwFbVJjMAqyx44mZmSiSD1d0LIPn3xl%2BXdG5tpr8NaSiX3WKKyJ9ZpvOgOrFeTHY0OxanQE0KuqVYWZkjN2ziRsRd7eNYU2bQ5K0%2BcMdiLHxBDYxd1V%2FBM0s8%2BPaFxm%2BrQOlNZP3MdqWVveG1vh3p6osuvnteEpOFERbPN7SCAM6RHNoYhhTLvRk7Uunx6RcuFs%2BlgqhrURWYGi6gC%2B9U%2B7DdkabOQeBpC85KKTdqqoCDKQsJ8ryXNrX%2BPswkGH8YJQBP30WLxm68fXy1TEO86WwUSE7hLJ%2FJ8IYUdrDC6n5ZF9kQ6cd3k422H7wzgL%2BRt3OzZ6SO3yYPGa6cF%2FEpoiPKmXe9Jjly4k05G3LUwJ7D5fDIvBMwMPCQ8%3D; _csrf_token=1528103814290; sec=5b150c8acd186b074b0c918a774646e3bc380ff7; isg=BNzcazzT6_q-SJ4P3nB_G574rfpO_YD5v4bWuLbdp0eqAXyL32VQD1KYZWn5ibjX"
                "cna=/EyzE3NLhFsCAX1GT747oqB0; ali_ab=125.70.177.102.1529893377253.0; UM_distinctid=16434c07e4011c-0b6ed6a24c0176-5e452019-1fa400-16434c07e4318f; lid=%E4%BB%98%E7%8C%AA%E5%BE%B7; ali_apache_track=c_mid=b2b-1839378211|c_lid=%E4%BB%98%E7%8C%AA%E5%BE%B7|c_ms=1; last_mid=b2b-1839378211; __last_loginid__=%E4%BB%98%E7%8C%AA%E5%BE%B7; _cn_slid_=faNQ3gTey1; JSESSIONID=8L78OCuu1-9iXZJMlFi80moBzZF8-As2uJwQ-19r57; cookie2=13a1a06d3e7f360cdd492b7e9ffccd86; t=f3482f813646aed3d6aacd11fee4d15e; _tb_token_=e8e33e1433be4; alicnweb=touch_tb_at%3D1530235941991%7ChomeIdttS%3D95179809422547921645983716339009199990%7ChomeIdttSAction%3Dtrue%7Clastlogonid%3D%25E4%25BB%2598%25E7%258C%25AA%25E5%25BE%25B7%7Cshow_inter_tips%3Dfalse; ad_prefer=\"2018/06/29 09:32:29\"; h_keys=\"%u5973%u88c5#%u6210%u90fd%u767e%u5174%u670d%u9970%u6279%u53d1%u5546%u884c#%u6210%u90fd%u5e02%u6c38%u661f%u8fb0%u5149%u7535%u79d1%u6280%u6709%u9650%u516c%u53f8#%u56db%u5ddd%u5965%u83f2%u514b%u65af%u5efa%u8bbe%u5de5%u7a0b%u6709%u9650%u516c%u53f8%u5efa%u6750%u9500%u552e%u5206%u516c%u53f8#%u65b0%u90fd%u533a%u6b23%u68a6%u60a6%u670d%u9970%u7ecf%u8425%u90e8#%u6b66%u4faf%u533a%u8369%u4f73%u670d%u88c5%u7ecf%u8425%u90e8#%u8863%u820d#%u7b80%u4e39%u8863%u820d#%u5e7f%u5dde%u6f6e%u6d41\"; cookie1=Vyh1zqk072gHvkZ5aTB5bn99eCovx4hr9YgGTQEeF9I%3D; cookie17=UonYuCff1bKy4g%3D%3D; sg=%E5%BE%B715; csg=edb9caef; __cn_logon__=true; __cn_logon_id__=%E4%BB%98%E7%8C%AA%E5%BE%B7; ali_apache_tracktmp=c_w_signed=Y; LoginUmid=JJTj%2B4v5rY8HL13Wj5e6Nu8pZOCziSL7ObaW1RwuC9vBlpK6JhHIZA%3D%3D; unb=1839378211; tbsnid=5DvwRZEzsHazelbfQpO%2BGUZYSFjQNLPtReG1qqEwzN46sOlEpJKl9g%3D%3D; cn_tmp=\"Z28mC+GqtZ1CLMKfEwL7u2ICZVpZdxHRmLcLU9/oAa6a5WC6V3phTq4t0hL4hddyUTqFuE25/OIAoVl0iVZTXNN5QIYGkA/9M4EDpHKyZ9Dcxefk3pjESWGrH4nCeNLQg9Ujw7kt+gez0jXO4yhcErmYkmTZ8spspIQ0XIXG4Bki9a/4/UVG0MCBxDgPNBvWrm6r5z80AbG48zQ737g4ljlPfx8ODmIrFUIZfFA4D0f/WfyASOTYZg==\"; login=kFeyVBJLQQI%3D; userID=YuieXOHrx2M4%2BtXcIgqZqt20SUO8PSgz96JsiX9uRzM6sOlEpJKl9g%3D%3D; _nk_=1nOtq11N8pk%3D; userIDNum=ZxPKNZE3L1psO6kAAHeQyQ%3D%3D; _csrf_token=1530235971055; _is_show_loginId_change_block_=b2b-1839378211_false; _show_force_unbind_div_=b2b-1839378211_false; _show_sys_unbind_div_=b2b-1839378211_false; _show_user_unbind_div_=b2b-1839378211_false; __rn_alert__=false; _tmp_ck_0=HYP1z9lZIkha4CyuV96KYjby6xkOOgBN%2Bl0THI2fj31ZPLi0eVySgArqzvsqPIJ2MsVdjwZRMbChC8rLidQrSF5ulzl2QCqg%2FqvnvDnlQZw6HoIEZowit9NpMElKi9C7837lfiFTIgLnTiKZGodVCFQ6Ro0reAmHtQrOsBhRI9QpAPmxehysTtPx%2Bui856eNYhGRE6CbUSSuSi7OCRpU9XK4ffR1gnuccHdM5xjmFUDHpC354kHrh2UOIgGHSs6u1l7afUnJe0T6rB3BAMwkNo0sSqy8Jv%2F4AURNwuEqiEtP1U2KNmD0iCzVLDWm9CLe%2Fu0omJJGdN7CKvO2cAnrUsEhDyqQDIms7Kx4qsf4RtNE4DUBB687JJOefaGOqpaSiXieFwGBt2JzWoUCQblSZanzgJoFBM58f34a8Ce1fh8%2BB6oTRdbQLB57CIkyityBqfBDzrnJHl6OfJ8ICtqsJW%2Fzoa2EijaEDyvrc9pIZZ4y7G1E0P0kMvSMqRC9wz1b0cOpLMz9bpmCxsP9CjLx5g%3D%3D; isg=BNTUkp9-c7Jn0ef7T1s--4lxpRKGhfiRtAFCv260T9_wWXWjlztJpp2QXRHkoTBv"
        );
        HashMap<String, Object> params = new HashMap<>();
//        params.put("site_id","winport");
//        params.put("_csrf_token","0a5a5f24179fa78a3146112b8bdf71ef");
//        params.put("site_key","f8a4d413523b4e26a51071afe14c72e3");
//        params.put("page_type","creditdetail");
        List<NameValuePair> pairList = new ArrayList<>(params.size());
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry
                    .getValue().toString());
            pairList.add(pair);
        }
//        httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));
        String result = HttpUtil.doMethod(httpPost);
        return result;
    }


    private String getDetail(){
        HttpPost httpPost = new HttpPost(
                "https://xinyong.1688.com/credit/creditsearch/detail.json?_csrf_token=1530251604724&_tb_token_=e8e33e1433be4"
        );
        httpPost.addHeader("Cookie",
//             "UM_distinctid=1627131a18726f-00c2677feff5ed-3a614f0b-1fa400-1627131a188313; cna=wT8uE4q3lhECAXZ0az8uGcYL; __last_loginid__=%E4%BB%98%E7%8C%AA%E5%BE%B7; last_mid=b2b-1839378211; hng=CN%7Czh-CN%7CCNY%7C156; lid=%E4%BB%98%E7%8C%AA%E5%BE%B7; ali_apache_track=c_mid=b2b-1839378211|c_lid=%E4%BB%98%E7%8C%AA%E5%BE%B7|c_ms=1; _cn_slid_=faNQ3gTey1; ali_ab=218.88.26.197.1525773293068.7; ali_beacon_id=218.88.26.197.1525773937972.710014.7; _uab_collina=152654884002745930298915; JSESSIONID=9L78lO0l1-GhXZ1OzZdQrAf9dqT9-t8CixtQ-oDe94; cookie2=16ddc1a30821550a15825fb47817e8e6; t=fda5943929603be20bb78ddb7f857fef; _tb_token_=7beee8197e7be; _umdata=0823A424438F76AB0D906A6B7CF2CA633BEAE0AA1526E4592B3622F5EB409468FF41029D23C5EC7CCD43AD3E795C914C1BC4DEB22555B7B57D7CC596D828FF2C; ali_apache_tracktmp=c_w_signed=Y; tbsnid=a6rIMybPxKbfjsezuQKj1%2Be7DbJN%2BS6QF528gS9gj5s6sOlEpJKl9g%3D%3D; _is_show_loginId_change_block_=b2b-1839378211_false; _show_force_unbind_div_=b2b-1839378211_false; _show_sys_unbind_div_=b2b-1839378211_false; _show_user_unbind_div_=b2b-1839378211_false; __rn_alert__=false; CNZZDATA1257121278=526688878-1525831332-https%253A%252F%252Fshop3388t654913l9.1688.com%252F%7C1528101517; ad_prefer=\"2018/06/04 17:06:08\"; h_keys=\"%u60e0%u5dde%u5e02%u76c8%u73c8%u670d%u9970%u6709%u9650%u516c%u53f8#%u6df1%u5733%u5e02%u9f99%u5c97%u533a%u8863%u6e58%u574a%u670d%u9970%u5546%u884c#%u5973%u88c5#%u6df1%u5733%u5e02%u5de8%u660a%u946b%u8d38%u6613%u6709%u9650%u516c%u53f8#%u6df1%u5733%u5e02%u5b9d%u5b89%u533a%u897f%u4e61%u552f%u7231%u8863%u670d%u88c5%u5382#%u6df1%u5733%u5e02%u5361%u95e8%u670d%u9970%u6709%u9650%u516c%u53f8#%u6df1%u5733%u5e02%u9f99%u5c97%u533a%u7f18%u83b1%u6052%u6f6e%u6d41%u670d%u9970%u5546%u884c#%u6fc2%u5ba0%ue5ca#%u6df1%u5733%u5e02%u653f%u9e3f%u5fae%u7535%u5b50%u6709%u9650%u516c%u53f8#%u7537%u88c5\"; alicnweb=touch_tb_at%3D1528103758701%7Clastlogonid%3D%25E4%25BB%2598%25E7%258C%25AA%25E5%25BE%25B7%7ChomeIdttS%3D53164068340257168870485693372392195909%7ChomeIdttSAction%3Dtrue%7Chp_newbuyerguide%3Dtrue%7Cshow_inter_tips%3Dfalse; cookie1=Vyh1zqk072gHvkZ5aTB5bn99eCovx4hr9YgGTQEeF9I%3D; cookie17=UonYuCff1bKy4g%3D%3D; sg=%E5%BE%B715; csg=fee18fdd; __cn_logon__=true; __cn_logon_id__=%E4%BB%98%E7%8C%AA%E5%BE%B7; LoginUmid=xLJwvClELt816qnTW0KkZdo5sLWk%2Ftj0aq4l%2BV0yj5Yyp8JBi5lr6w%3D%3D; unb=1839378211; cn_tmp=\"Z28mC+GqtZ1CLMKfEwL7u2ICZVpZdxHRmLcLU9/oAa6a5WC6V3phTq4t0hL4hddyUTqFuE25/OIAoVl0iVZTXNN5QIYGkA/9M4EDpHKyZ9Dcxefk3pjESWGrH4nCeNLQg9Ujw7kt+gez0jXO4yhcErmYkmTZ8spspIQ0XIXG4Bki9a/4/UVG0MCBxDgPNBvWrm6r5z80AbG48zQ737g4ljlPfx8ODmIrhObyRtIGp8r/MNdSbI2pug==\"; login=kFeyVBJLQQI%3D; userID=YuieXOHrx2M4%2BtXcIgqZqt20SUO8PSgz96JsiX9uRzM6sOlEpJKl9g%3D%3D; _nk_=1nOtq11N8pk%3D; userIDNum=ZxPKNZE3L1psO6kAAHeQyQ%3D%3D; _tmp_ck_0=jTA9N8%2B4RDUuHZ33VyF66fH8ELAIDdWw1zmFy8oLErXwhxPIe21PIfVOZNxMeuVX%2F1MlSerfAxs6cv%2Bze9lbf%2BRVlJwiQns864G%2BciO%2BA%2BRaJjtSVVtk9VpwuMXZxLmQ8oXxUKxW0KmRr3qXJ73o38ymqZiV6fER8zrUsxBbagOxFMqD3YwFbVJjMAqyx44mZmSiSD1d0LIPn3xl%2BXdG5tpr8NaSiX3WKKyJ9ZpvOgOrFeTHY0OxanQE0KuqVYWZkjN2ziRsRd7eNYU2bQ5K0%2BcMdiLHxBDYxd1V%2FBM0s8%2BPaFxm%2BrQOlNZP3MdqWVveG1vh3p6osuvnteEpOFERbPN7SCAM6RHNoYhhTLvRk7Uunx6RcuFs%2BlgqhrURWYGi6gC%2B9U%2B7DdkabOQeBpC85KKTdqqoCDKQsJ8ryXNrX%2BPswkGH8YJQBP30WLxm68fXy1TEO86WwUSE7hLJ%2FJ8IYUdrDC6n5ZF9kQ6cd3k422H7wzgL%2BRt3OzZ6SO3yYPGa6cF%2FEpoiPKmXe9Jjly4k05G3LUwJ7D5fDIvBMwMPCQ8%3D; _csrf_token=1528103814290; sec=5b150c8acd186b074b0c918a774646e3bc380ff7; isg=BNzcazzT6_q-SJ4P3nB_G574rfpO_YD5v4bWuLbdp0eqAXyL32VQD1KYZWn5ibjX"
            "cna=/EyzE3NLhFsCAX1GT747oqB0; ali_ab=125.70.177.102.1529893377253.0; UM_distinctid=16434c07e4011c-0b6ed6a24c0176-5e452019-1fa400-16434c07e4318f; lid=%E4%BB%98%E7%8C%AA%E5%BE%B7; ali_apache_track=c_mid=b2b-1839378211|c_lid=%E4%BB%98%E7%8C%AA%E5%BE%B7|c_ms=1; last_mid=b2b-1839378211; __last_loginid__=%E4%BB%98%E7%8C%AA%E5%BE%B7; _cn_slid_=faNQ3gTey1; _uab_collina=153016850137262157826773; JSESSIONID=8L78OCuu1-9iXZJMlFi80moBzZF8-As2uJwQ-19r57; cookie2=13a1a06d3e7f360cdd492b7e9ffccd86; t=f3482f813646aed3d6aacd11fee4d15e; _tb_token_=e8e33e1433be4; ali_apache_tracktmp=c_w_signed=Y; tbsnid=5DvwRZEzsHazelbfQpO%2BGUZYSFjQNLPtReG1qqEwzN46sOlEpJKl9g%3D%3D; __rn_alert__=false; ali_beacon_id=125.70.177.102.1530238669534.021450.2; _umdata=535523100CBE37C3C3410FA6ED6DB03968593E43832737F23915CA0E0C4CC4085390A17FCD8F3514CD43AD3E795C914CE4566E55609B85AFE42AE0759D468F09; h_keys=\"%u6210%u90fd%u5c1a%u598d%u7f8e%u5546%u8d38%u6709%u9650%u516c%u53f8#%u6210%u90fd%u5e02%u91d1%u725b%u533a%u946b%u610f%u670d%u88c5%u7ecf%u8425%u90e8#%u5973%u88c5#%u6210%u90fd%u767e%u5174%u670d%u9970%u6279%u53d1%u5546%u884c#%u6210%u90fd%u5e02%u6c38%u661f%u8fb0%u5149%u7535%u79d1%u6280%u6709%u9650%u516c%u53f8#%u56db%u5ddd%u5965%u83f2%u514b%u65af%u5efa%u8bbe%u5de5%u7a0b%u6709%u9650%u516c%u53f8%u5efa%u6750%u9500%u552e%u5206%u516c%u53f8#%u65b0%u90fd%u533a%u6b23%u68a6%u60a6%u670d%u9970%u7ecf%u8425%u90e8#%u6b66%u4faf%u533a%u8369%u4f73%u670d%u88c5%u7ecf%u8425%u90e8#%u8863%u820d#%u7b80%u4e39%u8863%u820d\"; _is_show_loginId_change_block_=b2b-1839378211_false; _show_force_unbind_div_=b2b-1839378211_false; _show_sys_unbind_div_=b2b-1839378211_false; _show_user_unbind_div_=b2b-1839378211_false; cookie1=Vyh1zqk072gHvkZ5aTB5bn99eCovx4hr9YgGTQEeF9I%3D; cookie17=UonYuCff1bKy4g%3D%3D; sg=%E5%BE%B715; csg=044f6f8d; __cn_logon__=true; __cn_logon_id__=%E4%BB%98%E7%8C%AA%E5%BE%B7; LoginUmid=JJTj%2B4v5rY8HL13Wj5e6Nu8pZOCziSL7ObaW1RwuC9vBlpK6JhHIZA%3D%3D; unb=1839378211; cn_tmp=\"Z28mC+GqtZ1CLMKfEwL7u2ICZVpZdxHRmLcLU9/oAa6a5WC6V3phTq4t0hL4hddyUTqFuE25/OIAoVl0iVZTXNN5QIYGkA/9M4EDpHKyZ9Dcxefk3pjESWGrH4nCeNLQg9Ujw7kt+gez0jXO4yhcErmYkmTZ8spspIQ0XIXG4Bki9a/4/UVG0MCBxDgPNBvWrm6r5z80AbG48zQ737g4ljlPfx8ODmIrLG81oBHuy6kix4MDJ28aZQ==\"; login=kFeyVBJLQQI%3D; userID=YuieXOHrx2M4%2BtXcIgqZqt20SUO8PSgz96JsiX9uRzM6sOlEpJKl9g%3D%3D; _nk_=1nOtq11N8pk%3D; userIDNum=ZxPKNZE3L1psO6kAAHeQyQ%3D%3D; _tmp_ck_0=uS3J%2FTASuVdmdTr%2ByuIhHQyNl%2BHWFUm4HCMT5rxqbRDd6t%2FGpthdX0mV5TRe2uNT%2FL2lzOKmzadbQvLzw3feii1MobJxPRA5rNBa2Gf7r1z6Z%2Bmhc3IjZ9RQJacIGzBPsPAp413WhWPZozeuIe6zgZC1jFErKlFGTHzsoSUHkkLqgwxPQkvK7ZuAaKMLiUJy0pDyAqQqCQ%2BQfj%2F4wrOHfEVR3ZF546DlO%2F8h0RuGv1waQcygCvj%2BTNrEZO5PA6B8YYy29%2Fs4mp3Q3XfQSawWuQaIY4IkdtOjOZW7WUfbwvzZ4Q80JX7VYR%2FjdRIFhXLP97QhI%2Bjr30u%2BJ39KFeHAnZG5mkRKPgMxBnJYLWroNeGkW6D5De3yPOJL1vNG4uNztl1%2FIX1SOpusEnU4%2FmDnSfp13j1lsOVAUmqkl2aPDcFK3oA7UDkjC%2FG5U2q2Oks6mVGVCKSJxkurD55gaLEFaDvj1wjiJ0d13XlXsuo7JvKWmNBboTh75EjjwQ%2BIxXfL4JtmyOVzz7FZLSETtfT%2FYQ%3D%3D; _csrf_token=1530251604724; ad_prefer=\"2018/06/29 13:56:17\"; CNZZDATA1257121278=1649830350-1529997565-https%253A%252F%252Fshop1493139484737.1688.com%252F%7C1530251445; alicnweb=touch_tb_at%3D1530252865998%7ChomeIdttS%3D95179809422547921645983716339009199990%7ChomeIdttSAction%3Dtrue%7Clastlogonid%3D%25E4%25BB%2598%25E7%258C%25AA%25E5%25BE%25B7%7Cshow_inter_tips%3Dfalse; sec=5b35d51f79c198d9fb426dc4f898035ef14a53b9; isg=BLu7TkIzZPdse1hGzFap3toESp_luM-UR3RdIq14hLrRDNvuNOBfYtllIuznLCcK"
        );
        HashMap<String, Object> params = new HashMap<>();
        params.put("id","");
        params.put("module","basic");
        params.put("acnt","tSucRtXxm0p_ZUY2C75jjg");
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

    //解析黄页
    private Map<String, Object> parseYellowPage(String url) throws IOException, InterruptedException {
        for(int i = 0; i < 100; i++) {
            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader("Cookie",
                "cna=/EyzE3NLhFsCAX1GT747oqB0; ali_ab=125.70.177.102.1529893377253.0; UM_distinctid=16434c07e4011c-0b6ed6a24c0176-5e452019-1fa400-16434c07e4318f; lid=%E4%BB%98%E7%8C%AA%E5%BE%B7; ali_apache_track=c_mid=b2b-1839378211|c_lid=%E4%BB%98%E7%8C%AA%E5%BE%B7|c_ms=1; last_mid=b2b-1839378211; __last_loginid__=%E4%BB%98%E7%8C%AA%E5%BE%B7; _cn_slid_=faNQ3gTey1; JSESSIONID=8L78LCuu1-sgXZdKPKsenkzD6gi4-aVcn2wQ-rfuX6; cookie2=1abfb8a7f92212fdd803b61461c9e154; t=f3482f813646aed3d6aacd11fee4d15e; _tb_token_=e33847335b5e8; ali_apache_tracktmp=c_w_signed=Y; LoginUmid=JJTj%2B4v5rY8HL13Wj5e6Nu8pZOCziSL7ObaW1RwuC9vBlpK6JhHIZA%3D%3D; tbsnid=eCtA6iVkAaNaHY9qyggZ1fIg459dnambCqKYQ%2FSps4U6sOlEpJKl9g%3D%3D; userID=YuieXOHrx2M4%2BtXcIgqZqt20SUO8PSgz96JsiX9uRzM6sOlEpJKl9g%3D%3D; userIDNum=ZxPKNZE3L1psO6kAAHeQyQ%3D%3D; __rn_alert__=false; ad_prefer=\"2018/06/28 10:09:49\"; h_keys=\"%u5973%u88c5#%u56db%u5ddd%u5965%u83f2%u514b%u65af%u5efa%u8bbe%u5de5%u7a0b%u6709%u9650%u516c%u53f8%u5efa%u6750%u9500%u552e%u5206%u516c%u53f8#%u65b0%u90fd%u533a%u6b23%u68a6%u60a6%u670d%u9970%u7ecf%u8425%u90e8#%u6b66%u4faf%u533a%u8369%u4f73%u670d%u88c5%u7ecf%u8425%u90e8#%u8863%u820d#%u7b80%u4e39%u8863%u820d#%u5e7f%u5dde%u6f6e%u6d41#%u533b%u836f\"; _is_show_loginId_change_block_=b2b-1839378211_false; _show_force_unbind_div_=b2b-1839378211_false; _show_sys_unbind_div_=b2b-1839378211_false; _show_user_unbind_div_=b2b-1839378211_false; alicnweb=touch_tb_at%3D1530164423921%7ChomeIdttS%3D95179809422547921645983716339009199990%7ChomeIdttSAction%3Dtrue%7Clastlogonid%3D%25E4%25BB%2598%25E7%258C%25AA%25E5%25BE%25B7%7Cshow_inter_tips%3Dfalse; cookie1=Vyh1zqk072gHvkZ5aTB5bn99eCovx4hr9YgGTQEeF9I%3D; cookie17=UonYuCff1bKy4g%3D%3D; sg=%E5%BE%B715; csg=c356ba94; __cn_logon__=true; __cn_logon_id__=%E4%BB%98%E7%8C%AA%E5%BE%B7; unb=1839378211; cn_tmp=\"Z28mC+GqtZ1CLMKfEwL7u2ICZVpZdxHRmLcLU9/oAa6a5WC6V3phTq4t0hL4hddyUTqFuE25/OIAoVl0iVZTXNN5QIYGkA/9M4EDpHKyZ9Dcxefk3pjESWGrH4nCeNLQg9Ujw7kt+gez0jXO4yhcErmYkmTZ8spspIQ0XIXG4Bki9a/4/UVG0MCBxDgPNBvWrm6r5z80AbG48zQ737g4ljlPfx8ODmIrl5URT7L9NWqlB72fDSjP9w==\"; login=kFeyVBJLQQI%3D; _nk_=1nOtq11N8pk%3D; _tmp_ck_0=52pEfsbSEfAnyiX8v64FxWad9ok6MxCq2D3qHpmtAN01TlbykrkAEfa1m7T9mRRVmlQuImhFp%2FHM7QU7la8rUEGADl5MuTgfXapsduzkssitSfpn8EjCeTJGjExkP3c%2FV9Sc1NHWEAaD6GCUVDeiTsXjP61NOYmrK82%2BJkgzSmSXkKQxJL5FSVJEBvyyHes5I%2B%2F8DxzJfJ0KwdsfB2W2X%2FOjeXlseX0DpjNhWVH3vlJmVR5P9U477NRt0F7u2EW1jrX440Ai9RfI131E2e5hqKMAA8ln5rDeItDJi6yOl%2BYs%2FMUMHqpValfUBzMMFdHicicY7gVujMOB0vtLHlDaUkKoXuCZn4Wz8vqPIpgBucqc54YE8%2FU7jl2G4GBby2dKyHHvfHIyHAMSVOLNzTBU47tklSDM9gOi1plcmkkNtX75j8SIicty%2F4PzXdC9oB%2FNHySlWdxK%2F3S125%2F5VobAQtUYbzuQjDPuK6%2FU9EH%2BnxmfCCe0paKHwsq0V0uV9B%2FSO8RRvB1ZT8I1ORZNuW8iU6idM269l8xa8JlEYiHeOwY%3D; _csrf_token=1530164989878; CNZZDATA1000110779=1076705787-1530003600-https%253A%252F%252Fshop1394672810794.1688.com%252F%7C1530164485; isg=BE1Nnk3PKhgMDI4wrnzXoNgOXGkHgoF6hQ4rNI_SieRThm04V3qRzJsU9FpFRpm0"
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
        FileOutputStream fos = new FileOutputStream("C:\\Users\\SRKJ\\IdeaProjects\\WebMagic\\src\\main\\java\\com\\fzd\\ali\\a.jpg");
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
                "cna=/EyzE3NLhFsCAX1GT747oqB0; ali_ab=125.70.177.102.1529893377253.0; UM_distinctid=16434c07e4011c-0b6ed6a24c0176-5e452019-1fa400-16434c07e4318f; lid=%E4%BB%98%E7%8C%AA%E5%BE%B7; ali_apache_track=c_mid=b2b-1839378211|c_lid=%E4%BB%98%E7%8C%AA%E5%BE%B7|c_ms=1; last_mid=b2b-1839378211; __last_loginid__=%E4%BB%98%E7%8C%AA%E5%BE%B7; _cn_slid_=faNQ3gTey1; JSESSIONID=8L78LCuu1-sgXZdKPKsenkzD6gi4-aVcn2wQ-rfuX6; cookie2=1abfb8a7f92212fdd803b61461c9e154; t=f3482f813646aed3d6aacd11fee4d15e; _tb_token_=e33847335b5e8; ali_apache_tracktmp=c_w_signed=Y; LoginUmid=JJTj%2B4v5rY8HL13Wj5e6Nu8pZOCziSL7ObaW1RwuC9vBlpK6JhHIZA%3D%3D; tbsnid=eCtA6iVkAaNaHY9qyggZ1fIg459dnambCqKYQ%2FSps4U6sOlEpJKl9g%3D%3D; userID=YuieXOHrx2M4%2BtXcIgqZqt20SUO8PSgz96JsiX9uRzM6sOlEpJKl9g%3D%3D; userIDNum=ZxPKNZE3L1psO6kAAHeQyQ%3D%3D; __rn_alert__=false; ad_prefer=\"2018/06/28 10:09:49\"; h_keys=\"%u5973%u88c5#%u56db%u5ddd%u5965%u83f2%u514b%u65af%u5efa%u8bbe%u5de5%u7a0b%u6709%u9650%u516c%u53f8%u5efa%u6750%u9500%u552e%u5206%u516c%u53f8#%u65b0%u90fd%u533a%u6b23%u68a6%u60a6%u670d%u9970%u7ecf%u8425%u90e8#%u6b66%u4faf%u533a%u8369%u4f73%u670d%u88c5%u7ecf%u8425%u90e8#%u8863%u820d#%u7b80%u4e39%u8863%u820d#%u5e7f%u5dde%u6f6e%u6d41#%u533b%u836f\"; _is_show_loginId_change_block_=b2b-1839378211_false; _show_force_unbind_div_=b2b-1839378211_false; _show_sys_unbind_div_=b2b-1839378211_false; _show_user_unbind_div_=b2b-1839378211_false; alicnweb=touch_tb_at%3D1530164423921%7ChomeIdttS%3D95179809422547921645983716339009199990%7ChomeIdttSAction%3Dtrue%7Clastlogonid%3D%25E4%25BB%2598%25E7%258C%25AA%25E5%25BE%25B7%7Cshow_inter_tips%3Dfalse; cookie1=Vyh1zqk072gHvkZ5aTB5bn99eCovx4hr9YgGTQEeF9I%3D; cookie17=UonYuCff1bKy4g%3D%3D; sg=%E5%BE%B715; csg=c356ba94; __cn_logon__=true; __cn_logon_id__=%E4%BB%98%E7%8C%AA%E5%BE%B7; unb=1839378211; cn_tmp=\"Z28mC+GqtZ1CLMKfEwL7u2ICZVpZdxHRmLcLU9/oAa6a5WC6V3phTq4t0hL4hddyUTqFuE25/OIAoVl0iVZTXNN5QIYGkA/9M4EDpHKyZ9Dcxefk3pjESWGrH4nCeNLQg9Ujw7kt+gez0jXO4yhcErmYkmTZ8spspIQ0XIXG4Bki9a/4/UVG0MCBxDgPNBvWrm6r5z80AbG48zQ737g4ljlPfx8ODmIrl5URT7L9NWqlB72fDSjP9w==\"; login=kFeyVBJLQQI%3D; _nk_=1nOtq11N8pk%3D; _tmp_ck_0=52pEfsbSEfAnyiX8v64FxWad9ok6MxCq2D3qHpmtAN01TlbykrkAEfa1m7T9mRRVmlQuImhFp%2FHM7QU7la8rUEGADl5MuTgfXapsduzkssitSfpn8EjCeTJGjExkP3c%2FV9Sc1NHWEAaD6GCUVDeiTsXjP61NOYmrK82%2BJkgzSmSXkKQxJL5FSVJEBvyyHes5I%2B%2F8DxzJfJ0KwdsfB2W2X%2FOjeXlseX0DpjNhWVH3vlJmVR5P9U477NRt0F7u2EW1jrX440Ai9RfI131E2e5hqKMAA8ln5rDeItDJi6yOl%2BYs%2FMUMHqpValfUBzMMFdHicicY7gVujMOB0vtLHlDaUkKoXuCZn4Wz8vqPIpgBucqc54YE8%2FU7jl2G4GBby2dKyHHvfHIyHAMSVOLNzTBU47tklSDM9gOi1plcmkkNtX75j8SIicty%2F4PzXdC9oB%2FNHySlWdxK%2F3S125%2F5VobAQtUYbzuQjDPuK6%2FU9EH%2BnxmfCCe0paKHwsq0V0uV9B%2FSO8RRvB1ZT8I1ORZNuW8iU6idM269l8xa8JlEYiHeOwY%3D; _csrf_token=1530164989878; CNZZDATA1000110779=1076705787-1530003600-https%253A%252F%252Fshop1394672810794.1688.com%252F%7C1530164485; isg=BE1Nnk3PKhgMDI4wrnzXoNgOXGkHgoF6hQ4rNI_SieRThm04V3qRzJsU9FpFRpm0"
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
                "098#E1hvjQvZv7IvUvCkvvvvvjiPPFLhgjnhR2zZ0j3mPmPZQjEVP2qpAjlWRLc9gj3nCQhvHXzw7DdN98DUTOkY1NEL4U9HuwdPJsyCvvpvvvvvCQhvHXzw7DdN4RiUTOkY1NEL4U9HuwdPJT6CvvyvhvwvuT+vbnJtvpvIvvvvvhCvvvvvvUnvphvhcQvv96CvpC29vvm2phCvhhvvvUnvphvpp8yCvv9vvUm1FTukpUyCvvOUvvVvayWEvpCW2VsXvvwdD7zhA8TZfvDr1W3l5dXKjdzCI8p7+3+u6jc6D46XwAtrD7zhV4gaWXxr1WkK5ehhfwLpaB46NB3r1W3l5t10BnsIlRmXe9hCvvOvUvvvphvPvpvhvv2MMsyCvvpvvvvviQhvCvvv9U8jvpvhvvpvvUhCvCuacVx4drMwzn1aUxdAVg3UoZFES9WkuJu84FyCvvpvvvvvRphvCvvvvvvjvpvx5a147rYQghFikbzHGDnIkv7iA+YqRphvCvvvvvmCvpvWzH10762NznswOvC4RphvCvvvvvv="
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
