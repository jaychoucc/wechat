package com.controller.wechat;

import com.dynamic.Response;
import com.pojo.AccessToken;
import com.utils.weixin.MenuManager;
import com.utils.weixin.WeixinUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/wechat/", produces = {"application/json;charset=utf-8","application/xml;charset=utf-8"})
@Api(value = "/菜单接口-菜单模块", description="菜单模块")
public class WechatMenu {

    private static Logger log = LoggerFactory.getLogger(MenuManager.class);

    @ApiOperation(value = "菜单模块", httpMethod = "GET", notes = "微信菜单模块接口 正返有值1000 正返无值1001 错返有值1003 错返无值1004")
    @ResponseBody
    @RequestMapping(value = { "/menu.json" }, method = RequestMethod.GET)
    public Response menu(@RequestParam String appId, @RequestParam String appSecret){
//        // 第三方用户唯一凭证
//        String appId = "wxa7634b426ba0e588";
//        // 第三方用户唯一凭证密钥
//        String appSecret = "47e32a1df37dfec1a14628ad64897cf5";

        // 调用接口获取access_token
        AccessToken at = WeixinUtil.getAccessToken(appId, appSecret);

        if (null != at) {
            // 调用接口创建菜单
            int result = WeixinUtil.createMenu(MenuManager.getMenu(), at.getToken());

            // 判断菜单创建结果
            if (0 == result) {
                log.info("菜单创建成功！");
                return Response.success("菜单创建成功", 1000);
            }
            else {
                log.info("菜单创建失败，错误码：" + result);
                return Response.success("菜单创建失败", 1004);
            }
        }
        return Response.success("菜单创建成功", 1000);
    }
}
