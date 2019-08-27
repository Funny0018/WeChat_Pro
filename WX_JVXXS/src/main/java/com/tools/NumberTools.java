package com.tools;

public class NumberTools {
    public float toFloat(String info) {
        try {
            return Float.parseFloat(info.replace("￥", ""));
        } catch (Exception e) {
            return (float) 0.00;
        }
    }
}
