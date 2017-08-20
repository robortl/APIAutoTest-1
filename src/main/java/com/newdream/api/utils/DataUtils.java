package com.newdream.api.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.testng.Reporter;

import java.io.IOException;
import java.util.*;

/**
 * Created by qiuwei on 2017/7/21.
 */
public class DataUtils {

    static ReportUtils report = new ReportUtils();

    /**
     * TODO 判断用例是否需要运行
     *
     * @param data 从Excel中读取的数据
     * @return
     * @author qiuwei
     * @dateTime 2017/7/21 21:20
     */
    public static boolean isRun(Map<String, Object> data) {
        return data.get("ISRUN") == null ||
                data.get("ISRUN").toString().contains("1");
    }

    /**
     * TODO 判断用例是否需要检查
     *
     * @param data 从Excel中读取的数据
     * @return
     * @author qiuwei
     * @dateTime 2017/7/21 21:21
     */
    public static boolean isCheck(Map<String, Object> data) {
        return data.get("ISCHECK") == null ||
                data.get("ISCHECK").toString().contains("1");
    }

    /**
     * TODO map(接口参数)转换成List
     *
     * @param data 从Excel中读取的数据
     * @return
     * @author qiuwei
     * @dateTime 2017/7/21 21:22
     */

    public static List<NameValuePair> map2List(Map<String, String> data) {
        //声明接收的list
        List<NameValuePair> list = new ArrayList<NameValuePair>();

        //判空
        if (data.equals("")) {
            report.log("源数据为空,不进行转换");
            return null;
        } else {
            //遍历data的key,获取value值，并依次加入到list
            for (String key : data.keySet()) {
                if (key.contains("params_")) {
                    list.add(new BasicNameValuePair(key.substring(7), data.get(key)));
                }
            }
            report.paraLight("请求参数为：【" + list + "】");
            //Reporter.log("请求参数为：【" + list.toString() + "】");
            return list;
        }
    }

    /**
     * TODO map(接口参数)转换成JSONObject
     *
     * @param data 从Excel中获取的接口参数数据
     * @return
     * @author qiuwei
     * @dateTime 2017/7/21 22:12
     */
    public static JSONObject map2Json(Map<String, String> data) {
        if (data == null || data.equals("")) {
            report.log("数据源为空");
            return null;
        }
        Map<String, String> map = new HashMap<String, String>();
        for (String key : data.keySet()) {
            if (key.contains("params_")) {
                String value = data.get(key);
                map.put(key.substring(7), value);
            }
        }
        JSONObject jsonObject = string2Json(JSON.toJSONString(map));
        report.paraLight("请求的参数为：【" + jsonObject + "】");
        return jsonObject;
    }


    /**
     * TODO 解析Json响应内容
     *
     * @param response 响应内容
     * @param path     jsonPath表达式
     * @return String 解析出来的字符串
     * @author qiuwei
     * @dateTime 2017/7/21 21:38
     */
    public static String JSONParse(String response, String path) {
        return String.valueOf(JSONParse(string2Json(response), path));
    }

    public static int JSONParseInt(String response,String path){
        return Integer.parseInt(JSONParse(response,path));
    }

    public static float JSONParseFloat(String response,String path){
        return Float.valueOf(JSONParse(response,path));
    }

    public static double JSONParseDouble(String response,String path){
        return Double.valueOf(JSONParse(response,path));
    }

    public static boolean JSONParseBoolean(String response,String path){
        return Boolean.valueOf(JSONParse(response,path));
    }

    /**
     * TODO 私有方法-字符串转换为json
     *
     * @param string 响应内容字符串
     * @return
     * @author qiuwei
     * @dateTime 2017/7/21 21:41
     */
    private static JSONObject string2Json(String string) {
        if (string == null || string.equals("")) {
            report.log("传入的参数为空,不进行转换");
            return null;
        } else {
            JSONObject value = JSONObject.parseObject(string);
            //report.highLight("解析的内容为：【" + value + "】");
            return value;
        }
    }

    /**
     * TODO 私有方法-json数据解析
     *
     * @param jsonObject json数据
     * @param path       jsonPath表达式
     * @return
     * @author qiuwei
     * @dateTime 2017/7/21 21:42
     */
    private static Object JSONParse(JSONObject jsonObject, String path) {
        if (path == null || path.equals("")) {
            report.log("path为空，返回原来的json数据");
            //Reporter.log("path为空，返回原来的json数据");
            return jsonObject.toString();
        } else if (jsonObject == null) {
            report.log("传入的参数为空,不进行解析");
            //Reporter.log("传入的参数为空,不进行解析");
            return null;
        } else {
            Object value = JSONPath.eval(jsonObject, path);
            report.highLight("【"+path+"】"+"解析的内容为：【" + value + "】");
            //Reporter.log("解析的内容为：【" + value + "】");
            return value;

        }
    }

    /**
     * TODO 解析Html响应内容
     *
     * @param html html字符串
     * @param path jsoup解析表达式
     * @param key  需要解析的属性(可选)
     * @return
     * @author qiuwei
     * @dateTime 2017/7/21 21:43
     */
    public static String HtmlParse(String html, String path, String... key) {
        if (key.length == 0) {
            return HtmlParseText(string2HTML(html), path);
        } else {
            return HtmlParseAttr(string2HTML(html), path, key[0]);
        }
    }

    /**
     * TODO 获取接口信息
     *
     * @param url 接口地址
     * @return
     * @author qiuwei
     * @dateTime 2017/7/21 21:45
     */
    public static Document url2HTML(String url) throws IOException {
        if (url == null || url.equals("")) {
            report.log("请求的url为空，不进行请求");
            return null;
        } else {
            return Jsoup.connect(url).get();
        }
    }

    /**
     * TODO 私有方法-字符串转换成网页Document
     *
     * @param html 网页字符串
     * @return
     * @author qiuwei
     * @dateTime 2017/7/21 21:46
     */
    private static Document string2HTML(String html) {
        if (html == null || html.equals("")) {
            report.log("转换的html为空，不进行转换");
            return null;
        } else {
            return Jsoup.parse(html);
        }
    }

    /**
     * TODO 私有方法-解析网页的属性值
     *
     * @param document html
     * @param path     jsoup表达式
     * @param key      属性名
     * @return
     * @author qiuwei
     * @dateTime 2017/7/21 21:46
     */
    private static String HtmlParseAttr(Document document, String path, String key) {
        if (path == null || path.equals("")) {
            report.log("解析路径为空,返回原来的html");
            return document.toString();
        } else {
            String value = document.select(path).attr(key);
            report.highLight("【"+path+"】"+"解析的【"+key+"】的值为：【" + value + "】");
            return document.select(path).attr(key);
        }
    }

    /**
     * TODO 私有方法-解析网页的文本内容
     *
     * @param document 网页Html
     * @param path     jsoup表达式
     * @return
     * @author qiuwei
     * @dateTime 2017/7/21 21:47
     */
    private static String HtmlParseText(Document document, String path) {
        if (path == null || path.equals("")) {
            report.log("解析路径为空,返回原来的html");
            return document.toString();
        } else {
            String value = document.select(path).text();
            report.highLight("【"+path+"】"+"解析的内容为：【" + value + "】");
            return value;
        }
    }
}
