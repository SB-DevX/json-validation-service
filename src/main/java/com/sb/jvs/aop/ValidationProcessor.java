package com.sb.jvs.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import com.sb.jvs.annotation.ValidateSchema;
import com.sb.jvs.exception.ValidationFailedException;
import com.sb.jvs.processor.FetchJsonSchemasProcessor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

@Component
@Aspect
public class ValidationProcessor {
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    private final Function<String, JsonSchema> jsonSchemaProducer = type -> {
        JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);
        JsonNode jsonSchemaNode = FetchJsonSchemasProcessor.getJsonSchema(type);
        InputStream jsonSchemaStream = Optional.ofNullable(jsonSchemaNode).map(jsonNode -> {
            InputStream inputStream;
            try {
                byte[] bytes = this.objectMapper.writeValueAsBytes(jsonNode);
                inputStream = new ByteArrayInputStream(bytes);
            } catch (JsonProcessingException e) {
                throw new ValidationFailedException(e);
            }
            return inputStream;
        }).orElseThrow(() -> new ValidationFailedException("Json schema is not configured"));
        return factory.getSchema(jsonSchemaStream);
    };
    
    @Before("@annotation(com.sb.jvs.annotation.ValidateSchema)")
    public void doProcess(JoinPoint joinPoint) {
        Object[] signatureArgs = joinPoint.getArgs();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Annotation annotation = method.getAnnotation(ValidateSchema.class);
        String schema = (String) AnnotationUtils.getValue(annotation, "schema");
        Set<ValidationMessage> errors = this.jsonSchemaProducer.apply(schema).validate((JsonNode) signatureArgs[0]);
        if(!errors.isEmpty()) {
            throw new ValidationFailedException("Json validation failed", errors);
        }
    }
}
