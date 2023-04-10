package com.example.demo.utils;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;

import javax.servlet.http.HttpServletRequest;

public class HttpUtil {

    /**
     * 获取浏览器名字
     *
     * @param request 请求
     * @return {@link String}
     */
    public static String getBrowserName(HttpServletRequest request) {
        String uaStr = request.getHeader("User-Agent");
        UserAgent ua = UserAgentUtil.parse(uaStr);
        return ua.getBrowser().toString();
    }

    /**
     * 获取浏览器版本
     *
     * @param request 请求
     * @return {@link String}
     */
    public static String getBrowserVersion(HttpServletRequest request) {
        String uaStr = request.getHeader("User-Agent");
        UserAgent ua = UserAgentUtil.parse(uaStr);
        return ua.getVersion();
    }

    /**
     * 获取操作系统名称
     *
     * @param request 请求
     * @return {@link String}
     */
    public static String getOsName(HttpServletRequest request) {
        String uaStr = request.getHeader("User-Agent");
        UserAgent ua = UserAgentUtil.parse(uaStr);
        return ua.getOs().toString();
    }


    /**
     * 得到ip addr
     *
     * @param request 请求
     * @return {@link String}
     */
    public static String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        } else {
            String ip = request.getHeader("x-forwarded-for");
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }

            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("X-Forwarded-For");
            }

            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }

            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("X-Real-IP");
            }

            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }

            if ("0:0:0:0:0:0:0:1".equals(ip)) {
                ip = "127.0.0.1";
            }

            if (ip.contains(",")) {
                ip = ip.split(",")[0];
            }
            return ip;
        }
    }
}
