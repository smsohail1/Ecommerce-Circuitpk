package com.xekera.Ecommerce.ui.signup

import com.xekera.Ecommerce.R
import com.xekera.Ecommerce.data.rest.INetworkLoginSignup
import com.xekera.Ecommerce.data.rest.XekeraAPI
import com.xekera.Ecommerce.data.rest.response.LoginSignUPErrorResponse
import com.xekera.Ecommerce.data.rest.response.SignUpSuccessResponse
import com.xekera.Ecommerce.di.module.RetrofitModule
import com.xekera.Ecommerce.util.AppConstants.BASE_URL_LIVE
import com.xekera.Ecommerce.util.SessionManager
import com.xekera.Ecommerce.util.Utils
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class SignupModel(utils: Utils, sessionManager: SessionManager, xekeraAPI: XekeraAPI) : SignupMVP.Model {

    private var utils: Utils? = null
    private var sessionManager: SessionManager? = null
    private var xekeraAPI: XekeraAPI? = null;
    //private var retrofitModule: RetrofitModule? = null;


    fun SignupModel(
        utils: Utils,
        sessionManager: SessionManager,
        xekeraAPI: XekeraAPI
    ) {
        //  this.model = model
        this.utils = utils
        this.sessionManager = sessionManager
        this.xekeraAPI = xekeraAPI;
        //  this.retrofitModule=retrofitModule

    }

    override fun signUP(
        userName: String,
        password: String,
        phoneNo: String,
        emailID: String,
        iNetworkLoginSignup: INetworkLoginSignup<SignUpSuccessResponse>
    ) {
        var apiInterface: XekeraAPI = ApiClient.getClient()!!.create(XekeraAPI::class.java)
        var call: Call<SignUpSuccessResponse>
        call = apiInterface.postSignUpDetails(userName, password, emailID,phoneNo)

        //val call = xekeraAPI?.postSignUpDetails(userName, password, emailID)
        call?.enqueue(object : Callback<SignUpSuccessResponse> {
            override fun onResponse(
                call: Call<SignUpSuccessResponse>,
                response: Response<SignUpSuccessResponse>
            ) {
                try {
                    // val status = response.body()!!.getStatus()
                    iNetworkLoginSignup?.onSuccess(response.body())

//                    if (status) {
//                     //   val messageResponse = response.body()!!.getMessage()
//                        iNetworkLoginSignup?.onSuccess(response.body())
//
//                    } else {
//                       // val messageResponse = response.body()!!.getMessage()
//                        iNetworkLoginSignup?.onSuccess(response.body())
//
//                    }

                } catch (ex: Exception) {
                    ex.printStackTrace()
                    //  iNetworkLoginSignup.onError(getMessageResponse(ex.message.toString()))
                }

            }

            override fun onFailure(call: Call<SignUpSuccessResponse>, t: Throwable) {
                iNetworkLoginSignup.onFailure(t)
            }
        })


    }

//    private fun getMessageResponse(text: String): LoginSignUPErrorResponse {
//        val messageResponse = LoginSignUPErrorResponse()
//        messageResponse.message(text);
//        messageResponse.status(false)
//        return messageResponse
//    }


    class ApiClient {

        companion object {
            // val BASE_URL = "https://simplifiedcoding.net/demos/"
            var retrofit: Retrofit? = null

            fun getClient(): Retrofit? {
                if (retrofit == null) {
                    val interceptor = HttpLoggingInterceptor()
                    interceptor.level = HttpLoggingInterceptor.Level.BODY
                    val client = OkHttpClient.Builder().apply {
                        readTimeout(30, TimeUnit.SECONDS)
                        writeTimeout(30, TimeUnit.SECONDS)
                        connectTimeout(30, TimeUnit.SECONDS)
                        addInterceptor(interceptor)
                        addInterceptor { chain ->
                            var request = chain.request()
                            request = request.newBuilder()
                                .build()
                            val response = chain.proceed(request)
                            response
                        }
                    }
                    retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL_LIVE)
                        .client(client.build())

                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

                }

                return retrofit
            }
        }
    }
}


