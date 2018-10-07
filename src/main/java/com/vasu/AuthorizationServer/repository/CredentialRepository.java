package com.vasu.AuthorizationServer.repository;

import com.vasu.AuthorizationServer.domain.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredentialRepository extends JpaRepository<Credentials,Long> {
    Credentials findByNameAndEnabledTrue(String name);
}
