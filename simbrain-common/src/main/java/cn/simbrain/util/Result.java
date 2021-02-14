package cn.simbrain.util;

import java.io.Serializable;

/**
 * @author huowei
 * @version 1.0.0
 * @description 统一Controller返回值
 * @date 2021/2/13
 */
public class Result implements Serializable {

    private Integer code;
    private String msg;
    private Object data;

    public Result() {}

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private void setResultCode(ResultCode code) {
        this.code = code.code();
        this.msg = code.message();
    }

    /***
     * @description: 操作成功，没有返回的数据
     * @return: cn.simbrain.util.Result
     */
    public static Result success() {
        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }

    /**
     * @description: 操作成功，有返回的数据
     * @Param data: 数据实体
     * @return: cn.simbrain.util.Result
     */
    public static Result success(Object data) {
        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        result.setData(data);
        return result;
    }

    /**
     * @description: 操作失败，没有返回的数据
     * @Param resultCode: 错误码及错误原因
     * @return: cn.simbrain.util.Result
     */
    public static Result failure(ResultCode resultCode) {
        Result result = new Result();
        result.setResultCode(resultCode);
        return result;
    }

    /**
     * @description: 操作失败，有返回的数据
     * @Param resultCode: 错误码及错误原因
     * @Param data: 数据实体
     * @return: cn.simbrain.util.Result
     */
    public static Result failure(ResultCode resultCode, Object data) {
        Result result = new Result();
        result.setResultCode(resultCode);
        result.setData(data);
        return result;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
