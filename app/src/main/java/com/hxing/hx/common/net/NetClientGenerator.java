package com.hxing.hx.common.net;

import android.text.TextUtils;

import com.hxing.hx.common.constants.Constants;
import com.hxing.hx.common.base.MyApplication;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;

/**
 * @Description描述:
 * @Author作者: hx
 */
public class NetClientGenerator {

    public static final String API_BASE_URL = Constants.SERVER_URL + "/";

    private static OkHttpClient httpClient = null;

    private static final int TIMEOUT_SECONDS = 5;//5秒超时

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create());//rxjava



    public static <S> S createService(Class<S> serviceClass) {

        /* 添加一个拦截器 */

        if (httpClient==null) {
            createHttpClient();
        }



        Retrofit retrofit = builder.client(httpClient).build();
        return retrofit.create(serviceClass);
    }




    private static void createHttpClient(){
        httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {

                Request newRequest;
                    /* Header加入cookie */
                String cookieStr = MyApplication.getInstance().getCookieStr();
                if (!TextUtils.isEmpty(cookieStr))
                {
                    newRequest = chain.request().newBuilder().addHeader("Cookie",cookieStr).build();
                }
                else
                {
                    newRequest = chain.request();
                }



                Response response = chain.proceed(newRequest);


                    /*
                     * 取出cookie保存
                     * 短信验证码和登录接口都有cookie返回
                      * */
                if (response.request().url().toString().equalsIgnoreCase(API_BASE_URL + "api/Account/Login")
                        || response.request().url().toString().startsWith((API_BASE_URL + "api/Account/SendVerifyCode/"))) {

                    List<String> cookieStringList = response.headers("Set-Cookie");
                    for (String str : cookieStringList) {
                        String[] tempStrs = str.split(";");
                        MyApplication.getInstance().setCookieStr(tempStrs[0]);
                        return response;
                    }
                }

                return response;

            }
        })
                .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .build();


    }



    /* 将image放入RequestBody的map中 */
    public static void putImageToRequestBodyMap(Map<String, RequestBody> requestBodyMap, File imageFile)
    {
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), imageFile);
        String key = "image\";filename=\"" + imageFile.getName();
        requestBodyMap.put(key, requestBody);
    }

}
