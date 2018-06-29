package com.fzd.concurrency;

import com.fzd.ali.Crawl1688;

/**
 * Created by FZD on 2018/6/29.
 * Description:
 */
public class TestCrawl extends Thread{

    private Crawl1688 crawl1688 = new Crawl1688();
    @Override
    public void run() {
        super.run();
        crawl1688.crawlCredit("https://shop619r453d33008.1688.com/page/creditdetail_remark.htm", "b2b-389764901606e59");
        crawl1688.crawlCompanyBase("https://shop619r453d33008.1688.com/page/creditdetail.htm", "b2b-389764901606e59");
    }
}
