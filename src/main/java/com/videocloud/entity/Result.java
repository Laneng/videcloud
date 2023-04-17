package com.videocloud.entity;

/**
 * Author：Saika(刘江涛)
 * Date：2023/4/3
 * Description：
 */

public class Result {

    private Integer code;
    private String msg;
    private Integer count;
    private Object data;

    public Result(ResponseEnum responseEnum, Integer count, Object data) {
        this.code = responseEnum.getCode();
        this.msg = responseEnum.getMsg();
        this.count = count;
        this.data = data;
    }

    public Result() {
    }

    public void setResponse(ResponseEnum responseEnum) {
        this.code = responseEnum.getCode();
        this.msg = responseEnum.getMsg();
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", count=" + count +
                ", data=" + data +
                '}';
    }
}
