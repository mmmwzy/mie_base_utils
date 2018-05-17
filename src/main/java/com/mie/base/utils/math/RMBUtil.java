package com.mie.base.utils.math;

/**
 * Created by WangRicky on 2018/5/17.
 */
public final class RMBUtil {
    private static String[] HanDigiStr = new String[]{"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
    private static String[] HanDiviStr = new String[]{"", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "万", "拾", "佰", "仟"};

    private RMBUtil() {
    }

    public static final String convert(double val) {
        String SignStr = "";
        String TailStr = "";
        if (val < 0.0D) {
            val = -val;
            SignStr = "负";
        }

        if (val <= 1.0E14D && val >= -1.0E14D) {
            long temp = Math.round(val * 100.0D);
            long integer = temp / 100L;
            long fraction = temp % 100L;
            int jiao = (int)fraction / 10;
            int fen = (int)fraction % 10;
            if (jiao == 0 && fen == 0) {
                TailStr = "整";
            } else {
                TailStr = HanDigiStr[jiao];
                if (jiao != 0) {
                    TailStr = TailStr + "角";
                }

                if (integer == 0L && jiao == 0) {
                    TailStr = "";
                }

                if (fen != 0) {
                    TailStr = TailStr + HanDigiStr[fen] + "分";
                }
            }

            return SignStr + PositiveIntegerToHanStr(String.valueOf(integer)) + "元" + TailStr;
        } else {
            return "数值位数过大!";
        }
    }

    private static String PositiveIntegerToHanStr(String NumStr) {
        String RMBStr = "";
        boolean lastzero = false;
        boolean hasvalue = false;
        int len = NumStr.length();
        if (len > 15) {
            return "数值过大!";
        } else {
            for(int i = len - 1; i >= 0; --i) {
                if (NumStr.charAt(len - i - 1) != ' ') {
                    int n = NumStr.charAt(len - i - 1) - 48;
                    if (n < 0 || n > 9) {
                        return "输入含非数字字符!";
                    }

                    if (n != 0) {
                        if (lastzero) {
                            RMBStr = RMBStr + HanDigiStr[0];
                        }

                        if (n != 1 || i % 4 != 1 || i != len - 1) {
                            RMBStr = RMBStr + HanDigiStr[n];
                        }

                        RMBStr = RMBStr + HanDiviStr[i];
                        hasvalue = true;
                    } else if (i % 8 == 0 || i % 8 == 4 && hasvalue) {
                        RMBStr = RMBStr + HanDiviStr[i];
                    }

                    if (i % 8 == 0) {
                        hasvalue = false;
                    }

                    lastzero = n == 0 && i % 4 != 0;
                }
            }

            if (RMBStr.length() == 0) {
                return HanDigiStr[0];
            } else {
                return RMBStr;
            }
        }
    }
}

