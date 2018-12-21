package sbgl3.yunda.module.evaluate.mvp.model;

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
import sbgl3.yunda.module.evaluate.EquipmentEvaluateApi;
import sbgl3.yunda.module.evaluate.entry.EquipmentEvaluateBean;
import sbgl3.yunda.module.evaluate.mvp.contract.EquipmentEvaluateContract;


@ActivityScope
public class EquipmentEvaluateModel extends BaseModel implements EquipmentEvaluateContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public EquipmentEvaluateModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponsBean<List<EquipmentEvaluateBean>>> getEvaluateEquipment(String planStatus, int start, int limit, String entityJson) {
        return mRepositoryManager.obtainRetrofitService(EquipmentEvaluateApi.class).getEvaluateEquipment(planStatus,start,limit,entityJson)
                .subscribeOn(Schedulers.io());
    }
}