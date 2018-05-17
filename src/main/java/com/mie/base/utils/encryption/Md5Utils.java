package com.mie.base.utils.encryption;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mie.base.utils.json.JsonUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by WangRicky on 2018/5/17.
 */
public class Md5Utils {
    private static final String[] hexDigits = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public Md5Utils() {
    }

    public static String md5Object(Object object) throws NoSuchAlgorithmException, UnsupportedEncodingException, JsonProcessingException {
        return object == null ? "md5_null" : md5Encode(JsonUtils.writeValueAsString(object));
    }

    public static String md5Encode(String origin) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String resultString = null;
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(origin.getBytes("UTF-8"));
        resultString = byteArrayToHexString(md.digest());
        return resultString;
    }

    public static String byteArrayToHexString(byte[] b) {
        StringBuilder resultSb = new StringBuilder();
        byte[] arr$ = b;
        int len$ = b.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            byte aB = arr$[i$];
            resultSb.append(byteToHexString(aB));
        }

        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (b < 0) {
            n = 256 + b;
        }

        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }
}

