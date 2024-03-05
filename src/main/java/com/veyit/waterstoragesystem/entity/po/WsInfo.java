package com.veyit.waterstoragesystem.entity.po;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WsInfo {
    @JsonProperty(value = "xsId")
    private Integer xsId;
    private double size;
    private String shape;
    private String position;
    private Double longitude;
    private Double latitude;
    private Integer status;
    @JsonProperty(value = "isReview")
    private Integer isReview;
    @JsonProperty(value = "reviewSituation")
    private String reviewSituation;
}
