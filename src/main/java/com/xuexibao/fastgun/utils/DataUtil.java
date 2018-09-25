package com.xuexibao.fastgun.utils;

import java.math.BigDecimal;
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

    /**
     * 传入毫秒数转为日期格式2018-08-06
     * @param d
     * @return
     */
    public static  String convertTime(Double d){
        if(d==null){
            return null;
        }
        /*BigDecimal bd1 = new BigDecimal(d);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String time=new Date(Long.parseLong(bd1.toPlainString())).toLocaleString();*/
        //String time=new Date(Long.parseLong(bd1.toPlainString())).toLocaleString().split(" ")[0];
        BigDecimal bd1 = new BigDecimal(d);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date time=new Date(Long.parseLong(bd1.toPlainString()));
        String result=simpleDateFormat.format(time);
        return result;
    }



}
