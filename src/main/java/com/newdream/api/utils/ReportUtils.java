package com.newdream.api.utils;

/**
 * Created by qiuwei on 2017/7/22.
 */

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.testng.Reporter;

public class ReportUtils {

    private static Logger logger = Logger.getLogger(ReportUtils.class.getName());


    public ReportUtils() {
        System.setProperty("org.uncommons.reportng.escape-output", "false");
    }

    /*********************************************************************************************
     * 写log和报告操作
     *********************************************************************************************/
    /**
     * 写日志和报告
     *
     * @param comm
     */
    public void log(String... comm) {
        //String time = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        if (comm.length == 0) {
            Reporter.log(" <br />");
            logger.info("");
        } else {
            Reporter.log(comm[0] + "<br />");
            logger.info(comm[0]);
        }
    }

    /**
     * 写日志和报告
     *
     * @param comm
     * @comment 如果selenium.properties中【isPrintDebugMsg】值为0，则只打印looger，不打印report
     */
    public void debug(String... comm) {
        log("[debug]" + comm[0]);
    }

    /**
     * 写错误日志和报告
     *
     * @param comm
     */
    public void error(String comm) {
        //String time = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        Reporter.log("<font color=\"#FF0000\"> [ERROR] " + comm );
        logger.error(comm);
    }

    /**
     * 写警告日志和报告
     *
     * @param comm
     */
    public void warn(String comm) {
        String time = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        Reporter.log("<font color=\"#FF7F27\"> [WARNING] " + comm );
        logger.warn(comm);
    }

    /**
     * 写重要的日志和报告
     *
     * @param comm
     */
    public void highLight(String comm) {
        log("<font color=\"#FFE500\">" + comm);
    }

    /**
     * 写绿色高亮的的日志和报告
     *
     * @param comm
     */
    public void greenLight(String comm) {
        log("<font color=\"#CFFFBA\">" + comm);
    }

    /**
     * 参数高亮
     *
     * @param comm
     */
    public void paraLight(String comm) {
        if (1 == 1) {
            log("<font color=\"#E4FFD9\">" + comm);
        } else {
            logger.info(comm);
        }
    }

    /**
     * 写醒目的标题
     *
     * @param comm
     */
    public void title(String comm) {
        String str;
        str = "<p style=\"color:#0068BD;margin-top:25px;margin-bottom:8px\"><b>";
        str = str + "**********************************************************************************************<br>";
        str = str + "* " + comm + "<br>";
        str = str + "**********************************************************************************************</b>";
        str = str + "</p>";
        logger.info(str);
        Reporter.log(str);
    }

}
