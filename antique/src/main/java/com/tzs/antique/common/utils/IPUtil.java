package com.tzs.antique.common.utils;

import javax.servlet.http.HttpServletRequest;

public class IPUtil {

    private static final String UNKNOWN = "unknown";

    protected IPUtil() {

    }

    /**
     * 获取 IP地址
     * 使用 Nginx等反向代理软件， 则不能通过 request.getRemoteAddr()获取 IP地址
     * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，
     * X-Forwarded-For中第一个非 unknown的有效IP字符串，则为真实IP地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

//    /***
//     * ip 转数字
//     * @param ipAddress
//     * @return
//     */
//    public static long ipToLong(String ipAddress) {
//
//        long result = 0;
//
//        String[] ipAddressInArray = ipAddress.split("\\.");
//
//        for (int i = 3; i >= 0; i--) {
//
//            long ip = Long.parseLong(ipAddressInArray[3 - i]);
//
//            //left shifting 24,16,8,0 and bitwise OR
//
//            //1. 192 << 24
//            //1. 168 << 16
//            //1. 1   << 8
//            //1. 2   << 0
//            result |= ip << (i * 8);
//
//        }
//        return result;
//    }

//
//    /***
//     *  数字转为ip地址
//     * @param ip
//     * @return
//     */
//    public static String longToIp(long ip) {
//
//        return ((ip >> 24) & 0xFF) + "."
//                + ((ip >> 16) & 0xFF) + "."
//                + ((ip >> 8) & 0xFF) + "."
//                + (ip & 0xFF);
//    }
}
