package com.heking.qsy.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Lee on 2017/9/21.
 */

public class MyTextUtils {
    /**
     * 是否包含
     *
     * @param base  长的一个
     * @param regex 短的一个
     * @return
     */
    public static boolean isContent(String base, String... regex) {
        if (isEmpty(base)) return false;
        for (String item : regex) {
            Pattern p = Pattern.compile(item);
            Matcher matcher = p.matcher(base);
            if (matcher.find()) return true;
        }
        return false;
    }

    /**
     * 是否包含
     *
     * @param base  长的一个
     * @param regex 短的一个
     * @return
     */
    public static boolean isContent(String[] base, String... regex) {
        for (String item : base) {
            if (isContent(item, regex)) return true;
        }
        return false;
    }

    public static boolean isEmpty(String string) {
        return string == null || string.length() == 0 ? true : false;

    }
}
