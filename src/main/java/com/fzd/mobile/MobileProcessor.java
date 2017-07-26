package com.fzd.mobile;

import org.apache.log4j.Logger;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by SRKJ on 2017/7/13.
 */
public class MobileProcessor implements PageProcessor{

    private Site site = Site.me().setRetrySleepTime(1000).setRetryTimes(3);
    private static final Logger logger = Logger.getLogger("MobileProcesser");
    @Override
    public void process(Page page) {
        logger.info(page);
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String... args){
        Spider.create(new MobileProcessor()).addUrl("http://www.sc.10086.cn/login.html?url=my/SC_MY_INDEX.html").thread(5).run();
    }
}
