package com.vasu.AuthorizationServer.api;

import com.vasu.AuthorizationServer.models.ClientSecret;
import com.vasu.AuthorizationServer.models.ResetType;
import com.vasu.AuthorizationServer.service.SecretsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.apache.commons.lang.StringUtils.isEmpty;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/secrets")
@Log4j2
public class SecretsController {

    @Autowired
    private SecretsService secretsService;

    @RequestMapping(value = "/reset", method = POST)
    public ResponseEntity<String> reset(ClientSecret clientSecret) {
        log.info("Client "+clientSecret.getClientId()+"started reseting "+clientSecret.getResetType().toString());
        ResponseEntity<String> responseEntity = validateClientSecretMandatoryFields(clientSecret);
        if (responseEntity != null) return responseEntity;

        try {
            secretsService.reset(clientSecret);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    private ResponseEntity<String> validateClientSecretMandatoryFields(ClientSecret clientSecret) {
        if (clientSecret.getResetType() == null || !ResetType.contains(clientSecret.getResetType().toString())) {
            return new ResponseEntity<>("Bad Request: ResetType missing", HttpStatus.BAD_REQUEST);
        }

        if (clientSecret.getResetType() == ResetType.CLIENT_CREDENTIALS) {
            if (isEmpty(clientSecret.getClientId())) {
                return new ResponseEntity<>("Bad Request: ClientId missing", HttpStatus.BAD_REQUEST);
            }
            if (isEmpty(clientSecret.getExistingClientSecret()) && !isEmpty(clientSecret.getNewClientSecret())) {
                return new ResponseEntity<>("Bad Request: Existing Client Secret is missing", HttpStatus.BAD_REQUEST);
            } else if (isEmpty(clientSecret.getNewClientSecret())) {
                return new ResponseEntity<>("Bad Request: New Client Secret can't be empty", HttpStatus.BAD_REQUEST);
            }
        } else if (clientSecret.getResetType() == ResetType.PASSWORD) {
            if (isEmpty(clientSecret.getUserName())) {
                return new ResponseEntity<>("Bad Request: Username missing", HttpStatus.BAD_REQUEST);
            }
            if (isEmpty(clientSecret.getExistingPassword()) && !isEmpty(clientSecret.getNewPassword())) {
                return new ResponseEntity<>("Bad Request: Existing Password is missing", HttpStatus.BAD_REQUEST);
            } else if (isEmpty(clientSecret.getNewPassword())) {
                return new ResponseEntity<>("Bad Request: New password can't be empty", HttpStatus.BAD_REQUEST);
            }
        }
        return null;
    }
}
