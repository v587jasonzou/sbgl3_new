package sbgl3.yunda.module.gzqr.mvp.model;

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
import sbgl3.yunda.module.gzqr.GzqrApi;
import sbgl3.yunda.module.gzqr.entry.RepairScopeCase;
import sbgl3.yunda.module.gzqr.entry.RepairWorkOrder;
import sbgl3.yunda.module.gzqr.mvp.contract.WorkOrderConfirmContract;
import sbgl3.yunda.module.userconfirm.UserConfirmApi;


@ActivityScope
public class WorkOrderConfirmModel extends BaseModel implements WorkOrderConfirmContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public WorkOrderConfirmModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponsBean<List<RepairScopeCase>>> getRepairScopes(String entityJson,Long orgId) {
        return mRepositoryManager.obtainRetrofitService(GzqrApi.class).getRepairScops(0,200,entityJson,orgId)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseResponsBean<List<RepairWorkOrder>>> getWorkOrders(String entityJson) {
        return mRepositoryManager.obtainRetrofitService(GzqrApi.class).getWorkOrders(0,200,entityJson)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseResponsBean> confirmSave(String ids) {
        return mRepositoryManager.obtainRetrofitService(GzqrApi.class).confirmSave(ids)
                .subscribeOn(Schedulers.io());
    }
}