package com.vasu.AuthorizationServer.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class AdminService {

    @CacheEvict(value = {"approvals", "clientDetails", "credentials"}, allEntries = true)
    public void evictCache() {
        log.info("Deleted all the cache entries");
    }
}
