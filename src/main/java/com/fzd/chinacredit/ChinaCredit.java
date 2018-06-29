package com.fzd.chinacredit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

/**
 * Created by SRKJ on 2017/9/11.
 */
public class ChinaCredit {
    public static void main(String... args) throws IOException {
        final String CHART_SET = "UTF-8";
        File file = new File("C:\\Users\\SRKJ\\Desktop\\chinacredit.txt");
        Document doc = Jsoup.parse(file,CHART_SET,"http://www.creditchina.gov.cn");
        System.out.println("名称: " + doc.select(".page-channel-header > h1").text());
        Elements list = doc.select(".creditsearch-tagsinfo");
        String baseInfo = (list.get(0).select(".creditsearch-tagsinfo-ul").text());
        System.out.println("工商注册号: " + formatStr(baseInfo));
        System.out.println("状态: " + formatStr(baseInfo));

    }
    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }

    private static String formatStr(String str){
        StringBuffer stringBuffer = new StringBuffer(str.replaceAll(" ","\",\"").replaceAll("：","\":\""));
        stringBuffer.insert(0,"{\"");
        stringBuffer.insert(stringBuffer.length()-1,"\"}");
        return stringBuffer.toString();
    }
}
