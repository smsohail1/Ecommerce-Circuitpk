package com.xekera.Ecommerce.ui.login;

import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.data.rest.INetworkLoginSignup;
import com.xekera.Ecommerce.data.rest.XekeraAPI;
import com.xekera.Ecommerce.data.rest.response.LoginSuccessResponse;
import com.xekera.Ecommerce.util.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginModel implements LoginMVP.Model {
    private XekeraAPI xakeraAPI;
    private Utils utils;

    public LoginModel(XekeraAPI xakeraAPI, Utils utils){
        this.xakeraAPI = xakeraAPI;
        this.utils = utils;
    }


    @Override
    public void signIn(String username, String password, final INetworkLoginSignup<LoginSuccessResponse> iNetworkLoginSignup) {
        Call<LoginSuccessResponse> call = xakeraAPI.getSignInDetails(username,password);
        call.enqueue(new Callback<LoginSuccessResponse>() {
            @Override
            public void onResponse(Call<LoginSuccessResponse> call, Response<LoginSuccessResponse> response) {
                try {
                    LoginSuccessResponse loginSuccessResponse = response.body();

                    iNetworkLoginSignup.onSuccess(loginSuccessResponse);
//                    if(messageResponse == null){
//                        iNetworkLoginSignup.onErrorList(getMessageResponse(utils.getStringFromResourceId(R.string.error),
//                                utils.getStringFromResourceId(R.string.null_response_received)));
//                        return;
//                    }

                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<LoginSuccessResponse> call, Throwable t) {
                iNetworkLoginSignup.onFailure(t);
            }
        });
    }
}
