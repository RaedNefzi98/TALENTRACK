package com.hiring.hiring.auth.dto;

@Value
public class JwtAuthenticationResponse {
    private String accessToken;
    private UserInfo user;
}