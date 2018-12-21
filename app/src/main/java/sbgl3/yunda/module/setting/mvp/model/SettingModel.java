package sbgl3.yunda.module.setting.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.base.entry.BaseResponsBean;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import sbgl3.yunda.module.setting.SettingApi;
import sbgl3.yunda.module.setting.mvp.contract.SettingContract;


@ActivityScope
public class SettingModel extends BaseModel implements SettingContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;
    @Inject
    public SettingModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponsBean> DownLoadData(String tableName, String limit, String start) {
        return mRepositoryManager.obtainRetrofitService(SettingApi.class).upLoadInspectPlanEquip(tableName,limit,start)
              .subscribeOn(Schedulers.io());
    }
}