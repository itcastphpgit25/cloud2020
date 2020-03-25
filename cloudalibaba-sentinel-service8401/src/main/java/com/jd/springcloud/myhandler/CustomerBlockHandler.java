package com.jd.springcloud.myhandler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.entities.CommonResult;

/**
 *
 * sentinel的自定义类下的统一处理的兜底方法
 */
public class CustomerBlockHandler {

    public static CommonResult handlerException2(BlockException exception){
        return new CommonResult(444,exception.getClass().getCanonicalName()+"服务不可用01");
    }

    public static CommonResult handlerException3(BlockException exception){
        return new CommonResult(444,exception.getClass().getCanonicalName()+"服务不可用02");
    }
}
