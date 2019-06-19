package com.felipecsl.asymmetricgridview.app.volley;

import android.util.Log;

import org.json.JSONObject;

/**
 * Created by lenova on 22/11/17.
 */

public class ContentParser {

    NetWorkResultListener listener;
    private boolean status = false;
    private String displayMessage = "";
    private JSONObject responseObject;
    private JSONObject dataObject;
    private int errorCode, errorType;
    private String response;

    public ContentParser(String response) {

        if (response != null) {

            this.response = response;

            try {

                responseObject = new JSONObject(response);

                status = responseObject.getBoolean("status");

                if (status) {

                    dataObject = responseObject.getJSONObject("data");
                    displayMessage = responseObject.getString("displayMessage");

                } else {

                    displayMessage = responseObject.getString("displayMessage");
                    setDisplayMessage(displayMessage);

                }

            } catch (Exception e) {

                Log.e("Exception", ": " + e.toString());

            }
        }
    }

    public ContentParser(String response, NetWorkResultListener listener, String url) {
        this.listener = listener;

        if (response != null) {

            try {
                responseObject = new JSONObject(response);

                status = responseObject.getBoolean("status");

                if (status) {

                    dataObject = responseObject.getJSONObject("data");
                    displayMessage = responseObject.getString("displayMessage");
                    listener.onSuccess(response, url);

                } else {

                    displayMessage = responseObject.getString("displayMessage");
                    setDisplayMessage(displayMessage);

                    listener.onFailure("", displayMessage);
                }

            } catch (Exception e) {
                Log.e("Exception", ": " + e.toString());
            }
        }
    }

    public String response() {
        return response;
    }

    public boolean isSuccess() {
        return status;
    }

    public String getMessage() {

        return displayMessage;
    }

    public void setMessage(String message) {

        displayMessage = message;
    }

    public JSONObject getDataObject() {
        return dataObject;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorType() {
        return errorType;
    }

    public void setErrorType(int errorType) {
        this.errorType = errorType;
    }

    public String getDisplayMessage() {

        return displayMessage;

    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }


}
