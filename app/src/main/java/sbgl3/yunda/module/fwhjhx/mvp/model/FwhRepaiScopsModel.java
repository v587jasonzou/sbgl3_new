package sbgl3.yunda.module.fwhjhx.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.base.entry.BaseResponsBean;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import sbgl3.yunda.SysInfo;
import sbgl3.yunda.module.fwhjhx.RepairApi;
import sbgl3.yunda.module.fwhjhx.mvp.contract.FwhRepaiScopsContract;
import sbgl3.yunda.module.fwhjhx.mvp.entity.RepairScopeCase;
import sbgl3.yunda.module.fwhjhx.mvp.entity.RepairWorkOrder;


@ActivityScope
public class FwhRepaiScopsModel extends BaseModel implements FwhRepaiScopsContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public FwhRepaiScopsModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponsBean<List<RepairScopeCase>>> getRepairScopes(String entityJson) {
        return mRepositoryManager.obtainRetrofitService(RepairApi.class).getRepairScopes(0,200, SysInfo.orgid,entityJson)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseResponsBean<List<RepairWorkOrder>>> getWorkOrders(String entityJson) {
        return mRepositoryManager.obtainRetrofitService(RepairApi.class).getWorkOrders(0,200,entityJson)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseResponsBean> Updatatime(String ids) {
        return mRepositoryManager.obtainRetrofitService(RepairApi.class).UpdataTime(ids)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseResponsBean> ConfirmAll(String jsondata) {
        return mRepositoryManager.obtainRetrofitService(RepairApi.class).ConfirmOrderAll(jsondata)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseResponsBean> Confirm(String idx) {
        return mRepositoryManager.obtainRetrofitService(RepairApi.class).ConfirmOrder(idx)
                .subscribeOn(Schedulers.io());
    }
}