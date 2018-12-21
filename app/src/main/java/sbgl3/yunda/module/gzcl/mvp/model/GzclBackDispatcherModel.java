package sbgl3.yunda.module.gzcl.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.base.entry.BaseResponsBean;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import sbgl3.yunda.module.gzcl.mvp.contract.GzclBackDispatcherContract;
import sbgl3.yunda.module.tpgzpg.TpgzpgApi;


@ActivityScope
public class GzclBackDispatcherModel extends BaseModel implements GzclBackDispatcherContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public GzclBackDispatcherModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponsBean> back(String ids, String backReason, String backPersonDuty) {
        return mRepositoryManager.obtainRetrofitService(TpgzpgApi.class).back(ids,backReason,backPersonDuty)
                .subscribeOn(Schedulers.io());
    }
}