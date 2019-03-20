package com.xekera.Ecommerce.ui.login;

import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.data.rest.INetworkLoginSignup;
import com.xekera.Ecommerce.data.rest.XekeraAPI;
import com.xekera.Ecommerce.data.rest.response.ForgotPasswordResponse;
import com.xekera.Ecommerce.data.rest.response.LoginSuccessResponse;
import com.xekera.Ecommerce.data.rest.response.SignUpSuccessResponse;
import com.xekera.Ecommerce.util.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginModel implements LoginMVP.Model {
    private XekeraAPI xakeraAPI;
    private Utils utils;

    public LoginModel(XekeraAPI xakeraAPI, Utils utils) {
        this.xakeraAPI = xakeraAPI;
        this.utils = utils;
    }


    @Override
    public void signIn(String username, String password, final INetworkLoginSignup<LoginSuccessResponse> iNetworkLoginSignup) {
        Call<LoginSuccessResponse> call = xakeraAPI.getSignInDetails(username, password);
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

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<LoginSuccessResponse> call, Throwable t) {
                iNetworkLoginSignup.onFailure(t);
            }
        });
    }

    @Override
    public void registerFacebookUser(String username, String password, String phoneNo, String emailID, final INetworkLoginSignup<SignUpSuccessResponse> iNetworkLoginSignup) {
        Call<SignUpSuccessResponse> call = xakeraAPI.postSignUpDetails(username, password, emailID, "");
        call.enqueue(new Callback<SignUpSuccessResponse>() {
            @Override
            public void onResponse(Call<SignUpSuccessResponse> call, Response<SignUpSuccessResponse> response) {
                try {
                    SignUpSuccessResponse signUpSuccessResponse = response.body();

                    iNetworkLoginSignup.onSuccess(signUpSuccessResponse);


                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SignUpSuccessResponse> call, Throwable t) {
                iNetworkLoginSignup.onFailure(t);
            }
        });
    }

    @Override
    public void resetPassword(String password, String emailID, final INetworkLoginSignup<ForgotPasswordResponse> iNetworkLoginSignup) {
        Call<ForgotPasswordResponse> call = xakeraAPI.setForgotPassword(password, emailID);
        call.enqueue(new Callback<ForgotPasswordResponse>() {
            @Override
            public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {
                try {
                    ForgotPasswordResponse forgotPasswordResponse = response.body();

                    iNetworkLoginSignup.onSuccess(forgotPasswordResponse);


                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {
                iNetworkLoginSignup.onFailure(t);
            }
        });
    }
}
