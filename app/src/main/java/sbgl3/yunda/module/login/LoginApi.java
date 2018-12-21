package sbgl3.yunda.module.login;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import sbgl3.yunda.module.login.Entry.LoginReponsBody;

public interface LoginApi {
    //登录
    @FormUrlEncoded
    @POST("login/app")
    Observable<LoginReponsBody> Login(@Field("userid") String userid,
                                      @Field("userpwd") String userpwd);


}
