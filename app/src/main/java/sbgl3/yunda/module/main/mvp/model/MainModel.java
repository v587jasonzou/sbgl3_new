package sbgl3.yunda.module.main.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import sbgl3.yunda.module.login.Entry.LoginReponsBody;
import sbgl3.yunda.module.main.Entry.MenuBean;
import sbgl3.yunda.module.main.MainApi;
import sbgl3.yunda.module.main.mvp.contract.MainContract;


@ActivityScope
public class MainModel extends BaseModel implements MainContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MainModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<List<MenuBean>> getMenus() {
        return mRepositoryManager.obtainRetrofitService(MainApi.class).getMenu().subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<LoginReponsBody> getTotos() {
        return mRepositoryManager.obtainRetrofitService(MainApi.class).getTodos().subscribeOn(Schedulers.io());
    }
}