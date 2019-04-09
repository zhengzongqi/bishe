package com.example.appsdk.okhttp;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;
import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import  com.example.appsdk.okhttp.https.HttpsUtils;

/*请求的发送，请求参数的配置，http支持*/
public class CommonOkHttpClient {
    private static final int TIME_OUT = 30;
    private static OkHttpClient mOkHttpClient;

    static {
        //创建Client对象的构建者
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        //填充超时时间
        okHttpClientBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpClientBuilder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpClientBuilder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpClientBuilder.followRedirects(true);

        //HTTPS支持
        okHttpClientBuilder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });

        okHttpClientBuilder.sslSocketFactory(HttpsUtils.initSSLSocketFactory());
        mOkHttpClient = okHttpClientBuilder.build();
    }
}
