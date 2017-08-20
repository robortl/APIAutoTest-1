package com.newdream.api.base;

import java.util.ResourceBundle;

/**
 * Created by qiuwei on 2017/07/21.
 */
public class ApiConfig {
    private final static ResourceBundle rb = ResourceBundle.getBundle("Interface");

    public final static String HTTPHOST = rb.getString("HttpHost");
    public final static String REPORT_PATH = rb.getString("reportPath");
    public final static String SRC_REPORT = rb.getString("sourceReport");
}
