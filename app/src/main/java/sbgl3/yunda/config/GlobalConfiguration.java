package sbgl3.yunda.config;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.google.gson.GsonBuilder;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.BaseApplication;
import com.jess.arms.base.delegate.AppLifecycles;
import com.jess.arms.di.module.AppModule;
import com.jess.arms.di.module.ClientModule;
import com.jess.arms.di.module.GlobalConfigModule;
import com.jess.arms.http.GlobalHttpHandler;
import com.jess.arms.integration.ConfigModule;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.utilcode.util.SPUtils;
import com.jess.arms.utils.utilcode.util.StringUtils;
import com.jess.arms.utils.utilcode.util.ToastUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import me.jessyan.progressmanager.ProgressManager;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import sbgl3.yunda.constant.ApiUrls;
import sbgl3.yunda.module.login.mvp.ui.activity.LoginActivityActivity;

/**
 * 系统常用框架统一配置类
 * @author 周雪巍
 * @time 2018/09/12
 * */
public class GlobalConfiguration implements ConfigModule {
    @Override
    public void applyOptions(Context context, GlobalConfigModule.Builder builder) {
        //使用builder可以为框架配置一些配置信息
        String url = SPUtils.getInstance().getString("BaseUrl");
        if(StringUtils.isTrimEmpty(url)){
            url = ApiUrls.BaseUrl;
        }else {
            String[] http = url.split(":");
            if(http[0].equals("http")||http[0].equals("https")){
                if(url.charAt(url.length()-1)!='/'){
                    url = url+"/";
                }
            }else {
                ToastUtils.showShort("服务器地址配置错误请重配");
                return;
            }
        }
        //配置BaseUrl
        builder.baseurl(url).gsonConfiguration(new AppModule.GsonConfiguration() {
            @Override
            public void configGson(Context context, GsonBuilder builder) {
                builder
                        .serializeNulls()//支持序列化null的参数
                        .enableComplexMapKeySerialization();//支持将序列化key为object的map,默认只能序列化key为string的map
            }
        }
        //配置OkhttpClient参数
        ).okhttpConfiguration(new ClientModule.OkhttpConfiguration() {
            @Override
            public void configOkhttp(final Context context, OkHttpClient.Builder builder) {
                builder.writeTimeout(30, TimeUnit.SECONDS);
                builder.connectTimeout(30,TimeUnit.SECONDS);
                builder.readTimeout(30,TimeUnit.SECONDS);
                builder.addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder builder = chain.request().newBuilder();
                        builder.addHeader("Connection", "close");
                        return chain.proceed(builder.build());
                    }
                });
                builder.addInterceptor(getHttpLoggingInterceptor());
                // session失效验证
                builder.addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Response response = chain.proceed(request);
                        ResponseBody body = response.body();
                        MediaType contentType = body.contentType();
//                        if (contentType != null && contentType.subtype().equals("html")) {
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//                                new AlertDialog.Builder(context).setTitle("提示")
//                                        .setMessage("当前用户登录超时，请重新登录")
//                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                context.startActivity(new Intent(context, LoginActivityActivity.class));
//                                            }
//                                        }).setCancelable(false).show();
//                            }
//                            return null;
//                        }
                        return response;
                    }
                });
                //cookie配置
                builder.cookieJar(new CookieJar() {

                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        BaseApplication.cookieStore.put(url.host(), cookies);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies = BaseApplication.cookieStore.get(url.host());
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                });
            }
        }
        //retofit配置
        ).retrofitConfiguration(new ClientModule.RetrofitConfiguration() {
            @Override
            public void configRetrofit(Context context, Retrofit.Builder builder) {
                builder .addConverterFactory(ScalarsConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
//                        .baseUrl(url)
                ;
            }
        });
        //网络拦截
        builder.globalHttpHandler(new GlobalHttpHandler() {
            @Override
            public Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response) {
                return response;
            }

            @Override
            public Request onHttpRequestBefore(Interceptor.Chain chain, Request request) {
                return request;
            }
        });

    }

    @Override
    public void injectAppLifecycle(Context context, List<AppLifecycles> lifecycles) {
        //向Application的生命周期中注入一些自定义逻辑

    }

    @Override
    public void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> lifecycles) {
        //向Activity的生命周期中注入一些自定义逻辑
        lifecycles.add(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    @Override
    public void injectFragmentLifecycle(Context context, List<FragmentManager.FragmentLifecycleCallbacks> lifecycles) {
        //向Fragment的生命周期中注入一些自定义逻辑
    }
    /**
     * 日志输出
     * 自行判定是否添加
     *
     * @return
     */
    private HttpLoggingInterceptor getHttpLoggingInterceptor() {
        //日志显示级别
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(
//                new HttpLoggingInterceptor.Logger() {
//            @Override
//            public void log(String message) {
//                Log.d("RxRetrofit", "Retrofit====Message:" + message);
//            }
//        }
        );
        loggingInterceptor.setLevel(level);
        return loggingInterceptor;
    }
}
