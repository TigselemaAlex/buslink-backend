package com.cdg.buslinkbackend.util.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

/**
 * The ApiResponse class is a class that represents a response object that is
 * returned by the API
 */

@Getter
@JsonPropertyOrder({ "httpStatusCode", "message", "data" })
public class ApiResponse<T> {
    private final int httpStatusCode;

    private final String message;

    private final T data;

    // A constructor that takes in an ApiResponseBuilder object and sets the fields
    // of the ApiResponse
    // object to the fields of the ApiResponseBuilder object.
    private ApiResponse(ApiResponseBuilder builder) {
        this.httpStatusCode = builder.httpStatusCode;
        this.message = builder.message;
        this.data = (T) builder.data;
    }

    /**
     * The ApiResponseBuilder class is a builder class that builds an ApiResponse
     * object
     */
    public static class ApiResponseBuilder<T> {
        private final int httpStatusCode;

        private final String message;

        private T data;

        public ApiResponseBuilder(int httpStatusCode, String message) {
            this.httpStatusCode = httpStatusCode;
            this.message = message;
        }

        public ApiResponseBuilder<T> data(T data) {
            this.data = data;
            return this;
        }

        public ResponseEntity<ApiResponse> build() {
            ApiResponse<T> apiResponse = new ApiResponse<>(this);
            return new ResponseEntity<>(apiResponse, HttpStatusCode.valueOf(apiResponse.getHttpStatusCode()));
        }
    }

}
