package com.sb.jvs.model;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor(staticName = "msg")
@AllArgsConstructor(staticName = "msg")
@ToString
public class ValidationFailedMessage implements Serializable {
    private String message;
    private List<String> details;
}
