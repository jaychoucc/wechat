package com.utils.https;

import net.sf.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.security.SecureRandom;

/**
 * Created by LaoWang on 2017/11/11.
 */
public class HttpsUtil {
    /**
     * 发送Https请求并获取结果
     * @param requestUrl
     * @param method
     * @param data
     * @return
     */
    public static JSONObject httpsRequest(String requestUrl, String method, String data){
        JSONObject jsonObject=null;
        StringBuffer sb=new StringBuffer();

        try {
            //创建SSLContext对象,并使用我们指定的信任管理器初始化
            TrustManager[] tm={new MyX509TrustManager()};
            SSLContext sslContext= SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new SecureRandom());

            //获取SSLSocketFactory对象
            SSLSocketFactory ssf=sslContext.getSocketFactory();

            //创建HttpsURLConnection对象，并设置其SSLSocketFactory对象
            URL url=new URL(requestUrl);
            HttpsURLConnection conn=(HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);

            //设置连接参数
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod(method);//设置请求方式

            if(method.equals("GET")){
                conn.connect();
            }
            if(data!=null){
                OutputStream os=conn.getOutputStream();
                os.write(data.getBytes("utf-8"));
                os.close();
            }

            InputStream is=conn.getInputStream();
            InputStreamReader isr=new InputStreamReader(is,"utf-8");
            BufferedReader br=new BufferedReader(isr);

            String str=null;
            while((str=br.readLine())!=null){
                sb.append(str);
            }
            //清理
            br.close();
            isr.close();
            is.close();
            is=null;
            conn.disconnect();
            //转化为JSON对象
            jsonObject=JSONObject.fromObject(sb.toString());

        } catch (ConnectException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonObject;

    }
}
