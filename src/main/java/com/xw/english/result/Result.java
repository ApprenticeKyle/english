package com.xw.english.result;

public class Result<T> {

    private int code;

    private T data;

    public static <T> Result<T> success(T data) {
        Result<T> res = new Result<>();
        res.setCode(200);
        res.setData(data);
        return res;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
