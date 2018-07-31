package com.hamsoft.restapi.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserChangePasswordModel {

    @NotBlank
    String oldPassword;

    @NotBlank
    String newPassword;

}
