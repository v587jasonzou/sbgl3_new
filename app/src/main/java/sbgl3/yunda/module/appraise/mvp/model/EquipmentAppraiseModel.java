package sbgl3.yunda.module.appraise.mvp.model;

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
import sbgl3.yunda.module.appraise.EquipmentAppraiseApi;
import sbgl3.yunda.module.appraise.entry.EquipmentAppraiserBean;
import sbgl3.yunda.module.appraise.mvp.contract.EquipmentAppraiseContract;


@ActivityScope
public class EquipmentAppraiseModel extends BaseModel implements EquipmentAppraiseContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public EquipmentAppraiseModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponsBean<List<EquipmentAppraiserBean>>> getAppraiseEquipment(String planStatus,int start, int limit, String entityJson) {
        return mRepositoryManager.obtainRetrofitService(EquipmentAppraiseApi.class).getAppraiseEquipment(planStatus,start,limit,entityJson)
                .subscribeOn(Schedulers.io());
    }
}