package com.newdream.api.utils;

import org.apache.log4j.Logger;
import org.testng.Assert;

public class AssertPoint {
    private static ReportUtils report = new ReportUtils();
    private static Logger logger = Logger.getLogger(CheckPoint.class.getName());


    /**
     * @return
     * @TODO：Check里的专用打印格式模板
     * @author 作者 邱卫武:
     * @parameter @param comm 备注信息
     * @parameter @param actual 实际结果
     * @parameter @param expected 预期结果
     * @parameter @param result 结果
     */
    private static void ptFormat(String comm, String actual, String expected, String result) {
        String ptmsg;
        ptmsg = "┌────────┤ＣＨＥＣＫ　ＰＯＩＮＴ├────────┐<br>";
        ptmsg = ptmsg + "[目标]：" + comm + "<br>";
        ptmsg = ptmsg + "[实际]：" + actual + "　[预期]：" + expected + "<br>";
        if (result.contains("PASS")) {
            ptmsg = ptmsg + "└─────────────────────────────┘";
            ptmsg = "<P style=\"font-size:1em;color:#1C9340;\"><b>" + ptmsg + "</b></P>";
            report.log(ptmsg);
            logger.info(ptmsg);
        } else {
            ptmsg = ptmsg + "└─────────────────────────────┘<br />";
            ptmsg = "<P style=\"font-size:1em;color:#ED1C24;\"><b>" + ptmsg + "</b></P>";
            report.log(ptmsg);
            logger.info("【检查点检查出错误】");
        }
    }

    private static void assertEquals(Object actual, Object expected, String comment) {
        try {
            Assert.assertEquals(actual, expected);
            ptFormat(comment, actual + "", expected + "", "PASS");
        } catch (AssertionError e) {
            ptFormat(comment, actual + "", expected + "", "FAIL");
            Assert.fail("检查点检查出错误");
        }
    }

    private static void assertNotEquals(Object actual, Object expected, String comment) {
        try {
            Assert.assertNotEquals(actual, expected);
            ptFormat(comment, actual + "", expected + "", "PASS");
        } catch (AssertionError e) {
            ptFormat(comment, actual + "", expected + "", "FAIL");
            Assert.fail("检查点检查出错误");
        }
    }

    public static void assertEquals(String stringResponse, String jsonPathPoint, String... comment) {

        String comm;
        if (comment.length == 0) {
            comm = "检查实际结果是否为真";
        } else {
            comm = comment[0];
        }

        String jsonpath = null;
        String expected_result = null;
        if (jsonPathPoint == null) {
            report.log("Jsonpath检查点为空，不进行断言");
        } else {
            String[] jsonValue = jsonPathPoint.split("==");
            jsonpath = jsonValue[0];
            expected_result = jsonValue[1];
        }

        String actual = DataUtils.JSONParse(stringResponse, jsonpath);

        assertEquals(actual, expected_result, comm);
    }

    public static void assertNotEquals(String stringResponse, String jsonPathPoint, String... comment) {
        String comm;
        if (comment.length == 0) {
            comm = "检查实际结果是否为真";
        } else {
            comm = comment[0];
        }

        String jsonpath = null;
        String expected_result = null;
        if (jsonPathPoint == null) {
            report.log("Jsonpath检查点为空，不进行断言");
        } else {
            String[] jsonValue = jsonPathPoint.split("!==");
            jsonpath = jsonValue[0];
            expected_result = jsonValue[1];
        }

        String actual = DataUtils.JSONParse(stringResponse, jsonpath);
        assertNotEquals(actual, expected_result, comm);
    }

    public static void assertContains(String stringResponse, String jsonPathPoint, String... comment) {
        String comm;
        if (comment.length == 0) {
            comm = "检查实际结果是否为真";
        } else {
            comm = comment[0];
        }

        String jsonpath = null;
        String expected_result = null;
        if (jsonPathPoint == null) {
            report.log("Jsonpath检查点为空，不进行断言");
        } else {
            String[] jsonValue = jsonPathPoint.split("=");
            jsonpath = jsonValue[0];
            expected_result = jsonValue[1];
        }
        String actual = DataUtils.JSONParse(stringResponse, jsonpath);

        try {
            Assert.assertEquals(true, actual.contains(expected_result));
            ptFormat(comm, actual + "", expected_result + "", "PASS");
        } catch (AssertionError e) {
            ptFormat(comm, actual + "", expected_result + "", "FAIL");
            Assert.fail("检查点检查出错误");
        }

    }

    public static void assertNotContains(String stringResponse, String jsonPathPoint, String... comment) {
        String comm;
        if (comment.length == 0) {
            comm = "检查实际结果是否为真";
        } else {
            comm = comment[0];
        }

        String jsonpath = null;
        String expected_result = null;
        if (jsonPathPoint == null) {
            report.log("Jsonpath检查点为空，不进行断言");
        } else {
            String[] jsonValue = jsonPathPoint.split("!=");
            jsonpath = jsonValue[0];
            expected_result = jsonValue[1];
        }
        String actual = DataUtils.JSONParse(stringResponse, jsonpath);

        try {
            Assert.assertEquals(false, actual.contains(expected_result));
            ptFormat(comm, actual + "", expected_result + "", "PASS");
        } catch (AssertionError e) {
            ptFormat(comm, actual + "", expected_result + "", "FAIL");
            Assert.fail("检查点检查出错误");
        }
    }

    public static void assertIntNum(String stringResponse, String jsonPathPoint, String... comment){
        String comm;
        if (comment.length == 0) {
            comm = "检查实际结果是否为真";
        } else {
            comm = comment[0];
        }

        String jsonpath = null;
        int expected_result = 0;
        if (jsonPathPoint == null) {
            report.log("Jsonpath检查点为空，不进行断言");
        } else {
            String[] jsonValue = jsonPathPoint.split("=");
            jsonpath = jsonValue[0];
            expected_result = Integer.parseInt(jsonValue[1]);
        }
        int actual = DataUtils.JSONParseInt(stringResponse, jsonpath);

        try {
            Assert.assertEquals(actual,expected_result);
            ptFormat(comm, actual + "", expected_result + "", "PASS");
        } catch (AssertionError e) {
            ptFormat(comm, actual + "", expected_result + "", "FAIL");
            Assert.fail("检查点检查出错误");
        }
    }

    public static void assertFloatNum(String stringResponse, String jsonPathPoint, String... comment){
        String comm;
        if (comment.length == 0) {
            comm = "检查实际结果是否为真";
        } else {
            comm = comment[0];
        }

        String jsonpath = null;
        float expected_result = 0f;
        if (jsonPathPoint == null) {
            report.log("Jsonpath检查点为空，不进行断言");
        } else {
            String[] jsonValue = jsonPathPoint.split("=");
            jsonpath = jsonValue[0];
            expected_result = Integer.parseInt(jsonValue[1]);
        }
        float actual = DataUtils.JSONParseFloat(stringResponse, jsonpath);

        try {
            Assert.assertEquals(actual,expected_result);
            ptFormat(comm, actual + "", expected_result + "", "PASS");
        } catch (AssertionError e) {
            ptFormat(comm, actual + "", expected_result + "", "FAIL");
            Assert.fail("检查点检查出错误");
        }
    }

    public static void assertDoubleNum(String stringResponse, String jsonPathPoint, String... comment){
        String comm;
        if (comment.length == 0) {
            comm = "检查实际结果是否为真";
        } else {
            comm = comment[0];
        }

        String jsonpath = null;
        double expected_result = 0d;
        if (jsonPathPoint == null) {
            report.log("Jsonpath检查点为空，不进行断言");
        } else {
            String[] jsonValue = jsonPathPoint.split("=");
            jsonpath = jsonValue[0];
            expected_result = Integer.parseInt(jsonValue[1]);
        }
        double actual = DataUtils.JSONParseDouble(stringResponse, jsonpath);

        try {
            Assert.assertEquals(actual,expected_result);
            ptFormat(comm, actual + "", expected_result + "", "PASS");
        } catch (AssertionError e) {
            ptFormat(comm, actual + "", expected_result + "", "FAIL");
            Assert.fail("检查点检查出错误");
        }
    }
}
