package com.veyit.waterstoragesystem.entity.vo;

import com.veyit.waterstoragesystem.entity.po.StatusReason;
import lombok.Data;

@Data
public class WsInfoVO {
    private Integer xsId;
    private double size;
    private String shape;
    private String position;
    private Double longitude;
    private Double latitude;
    private Integer status;
    private Integer isReview;
    private String reviewSituation;
    private StatusReason statusReason;
}
