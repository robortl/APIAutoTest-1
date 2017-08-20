package com.newdream.api.weixinTest;

import com.newdream.api.utils.CheckPoint;
import com.newdream.api.utils.DataUtils;
import com.newdream.api.utils.ExcelIterator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.newdream.api.base.*;
import org.testng.asserts.Assertion;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by qiuwei on 2017/7/22.
 */
public class Test_getToken {
    Assertion assertion = new Assertion();

    @DataProvider(name = "dp")
    public Iterator<Object[]> getData() throws IOException {
        return new ExcelIterator("Test_Data/weixinTestData/getToken");
    }


    @Test(dataProvider = "dp" , enabled = false)
    public void getTokenTest(Map<String, String> map) {
        String stringRepsonse = RequestUtils.get(map.get("url"), DataUtils.map2List(map));
        //System.out.println(stringRepsonse);
        //Reporter.log(stringRepsonse);
        if (stringRepsonse != null) {

            if (stringRepsonse.contains("access_token")) {
                String exprise_in = DataUtils.JSONParse(stringRepsonse, "$.expires_in");
                //Assert.assertEquals(exprise_in,map.get("expected_result"));
                CheckPoint.checkString(exprise_in, map.get("expected_result"));
            } else {
                String errcode = DataUtils.JSONParse(stringRepsonse, "$.errcode");
                CheckPoint.checkString(errcode, map.get("expected_result"));

                //Assert.assertEquals(errcode,map.get("expected_result"));
            }
        } else {
            CheckPoint.checkString("", map.get("expected_result"));
            //Assert.assertEquals("",map.get("expected_result"));
        }
    }

    @Test(dataProvider = "dp")
    public void test(Map<String,String> map){
        String stringResponse = RequestUtils.get(map.get("url"),DataUtils.map2List(map));
        CheckPoint.assertEquals(stringResponse,map.get("jsonpathPoint"));
    }
}
