package com.imooc.utils;

import com.imooc.VO.ResultVO;

/**
 * @author 王兴明
 * @date 2019/5/13 16:57
 */

public class ResultVOUtils {
    public static ResultVO success(Object object){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(1);
        resultVO.setMsg("成功");
        resultVO.setData(object);
        return resultVO;
    }

    public static ResultVO success(){
        return success(null);
    }

    public static ResultVO error(Integer code, String msg){
        ResultVO resultVO = new ResultVO();
        resultVO.setMsg(msg);
        resultVO.setCode(code);
        return resultVO;
    }
}
