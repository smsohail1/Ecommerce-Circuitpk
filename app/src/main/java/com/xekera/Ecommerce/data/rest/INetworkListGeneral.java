package com.xekera.Ecommerce.data.rest;


public interface INetworkListGeneral<T> {
    void onSuccess(T response);

    void onFailure(Throwable t);
}
