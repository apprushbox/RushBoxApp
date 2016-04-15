package com.rushbox.android.rushboxapp.connections;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.HttpEntity;


/**
 * Created by Ronner on 06-04-2016.
 */
public class TheBoxAppClient {
    public static final String USERSERVICE_BASE_URL = "http://localhost:8080/UserService.svc/";
    public static final String PRODUCSERVICE_BASE_URL = "http://localhost:8080/ProductService.svc/";
    private static AsyncHttpClient theBoxClient = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        theBoxClient.get(url, params, responseHandler);
    }

    public static void post(Context context, String url, HttpEntity entity, String contentType, AsyncHttpResponseHandler responseHandler) {
        theBoxClient.post(context, url, entity, contentType, responseHandler);
    }

    public static void put(Context context, String url, HttpEntity entity, String contentType, AsyncHttpResponseHandler responseHandler) {
        theBoxClient.put(context, url, entity, contentType, responseHandler);
    }

    public static void delete(Context context, String url, HttpEntity entity, String contentType, AsyncHttpResponseHandler responseHandler) {
        theBoxClient.delete(context, url, entity, contentType, responseHandler);
    }

}
