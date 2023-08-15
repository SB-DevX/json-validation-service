package com.sb.jvs.model;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor(staticName = "pos")
@AllArgsConstructor(staticName = "pos")
@ToString
public class LatLng implements Serializable {
    private String latitude;
    private String longitude;
}
