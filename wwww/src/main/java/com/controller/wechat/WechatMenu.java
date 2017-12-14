package com.controller.wechat;

import com.dynamic.Response;
import com.pojo.AccessToken;
import com.pojo.SNSUserInfo;
import com.pojo.WeixinOauth2Token;
import com.utils.CommonUtil;
import com.utils.weixin.AdvancedUtil;
import com.utils.weixin.MenuManager;
import com.utils.weixin.WeixinUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


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
//        String appId = "";
//        // 第三方用户唯一凭证密钥
//        String appSecret = "";

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

    @RequestMapping(value = "/redirect.json", method = RequestMethod.GET)
    public void weixinRedirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa7634b426ba0e588&redirect_uri=http%3a%2f%2f3609662f.ngrok.io%2fwwww%2fwechat%2foauth.json?response_type=code&scope=snsapi_base&state=1&connect_redirect=1#wechat_redirect");
    }

    @RequestMapping(value = "/oauth2.json", method = RequestMethod.GET)
    public  void weixinOAuth2(@RequestParam String code){
        System.out.println("userinfo:"+code);
        WeixinOauth2Token weixinOauth2Token = AdvancedUtil.getOauth2AccessToken("wxa7634b426ba0e588","47e32a1df37dfec1a14628ad64897cf5",code);
        //是否获取到openId
        System.out.println("userinfo"+weixinOauth2Token.getOpenId());
        System.out.println("userinfo"+weixinOauth2Token.getAccessToken());
        SNSUserInfo userInfo = AdvancedUtil.getSNSUserInfo(weixinOauth2Token.getAccessToken(),weixinOauth2Token.getOpenId());
        System.out.println(userInfo.getNickname());
    }

    @RequestMapping(value = "/oauth.json", method = RequestMethod.GET)
    public String weixinOAuth(@RequestParam String code,HttpServletResponse response) throws IOException {

        System.out.println(code);
        WeixinOauth2Token weixinOauth2Token = AdvancedUtil.getOauth2AccessToken("wxa7634b426ba0e588","47e32a1df37dfec1a14628ad64897cf5",code);
        //是否获取到openId
        System.out.println(weixinOauth2Token.getOpenId());
        System.out.println(weixinOauth2Token.getAccessToken());
        //使用openid查库，有数据就截止，否则使用userinfo；

        response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa7634b426ba0e588&redirect_uri=http%3a%2f%2f3609662f.ngrok.io%2fwwww%2fwechat%2foauth2.json?response_type=code&scope=snsapi_userinfo&state=1&connect_redirect=1#wechat_redirect");




        /*
        //得到code
        String CODE = request.getParameter("code");
        String APPID = "你的APPID";
        String SECRET = "你的SECRET";
        //换取access_token 其中包含了openid
        String URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code".replace("APPID", APPID).replace("SECRET", SECRET).replace("CODE", CODE);
        //URLConnectionHelper是一个模拟发送http请求的类
        String jsonStr = URLConnection.sendGet(URL);
        //System.out.println(jsonStr);
        //out.print(jsonStr);
        /*
        JSONObject jsonObj = new JSONObject(jsonStr);
        String openid = jsonObj.get("openid").toString();
        */
        //有了用户的opendi就可以的到用户的信息了
        //地址为https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
        //得到用户信息之后返回到一个页面
        /*
        model.addAttribute("user", wechatUser);
        return "vip/userInfo";
        */

        return "";

    }

}
