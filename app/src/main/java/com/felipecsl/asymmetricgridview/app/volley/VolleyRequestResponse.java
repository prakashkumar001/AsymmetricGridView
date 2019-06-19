package com.felipecsl.asymmetricgridview.app.volley;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.felipecsl.asymmetricgridview.app.AppController;
import com.felipecsl.asymmetricgridview.app.Utils;


import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class VolleyRequestResponse {

    private static VolleyRequestResponse requestResponse;
    Context context;

    private VolleyRequestResponse() {

    }

    public static VolleyRequestResponse getInstance() {
        if (requestResponse == null) {
            requestResponse = new VolleyRequestResponse();
        }
        return requestResponse;
    }

    //volley

    public void apiCall(final Context context,final String url, JSONObject params, String requestMethod,
                        final String token, final NetWorkResultListener apiListener) {

        String URL;

            URL = url;


        int method;
        if (requestMethod.equalsIgnoreCase("POST")) {
            method = Request.Method.POST;
        } else {
            method = Request.Method.GET;
        }

        Log.e("URL", URL);


        if (!Utils.isInternetAvailable(context)) {

            Utils.showToast(context, "No internet connection");

            return;
        }

        Log.e("request :", "request : " + params.toString());

        Utils.showToast(context, "Please wait");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(method, URL, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("response", "response : " + response);

                        Utils.hideProgressDialog(context);

                        ContentParser contentParser = new ContentParser(response.toString());

                        if (!contentParser.isSuccess() && contentParser.getErrorCode() == 50) {
;

                            return;
                        }


                        try {
                            if (contentParser.isSuccess()) {

                                apiListener.onSuccess(response.toString(), url);
                            } else {

                                apiListener.onFailure(url, contentParser.getDisplayMessage());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Utils.hideProgressDialog(context);

                try {
                    NetworkResponse networkResponse = volleyError.networkResponse;

                    ContentParser contentParser = new ContentParser(new String(networkResponse.data));

                    Log.e("Error Response", "Error Response" + new String(networkResponse.data));



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                if (!token.isEmpty()) {
                    headers.put("Authorization", token);
                }
                Log.e("Params", headers.toString());
                return headers;
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(jsonObjectRequest, "tag_string_req");

    }


}
