package sbgl3.yunda.module.sbxj.mvp.model;

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
import sbgl3.yunda.module.sbdj.SbdjApi;
import sbgl3.yunda.module.sbxj.SbxjApi;
import sbgl3.yunda.module.sbxj.entry.InspectRecord;
import sbgl3.yunda.module.sbxj.mvp.contract.SBXJRecordContract;


@ActivityScope
public class SBXJRecordModel extends BaseModel implements SBXJRecordContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public SBXJRecordModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponsBean> UpLoadRecordTime(String ids) {
        return mRepositoryManager.obtainRetrofitService(SbxjApi.class).UpLoadRecordTime(ids)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseResponsBean<List<InspectRecord>>> GetInspectRecords(int start, int limit, String entityJson) {
        return mRepositoryManager.obtainRetrofitService(SbxjApi.class).getInspectRecords(start,limit,entityJson)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseResponsBean> ConfirmRecords(String ids, String checkResult) {
        return mRepositoryManager.obtainRetrofitService(SbxjApi.class).ConfirmRecords(ids,checkResult)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseResponsBean> ConfirmEquipPlan(String entytiJson) {
        return mRepositoryManager.obtainRetrofitService(SbxjApi.class).ConfirmEquipPlan(entytiJson)
                .subscribeOn(Schedulers.io());
    }
}