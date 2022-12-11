package com.cdg.buslinkbackend.util.response;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseBuilder {

    public ResponseEntity <ApiResponse> buildResponse(int httpStatusCode, String message){
        return new ApiResponse.ApiResponseBuilder<>(httpStatusCode, message).build();
    }

    public ResponseEntity <ApiResponse> buildResponse(int httpStatusCode, String message, Object data){
        return new ApiResponse.ApiResponseBuilder<>(httpStatusCode, message).data(data).build();
    }

}
