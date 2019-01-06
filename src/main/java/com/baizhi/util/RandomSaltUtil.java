/**
 *
 */
package com.baizhi.util;

import java.util.Random;

public class RandomSaltUtil {
    /**
     *
     * 方法作用：生成四个字符时间戳
     */
    public static String generetRandomSaltCode() {
        //字符串转char数组
        char[] str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            //随机生成0到str长度之间的数字做为下标
            int randomIndex = random.nextInt(str.length);
            //追加到sb对象
            sb.append(str[randomIndex]);
        }
        return sb.toString();
    }
}
