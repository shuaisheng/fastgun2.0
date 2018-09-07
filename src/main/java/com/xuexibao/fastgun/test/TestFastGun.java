package com.xuexibao.fastgun.test;

import com.xuexibao.fastgun.utils.HttpUtil;

import java.util.Objects;

/**
 * @Description:测试类
 * @Author: zyb
 * @Date: 2018/9/7 21:31
 */
public class TestFastGun {

    public static void main(String[] args) {
        //String a=HttpUtil.get("https://webapi.91xuexibao.com/user/api/verifyCode?mobile=13589085255");
        Object object=new Object();
        String b=HttpUtil.postJson("http://localhost:8762/testpost",object);
        System.out.println(b);
    }
}
