package sbgl3.yunda.module.evaluate.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.base.entry.BaseResponsBean;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import sbgl3.yunda.module.evaluate.EquipmentEvaluateApi;
import sbgl3.yunda.module.evaluate.entry.EquipmentEvaluatePlanBean;
import sbgl3.yunda.module.evaluate.mvp.contract.EquipmentEvaluateDetailEditContract;


@ActivityScope
public class EquipmentEvaluateDetailEditModel extends BaseModel implements EquipmentEvaluateDetailEditContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public EquipmentEvaluateDetailEditModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponsBean> submitEvaluateDetail(String entityJson) {
        return mRepositoryManager.obtainRetrofitService(EquipmentEvaluateApi.class).submitEvaluateDetail(entityJson)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseResponsBean<EquipmentEvaluatePlanBean>> getNextData(String idx) {
        return mRepositoryManager.obtainRetrofitService(EquipmentEvaluateApi.class).getNextData(idx)
                .subscribeOn(Schedulers.io());
    }
}