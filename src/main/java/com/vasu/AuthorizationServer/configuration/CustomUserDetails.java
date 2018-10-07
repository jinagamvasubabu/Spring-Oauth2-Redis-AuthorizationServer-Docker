package com.vasu.AuthorizationServer.configuration;

import com.vasu.AuthorizationServer.domain.Credentials;
import com.vasu.AuthorizationServer.repository.CredentialRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Log4j2
public class CustomUserDetails implements UserDetailsService {

    @Autowired
    private CredentialRepository credentialRepository;

    @Override
    @Cacheable(value="credentials", key="#username")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Credentials credentials = credentialRepository.findByNameAndEnabledTrue(username);
        if (credentials == null) {
            log.warn("User" + username + "can not be found");
            throw new UsernameNotFoundException("User" + username + "can not be found");
        }
        return new User(credentials.getName(), credentials.getPassword(), credentials.isEnabled(), true, true, true, credentials.getAuthorities());
    }
}
