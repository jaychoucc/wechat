package com.controller.wechat;



import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@RestController
@RequestMapping(value = "/wechat/", produces = {"application/json;charset=utf-8","application/xml;charset=utf-8"})
@Api(value = "/微信接口-验证模块", description="验证模块")
public class WechatValidation {
    /*token与公众号配置一致*/
    private String token="zjzj1122";

    @ApiOperation(value = "微信验证", httpMethod = "POST", notes = "微信验证借口2 正返有值1000 正返无值1001 错返有值1003 错返无值1004")
    @ResponseBody
    @RequestMapping(value = { "/yanzheng.json" },params = "api=getCarCollectionPage", method = RequestMethod.POST)

    public String validation(@RequestParam String timestamp,@RequestParam String nonce,@RequestParam String signature,@RequestParam String echostr){
        List<String> list = new ArrayList<String>();
        list.add(token);
        list.add(timestamp);
        list.add(nonce);
        Collections.sort(list);
        System.out.println(list);
        String pi = "";
        for(int i=0;i<list.size();i++){
            pi += list.get(i);
        }
        pi = DigestUtils.shaHex(pi);

        if(pi.equals(signature)){
            return echostr;
        }else {
            return "";
        }


    }
}
