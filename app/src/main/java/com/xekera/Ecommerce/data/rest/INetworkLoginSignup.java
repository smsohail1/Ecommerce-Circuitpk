package com.xekera.Ecommerce.data.rest;


import com.xekera.Ecommerce.data.rest.response.SignUpSuccessResponse;

public interface INetworkLoginSignup<T> {
    void onSuccess(T response);

    //  void onError(LoginSignUPErrorResponse loginSignUPErrorResponse);

    void onFailure(Throwable t);
}
