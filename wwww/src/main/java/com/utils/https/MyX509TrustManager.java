package com.utils.https;

import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * 证书信任管理类
 * @author LaoWang on 2017/11/11.
 *
 */
public class MyX509TrustManager implements X509TrustManager {

    /**
     * 检查客户端的证书，若不信任该证书则抛出异常
     */
    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
        // TODO Auto-generated method stub

    }

    /**
     * 检查服务器的证书，若不信任该证书同样抛出异常,如果是空函数体，则信任所有证书
     */
    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
        // TODO Auto-generated method stub

    }

    /**
     * 返回受信任的X509证书数组。
     */
    @Override
    public X509Certificate[] getAcceptedIssuers() {
        // TODO Auto-generated method stub
        return null;
    }

}
