package it.ac.enigma.rest;

import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import it.ac.enigma.utility.Constants;

public class GsonRequest<T> extends Request<T> {
    @Nullable
    @Override
    public Response.ErrorListener getErrorListener() {
        return super.getErrorListener();
    }

    private final Gson gson = new Gson();
    private final Class<T> clazz;
    private final Map<String, String> headers;
    private final Response.Listener<T> listener;
    private final String requestBody;
    private final boolean isJson;
    private final String token;

    private final static String TAG = "GsonRequest";

    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param url URL of the request to make
     * @param clazz Relevant class object, for Gson's reflection
     * @param headers Map of request headers
     */
    public GsonRequest(int method,
                       String url,
                       Class<T> clazz,
                       boolean isJson,
                       Map<String, String> headers,
                       String requestBody,
                       String token,
                       Response.Listener<T> listener,
                       Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.clazz = clazz;
        this.headers = headers;
        this.isJson = isJson;
        this.listener = listener;
        this.requestBody = requestBody;
        this.token = token;
    }
    @Override
    public String getBodyContentType() {

        if(this.isJson)
            return "application/json; charset=utf-8";

        return "application/x-www-form-urlencoded; charset=utf-8";
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        try {

            Log.d(TAG, "URL:" + getUrl());

            if(requestBody == null)
                return null;
            else {

                Log.d(TAG, "Request body: " + requestBody);

                return requestBody.getBytes("utf-8");
            }
        } catch (UnsupportedEncodingException uee) {
            Log.d(TAG, requestBody);
            //return null;
        }
        return null;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if(token != null) {
            try {
                Map<String, String> params = new HashMap<>();
                params.put(Constants.Header.JWT_AUTH, this.token);
                return params;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Log.d(TAG, "Json: " + json);
            return Response.success(gson.fromJson(json, clazz), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException | JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }
}
