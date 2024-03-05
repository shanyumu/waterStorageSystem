package com.veyit.waterstoragesystem.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回结果信息类
 * @param <T>
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result<T> {
    private int status;
    private String message;
    private T data;

    public Result(int status, String message) {
        this.status = status;
        this.message = message;
    }
}

