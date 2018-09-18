package com.xuexibao.fastgun.utils;

import com.xuexibao.fastgun.enums.IStatusEnums;
import com.xuexibao.fastgun.enums.impl.ResultEnumImpl;
import com.xuexibao.fastgun.viewobject.ResultVO;

public class ResultVOUtil {
    public  static ResultVO success(Object object){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("success");
        resultVO.setData(object);
        return  resultVO;
    }


    public  static ResultVO success(){
        return  success(null);
    }

    public  static  ResultVO error(Integer code,String msg){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return  resultVO;
    }

    public  static  ResultVO error(IStatusEnums result){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(result.getCode());
        resultVO.setMsg(result.getMsg());
        return  resultVO;
    }


}