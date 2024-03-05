package com.veyit.waterstoragesystem.entity.po;

import lombok.Data;

@Data
public class StatusReason {
    private Integer statusId;
    private Integer wsId;
    private Integer wsStatus;
    private String reason;
}
