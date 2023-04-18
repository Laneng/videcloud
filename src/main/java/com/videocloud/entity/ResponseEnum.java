package com.videocloud.entity;

/**
 * Author：Saika(刘江涛)
 * Date：2023/4/3
 * Description：
 */

public enum ResponseEnum {


    SELECT_SUCCESS(0,"查询成功!"),
    SELECT_FAIL(1,"查询失败!"),
    LOGIN_SUCCESS(0,"登录成功!"),
    LOGIN_FAIL(1,"登录失败!"),
    UPDATE_SUCCESS(2,"更新成功!"),
    UPDATE_FAIL(3,"更新失败!"),
    INSERT_SUCCESS(4,"新增成功!"),
    INSERT_FAIL(5,"新增失败!"),
    DELETE_SUCCESS(6,"删除成功!"),
    DELETE_FAIL(7,"删除失败!"),
    UPLOAD_SUCCESS(8,"上传成功!"),
    UPLOAD_FAIL(9,"上传失败");



    private Integer code;
    private String msg;

    private ResponseEnum(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
