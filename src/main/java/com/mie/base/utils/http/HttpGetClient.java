package com.mie.base.utils.http;

import com.mie.base.utils.json.JsonUtils;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by WangRicky on 2018/5/17.
 */
public class HttpGetClient {
    public HttpGetClient() {
    }

    public static String send(String url) throws NullPointerException, HttpException, IOException {
        return (String)send(url, String.class);
    }

    public static <T> T send(String url, Class<T> valueType) throws NullPointerException, HttpException, IOException {
        return (T)send(url, (NameValuePair[])null, (Class)valueType);
    }

    public static String send(String url, NameValuePair[] queryParams) throws NullPointerException, HttpException, IOException {
        return (String)send(url, queryParams, String.class);
    }

    public static <T> T send(String url, NameValuePair[] queryParams, Class<T> valueType) throws NullPointerException, HttpException, IOException {
        return send(url, queryParams, (Header[])null, valueType);
    }

    public static String send(String url, NameValuePair[] queryParams, Header[] headers) throws NullPointerException, HttpException, IOException {
        return (String)send(url, queryParams, headers, String.class);
    }

    public static <T> T send(String url, NameValuePair[] queryParams, Header[] headers, Class<T> valueType) throws NullPointerException, HttpException, IOException {
        HttpClient client = new HttpClient();
        GetMethod method = new GetMethod();
        URI serverUri = new URI(url, false);
        method.setURI(serverUri);
        if (queryParams != null && queryParams.length > 0) {
            method.setQueryString(queryParams);
        }

        int result;
        for(result = 0; headers != null && result < headers.length; ++result) {
            method.setRequestHeader(headers[result]);
        }

        result = client.executeMethod(method);
        if (result != 200) {
            throw new HttpException("http get failed, status:" + result);
        } else {
            String responseBody = getResponseBody(method);
            return valueType.equals(String.class) ? (T) responseBody : JsonUtils.convertValue(responseBody, valueType);
        }
    }

    private static String getResponseBody(GetMethod method) throws IOException {
        InputStream in = method.getResponseBodyAsStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        boolean var4 = false;

        int len;
        while((len = in.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
        }

        return baos.toString("UTF8");
    }

    public static void main(String[] args) throws NullPointerException, HttpException, IOException {
    }
}
