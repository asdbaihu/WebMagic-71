package com.fzd.local;

import org.apache.log4j.Logger;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;

import java.util.List;

/**
 * Created by SRKJ on 2017/7/13.
 */
public class LocalProcessor implements PageProcessor{
    private Site site = Site.me().setRetrySleepTime(1000).setRetryTimes(3);
    private static final Logger logger = Logger.getLogger("LocalProcessor");
    @Override
    public void process(Page page) {
        String str = new JsonPathSelector("$").select(page.getRawText());
        logger.info(str);
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String... args){
        Spider.create(new LocalProcessor()).addUrl("http://localhost:9200/").thread(5).run();
    }
}
