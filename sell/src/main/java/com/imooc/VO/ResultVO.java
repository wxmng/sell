package com.imooc.VO;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 返回最外层的对象
 * @author 王兴明
 * @date 2019/5/13 14:54
 */
@Data
public class ResultVO<T> implements Serializable {

    private static final long serialVersionUID = 4854804285819434850L;
    //错误码
    private int code;
    //提示信息
    private String msg;
    //数据内容
    private T data;

}
