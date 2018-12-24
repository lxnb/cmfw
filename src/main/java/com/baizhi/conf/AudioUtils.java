package com.baizhi.conf;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;

import java.io.File;

public class AudioUtils {
    public static String getDuration(File files) {
        //计算音频时长
        Encoder encoder = new Encoder();
        long s = 0;
        String istime;
        MultimediaInfo m;
        try {
            //获得文件信息
            m = encoder.getInfo(files);
            //获取音频长度
            s = m.getDuration();
            istime = s / 60000 + "分" + (s / 1000 - s / 60000 * 60) + "秒";
            System.out.println("此视频时长为:" + s / 60000 + "分" + (s / 1000 - s / 60000 * 60) + "秒");
            return istime;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }
}
