package com.android.jay.androiddiy.interfaces;

/**
 * Created by jerryyin on 01/02/2018.
 */

public interface onResponseListener<T> {

    // status = 1
    void onSuccess(T result);

    // status = 0
    void onFail(T result);

    // status = 3  user authorization failed
    void onWarning(T result);

    // network error; exception ...
    void onError(int errCode, String error);


}
