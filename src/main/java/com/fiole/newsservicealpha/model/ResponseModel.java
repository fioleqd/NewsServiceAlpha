package com.fiole.newsservicealpha.model;

import com.fiole.newsservicealpha.Enum.ResponseStatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ResponseModel {
    private String errorCode;
    private String errorInfo;
    private Map params;

    public static ResponseModel success(){
        ResponseModel responseModel = new ResponseModel();
        responseModel.errorCode = ResponseStatusEnum.Success.getStatusCode();
        responseModel.errorInfo = ResponseStatusEnum.Success.getStatusInfo();
        return responseModel;
    }

    public static ResponseModel success(Map params){
        ResponseModel responseModel = new ResponseModel();
        responseModel.errorCode = ResponseStatusEnum.Success.getStatusCode();
        responseModel.errorInfo = ResponseStatusEnum.Success.getStatusInfo();
        responseModel.params = params;
        return responseModel;
    }

    public static ResponseModel error(ResponseStatusEnum statusEnum){
        ResponseModel responseModel = new ResponseModel();
        responseModel.errorCode = statusEnum.getStatusCode();
        responseModel.errorInfo = statusEnum.getStatusInfo();
        return responseModel;
    }
}
