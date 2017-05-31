package cn.zzy.xxoo;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.Scanner;

/**
 * Created by Fairyland on 2017/5/26.
 */
public class XxooPageProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    public void process(Page page) {
        page.addTargetRequest(page.getHtml().xpath("//a[@class='previous-comment-page']/@href").toString());
        page.putField("li",page.getHtml().xpath("ol[@class='commentlist']/li").nodes());
    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Scanner sca = new Scanner(System.in);
        System.out.println("请输入点赞数");
        int like = sca.nextInt();
        System.out.println("请输入爬取数");
        int end = sca.nextInt();
        sca.close();

        Spider.create(new XxooPageProcessor())
                //从"http://jandan.net/ooxx"开始抓
                .addUrl("http://jandan.net/ooxx")
                .addPipeline(new XxooPipeline(like,end))
                //开启5个线程抓取
                .thread(5)
                //启动爬虫
                .run();
    }
}

