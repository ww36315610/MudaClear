package com.muda.utils;

import org.springframework.util.Assert;

public class ValidationUtils {
    public static void notAllNull(String message, Object... obj) {
        int i = 0;
        for (Object o : obj) {
            if (o != null) {
                i++;
            }
        }
        Assert.isTrue(i > 0, message);
    }

    public static void notNull(Object obj, String message) {
        Assert.notNull(obj, message);
    }

    public static void assertTrue(boolean flag, String message) {
        Assert.isTrue(flag, message);
    }

    public static void main(String[] args) {
        String rr = "";
        String nn = null;
        String oo = new String();
//        notNull(rr,"rr");
        notNull(nn,"nn");
//        notNull(oo,"oo");
    }
}
