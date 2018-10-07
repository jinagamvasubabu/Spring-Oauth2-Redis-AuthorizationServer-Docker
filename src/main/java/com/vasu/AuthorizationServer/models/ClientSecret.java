package com.vasu.AuthorizationServer.models;

import lombok.Data;

@Data
public class ClientSecret {
    private String clientId;
    private String existingClientSecret;
    private String newClientSecret;
    private String userName;
    private String existingPassword;
    private String newPassword;
    private ResetType resetType;
}
