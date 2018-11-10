package com.xekera.Ecommerce.data.rest.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MessageResponse {
    @SerializedName("MessageCode")
    @Expose
    private String messageCode;
    @SerializedName("MessageText")
    @Expose
    private String messageText;

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }



    public static MessageResponse getMessageResponse(String code, String text){
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessageCode(code);
        messageResponse.setMessageText(text);
        return messageResponse;
    }
}
