package com.xekera.Ecommerce.data.rest;


import com.xekera.Ecommerce.data.rest.response.LoginSuccessResponse;

import java.util.List;

public interface INetworkList<T> {
    void onSuccessList(List<T> response);
    void onErrorList(LoginSuccessResponse messageResponse);
    void onFailureList(Throwable t);
}
