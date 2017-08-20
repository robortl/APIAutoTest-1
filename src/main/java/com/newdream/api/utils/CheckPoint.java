package com.newdream.api.utils;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.Reporter;

/**
 * Created by qiuwei on 2017/7/22.
 */

public class CheckPoint {

    private static Logger logger = Logger.getLogger(CheckPoint.class.getName());
    private static ReportUtils report = new ReportUtils();

    /**
     * @return
     * @TODO：检查 int 型数字
     * @author 作者 邱卫武:
     * @parameter @param actual 实际结果
     * @parameter @param expected 预期结果
     * @parameter @param comment 备注信息，可写可不写
     */
    public static void checkIntNum(int actual, int expected, String... comment) {
        String comm;
        if (comment.length == 0) {
            comm = "检查int型数字是否一样";
        } else {
            comm = comment[0];
        }
        assertEquals(actual, expected, comm);
    }

    /**
     * @return
     * @TODO： 检查String类型，实际字符串与预期结果是否一致
     * @author 作者 邱卫武
     * @parameter @param actual 实际结果
     * @parameter @param expected 预期结果
     * @parameter @param comment 备注信息，可写可不写
     */
    public static void checkString(String actual, String expected, String... comment) {
        String comm;
        if (comment.length == 0) {
            comm = "检查是否一样";
        } else {
            comm = comment[0];
        }
        assertEquals(actual, expected, comm);
    }

    /**
     * @return
     * @TODO： 检查String类型，实际字符串中是否包含指定字段，存在为正确，不存在为错误
     * @author 作者 邱卫武
     * @parameter @param actual 实际结果
     * @parameter @param expected 预期结果
     * @parameter @param comment 备注信息，可写可不写
     */
    public static void checkStringContains(String actual, String expected, String... comment) {
        actual = actual.replaceAll("\n", " ");

        String comm;
        if (comment.length == 0) {
            comm = "检查实际字符串中是否有指定字段";
        } else {
            comm = comment[0];
        }
        try {
            Assert.assertEquals(true, actual.contains(expected));
            ptFormat(comm, actual + "", expected + "", "PASS");
        } catch (AssertionError e) {
            ptFormat(comm, actual + "", expected + "", "FAIL");
            Assert.fail("检查点检查出错误");
        }
    }

    /**
     * @return
     * @TODO：检查String类型，实际字符串中是否存在指定字段，存在为错，不存在为正确
     * @author 作者 邱卫武
     * @parameter @param actual 实际结果
     * @parameter @param expected 预期结果
     * @parameter @param comment 备注信息，可写可不写
     */
    public static void checkStringNotContains(String actual, String expected, String... comment) {
        actual = actual.replaceAll("\n", " ");

        String comm;
        if (comment.length == 0) {
            comm = "检查实际字符串中是否存在指定字段，存在为错，不存在为正确";
        } else {
            comm = comment[0];
        }
        try {
            Assert.assertEquals(false, actual.contains(expected));
            ptFormat(comm, actual + "", "不包含\"" + expected + "\"", "PASS");
        } catch (AssertionError e) {
            ptFormat(comm, actual + "", expected + "", "FAIL");
            Assert.fail("检查点检查出错误");
        }
    }

    /**
     * @return
     * @TODO： double类型校验
     * @author 作者 邱卫武
     * @parameter @param actual 实际结果
     * @parameter @param expected 期望结果
     * @parameter @param comment 备注信息，可写可不写
     */
    public static void checkDouble(double actual, double expected, String... comment) {
        String comm;
        if (comment.length == 0) {
            comm = "检查实际字符串中是否有指定字段";
        } else {
            comm = comment[0];
        }
        assertEquals(actual, expected, comm);
    }

    /**
     * @return
     * @TODO：检查实际double字符串中是否包含指定double字段，实际结果为空时，不校验
     * @author 作者 邱卫武:
     * @parameter @param actual 实际结果
     * @parameter @param expected 预期结果
     * @parameter @param comment 备注信息，可写可不写
     */
    public static void checkDoubleNotNull(double actual, double expected, String... comment) {
        String comm;
        if (comment.length == 0) {
            comm = "检查实际字符串中是否有指定字段";
        } else {
            comm = comment[0];
        }
        if (actual != 0 && expected != 0) {
            assertEquals(actual, expected, comm);
        }
    }

    /**
     * @return
     * @TODO：检查boolean类型实际结果是否为真
     * @author 作者 邱卫武
     * @parameter @param actual 需要对比的条件，布尔类型
     * @parameter @param comment 备注信息，可写可不写
     */
    public static void checkisTrue(boolean actual, String... comment) {
        String comm;
        if (comment.length == 0) {
            comm = "检查实际结果是否为真";
        } else {
            comm = comment[0];
        }
        try {
            Assert.assertEquals(true, actual);
            ptFormat(comm, actual + "", "true", "PASS");
        } catch (AssertionError e) {
            ptFormat(comm, actual + "", "true", "FAIL");
            Assert.fail("检查点检查出错误");
        }
    }

    /**
     * @return
     * @TODO：检查boolean类型
     * @author 作者 邱卫武:
     * @parameter @param actual 实际结果
     * @parameter @param expected 预期结果
     * @parameter @param comment 备注信息，可写可不写
     */
    public static void checkBoolean(boolean actual, boolean expected, String... comment) {

        String comm;
        if (comment.length == 0) {
            comm = "检查boolean类型";
        } else {
            comm = comment[0];
        }
        assertEquals(actual, expected, comm);
    }

    /**
     * @return
     * @TODO：输出错误
     * @author 作者 邱卫武:
     * @parameter @param comment 备注信息，可写可不写
     */
    public void printError(String comment) {
        ptFormat(comment, "NULL", "NULL", "FAIL");
        Assert.fail("检查点检查出错误");
    }

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
            String[] jsonValue = jsonPathPoint.split("=");
            jsonpath = jsonValue[0];
            expected_result = jsonValue[1];
        }

        String actual = DataUtils.JSONParse(stringResponse, jsonpath);

        assertEquals(actual, expected_result, comm);
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

}
