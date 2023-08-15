package com.sb.jvs.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor(onConstructor = @__(@Autowired), staticName = "create")
public class MongoRepository<T> {
    private final MongoTemplate template;
    private final Class<T> type;
    private final String collectionName;
    
    public T fetch(Query query) {
        return this.template.findOne(query, this.type, this.collectionName);
    }
    
    public List<T> fetchAll(Query query) {
        return this.template.find(query, this.type, this.collectionName);
    }
    
    public List<T> fetchAll() {
        return this.template.findAll(this.type, this.collectionName);
    }
    
    public T save(T req) {
        return this.template.insert(req, this.collectionName);
    }
    
    public Collection<T> saveAll(Collection<T> reqs) {
        return this.template.insert(reqs, this.collectionName);
    }
}
