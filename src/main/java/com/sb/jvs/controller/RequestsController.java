package com.sb.jvs.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.sb.jvs.service.RequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("requests")
public class RequestsController {
    @Autowired
    private RequestsService requestsService;
    
    @PostMapping("create")
    public boolean create(@RequestBody JsonNode jsonReq) {
        return this.requestsService.processRequest(jsonReq);
    }
}
