package com.mie.base.utils.encryption;

/**
 * Created by WangRicky on 2018/5/17.
 */
public class DESUtils {
    private static DES des;

    public DESUtils() {
    }

    private static DES getInstance() throws Exception {
        if (des == null) {
            des = new DES();
        }

        return des;
    }

    public static String encrypt(String strIn, String key) throws Exception {
        DES des = new DES(key);
        return des.encrypt(strIn);
    }

    public static String encrypt(String strIn) throws Exception {
        return getInstance().encrypt(strIn);
    }

    public static byte[] encrypt(byte[] arrB) throws Exception {
        return getInstance().decrypt(arrB);
    }

    public static String decrypt(String strIn, String key) throws Exception {
        DES des = new DES(key);
        return des.decrypt(strIn);
    }

    public static String decrypt(String strIn) throws Exception {
        return getInstance().decrypt(strIn);
    }

    public static byte[] decrypt(byte[] arrB) throws Exception {
        return getInstance().decrypt(arrB);
    }
}

