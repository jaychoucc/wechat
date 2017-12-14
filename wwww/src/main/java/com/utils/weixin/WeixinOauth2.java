package com.utils.weixin;

public class WeixinOauth2 {

    private String APPID;

    /**
     * 生成用于获取access_token的Code的Url
     *
     */
    public String getRequestCodeUrl(String redirectUrl) {
        return String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect",
                APPID, redirectUrl, "snsapi_userinfo", "xxxx_state");
    }
}
