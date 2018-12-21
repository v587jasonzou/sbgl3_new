package sbgl3.yunda.module.appraise.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.base.entry.BaseResponsBean;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import sbgl3.yunda.module.appraise.EquipmentAppraiseApi;
import sbgl3.yunda.module.appraise.entry.EquipmentAppraisePlanBean;
import sbgl3.yunda.module.appraise.mvp.contract.EquipmentAppraiseDetailEditContract;


@ActivityScope
public class EquipmentAppraiseDetailEditModel extends BaseModel implements EquipmentAppraiseDetailEditContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public EquipmentAppraiseDetailEditModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponsBean> submitAppraiseDetail(String entityJson) {
        return mRepositoryManager.obtainRetrofitService(EquipmentAppraiseApi.class).submitAppraiseDetail(entityJson)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseResponsBean<EquipmentAppraisePlanBean>> getNextData(String idx) {
        return mRepositoryManager.obtainRetrofitService(EquipmentAppraiseApi.class).getNextData(idx)
                .subscribeOn(Schedulers.io());
    }


}