package com.xekera.Ecommerce.data.rest;

import com.xekera.Ecommerce.data.rest.response.*;
import com.xekera.Ecommerce.data.rest.response.searchAllProductReponse.AllProductsResponse;
import com.xekera.Ecommerce.data.room.model.AddToCart;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.HashMap;
import java.util.List;

public interface XekeraAPI {

    String WEB_API_SIGN_UP_URL = "_login_&_signup_/_login_&_signup_/User/";
    String WEB_API_CATEGORY_URL = "category_api/categ_api/Category/Category/";
    String WEB_API_PRODUCT_URL = "products/";
    String WEB_API_FORGOT_PASSWORD = "_login_&_signup_/_login_&_signup_/User/";
    String WEB_API_SUBMIT_ORDER = "Submit_Order/";
    String WEB_API_HISTORY_ID = "Order_Histroy/";
    String WEB_API_ALL_PRODUCTS = "products/";

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
    Call<HistoryOrderIdDiscriptionResponse> getOrderHistoryIdDetail(@Query("orderID") String orderId);


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
    // @Headers({"Content-Type: application/json;charset=UTF-8"})
    // @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @POST(WEB_API_SUBMIT_ORDER + "submitorder.php?jsondata=")
    @FormUrlEncoded
    @POST(WEB_API_SUBMIT_ORDER + "submitorder.php")

    //  @Multipart
    // Call<SubmitOrderResponse> postOrderDeatils(@Body String jsonData);
    Call<SubmitAddressResponse> postOrderDeatils(@Field("name") String name,
                                                 @Field("address") String address,
                                                 @Field("email") String email,
                                                 @Field("company") String company,
                                                 @Field("phone") String phone,
                                                 @Field("payment") String payment,
                                                 @Field("message") String message,
                                                 @Field("username") String logedInUsername,
                                                 @Field("GST") String gst,
                                                 @Field("FlatCharges") String flatCharges,
                                                 @Field("Total") String totalAmount);
    //Call<ResponseBody> postOrderDeatils(@Body String jsonData);

    @FormUrlEncoded
    @POST(WEB_API_SUBMIT_ORDER + "submitdata.php")
    Call<SubmitOrderSingleListResponse> postOrderListDeatils(@Field("product_id") String product_id,
                                                             @Field("itemQuantity") String itemQuantity,
                                                             @Field("itemPrice") String itemPrice,
                                                             @Field("last_id") String last_id,
                                                             @Field("emailaddress") String emailaddress,
                                                             @Field("sendemailbit") String sendemailbit
    );


    @Headers({
            "Content-type: application/json"
    })
    @POST(WEB_API_SUBMIT_ORDER + "submitdata.php")
    Call<ResponseBody> postOrderListDeatils1(@Body String arrayOfSids);


    @GET(WEB_API_ALL_PRODUCTS + "searchproduct.php")
    Call<AllProductsResponse> getAllProducts();

//    @FormUrlEncoded
//    @POST(WEB_API_SUBMIT_ORDER + "submitdata.php")
//    Call<ResponseBody> postOrderListDeatils3(@Field("productlist[]") List<AddToCart> arrayOfSids);

}
