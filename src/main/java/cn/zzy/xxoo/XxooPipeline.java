package cn.zzy.xxoo;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.selector.Selectable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Fairyland on 2017/5/26.
 */
public class XxooPipeline implements Pipeline {

    private int likeNum;
    private int end;
    private int start = 0;

    XxooPipeline(int likeNum,int end){
        this.likeNum = likeNum;
        this.end = end;
    }

    static{
        File xxoo = new File("c:/xxoo");
        if (!xxoo.exists()) {
            xxoo.mkdir();
        }
    }


    public void process(ResultItems resultItems, Task task) {

        List<Selectable> lis = resultItems.get("li");
        for(Selectable li : lis){
            String like = li.xpath("//div[@class='jandan-vote']/span[1]/text()").toString();
            int likes = Integer.parseInt(like);
            String url = li.xpath("//a[@class='view_img_link']/@href").toString();
            if(start < end){
                if(likeNum<=likes){
                    System.out.println("第"+start+"张"+"__"+like+":"+url);
                    start++;
                    downImg(url,like);
                    System.out.println("下载完成");
                }

            }
        }
        return;
    }


    public void downImg(String url,String like){
        FileOutputStream fos = null;
        InputStream in = null;
        try {
            URL imgURL = new URL(url);
            in = imgURL.openStream();
            String name = like +"_"+ url.substring(url.lastIndexOf("/")+1);
            fos = new FileOutputStream("c:"+File.separator+"xxoo"+File.separator+name);
            byte[] imgByte = new byte[1024];
            int d = -1;

            while((d = in.read(imgByte)) != -1){
                fos.write(imgByte,0,d);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
