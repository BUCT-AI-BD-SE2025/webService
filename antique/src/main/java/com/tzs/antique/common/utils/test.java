package com.tzs.antique.common.utils;

public class test {
    public static void main(String[] args) {
        String path = System.getProperty("java.class.path");
       // String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
        System.out.println(path);
    }
}
