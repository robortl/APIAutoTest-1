package com.newdream.api.base;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.naming.Name;
import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RequestUtil {

    /*
          1、创建一个httpclient对象
          2、创建请求方式
          3、创建实体并放入数据
          4、将实体放入请求方式
          5、执行请求
          6、获取响应，获取响应正文，并转换成String
      */

    public static String get(String url) {
        //创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建string字符串用于接收response
        String stringResponse = null;
        //创建get请求对象
        HttpGet get = new HttpGet(url);
        try {
            //执行请求
            CloseableHttpResponse response = httpClient.execute(get);
            //获取响应正文，并转换成string
            if (response != null) {
                if (response.getEntity().getContentLength() == 0) {
                    System.out.println("响应正文为空");
                    response.close();
                    return null;
                }
                //获取状态码
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    stringResponse = EntityUtils.toString(response.getEntity());
                    System.out.println("响应正文为：" + stringResponse);
                    response.close();
                } else {
                    System.out.println("请求失败，响应内容为" + response.toString());
                    response.close();
                    return null;
                }
            } else {
                System.out.println("响应的response为空");
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringResponse;
    }

    public static String get(String url, List<NameValuePair> params) {
        url = url + "?";
        for (int i = 0; i < params.size(); i++) {
           url = url + params.get(i) + "&";
        }
        return get(url);
    }

    public static void main(String[] args) {
        //RequestUtil.get("http://www.baidu.com");
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("grant_type" ,"client_credential"));
        params.add(new BasicNameValuePair("appid" ,"wx17b22d8fa02d4c00"));
        params.add(new BasicNameValuePair("secret" ,"71003eb6deda64b9722bdeec6c0c7364"));
        RequestUtil.get("https://api.weixin.qq.com/cgi-bin/token",params);
    }
}
