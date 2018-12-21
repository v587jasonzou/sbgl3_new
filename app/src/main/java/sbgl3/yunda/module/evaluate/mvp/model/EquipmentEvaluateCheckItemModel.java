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
import sbgl3.yunda.module.evaluate.entry.EquipmentEvaluatePlanBean;
import sbgl3.yunda.module.evaluate.mvp.contract.EquipmentEvaluateCheckItemContract;


@ActivityScope
public class EquipmentEvaluateCheckItemModel extends BaseModel implements EquipmentEvaluateCheckItemContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public EquipmentEvaluateCheckItemModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponsBean<List<EquipmentEvaluatePlanBean>>> getEvaluatePlan(int start, int limit, String entityJson) {
        return mRepositoryManager.obtainRetrofitService(EquipmentEvaluateApi.class).getEvaluatePlan(start,limit,entityJson)
                .subscribeOn(Schedulers.io());
    }
}