package sbgl3.yunda.module.userconfirm.mvp.model;

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
import sbgl3.yunda.module.userconfirm.UserConfirmApi;
import sbgl3.yunda.module.userconfirm.entry.FaultOrderBean;
import sbgl3.yunda.module.userconfirm.entry.PlanRepairEquipListBean;
import sbgl3.yunda.module.userconfirm.mvp.contract.UserConfirmContract;


@ActivityScope
public class UserConfirmModel extends BaseModel implements UserConfirmContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public UserConfirmModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponsBean<List<PlanRepairEquipListBean>>> getPlanRepairEquipments(int start, int limit,String entityJson) {
        return mRepositoryManager.obtainRetrofitService(UserConfirmApi.class).getPlanRepairEquipments(start,limit,entityJson)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseResponsBean<List<PlanRepairEquipListBean>>> getAllPlanRepairEquipments(String entityJson) {
        return mRepositoryManager.obtainRetrofitService(UserConfirmApi.class).getAllPlanRepairEquipments(entityJson)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseResponsBean<List<FaultOrderBean>>> getFaultEquipments(int start, int limit, String entityJson) {
        return mRepositoryManager.obtainRetrofitService(UserConfirmApi.class).getFaultEquipments(start,limit,entityJson)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseResponsBean<List<FaultOrderBean>>> getAllFaultEquipments(String entityJson) {
        return mRepositoryManager.obtainRetrofitService(UserConfirmApi.class).getAllFaultEquipments(entityJson)
                .subscribeOn(Schedulers.io());
    }
}