package com.xekera.Ecommerce.data.rest;


import com.xekera.Ecommerce.data.rest.response.MessageResponse;

public interface INetwork<T> {
    void onSuccess(T response);

    void onError(MessageResponse messageResponse);

    void onFailure(Throwable t);
}
