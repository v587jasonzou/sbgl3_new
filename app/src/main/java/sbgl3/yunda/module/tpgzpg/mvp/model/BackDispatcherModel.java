package sbgl3.yunda.module.tpgzpg.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.base.entry.BaseResponsBean;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import sbgl3.yunda.module.tpgzpg.TpgzpgApi;
import sbgl3.yunda.module.tpgzpg.mvp.contract.BackDispatcherContract;


@ActivityScope
public class BackDispatcherModel extends BaseModel implements BackDispatcherContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public BackDispatcherModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponsBean> back(String ids, String backReason,String backPersonDuty) {
        return mRepositoryManager.obtainRetrofitService(TpgzpgApi.class).back(ids,backReason,backPersonDuty)
                .subscribeOn(Schedulers.io());
    }
}