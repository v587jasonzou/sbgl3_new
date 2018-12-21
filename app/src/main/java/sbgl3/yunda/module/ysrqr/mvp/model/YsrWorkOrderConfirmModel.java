package sbgl3.yunda.module.ysrqr.mvp.model;

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
import sbgl3.yunda.module.ysrqr.YsrqrApi;
import sbgl3.yunda.module.ysrqr.entry.RepairScopeCase;
import sbgl3.yunda.module.ysrqr.entry.RepairWorkOrder;
import sbgl3.yunda.module.ysrqr.mvp.contract.YsrWorkOrderConfirmContract;


@ActivityScope
public class YsrWorkOrderConfirmModel extends BaseModel implements YsrWorkOrderConfirmContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public YsrWorkOrderConfirmModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponsBean<List<RepairScopeCase>>> getRepairScopes(int start, int limit, String entityJson) {
        return mRepositoryManager.obtainRetrofitService(YsrqrApi.class).getRepairScops(start,limit,entityJson)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseResponsBean<List<RepairWorkOrder>>> getWorkOrders(int start, int limit, String entityJson) {
        return mRepositoryManager.obtainRetrofitService(YsrqrApi.class).getWorkOrders(start,limit,entityJson)
                .subscribeOn(Schedulers.io());
    }

}