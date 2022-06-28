package com.nodayst.promotion.common.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseDto {

    private String status;
    private String message;

    public static String SUCCESS = "SUCCESS";
    public static String FAIL = "FAIL";
}
