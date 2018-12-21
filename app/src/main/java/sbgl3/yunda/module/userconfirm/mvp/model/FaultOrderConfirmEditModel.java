package sbgl3.yunda.module.userconfirm.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.base.entry.BaseResponsBean;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import sbgl3.yunda.globle.GlobleApi;
import sbgl3.yunda.module.userconfirm.UserConfirmApi;
import sbgl3.yunda.module.userconfirm.mvp.contract.FaultOrderConfirmEditContract;


@ActivityScope
public class FaultOrderConfirmEditModel extends BaseModel implements FaultOrderConfirmEditContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public FaultOrderConfirmEditModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponsBean> getImages(String idx) {
        return mRepositoryManager.obtainRetrofitService(GlobleApi.class).getImages(idx)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseResponsBean> faultConfirm(String ids) {
        return mRepositoryManager.obtainRetrofitService(UserConfirmApi.class).faultConfirm(ids)
                .subscribeOn(Schedulers.io());
    }
}