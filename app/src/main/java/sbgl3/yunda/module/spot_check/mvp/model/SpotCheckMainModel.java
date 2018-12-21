package sbgl3.yunda.module.spot_check.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.base.entry.BaseResponsBean;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import sbgl3.yunda.module.spot_check.SpotApi;
import sbgl3.yunda.module.spot_check.entity.SpotCheckResponse;
import sbgl3.yunda.module.spot_check.mvp.contract.SpotCheckMainContract;


@ActivityScope
public class SpotCheckMainModel extends BaseModel implements SpotCheckMainContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public SpotCheckMainModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<SpotCheckResponse> getSpotCheck(String code) {
        return mRepositoryManager.obtainRetrofitService(SpotApi.class).getSpotCheck(code)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseResponsBean> ChangeItemStatus(String idx, String statu) {
        return mRepositoryManager.obtainRetrofitService(SpotApi.class).SaveItemStatus(idx,statu)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseResponsBean> ConfirmSpot(String entityJson) {
        return mRepositoryManager.obtainRetrofitService(SpotApi.class).ConfirmSpot(entityJson)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseResponsBean> StartEquip(String idx) {
        return mRepositoryManager.obtainRetrofitService(SpotApi.class).StartEquip(idx)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<SpotCheckResponse> EndEquip(String idx) {
        return mRepositoryManager.obtainRetrofitService(SpotApi.class).EndEquip(idx)
                .subscribeOn(Schedulers.io());
    }
}