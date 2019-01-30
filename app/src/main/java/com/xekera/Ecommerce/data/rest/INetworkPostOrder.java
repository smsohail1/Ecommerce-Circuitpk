package com.xekera.Ecommerce.data.rest;

public interface INetworkPostOrder<T> {
    void onSuccess(T response, int counts);


    void onFailure(Throwable t);
}
