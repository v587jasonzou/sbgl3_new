package sbgl3.yunda.module.main;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import sbgl3.yunda.module.login.Entry.LoginReponsBody;
import sbgl3.yunda.module.main.Entry.MenuBean;

public interface MainApi {
    /**获取用户菜单**/
    @GET("login/purview")
    Observable<List<MenuBean>> getMenu();
    /**获取用户待办**/
    @GET("base/getToDoListContext.do")
    Observable<LoginReponsBody> getTodos();

}
