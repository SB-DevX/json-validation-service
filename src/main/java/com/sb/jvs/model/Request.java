package com.sb.jvs.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor(staticName = "request")
@AllArgsConstructor(staticName = "request")
@Document("requests")
@ToString
public class Request implements Serializable {
    private String id;
    private LatLng start;
    private LatLng end;
}
