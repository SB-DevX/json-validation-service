package com.sb.jvs.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sb.jvs.annotation.ValidateSchema;
import com.sb.jvs.model.Request;
import com.sb.jvs.repository.MongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class RequestsService {
    private final ObjectMapper mapper = new ObjectMapper();
    
    @Autowired
    @Qualifier("mongoRequestsRepository")
    private MongoRepository<Request> repository;
    
    @ValidateSchema(schema = "request")
    public boolean processRequest(JsonNode jsonReq) {
        Request req = this.mapper.convertValue(jsonReq, Request.class);
        try {
            this.repository.save(req);
            return true;
        }catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
