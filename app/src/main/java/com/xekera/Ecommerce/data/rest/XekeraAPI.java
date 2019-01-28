package com.xekera.Ecommerce.data.rest;

import com.xekera.Ecommerce.data.rest.response.*;
import retrofit2.Call;
import retrofit2.http.*;

public interface XekeraAPI {

    String WEB_API_SIGN_UP_URL = "_login_&_signup_/_login_&_signup_/User/";
    String WEB_API_CATEGORY_URL = "category_api/categ_api/Category/Category/";
    String WEB_API_PRODUCT_URL = "products/";
    String WEB_API_FORGOT_PASSWORD = "_login_&_signup_/_login_&_signup_/User/";

    @FormUrlEncoded
    @POST(WEB_API_SIGN_UP_URL + "signup.php")
    Call<SignUpSuccessResponse> postSignUpDetails(@Field("username") String username,
                                                  @Field("password") String password,
                                                  @Field("email") String emailID);

    @GET(WEB_API_SIGN_UP_URL + "login.php")
    Call<LoginSuccessResponse> getSignInDetails(@Query("username") String username,
                                                @Query("password") String password);


    @GET(WEB_API_CATEGORY_URL + "readaip.php")
    Call<CategoryResponse> getCategory();


    @GET(WEB_API_PRODUCT_URL + "read_single_product.php")
    Call<ProductResponse> getProducts(@Query("categoryname") String categoryName);

    @FormUrlEncoded
    @POST(WEB_API_FORGOT_PASSWORD + "forgotpassword.php")
    Call<ForgotPasswordResponse> setForgotPassword(@Field("password") String password,
                                                   @Field("email") String email);
}
