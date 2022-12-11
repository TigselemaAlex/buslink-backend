package com.cdg.buslinkbackend.util.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;


@Getter
@JsonPropertyOrder({"httpStatusCode", "message", "data"})
public class ApiResponse<T> {
    private final int httpStatusCode;

    private final String message;

    private final T data;

    private ApiResponse(ApiResponseBuilder builder){
        this.httpStatusCode = builder.httpStatusCode;
        this.message = builder.message;
        this.data = (T) builder.data;
    }


    public static class ApiResponseBuilder<T>{
        private final int httpStatusCode;

        private final String message;

        private  T data;

        public ApiResponseBuilder(int httpStatusCode, String message){
            this.httpStatusCode = httpStatusCode;
            this.message = message;
        }

        public ApiResponseBuilder<T> data (T data){
            this.data = data;
            return this;
        }

        public ResponseEntity<ApiResponse> build(){
            ApiResponse<T> apiResponse = new ApiResponse<>(this);
            return new ResponseEntity<>(apiResponse, HttpStatusCode.valueOf(apiResponse.getHttpStatusCode()));
        }
    }

}
