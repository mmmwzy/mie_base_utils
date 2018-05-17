package com.mie.base.utils.i18n;

import com.mie.base.utils.SpringContextHolder;
import org.apache.commons.lang.LocaleUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.LocaleContextResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by WangRicky on 2018/5/17.
 */
public class I18nUtils {
    private static Logger logger = LoggerFactory.getLogger(I18nUtils.class);
    public static final String COOKIE_I18N = "COOKIE_I18N";
    public static final String URL_PARAM_I18N = "local_i18n";
    private static Pattern domainRegex = Pattern.compile("^.+?(\\.\\w+\\.[a-z]+)$");

    public I18nUtils() {
    }

    public static void setLocalByRequest(HttpServletRequest request, HttpServletResponse response) {
        String newLocale = request.getParameter("local_i18n");
        if (!StringUtils.isBlank(newLocale)) {
            LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
            if (localeResolver == null) {
                try {
                    localeResolver = (LocaleResolver)SpringContextHolder.getBean("localeResolver");
                } catch (Exception var7) {
                    logger.warn("设置国际化异常，容器中找不到国际化的处理器[localeResolver]");
                }
            }

            if (localeResolver == null) {
                try {
                    localeResolver = (LocaleResolver)SpringContextHolder.getOneBean(LocaleResolver.class);
                } catch (Exception var6) {
                    logger.warn("设置国际化异常，容器中找不到国际化的处理器[LocaleResolver.class]");
                }
            }

            if (localeResolver != null) {
                if (localeResolver instanceof LocaleContextResolver) {
                    LocaleContextResolver tmp = (LocaleContextResolver)localeResolver;
                    tmp.resolveLocaleContext(request);
                } else {
                    localeResolver.resolveLocale(request);
                }
            }

            Cookie i18nCookie = new Cookie("COOKIE_I18N", newLocale);
            i18nCookie.setPath("/");
            i18nCookie.setMaxAge(2147483647);
            Matcher matcher = domainRegex.matcher(request.getServerName());
            if (matcher.find()) {
                i18nCookie.setDomain(matcher.group(1));
            }

            response.addCookie(i18nCookie);
        }
    }

    public static Locale getByLocale(String localStr) {
        if (StringUtils.isBlank(localStr)) {
            return null;
        } else {
            if (localStr.contains("-")) {
                localStr = localStr.replaceAll("\\-", "_");
            }

            String[] array = localStr.split("_");
            if (array.length > 1) {
                localStr = array[0] + "_" + array[1].toUpperCase();
            }

            Locale locale = null;

            try {
                locale = LocaleUtils.toLocale(localStr);
            } catch (Exception var4) {
                logger.warn("传参错误，不支持该国际化:" + localStr);
                locale = new Locale("zh", "CN");
            }

            return locale;
        }
    }

    public static String getMessage(String i18nCode) {
        Locale locale = LocaleContextHolder.getLocale();
        if (locale == null) {
            return null;
        } else {
            String i18nMsg = SpringContextHolder.getApplicationContext().getMessage(i18nCode, (Object[])null, locale);
            return i18nMsg;
        }
    }
}

