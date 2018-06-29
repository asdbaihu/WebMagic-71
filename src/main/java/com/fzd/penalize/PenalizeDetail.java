package com.fzd.penalize;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

/**
 * Created by SRKJ on 2017/9/6.
 */
public class PenalizeDetail {
    public static void main(String... args) throws IOException {
        final String CHART_SET = "UTF-8";
        File file = new File("C:\\Users\\SRKJ\\Desktop\\test.txt");
        Document doc = Jsoup.parse(file,CHART_SET,"http://www.mep.gov.cn");
        Elements firstTds = doc.select("[width=50%]");
        Elements secondTds = doc.select("[width=50%] + td");
        Elements secondSpans = secondTds.select("span");
        System.out.println("索引号: " + firstTds.get(0).childNode(1).toString().replaceAll("&nbsp;",""));
        System.out.println("发布机关: " + firstTds.get(1).childNode(3).childNode(0));
        System.out.println("文号: " + firstTds.get(2).childNode(3).childNode(0));
        System.out.println("分类: " + secondSpans.get(0).text());
        System.out.println("生成日期: " + secondSpans.get(1).text());
        System.out.println("主题词: " + secondSpans.get(2).text());
        System.out.println("公司名称: " + doc.select(".content").select("p").get(0).childNode(0).toString().replaceAll("：","").replaceAll(":","").trim());
        for (Element item : firstTds) {
            print(" * a:  (%s)", item.outerHtml());
        }
        for (Element item : secondTds) {
            print(" * a:  (%s)", item.outerHtml());
        }
        System.out.println(doc.select(".Custom_UnionStyle").text());
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
}
