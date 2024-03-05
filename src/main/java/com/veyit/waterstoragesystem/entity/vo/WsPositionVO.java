package com.veyit.waterstoragesystem.entity.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class WsPositionVO {
    private String name;
    private List<Double> value;
    private Integer status;
}
