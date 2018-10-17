package com.newdream.api.utils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by qiuwei on 2017/7/28.
 */
public class AssertTest {

    //assertThat(实际结果).as(检查描述).isEqualTo(预期结果)

    @Test
    public void test(){

        //SoftAssertions soft = new SoftAssertions();
        //assertThat(1).as("数字").isEqualTo(12);
        //System.out.println("检查qiuqiu的颜色" );
        //soft.assertThat("qiuqiu").as("检查%s的颜色","qiuqiu").isEqualTo("qiuqiu");
        //soft.assertThat("jack").as("检查%s的颜色","jack").isEqualTo("sdfsdf");
        //soft.assertAll();
        //String json = "\n" +
        //        "{\"errcode\":40013,\"errmsg\":\"invalid appid\"}";
        //JSONObject jsonObject = JSON.parseObject(json);
        //System.out.println(jsonObject);
        //assertThat(jsonObject).as("json中是否包含").extracting("errmsg").isEqualTo("appid");

        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(0,new BasicNameValuePair("errcode","40013"));
        list.add(1,new BasicNameValuePair("errmsg","invalid appid"));
        System.out.println(list);
//        assertThat(list).as("list中包含").contains(new BasicNameValuePair("errcode","40013"));
    }
}
