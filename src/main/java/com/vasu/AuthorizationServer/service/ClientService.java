package com.vasu.AuthorizationServer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang.StringUtils.isEmpty;

@Service
@Slf4j
public class ClientService {
    @Autowired
    private JdbcClientDetailsService clientsDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Cacheable(value = "clientDetails", key = "#clientId", condition = "#newClient != null")
    public ClientDetails fetchClientDetails(String clientId) {
        ClientDetails clientDetails = null;
        try {
            if (clientId != null) {
                clientDetails = clientsDetailsService.loadClientByClientId(clientId);
            } else {
                clientDetails = new BaseClientDetails();
            }
        } catch (InvalidClientException ex) {
            log.error(ex.toString());
        }

        return clientDetails;
    }

    @CachePut(value = "clientDetails", key = "#newClient", condition = "#newClient != null", unless = "#result == null")
    public void editClient(BaseClientDetails clientDetails, String newClient) {
        try {
            if (!isEmpty(clientDetails.getClientSecret()))
                clientDetails.setClientSecret(passwordEncoder().encode(clientDetails.getClientSecret()));
            if (newClient == null) {
                clientsDetailsService.updateClientDetails(clientDetails);
            } else {
                clientsDetailsService.addClientDetails(clientDetails);
            }
            if (!clientDetails.getClientSecret().isEmpty()) {
                clientsDetailsService.updateClientSecret(clientDetails.getClientId(), clientDetails.getClientSecret());
            }
        } catch (NoSuchClientException ex) {
            log.error(ex.toString());
        }

    }


    @CacheEvict(value = "clientDetails", key = "#clientId", condition = "#newClient != null")
    public void deleteClient(String clientId) {
        try {
            clientsDetailsService.removeClientDetails(clientId);
        } catch (NoSuchClientException ex) {
            log.error(ex.toString());
        }

    }


}
