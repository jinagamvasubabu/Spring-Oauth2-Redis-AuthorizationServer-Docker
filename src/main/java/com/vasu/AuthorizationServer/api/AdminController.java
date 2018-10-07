package com.vasu.AuthorizationServer.api;

import com.vasu.AuthorizationServer.service.AdminService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/deleteAllCache", method = RequestMethod.POST)
    public ResponseEntity<String> deleteAllCache() {
        log.info("Deleting all entries from caches!!!");
        adminService.evictCache();
        return new ResponseEntity<>("<h1>Cache Cleared</h1>", HttpStatus.OK);
    }
}
