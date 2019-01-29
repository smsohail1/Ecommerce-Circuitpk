package com.xekera.Ecommerce.data.rest;

import com.xekera.Ecommerce.data.rest.response.*;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface XekeraAPI {

    String WEB_API_SIGN_UP_URL = "_login_&_signup_/_login_&_signup_/User/";
    String WEB_API_CATEGORY_URL = "category_api/categ_api/Category/Category/";
    String WEB_API_PRODUCT_URL = "products/";
    String WEB_API_FORGOT_PASSWORD = "_login_&_signup_/_login_&_signup_/User/";
    String WEB_API_SUBMIT_ORDER = "Submit_Order/";
    String WEB_API_HISTORY_ID = "Order_Histroy/";

    @FormUrlEncoded
    @POST(WEB_API_SIGN_UP_URL + "signup.php")
    Call<SignUpSuccessResponse> postSignUpDetails(@Field("username") String username,
                                                  @Field("password") String password,
                                                  @Field("email") String emailID);

    @GET(WEB_API_SIGN_UP_URL + "login.php")
    Call<LoginSuccessResponse> getSignInDetails(@Query("username") String username,
                                                @Query("password") String password);


    @GET(WEB_API_HISTORY_ID + "view_histroy.php")
    Call<HistoryOrderIdResponse> getOrderHistroryId(@Query("username") String username,
                                                    @Query("emailaddress") String email);


    @GET(WEB_API_HISTORY_ID + "OrderDetail.php")
    Call<HistoryDetailsResponse> getOrderHistoryIdDetail(@Query("orderID") String orderId);


    @GET(WEB_API_PRODUCT_URL + "read_single_product.php")
    Call<ProductResponse> getProducts(@Query("categoryname") String categoryName);

    @FormUrlEncoded
    @POST(WEB_API_FORGOT_PASSWORD + "forgotpassword.php")
    Call<ForgotPasswordResponse> setForgotPassword(@Field("password") String password,
                                                   @Field("email") String email);

    @GET(WEB_API_CATEGORY_URL + "readaip.php")
    Call<CategoryResponse> getCategory();


//
//    @POST("/api/employee/checkin")
//    Call<ResponseBody> CHECKIN(@Body String data);


    // @FormUrlEncoded
//    @Headers({
//            "Content-Type: application/json"
////            "x-access-token: eyJhbGciOiJIU"
//    })
    @FormUrlEncoded
    // @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST(WEB_API_SUBMIT_ORDER + "submitorder.php")
    //  @Multipart
    // Call<SubmitOrderResponse> postOrderDeatils(@Body String jsonData);
    Call<ResponseBody> postOrderDeatils(@Field("jsondata") String jsonData);


}
