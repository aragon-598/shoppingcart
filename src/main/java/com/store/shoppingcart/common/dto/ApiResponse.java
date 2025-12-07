package com.store.shoppingcart.common.dto;

public record ApiResponse<T>(
    int status,
    T data,
    Object errors
) {
    public static <T> ApiResponse<T> success(int status, T data) {
        return new ApiResponse<>(status, data, null);
    }
    
    public static <T> ApiResponse<T> error(int status, Object errors) {
        return new ApiResponse<>(status, null, errors);
    }
}
