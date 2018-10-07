package com.vasu.AuthorizationServer.service;

import com.vasu.AuthorizationServer.domain.Credentials;
import com.vasu.AuthorizationServer.models.ClientSecret;
import com.vasu.AuthorizationServer.repository.CredentialRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class SecretsService {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private CredentialRepository credentialRepository;

    @Autowired
    private JdbcClientDetailsService clientsDetailsService;


    public void reset(ClientSecret secret) throws NoSuchClientException, UnauthorizedClientException {
        if (!StringUtils.isEmpty(secret.getNewClientSecret())) {
            log.info("Reseting client secret for the clientId={}", secret.getClientId());
            ClientDetails clientDetails = clientsDetailsService.loadClientByClientId(secret.getClientId());

            if (clientDetails == null) throw new NoSuchClientException("Forbidden: No Such Client Exists");

            //password matching
            if (!passwordEncoder().matches(secret.getExistingClientSecret(), clientDetails.getClientSecret())) {
                throw new UnauthorizedClientException("Forbidden: Old Client_secret doesn't match with the records");
            }

            clientsDetailsService.updateClientSecret(secret.getClientId(), passwordEncoder().encode(secret.getNewClientSecret()));
            log.info("client secret reset complete for the clientId={}", secret.getClientId());
        }

        if (!StringUtils.isEmpty(secret.getNewPassword())) {
            log.info("Reseting Password for the userName={}", secret.getUserName());
            Credentials credentials = credentialRepository.findByNameAndEnabledTrue(secret.getUserName());

            if (!passwordEncoder().matches(secret.getExistingPassword(), credentials.getPassword())) {
                throw new UnauthorizedClientException("Forbidden: Old Password doesn't match with the records");
            }
            credentials.setPassword(passwordEncoder().encode(secret.getNewPassword()));
            credentialRepository.save(credentials);
            log.info("Password reset complete for the userName={}", secret.getUserName());
        }
    }
}
