package com.xekera.Ecommerce.data.rest;

import com.google.gson.JsonObject;
import com.xekera.Ecommerce.data.rest.response.*;
import com.xekera.Ecommerce.data.rest.response.add_remove_cart_response.AddRemoveCartResponse;
import com.xekera.Ecommerce.data.rest.response.add_to_cart_response.AddToCartResponse;
import com.xekera.Ecommerce.data.rest.response.delete_item_cart_response.DeleteItemCartResponse;
import com.xekera.Ecommerce.data.rest.response.searchAllProductReponse.AllProductsResponse;
import com.xekera.Ecommerce.data.rest.response.submit_order_json_response.SubmitOrderJsonResponse;
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
    String WEB_API_ADD_TO_CART_ORDER = "Submit_Order/";
    String WEB_API_FETCH_ALL_CART_ORDER = "products/";
    String WEB_API_DELETE_ORDER = "products/";
    String WEB_API_ADD_REMOVE_ITEMS_CART = "products/";
    String WEB_API_ADD_TO_FAVOURITE = "favorite/";


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

    @GET(WEB_API_ADD_TO_CART_ORDER + "addtocartweb.php")
    Call<ResponseBody> addToProducts(@Query("product_id") String product_id,
                                     @Query("itemQuantity") String itemQuantity,
                                     @Query("itemPrice") String itemPrice,
                                     @Query("last_id") String last_id);


    @GET(WEB_API_FETCH_ALL_CART_ORDER + "cartproducts.php")
    Call<AddToCartResponse> getAllCarts(@Query("cartId") String cartId);

    @GET(WEB_API_DELETE_ORDER + "deletecartproduct.php")
    Call<DeleteItemCartResponse> deleteSingleItemCart(@Query("deleteOrder") String deleteOrder);


    @GET(WEB_API_ADD_REMOVE_ITEMS_CART + "updatecart.php")
    Call<AddRemoveCartResponse> addRemoveItemsCart(@Query("updatequant") String updatequant,
                                                   @Query("cartid") String cartid);

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

    @FormUrlEncoded
    @POST(WEB_API_SUBMIT_ORDER + "webordersubmit.php")
    Call<SubmitOrderJsonResponse> postOrder(@Field("orderkeyid") String randomKey,
                                            @Field("orderfrom") String platform,
                                            @Field("name") String name,
                                            @Field("username") String username,
                                            @Field("address") String address,
                                            @Field("email") String email,
                                            @Field("company") String company,
                                            @Field("phone") String phone,
                                            @Field("payment") String payment,
                                            @Field("message") String message,
                                            @Field("GST") String gst,
                                            @Field("FlatCharges") String flatCharges,
                                            @Field("Total") String totalAmount);


    //@FormUrlEncoded
    //@Multipart
    //@Headers("Content-Type: application/json")
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST(WEB_API_SUBMIT_ORDER + "webordersubmit.php")
    Call<SubmitOrderJsonResponse> postOrderBody(@Body JsonObject jsonObejct);

    @GET(WEB_API_ALL_PRODUCTS + "searchproduct.php")
    Call<AllProductsResponse> getAllProducts();

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST(WEB_API_ADD_TO_FAVOURITE + "fav.php")
    Call<ResponseBody> postAddToFavouriteBody(@Body JsonObject jsonObejct);

//    @FormUrlEncoded
//    @POST(WEB_API_SUBMIT_ORDER + "submitdata.php")
//    Call<ResponseBody> postOrderListDeatils3(@Field("productlist[]") List<AddToCart> arrayOfSids);

}
