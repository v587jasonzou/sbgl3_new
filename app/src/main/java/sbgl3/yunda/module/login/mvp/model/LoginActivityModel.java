package sbgl3.yunda.module.login.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import sbgl3.yunda.module.login.Entry.LoginReponsBody;
import sbgl3.yunda.module.login.LoginApi;
import sbgl3.yunda.module.login.mvp.contract.LoginActivityContract;


@ActivityScope
public class LoginActivityModel extends BaseModel implements LoginActivityContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public LoginActivityModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<LoginReponsBody> Login(String username, String password) {
        return mRepositoryManager.obtainRetrofitService(LoginApi.class).Login(username,password)
                .subscribeOn(Schedulers.io());
    }
}