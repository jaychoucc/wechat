package com.controller.wechat;



import com.pojo.TextMessage;
import com.service.WechatService;
import com.utils.weixin.MenuManager;
import com.utils.weixin.MessageUtil;
import com.utils.weixin.SignUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
@RequestMapping(value = "/wechat", produces = {"application/json;charset=utf-8","application/xml;charset=utf-8"})
@Api(value = "/微信接口-验证模块", description="验证模块")
public class WechatValidation {

    @Resource
    WechatService wechatService;

    /*token与公众号配置一致*/
    private String token="zjzj1122";

    private static Logger log = LoggerFactory.getLogger(WechatValidation.class);

    @ApiOperation(value = "微信验证", httpMethod = "GET", notes = "微信验证接口 (正确返回echostr)正返有值1000 正返无值1001 错返有值1003 错返无值1004")
    @ResponseBody
    @RequestMapping(value = { "/yanzheng.json" }, method = {RequestMethod.GET,RequestMethod.POST})
    public void validation(HttpServletRequest request,HttpServletResponse response) throws IOException {
        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8");  //微信服务器POST消息时用的是UTF-8编码，在接收时也要用同样的编码，否则中文会乱码；
        response.setCharacterEncoding("UTF-8"); //在响应消息（回复消息给用户）时，也将编码方式设置为UTF-8，
        boolean isGet = request.getMethod().toLowerCase().equals("get");
        PrintWriter out = response.getWriter();
        try {
            if (isGet) {
                String signature = request.getParameter("signature");// 微信加密签名
                String timestamp = request.getParameter("timestamp");// 时间戳
                String nonce = request.getParameter("nonce");// 随机数
                String echostr = request.getParameter("echostr");//随机字符串

                    // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
                if (SignUtil.checkSignature(signature, timestamp, nonce)) {
                    log.info("Connect the weixin server is successful.");
                    response.getWriter().write(echostr);
                } else {
                    log.error("Failed to verify the signature!");
                }
            }else{
                String respMessage = "异常消息！";

                try {
                    respMessage = wechatService.weixinPost(request);
                    out.write(respMessage);
                    log.info("The request completed successfully");
                    log.info("to weixin server "+respMessage);
                } catch (Exception e) {
                    log.error("Failed to convert the message from weixin!");
                }

            }
        } catch (Exception e) {
            log.error("Connect the weixin server is error.");
        }finally{
            out.close();
        }

    }



}
