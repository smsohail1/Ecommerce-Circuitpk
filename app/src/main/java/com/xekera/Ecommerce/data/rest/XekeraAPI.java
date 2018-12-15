package com.xekera.Ecommerce.data.rest;

import com.xekera.Ecommerce.data.rest.response.LoginSuccessResponse;
import com.xekera.Ecommerce.data.rest.response.SignUpSuccessResponse;
import retrofit2.Call;
import retrofit2.http.*;

public interface XekeraAPI {

    String WEB_API_SIGN_UP_URL = "_login_&_signup_/_login_&_signup_/User/";

    @FormUrlEncoded
    @POST(WEB_API_SIGN_UP_URL + "signup.php")
    Call<SignUpSuccessResponse> postSignUpDetails(@Field("username") String username,
                                                  @Field("password") String password,
                                                  @Field("email") String emailID);

    @GET(WEB_API_SIGN_UP_URL + "login.php")
    Call<LoginSuccessResponse> getSignInDetails(@Query("username") String username,
                                                @Query("password") String password);

}
