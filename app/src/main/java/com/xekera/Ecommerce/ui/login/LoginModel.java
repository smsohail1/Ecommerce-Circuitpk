package com.xekera.Ecommerce.ui.login;

import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.data.rest.XekeraAPI;
import com.xekera.Ecommerce.data.rest.response.MessageResponse;
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
    public void signIn(String courierCode, String password ) {

    }

    private MessageResponse getMessageResponse(String code, String text){
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessageCode(code);
        messageResponse.setMessageText(text);
        return messageResponse;
    }
}
