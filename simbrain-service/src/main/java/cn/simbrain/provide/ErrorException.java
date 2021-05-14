package cn.simbrain.provide;

/**
 * @author huowei
 * @version 1.0.0
 * @description 自定义异常
 * @date 2021/5/14
 */
public class ErrorException extends Exception{

    /**
     * @description: 无参构造
     * @return: null
     */
    public ErrorException(){
    }

    /**
     * @description: 有参构造
     * @Param message: 出错信息
     * @return: null
     */
    public ErrorException(String message){
        super(message);
    }
}
