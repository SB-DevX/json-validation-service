package com.sb.jvs.processor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sb.jvs.repository.MongoRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FetchJsonSchemasProcessor {
    private static final Map<String, JsonNode> jsonSchemasMap = new HashMap<>();
    private final ObjectMapper mapper = new ObjectMapper();
    
    @Autowired
    @Qualifier("mongoJsonSchemasRepository")
    private MongoRepository<Object> repository;
    
    @PostConstruct
    private void fetchJsonSchemas() {
        List<Object> jsonschemas = repository.fetchAll();
        
        jsonschemas.forEach(objectSchema -> {
            JsonNode jsonSchema = mapper.convertValue(objectSchema, JsonNode.class);
            jsonSchemasMap.put(jsonSchema.get("key").asText(), jsonSchema);
        });
    }
    
    public static JsonNode getJsonSchema(String key) {
        return FetchJsonSchemasProcessor.jsonSchemasMap.get(key);
    }
}
