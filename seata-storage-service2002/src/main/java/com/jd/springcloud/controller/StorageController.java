package com.jd.springcloud.controller;

import com.jd.springcloud.domian.CommonResult;
import com.jd.springcloud.service.StorageService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class StorageController {

    @Resource
    private StorageService storageService;
    @PostMapping("/storage/decrease")
    public CommonResult decrease(@RequestParam("productId") Long productId, @RequestParam("count")Integer count){
         storageService.update(productId,count);
         return new CommonResult(200,"库存扣除成功");
    }
}
