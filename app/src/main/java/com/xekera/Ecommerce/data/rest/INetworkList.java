package com.xekera.Ecommerce.data.rest;


import com.xekera.Ecommerce.data.rest.response.MessageResponse;

import java.util.List;

public interface INetworkList<T> {
    void onSuccessList(List<T> response);
    void onErrorList(MessageResponse messageResponse);
    void onFailureList(Throwable t);
}
