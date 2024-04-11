package com.schedular.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class JWTResponse {
    private String tokenType="Bearer";
    private String token;

    public JWTResponse(String token) {
        this.token = token;
    }
}
