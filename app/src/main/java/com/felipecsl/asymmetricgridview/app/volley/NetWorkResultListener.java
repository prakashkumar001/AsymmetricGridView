package com.felipecsl.asymmetricgridview.app.volley;

import java.io.Serializable;

public interface NetWorkResultListener extends Serializable{
    void onSuccess(String response, String requestType);
    void onFailure(String requestType, String error);
}
