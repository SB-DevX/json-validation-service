package com.sb.jvs.model;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor(staticName = "msg")
@AllArgsConstructor(staticName = "msg")
@ToString
public class RuntimeMessage implements Serializable {
    private String message;
}
