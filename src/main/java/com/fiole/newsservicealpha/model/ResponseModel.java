package com.fiole.newsservicealpha.model;

import com.fiole.newsservicealpha.Enum.ResponseStatusEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseModel {
    private String errorCode;
    private String errorInfo;

    public static ResponseModel success(){
        ResponseModel responseModel = new ResponseModel();
        responseModel.errorCode = ResponseStatusEnum.Success.getStatusCode();
        responseModel.errorInfo = ResponseStatusEnum.Success.getStatusInfo();
        return responseModel;
    }

    public static ResponseModel error(ResponseStatusEnum statusEnum){
        ResponseModel responseModel = new ResponseModel();
        responseModel.errorCode = statusEnum.getStatusCode();
        responseModel.errorInfo = statusEnum.getStatusInfo();
        return responseModel;
    }
}
