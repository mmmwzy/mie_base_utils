package com.mie.base.utils.math;

import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by WangRicky on 2018/5/17.
 */
public class BigDecimalUtils {
    public static final int DEFALUT_SCALE = 6;

    public BigDecimalUtils() {
    }

    public static double add(double value1, double value2) {
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);
        BigDecimal result = b1.add(b2).setScale(6, RoundingMode.HALF_UP);
        return result.doubleValue();
    }

    public static double add(BigDecimal b1, BigDecimal b2) {
        Assert.isNull(b1, "参数不能为空");
        Assert.isNull(b2, "参数不能为空");
        BigDecimal result = b1.add(b2).setScale(6, RoundingMode.HALF_UP);
        return result.doubleValue();
    }

    public static double subtract(double value1, double value2) {
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);
        BigDecimal result = b1.subtract(b2).setScale(6, RoundingMode.HALF_UP);
        return result.doubleValue();
    }

    public static double multiply(double value1, double value2) {
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);
        BigDecimal result = b1.multiply(b2).setScale(6, RoundingMode.HALF_UP);
        return result.doubleValue();
    }

    public static double divide(double value1, double value2, int scale) throws IllegalAccessException {
        if (scale < 0) {
            throw new IllegalAccessException("精确度不能小于0");
        } else if (value2 == 0.0D) {
            throw new IllegalAccessException("除数不能等于0");
        } else {
            BigDecimal b1 = new BigDecimal(value1);
            BigDecimal b2 = new BigDecimal(value2);
            BigDecimal result = b1.divide(b2, scale, RoundingMode.HALF_UP);
            return result.doubleValue();
        }
    }

    public static BigDecimal[] divideAndRemainder(double value1, double value2) throws IllegalAccessException {
        if (value2 == 0.0D) {
            throw new IllegalAccessException("除数不能等于0");
        } else {
            BigDecimal b1 = new BigDecimal(value1);
            BigDecimal b2 = new BigDecimal(value2);
            return b1.divideAndRemainder(b2);
        }
    }
}

