package com.mie.base.utils.http;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by WangRicky on 2018/5/17.
 */
public class HttpRequestUtils {
    public HttpRequestUtils() {
    }

    public static String getBasePath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + contextPath + "/";
        return basePath;
    }

    public static Map<String, String> getQueryParam(HttpServletRequest request) {
        Map<String, String> params = new HashMap();
        Enumeration enu = request.getParameterNames();

        while(enu.hasMoreElements()) {
            String paraName = (String)enu.nextElement();
            System.out.println(paraName + ": " + request.getParameter(paraName));
            params.put(paraName, request.getParameter(paraName));
        }

        return params;
    }

    public static String getRequestBody(HttpServletRequest request) throws IOException {
        request.setCharacterEncoding("UTF-8");
        StringBuffer bodyBuffer = new StringBuffer();
        ServletInputStream in = request.getInputStream();
        byte[] tmp = new byte[1024];

        while(in.read(tmp) != -1) {
            bodyBuffer.append(new String(tmp, "utf-8"));
        }

        return bodyBuffer.toString();
    }

    public static boolean isAjaxRequest(HttpServletRequest request) {
        boolean isAjax = "XMLHttpRequest".equalsIgnoreCase(request.getHeader("x-requested-with"));
        String ajax = request.getParameter("ajax");
        return isAjax || "true".equalsIgnoreCase(ajax);
    }
}
