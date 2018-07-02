package com.fzd.ali;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fzd.httpclient4_5.HttpUtil;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 付祖德 on 2018/6/30.
 */
public class ZmxyCrawl {

    static String cookie =
            "cna=VI4VEv+m/UACAXWvgSDbMl9X; UM_distinctid=164471d4ee9118-01d1fdb2189c72-59462f1d-1fa400-164471d4eea3cf; _uab_collina=153032730847184698568307; ali_apache_track=c_mid=b2b-2137605481|c_lid=%E6%97%AD%E4%BA%8C%E7%88%B8|c_ms=1; last_mid=b2b-2137605481; __last_loginid__=%E6%97%AD%E4%BA%8C%E7%88%B8; _cn_slid_=mAlpAE9w0F; h_keys=\"%u5973%u88c5#%u5e7f%u5b89%u5e02%u6708%u9999%u98df%u54c1%u6709%u9650%u516c%u53f8\"; ad_prefer=\"2018/07/02 22:48:02\"; ali_ab=183.221.104.189.1530327152557.9; JSESSIONID=VaAZrji-sgXZtUQ9AnGyyOHbo4-9zxfewQ-0GCG6; cookie2=18f1c110886fed76ca3a82229ec02cf7; hng=CN%7Czh-CN%7CCNY%7C156; t=54ddad72928a4fa979b985166108da1f; _tb_token_=eeeae37ee3a16; lid=%E6%97%AD%E4%BA%8C%E7%88%B8; __cn_logon__=false; _tmp_ck_0=W7DaJXRytOQDLJ6gHcSq3ARID2s7dut5DX%2F2uxmTgqNfEvLhB9DR9%2B8UWVwR16c7OTk%2BRDD9A%2BsWIZWkiSvNyD6bIvZlJl9i45abpo7gpoqSNzyGzgudaBit8QpJChrj%2BXEXdNz3R52udYxJ1H%2B%2Faulu2P3NpEEX5Zjdf5uAIzL6c6aFAk4hp18HlcIGx2YZlLdNQSDtysCpLjdT%2F%2FSIti32UufjUNNVm8M8uLMEKSP8Q%2BgArsbqv1HSVO93mdkoLbvAieiWaGFv2Ly0Romefm7vxIhZ5NdkaDpmuR8viZ7NNMYcFKD1zLvZxR7OmWjMe090eg0rFqsY87HhpaJ6WUWzyxZluJrfH7KJCVM5NlYD%2FVyglC4vyEI4NLmTRVtR; alicnweb=touch_tb_at%3D1530542884101%7ChomeIdttS%3D15400825400402656305143168106873000371%7ChomeIdttSAction%3Dtrue%7Clastlogonid%3D%25E6%2597%25AD%25E4%25BA%258C%25E7%2588%25B8; _csrf_token=1530542887889; _umdata=0712F33290AB8A6D31D8021E7A2169E2E23F2E4F2C1E6CDAD5F6C68E50CF523BD82D98307C63C3BECD43AD3E795C914CC2466B386E104C8396476683B66BE46B; isg=BPz8DtqJi7XpUr9-mglEf8D2zZpuXaDWLCvSUNZ9IefJoZwr_wVwr3I4hIl82th3; CNZZDATA1257121278=1815443264-1530198675-https%253A%252F%252Fshop8s1463487d150.1688.com%252F%7C1530541563"
            ;
    public static void main(String... args){
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
        System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.commons.httpclient", "stdout");
        ZmxyCrawl zmxyCrawl = new ZmxyCrawl();
//        zmxyCrawl.get();
//        zmxyCrawl.statistic();
        zmxyCrawl.getTbToken();
    }

    private void getTbToken(){
        HttpGet httpGet = new HttpGet("https://xinyong.1688.com/credit/openpage/index.htm");
        httpGet.addHeader("Cookie",
                "cna=/EyzE3NLhFsCAX1GT747oqB0; ali_ab=125.70.177.102.1529893377253.0; UM_distinctid=16434c07e4011c-0b6ed6a24c0176-5e452019-1fa400-16434c07e4318f; lid=%E4%BB%98%E7%8C%AA%E5%BE%B7; ali_apache_track=c_mid=b2b-1839378211|c_lid=%E4%BB%98%E7%8C%AA%E5%BE%B7|c_ms=1; last_mid=b2b-1839378211; __last_loginid__=%E4%BB%98%E7%8C%AA%E5%BE%B7; _cn_slid_=faNQ3gTey1; JSESSIONID=8L78OCuu1-9iXZJMlFi80moBzZF8-As2uJwQ-19r57; cookie2=13a1a06d3e7f360cdd492b7e9ffccd86; t=f3482f813646aed3d6aacd11fee4d15e; _tb_token_=e8e33e1433be4; ali_apache_tracktmp=c_w_signed=Y; tbsnid=5DvwRZEzsHazelbfQpO%2BGUZYSFjQNLPtReG1qqEwzN46sOlEpJKl9g%3D%3D; ali_beacon_id=125.70.177.102.1530238669534.021450.2; LoginUmid=JJTj%2B4v5rY8HL13Wj5e6Nu8pZOCziSL7ObaW1RwuC9vBlpK6JhHIZA%3D%3D; userID=YuieXOHrx2M4%2BtXcIgqZqt20SUO8PSgz96JsiX9uRzM6sOlEpJKl9g%3D%3D; userIDNum=ZxPKNZE3L1psO6kAAHeQyQ%3D%3D; csg=3b1dfc30; __cn_logon__=true; __cn_logon_id__=%E4%BB%98%E7%8C%AA%E5%BE%B7; cn_tmp=\"Z28mC+GqtZ1CLMKfEwL7u2ICZVpZdxHRmLcLU9/oAa6a5WC6V3phTq4t0hL4hddyUTqFuE25/OIAoVl0iVZTXNN5QIYGkA/9M4EDpHKyZ9Dcxefk3pjESWGrH4nCeNLQg9Ujw7kt+gez0jXO4yhcErmYkmTZ8spspIQ0XIXG4Bki9a/4/UVG0MCBxDgPNBvWrm6r5z80AbG48zQ737g4ljlPfx8ODmIrmaG53zZPYKejsThOBSeXHg==\"; _csrf_token=1530515835286; ad_prefer=\"2018/07/02 16:57:25\"; h_keys=\"%u6210%u90fd%u5e02%u7504%u7504%u7ee3%u54c1%u6709%u9650%u516c%u53f8#%u6210%u90fd%u540c%u76df%u96c5%u81f4%u670d%u88c5%u6709%u9650%u516c%u53f8#%u91d1%u725b%u533a%u946b%u8363%u548c%u65e5%u7528%u54c1%u7ecf%u8425%u90e8#%u91d1%u5802%u53bf%u81ea%u7531%u72ac%u5ba0%u7269%u670d%u9970%u5382#%u6210%u90fd%u542f%u8fd0%u901a%u4eea%u5668%u6709%u9650%u516c%u53f8#%u5973%u88c5#%u9526%u6c5f%u533a%u59ae%u53ef%u5973%u978b%u5546%u8d38%u90e8#%u91d1%u725b%u533a%u5c0f%u6dd8%u6c14%u6bdb%u7ed2%u73a9%u5177%u6279%u53d1%u90e8#%u6210%u90fd%u591a%u683c%u5c14%u5546%u8d38%u6709%u9650%u516c%u53f8#%u5927%u540c%u4f0d%u5c06%u6210%u90fd%u6570%u63a7%u79d1%u6280%u6709%u9650%u516c%u53f8\"; _tmp_ck_0=Us6kfQ%2FxEOO5PGRnfn8HzOGQsjYlQyOQFZY0js6s0ek%2BH80hAXAqxiNLoGyWPheX8qHw%2FNclNkzzu5Gnv7Tt254mhkSO%2FZSeHwqZ8jZHQ9VYb4GNRqFD96wunMoSE33RMpaKXLLcgFxWn9zusIN96cmdCWShH5yucRA%2BKhJAFESpOsSHgw9gPR6xvcf6jas2O8wWNAsTRMaTlAig%2F%2FNCHhgRG9ldYHPqaseJxgY1OMRIhDrFMwt1%2BwTfwbA9PplxoFacJMdqiBcI2SStxFOcsuqUDgR%2BCK%2Frfedfc%2Bm%2FfKlei9%2BWyCNCc1K3a7ZGqjJVeTWReQ9cL9445RiJZZkuaSKuN1ldcvZm1iyR9B%2BGvGVJX8S5LpNRBaXjcFbBIQ81cMWvcT7yl40Fr%2F1t6PYaLfwSX2LtknhAWz%2BFj56Wt9ssiqAd0Gh8a2DgZh%2FVhc6FQgsVswMxTddiCs7wQTjBjKDagID3viGgW6BjGmofMVqAOuFq0%2B2ZVJ9SJAzTtLqUPo8yE%2Bwmy%2FfhGjbzedBDQsyWeIlyyOz3Us4Po3k3QPs%3D; alicnweb=touch_tb_at%3D1530546208921%7ChomeIdttS%3D95179809422547921645983716339009199990%7ChomeIdttSAction%3Dtrue%7Clastlogonid%3D%25E4%25BB%2598%25E7%258C%25AA%25E5%25BE%25B7%7Cshow_inter_tips%3Dfalse; isg=BGFhTAr1ngoisTJ0YhjjLCzicC27ptWGcdK38MM2UmjAKoH8C17l0I9oiB4J4m04"
                );
        String result = HttpUtil.doMethod(httpGet);
        System.out.println(result.contains("_tb_token_"));
        System.out.println(result.substring(result.indexOf("_tb_token_") + "_tb_token_'] = '".length() , result.indexOf("_tb_token_") + "_tb_token_'] = '".length() + 13));
    }

    private void get(){
        for(int i =0; i < 1; i++) {
            HttpGet httpGet = new HttpGet(
                    "https://xinyong.1688.com/credit/score/get.json?acnt=7AYGuyZp-7pKdLHa7EJgEQ"
            );
            httpGet.addHeader("Cookie",cookie);
            String result = HttpUtil.doMethod(httpGet);
            System.out.println(i);
            if(result.contains("亲，访问受限了")){
                System.out.println("亲，访问受限了");
            }else {
                System.out.println(result);
            }
        }
    }

    private void statisticBase(){
        for(int i =0; i < 1; i++) {
            HttpGet httpGet = new HttpGet(
                    "https://xinyong.1688.com/credit/report/statistic.json?_csrf_token=1530368047207&_tb_token_=ee73be15ebd99&acnt=7AYGuyZp-7pKdLHa7EJgEQ"
            );
            httpGet.addHeader("Cookie", cookie);
            String result = HttpUtil.doMethod(httpGet);
//            System.out.println(i);
            if(result.contains("亲，访问受限了")){
                System.out.println("亲，访问受限了");
            }else {
                System.out.println(result);
            }
        }
    }

    private void statistic(){
        for(int i =0; i < 100; i++) {
            HttpPost httpPost = new HttpPost(
                    "https://xinyong.1688.com/credit/creditsearch/statistic.json?_tb_token_=eeeae37ee3a16"
            );
            httpPost.addHeader("Cookie",
                   cookie
            );
            Map<String, Object> params = new HashMap<>();
            params.put("id","");
            params.put("acnt","PYRQjBW0b6YudEcGmV_SgQ");
            List<NameValuePair> pairList = new ArrayList<>(params.size());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry
                        .getValue().toString());
                pairList.add(pair);
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));
            String result = HttpUtil.doMethod(httpPost);
//            System.out.println(i);
            if(result.contains("亲，访问受限了")){
                System.out.println("亲，访问受限了");
            }else {
                System.out.println(result);
            }
        }
    }

    private String getDetail(){
        HttpPost httpPost = new HttpPost(
                "https://xinyong.1688.com/credit/score/get.json?acnt=vdSizJGEonZQriasrdLrnQ"
        );
        httpPost.addHeader("Cookie",
//             "UM_distinctid=1627131a18726f-00c2677feff5ed-3a614f0b-1fa400-1627131a188313; cna=wT8uE4q3lhECAXZ0az8uGcYL; __last_loginid__=%E4%BB%98%E7%8C%AA%E5%BE%B7; last_mid=b2b-1839378211; hng=CN%7Czh-CN%7CCNY%7C156; lid=%E4%BB%98%E7%8C%AA%E5%BE%B7; ali_apache_track=c_mid=b2b-1839378211|c_lid=%E4%BB%98%E7%8C%AA%E5%BE%B7|c_ms=1; _cn_slid_=faNQ3gTey1; ali_ab=218.88.26.197.1525773293068.7; ali_beacon_id=218.88.26.197.1525773937972.710014.7; _uab_collina=152654884002745930298915; JSESSIONID=9L78lO0l1-GhXZ1OzZdQrAf9dqT9-t8CixtQ-oDe94; cookie2=16ddc1a30821550a15825fb47817e8e6; t=fda5943929603be20bb78ddb7f857fef; _tb_token_=7beee8197e7be; _umdata=0823A424438F76AB0D906A6B7CF2CA633BEAE0AA1526E4592B3622F5EB409468FF41029D23C5EC7CCD43AD3E795C914C1BC4DEB22555B7B57D7CC596D828FF2C; ali_apache_tracktmp=c_w_signed=Y; tbsnid=a6rIMybPxKbfjsezuQKj1%2Be7DbJN%2BS6QF528gS9gj5s6sOlEpJKl9g%3D%3D; _is_show_loginId_change_block_=b2b-1839378211_false; _show_force_unbind_div_=b2b-1839378211_false; _show_sys_unbind_div_=b2b-1839378211_false; _show_user_unbind_div_=b2b-1839378211_false; __rn_alert__=false; CNZZDATA1257121278=526688878-1525831332-https%253A%252F%252Fshop3388t654913l9.1688.com%252F%7C1528101517; ad_prefer=\"2018/06/04 17:06:08\"; h_keys=\"%u60e0%u5dde%u5e02%u76c8%u73c8%u670d%u9970%u6709%u9650%u516c%u53f8#%u6df1%u5733%u5e02%u9f99%u5c97%u533a%u8863%u6e58%u574a%u670d%u9970%u5546%u884c#%u5973%u88c5#%u6df1%u5733%u5e02%u5de8%u660a%u946b%u8d38%u6613%u6709%u9650%u516c%u53f8#%u6df1%u5733%u5e02%u5b9d%u5b89%u533a%u897f%u4e61%u552f%u7231%u8863%u670d%u88c5%u5382#%u6df1%u5733%u5e02%u5361%u95e8%u670d%u9970%u6709%u9650%u516c%u53f8#%u6df1%u5733%u5e02%u9f99%u5c97%u533a%u7f18%u83b1%u6052%u6f6e%u6d41%u670d%u9970%u5546%u884c#%u6fc2%u5ba0%ue5ca#%u6df1%u5733%u5e02%u653f%u9e3f%u5fae%u7535%u5b50%u6709%u9650%u516c%u53f8#%u7537%u88c5\"; alicnweb=touch_tb_at%3D1528103758701%7Clastlogonid%3D%25E4%25BB%2598%25E7%258C%25AA%25E5%25BE%25B7%7ChomeIdttS%3D53164068340257168870485693372392195909%7ChomeIdttSAction%3Dtrue%7Chp_newbuyerguide%3Dtrue%7Cshow_inter_tips%3Dfalse; cookie1=Vyh1zqk072gHvkZ5aTB5bn99eCovx4hr9YgGTQEeF9I%3D; cookie17=UonYuCff1bKy4g%3D%3D; sg=%E5%BE%B715; csg=fee18fdd; __cn_logon__=true; __cn_logon_id__=%E4%BB%98%E7%8C%AA%E5%BE%B7; LoginUmid=xLJwvClELt816qnTW0KkZdo5sLWk%2Ftj0aq4l%2BV0yj5Yyp8JBi5lr6w%3D%3D; unb=1839378211; cn_tmp=\"Z28mC+GqtZ1CLMKfEwL7u2ICZVpZdxHRmLcLU9/oAa6a5WC6V3phTq4t0hL4hddyUTqFuE25/OIAoVl0iVZTXNN5QIYGkA/9M4EDpHKyZ9Dcxefk3pjESWGrH4nCeNLQg9Ujw7kt+gez0jXO4yhcErmYkmTZ8spspIQ0XIXG4Bki9a/4/UVG0MCBxDgPNBvWrm6r5z80AbG48zQ737g4ljlPfx8ODmIrhObyRtIGp8r/MNdSbI2pug==\"; login=kFeyVBJLQQI%3D; userID=YuieXOHrx2M4%2BtXcIgqZqt20SUO8PSgz96JsiX9uRzM6sOlEpJKl9g%3D%3D; _nk_=1nOtq11N8pk%3D; userIDNum=ZxPKNZE3L1psO6kAAHeQyQ%3D%3D; _tmp_ck_0=jTA9N8%2B4RDUuHZ33VyF66fH8ELAIDdWw1zmFy8oLErXwhxPIe21PIfVOZNxMeuVX%2F1MlSerfAxs6cv%2Bze9lbf%2BRVlJwiQns864G%2BciO%2BA%2BRaJjtSVVtk9VpwuMXZxLmQ8oXxUKxW0KmRr3qXJ73o38ymqZiV6fER8zrUsxBbagOxFMqD3YwFbVJjMAqyx44mZmSiSD1d0LIPn3xl%2BXdG5tpr8NaSiX3WKKyJ9ZpvOgOrFeTHY0OxanQE0KuqVYWZkjN2ziRsRd7eNYU2bQ5K0%2BcMdiLHxBDYxd1V%2FBM0s8%2BPaFxm%2BrQOlNZP3MdqWVveG1vh3p6osuvnteEpOFERbPN7SCAM6RHNoYhhTLvRk7Uunx6RcuFs%2BlgqhrURWYGi6gC%2B9U%2B7DdkabOQeBpC85KKTdqqoCDKQsJ8ryXNrX%2BPswkGH8YJQBP30WLxm68fXy1TEO86WwUSE7hLJ%2FJ8IYUdrDC6n5ZF9kQ6cd3k422H7wzgL%2BRt3OzZ6SO3yYPGa6cF%2FEpoiPKmXe9Jjly4k05G3LUwJ7D5fDIvBMwMPCQ8%3D; _csrf_token=1528103814290; sec=5b150c8acd186b074b0c918a774646e3bc380ff7; isg=BNzcazzT6_q-SJ4P3nB_G574rfpO_YD5v4bWuLbdp0eqAXyL32VQD1KYZWn5ibjX"
                "cna=VI4VEv+m/UACAXWvgSDbMl9X; UM_distinctid=164471d4ee9118-01d1fdb2189c72-59462f1d-1fa400-164471d4eea3cf; h_keys=\"%u5973%u88c5\"; ali_apache_track=c_mid=b2b-1839378211|c_lid=%E4%BB%98%E7%8C%AA%E5%BE%B7|c_ms=1; _uab_collina=153032730847184698568307; last_mid=b2b-1839378211; __last_loginid__=%E4%BB%98%E7%8C%AA%E5%BE%B7; _cn_slid_=faNQ3gTey1; JSESSIONID=8L78atou1-AiXZFx028EP6IgjWwH-x54YQwQ-U2166; cookie2=3b6d78656c2b36815700b82cafcbc1cd; hng=CN%7Czh-CN%7CCNY%7C156; t=54ddad72928a4fa979b985166108da1f; _tb_token_=ee73be15ebd99; lid=%E4%BB%98%E7%8C%AA%E5%BE%B7; __cn_logon__=false; _tmp_ck_0=8JPXUMM5iCQiXKe8EvLNxDRhDZKjX%2FRS4NliCBE4o7v1TWlnltALC3vIY0%2BYjs1RpI8u5RY5UdnQfOu08pkIEkOwl9T8Kc0xAyAWau6oXCKWqt2QBCU6C6m7CrF%2FyqRo7gbGQ1ZKt2iiDgbyD%2BHpUr6syDK0q%2FUHUsfnEh4J%2FPk3pXyx2arpgEVujKULhtxznFgdv%2BEpjzz1eq7viBGQa78IhdZdGly4CKRrsUMo%2BG8t%2Fp5Wkml3VhSKSGNrzicQL%2FlIt6gvmpgv6cc4osms%2FR3igQuvQ%2FbiQIgb8e%2FLMYD3ax9fp8B%2BvXPqB%2FbTmq3kpMIEGr55%2BEAjn8doG4L%2FFRl8Bgfr6nWm8Kiwt%2FF56tZaAT0eopwBpBYu42WD3Of8; ad_prefer=\"2018/06/30 12:48:55\"; ali_ab=183.221.104.189.1530327152557.9; _csrf_token=1530334140375; alicnweb=touch_tb_at%3D1530353036475%7ChomeIdttS%3D15400825400402656305143168106873000371%7ChomeIdttSAction%3Dtrue%7Clastlogonid%3D%25E4%25BB%2598%25E7%258C%25AA%25E5%25BE%25B7; _umdata=0712F33290AB8A6D31D8021E7A2169E2E23F2E4F2C1E6CDAD5F6C68E50CF523BD82D98307C63C3BECD43AD3E795C914C38A3D9A8AF66FC6F88EDFC6F369935EE; sec=5b375c726cfe3ef1c792007744e03a4e9c1005a9; isg=BHJyqRs5fbqE5EGccN8yPfpYw7iUq3ZUprUMcjxL4iUQzxLJJZPGrXhOuytWv-41; CNZZDATA1257121278=1815443264-1530198675-https%253A%252F%252Fshop8s1463487d150.1688.com%252F%7C1530350774"
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
}
