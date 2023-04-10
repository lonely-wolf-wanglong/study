package com.example.demo.entity;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <ul>
 * <li>Title: 昆易科技 R</li>
 * <li>Description:  通用返回类 </li>
 * <li>Copyright: Copyright (c) 2022</li>
 * <li>Company: http://www.kun-yi.com</li>
 * </ul>
 *
 * @author shenggs
 * @version v1.0
 * @date 2022/3/16
 */
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class R<T> implements Serializable {

    @Getter
    @Setter
    private int code;

    @Getter
    @Setter
    private String msg;

    @Getter
    @Setter
    private String extCode;

    @Getter
    @Setter
    private T data;

    public static <T> R<T> ok() {
        return restResult(null, CommonConstants.SUCCESS, null);
    }

    public static <T> R<T> ok(T data) {
        return restResult(data, CommonConstants.SUCCESS, null);
    }

    public static <T> R<T> ok(String msg) {
        return restResult(null, CommonConstants.SUCCESS, msg);
    }

    public static <T> R<T> ok(T data, String msg) {
        return restResult(data, CommonConstants.SUCCESS, msg);
    }

    public static <T> R<T> ok(String msg,T data) {
        return restResult(data, CommonConstants.SUCCESS, msg);
    }

    public static <T> R<T> fail() {
        return restResult(null, CommonConstants.FAIL, null);
    }

    public static <T> R<T> fail(String msg) {
        return restResult(null, CommonConstants.FAIL, msg);
    }

    public static <T> R<T> fail(T data) {
        return restResult(data, CommonConstants.FAIL, null);
    }

    public static <T> R<T> fail( String msg, int code) {
        return restResult(null, code, msg);
    }

    public static <T> R<T> fail(T data, String msg) {
        return restResult(data, CommonConstants.FAIL, msg);
    }

    public static <T> R<T> fail(String msg, String extCode) {
        return restResult(null, CommonConstants.FAIL, msg, extCode);
    }

    private static <T> R<T> restResult(T data, int code, String msg) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    private static <T> R<T> restResult(T data, int code, String msg,String extCode) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        apiResult.setExtCode(extCode);
        return apiResult;
    }
}
