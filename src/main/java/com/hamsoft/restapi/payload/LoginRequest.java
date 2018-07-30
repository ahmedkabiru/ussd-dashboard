package com.hamsoft.restapi.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {


    @NotNull
    private String username;

    @NotNull
    private String password;

    private Boolean rememberMe = false;

    public Boolean isRememberMe() {
        return rememberMe;
    }

}
