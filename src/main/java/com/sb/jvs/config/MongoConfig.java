package com.sb.jvs.config;

import com.mongodb.client.MongoClient;
import com.sb.jvs.model.Request;
import com.sb.jvs.repository.MongoRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories
public class MongoConfig {
    @Value("${mongo.jsonschema.collection.name}")
    private String jsonschemaCollectionName;
    
    @Value("${mongo.request.collection.name}")
    private String requestCollectionName;
    
    @Value("${mongo.config.db.name}")
    private String configDb;
    
    @Value("${mongo.request.db.name}")
    private String requestDb;
    
    @Bean("configDbTemplate")
    public MongoTemplate configDbTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, this.configDb);
    }
    
    @Bean("requestDbTemplate")
    @Primary
    public MongoTemplate requestDbTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, this.requestDb);
    }
    
    @Bean("mongoJsonSchemasRepository")
    public MongoRepository<Object> mongoJsonSchemasRepository(@Qualifier("configDbTemplate") MongoTemplate template) {
        return MongoRepository.create(template, Object.class, this.jsonschemaCollectionName);
    }
    
    @Bean("mongoRequestsRepository")
    public MongoRepository<Request> mongoRequestRepository(@Qualifier("requestDbTemplate") MongoTemplate template) {
        return MongoRepository.create(template, Request.class, this.requestCollectionName);
    }
}
