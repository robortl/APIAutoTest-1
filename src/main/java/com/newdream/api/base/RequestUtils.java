package com.newdream.api.base;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

import com.alibaba.fastjson.JSONObject;
import com.newdream.api.utils.DataUtils;
import com.newdream.api.utils.ReportUtil;
import com.newdream.api.utils.ReportUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.Nullable;
import org.testng.Reporter;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by qiuwei on 2017/3/30.
 */
public class RequestUtils {
    /*
      定义规范：
           1、请求方法的返回值 统一使用String类型
           2、数据处理 统一使用String类型
    */

    private static ReportUtils report = new ReportUtils();

    /**
     * TODO post(json)请求
     *
     * @param url        接口请求地址
     * @param jsonObject post json数据
     * @return
     * @author qiuwei
     * @dateTime 2017/7/21 22:18
     */
    public static String post(String url, JSONObject jsonObject, CookieStore... cookieStore) {
        //声明HttpClient对象
        CloseableHttpClient httpClient;
        //声明response,返回值stringResponse
        String stringResponse = null;

        //判断是否有cookie，有则在请求时载入cookie，没有则直接请求
        if (cookieStore.length == 0) {
            httpClient = HttpClients.createDefault();
        } else {
            report.greenLight("cookie为 【" + cookieStore[0].getCookies() + "】");
            httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore[0]).build();
        }

        //声明post请求
        HttpPost post = new HttpPost(url);


        //判断上传json数据是否为空，为空则不做请求，直接返回null
        if (jsonObject != null) {

            //将请求数据放入实体，并设置请求的消息报头信息
            StringEntity entity = new StringEntity(jsonObject.toString(), "utf-8");
            entity.setContentEncoding("utf-8");
            entity.setContentType("application/json");
            post.setEntity(entity);

            try {
                report.log("开始执行post请求" + url);
                //执行请求,获取响应
                CloseableHttpResponse response = httpClient.execute(post);

                //将response转换成string
                stringResponse = getStringResponse(response);

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringResponse;
    }

    /**
     * TODO post(list)请求
     *
     * @param url    接口请求地址
     * @param params 接口请求参数(list)
     * @return
     * @author qiuwei
     * @dateTime 2017/7/21 22:20
     */
    public static String post(String url, List<NameValuePair> params, CookieStore... cookieStore) {
        //声明HttpClient对象
        CloseableHttpClient httpClient;
        //声明response,返回值stringResponse
        String stringResponse = null;

        //判断是否有cookie，有则在请求时载入cookie，没有则直接请求
        if (cookieStore.length == 0) {
            httpClient = HttpClients.createDefault();
        } else {
            httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore[0]).build();
        }

        //声明post请求
        HttpPost post = new HttpPost(url);
        //声明表单实体
        UrlEncodedFormEntity uefEntity;

        //判断参数是否为空,若为空，则返回null
        if (params != null) {
            try {
                //将请求数据放入实体，并设置请求的消息报头信息
                uefEntity = new UrlEncodedFormEntity(params, "utf-8");
                uefEntity.setContentEncoding("utf-8");
                post.setEntity(uefEntity);

                report.log("开始执行post请求" + url);

                //执行请求
                CloseableHttpResponse response = httpClient.execute(post);
                //将response转换成string
                stringResponse = getStringResponse(response);

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("post数据为空，不做请求");
            return null;
        }

        return stringResponse;
    }

    /**
     * TODO post(file)请求
     *
     * @param url  接口地址
     * @param file 上传文件
     * @return
     * @author qiuwei
     * @dateTime 2017/7/21 22:24
     */
    public static String post(String url, File file, CookieStore... cookieStore) {
        //声明HttpClient对象
        CloseableHttpClient httpclient;

        //判断是否有cookie，有则在请求时载入cookie，没有则直接请求
        if (cookieStore.length == 0) {
            httpclient = HttpClients.createDefault();
        } else {
            httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore[0]).build();
        }

        //声明response,返回值stringResponse
        String stringResponse = null;

        try {
            HttpPost httpPost = new HttpPost(url);

            //将文件放入实体
            MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
            //file  -->  MultipartEntityBuilder  -->  MultipartEntity
            entityBuilder.addBinaryBody("file", file);
            httpPost.setEntity(entityBuilder.build());

            report.log("开始执行post请求" + url);
            //执行请求
            CloseableHttpResponse response = httpclient.execute(httpPost);
            //将response转换成string
            stringResponse = getStringResponse(response);

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringResponse;
    }

    /**
     * TODO get(list)请求
     *
     * @param url    接口请求地址
     * @param params 请求参数
     * @return
     * @author qiuwei
     * @dateTime 2017/7/21 22:27
     */
    public static String get(String url, List<NameValuePair>... params) {

        //声明返回的stringResponse
        String stringResponse ;

        //判断参数是否空,为空则直接执行get请求
        if (params.length == 0) {
            stringResponse = get(url);
        } else {
            //如果有参数则将参数跟url拼接
            url = url + "?";
            for (int i = 0; i < params[0].size(); i++) {
                url = url + params[0].get(i) + "&";
            }
            stringResponse = get(url);
        }
        return stringResponse;
    }

    /**
     * TODO 私有方法-get请求
     *
     * @param url 参数地址
     * @return
     * @author qiuwei
     * @dateTime 2017/7/21 22:30
     */
    private static String get(String url) {
        //声明HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        String stringResponse = null;
        try {
            // 创建HttpGet
            HttpGet doGet = new HttpGet(url);

            report.log("开始执行get请求" + url);
            //执行请求，获取响应
            CloseableHttpResponse response = httpClient.execute(doGet);

            //将response转换成string
            stringResponse = getStringResponse(response);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接，释放资源
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringResponse;
    }

    /**
     * TODO put请求
     *
     * @param url    参数地址
     * @param params 请求参数
     * @return
     * @author qiuwei
     * @dateTime 2017/7/21 22:32
     */
    public static String put(String url, List<NameValuePair> params, CookieStore... cookieStore) {
        //声明HttpClient对象
        CloseableHttpClient httpClient;
        //声明返回值strinResponse
        String stringResponse = null;

        //判断是否有cookie，有则在请求时载入cookie，没有则直接请求
        if (cookieStore.length == 0) {
            httpClient = HttpClients.createDefault();
        } else {
            httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore[0]).build();
        }

        try {
            //声明请求方法
            HttpPut put = new HttpPut(url);

            report.log("开始执行get请求" + url);

            //将请求的参数放入表单实体
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params, "utf-8");
            formEntity.setContentType("utf-8");
            formEntity.setContentType("*/*");
            put.setEntity(formEntity);

            //执行请求
            CloseableHttpResponse response = httpClient.execute(put);

            //将response转换成string
            stringResponse = getStringResponse(response);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringResponse;
    }


    /**
     * TODO 获取并设置cookie
     *
     * @param
     * @return
     * @author qiuwei
     * @dateTime 2017/7/21 23:57
     */
    private static CookieStore setCookieStore(CloseableHttpResponse httpResponse, String cookieKey, String path) {
        CookieStore cookieStore = new BasicCookieStore();
        String setCookie = httpResponse.getFirstHeader("Set-Cookie").getValue();
        String cookieValue = setCookie.substring((cookieKey + "=").length(), setCookie.indexOf(";"));
        report.paraLight(cookieKey + "为" + cookieValue);
        // 新建一个Cookie
        BasicClientCookie cookie = new BasicClientCookie(cookieKey, cookieValue);
        cookie.setVersion(0);
        cookie.setDomain(ApiConfig.HTTPHOST);
        cookie.setPath(path);
        cookieStore.addCookie(cookie);
        return cookieStore;
    }

    @Nullable
    private static String getStringResponse(CloseableHttpResponse response) throws IOException {
        String stringResponse = null;

        if (response != null) {
            if (response.getEntity().getContentLength() == 0) {
                report.error("返回的response为空");
                return null;
            } else {
                // 获取状态码
                int statuCode = response.getStatusLine().getStatusCode();
                // 若状态码不为200，则关闭response，若为200，则获取返回信息
                if (statuCode != 200) {
                    report.error("访问失败，返回的状态码为：【" + statuCode + response.getStatusLine() + "】");
                    response.close();
                } else {
                    stringResponse = EntityUtils.toString(response.getEntity());
                    response.close();
                    report.log("响应内容为：【" + stringResponse + "】");
                }
            }
        } else {
            report.log("response为空");
            return null;
        }
        return stringResponse;

    }


//    public static void main(String[] args) {
//        RequestUtils.get("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET");
//        List<NameValuePair> params = new ArrayList<NameValuePair>();
//        NameValuePair grantType = new BasicNameValuePair("grant_type", "client_credential");
//        NameValuePair appid = new BasicNameValuePair("appid", "wx17b22d8fa02d4c00");
//        NameValuePair secret = new BasicNameValuePair("secret", "71003eb6deda64b9722bdeec6c0c7364");
//        params.add(grantType);
//        params.add(appid);
//        params.add(secret);
//        String result = RequestUtils.get("https://api.weixin.qq.com/cgi-bin/token", params);
//        String token = DataUtils.JSONParse(result, "$.access_token");
//    }

}

