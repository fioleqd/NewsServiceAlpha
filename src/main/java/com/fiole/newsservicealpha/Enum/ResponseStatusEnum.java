package com.fiole.newsservicealpha.Enum;


public enum ResponseStatusEnum {
    Success("0","Success"),

    LoginInvalidUser("1001","User not exist"),
    LoginInvalidParameters("1002","Login parameters is null"),
    LoginInvalidToken("1003","Invalid token"),
    LoginRepeat("1004","User has login"),

    SubmitUserNotLogin("2001","User not login"),
    SubmitUserNotCurrentLogin("2002","Submit user not online user"),
    AddCommentFailed("2003","Add comment failed"),

    LogoutError("3001","User not login when logout"),

    WrongArticleType("4001","Wrong article type"),

    RegistryPasswordNotSame("5001","Password input twice not same"),
    UserHasExist("5002","User has exist"),
    InvalidUsername("5003","Invalid username"),
    WrongPasswordLength("5004","Wrong password length"),
    InvalidPassword("5005","Invalid password");

    private String statusCode;
    private String statusInfo;
    ResponseStatusEnum(String statusCode,String statusInfo){
        this.statusCode = statusCode;
        this.statusInfo = statusInfo;
    }

    public String getStatusCode(){
        return statusCode;
    }

    public String getStatusInfo() {
        return statusInfo;
    }
}
