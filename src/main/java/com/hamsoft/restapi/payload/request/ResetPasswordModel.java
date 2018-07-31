package com.hamsoft.restapi.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordModel {

    @NotBlank
    String resetKey;

    @NotBlank
    String password;
}
