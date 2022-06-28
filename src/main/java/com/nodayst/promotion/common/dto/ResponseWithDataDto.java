package com.nodayst.promotion.common.dto;

import lombok.Data;

@Data
public class ResponseWithDataDto<T> extends ResponseDto {
    private T data;

    public ResponseWithDataDto(String status, String message, T data) {
        super(status, message);
        this.data = data;
    }
}
