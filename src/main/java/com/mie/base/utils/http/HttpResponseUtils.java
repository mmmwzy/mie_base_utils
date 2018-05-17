package com.mie.base.utils.http;

import com.mie.base.utils.json.JsonUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by WangRicky on 2018/5/17.
 */
public class HttpResponseUtils {
    public HttpResponseUtils() {
    }

    public static void responseResutlAsJson(HttpServletResponse response, Object result) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(JsonUtils.writeValueAsString(result));
        out.flush();
        out.close();
    }
}
