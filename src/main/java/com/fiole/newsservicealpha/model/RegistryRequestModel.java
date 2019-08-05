package com.fiole.newsservicealpha.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class RegistryRequestModel {
    @NotEmpty
    String username;
    @NotEmpty
    String nickname;
    @NotEmpty
    String password;
    @NotEmpty
    String rePassword;
}
