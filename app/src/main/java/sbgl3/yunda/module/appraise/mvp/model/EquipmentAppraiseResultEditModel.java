package sbgl3.yunda.module.appraise.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.base.entry.BaseResponsBean;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import io.reactivex.schedulers.Schedulers;
import io.reactivex.Observable;
import sbgl3.yunda.module.appraise.EquipmentAppraiseApi;
import sbgl3.yunda.module.appraise.mvp.contract.EquipmentAppraiseResultEditContract;


@ActivityScope
public class EquipmentAppraiseResultEditModel extends BaseModel implements EquipmentAppraiseResultEditContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public EquipmentAppraiseResultEditModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponsBean> submitAppraiseResult(String entityJson) {
        return mRepositoryManager.obtainRetrofitService(EquipmentAppraiseApi.class).submitAppraiseResult(entityJson)
                .subscribeOn(Schedulers.io());
    }
}