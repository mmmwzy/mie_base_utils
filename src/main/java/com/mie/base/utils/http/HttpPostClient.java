package com.mie.base.utils.http;

import com.mie.base.utils.json.JsonUtils;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.lang.ArrayUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by WangRicky on 2018/5/17.
 */
public class HttpPostClient {
    public HttpPostClient() {
    }

    public static String sendJson(String url, String json) throws NullPointerException, HttpException, IOException {
        return (String)sendJson(url, json, String.class);
    }

    public static <T> T sendJson(String url, String json, Class<T> valueType) throws NullPointerException, HttpException, IOException {
        return sendJson(url, (NameValuePair[])null, json, valueType);
    }

    public static <T> T sendJson(String url, NameValuePair[] queryParams, String json, Class<T> valueType) throws NullPointerException, HttpException, IOException {
        return sendJson(url, queryParams, (Header[])null, json, valueType);
    }

    public static <T> T sendJson(String url, NameValuePair[] queryParams, Header[] headers, String json, Class<T> valueType) throws NullPointerException, HttpException, IOException {
        Header[] newHeaders = (Header[])((Header[]) ArrayUtils.add(headers, new Header("Content-Type", "application/json")));
        RequestEntity entitie = new ByteArrayRequestEntity(json.getBytes());
        return send(url, queryParams, newHeaders, new RequestEntity[]{entitie}, valueType);
    }

    public static String send(String url) throws NullPointerException, HttpException, IOException {
        return (String)send(url, String.class);
    }

    public static <T> T send(String url, Class<T> valueType) throws NullPointerException, HttpException, IOException {
        return (T) send(url, (NameValuePair[])null, (Class)valueType);
    }

    public static String send(String url, NameValuePair[] queryParams) throws NullPointerException, HttpException, IOException {
        return (String)send(url, queryParams, String.class);
    }

    public static <T> T send(String url, NameValuePair[] queryParams, Class<T> valueType) throws NullPointerException, HttpException, IOException {
        return send(url, queryParams, (Header[])null, (RequestEntity[])null, valueType);
    }

    public static String send(String url, NameValuePair[] queryParams, Header[] headers) throws NullPointerException, HttpException, IOException {
        return (String)send(url, queryParams, headers, (RequestEntity[])null, String.class);
    }

    public static <T> T send(String url, NameValuePair[] queryParams, Header[] headers, RequestEntity[] entities, Class<T> valueType) throws NullPointerException, HttpException, IOException {
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod();
        URI uri = new URI(url, false);
        method.setURI(uri);
        if (queryParams != null && queryParams.length > 0) {
            method.setQueryString(queryParams);
        }

        int result;
        for(result = 0; headers != null && result < headers.length; ++result) {
            method.setRequestHeader(headers[result]);
        }

        for(result = 0; entities != null && result < entities.length; ++result) {
            method.setRequestEntity(entities[result]);
        }

        result = client.executeMethod(method);
        if (result >= 200 && result < 400) {
            String responseBody = getResponseBody(method);
            return valueType.equals(String.class) ?(T) responseBody : JsonUtils.convertValue(responseBody, valueType);
        } else {
            throw new HttpException("http post failed, status:" + result + ", body:" + getResponseBody(method));
        }
    }

    private static String getResponseBody(PostMethod method) throws IOException {
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

