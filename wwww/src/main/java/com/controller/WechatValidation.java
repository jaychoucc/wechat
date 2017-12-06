package com.controller;


import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@ResponseBody
@Controller
public class WechatValidation {
    private String token="zjzj1122";

    @RequestMapping("/yanzheng")
    public String validation(String signature,String timestamp,String nonce,String echostr){
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
