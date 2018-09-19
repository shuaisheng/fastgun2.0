package com.xuexibao.fastgun.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtil {


    /**
     * 将字符串时间戳 转换为日期
     * 支持 毫秒和秒的时间戳
     * yyyy-MM-dd HH:mm:ss
     * @param timeStamp
     * @param format
     * @return
     */
    public static String timeStamp2Date(String timeStamp,String format) {

        if(timeStamp == null || timeStamp.isEmpty() || timeStamp.equals("null")){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Long time = Long.valueOf(timeStamp);
        if (timeStamp.length()<13) {
            time = Long.valueOf(timeStamp+"000");
        }

        return sdf.format(new Date(time));
    }


    /**
     * 将字符串时间戳 转换为日期
     * 支持 毫秒和秒的时间戳
     * yyyy-MM-dd HH:mm:ss
     * @param timeStamp
     * @param format
     * @return
     */
    public static String timeStamp2Date(Long timeStamp,String format) {

        SimpleDateFormat sdf = new SimpleDateFormat(format);

        if ((timeStamp+"").length()<13) {
         timeStamp = Long.valueOf(timeStamp+"000");
        }

        return sdf.format(new Date(timeStamp));
    }

}
