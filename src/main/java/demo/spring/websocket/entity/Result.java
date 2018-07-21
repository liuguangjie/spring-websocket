package demo.spring.websocket.entity;

import java.io.Serializable;

/**
 * Created by free on 18-7-21.
 */
public class Result implements Serializable {

    private String code;

    private String msg;

    private Object data;


    public static Result ok(Object data) {
        Result result = new Result();

        result.setCode("200");
        result.setMsg("ok");

        result.setData(data);
        return result;
    }

    public static Result error() {
        Result result = new Result();

        result.setCode("500");
        result.setMsg("userName or passwd error");

        return result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
