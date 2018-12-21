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
import sbgl3.yunda.module.evaluate.mvp.contract.EquipmentEvaluateResultEditContract;


@ActivityScope
public class EquipmentEvaluateResultEditModel extends BaseModel implements EquipmentEvaluateResultEditContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public EquipmentEvaluateResultEditModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponsBean> submitEvaluateResult(String entityJson) {
        return mRepositoryManager.obtainRetrofitService(EquipmentEvaluateApi.class).submitEvaluateResult(entityJson)
                .subscribeOn(Schedulers.io());
    }
}