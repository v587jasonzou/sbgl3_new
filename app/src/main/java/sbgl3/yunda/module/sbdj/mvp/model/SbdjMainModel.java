package sbgl3.yunda.module.sbdj.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.base.entry.BaseResponsBean;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import sbgl3.yunda.module.sbdj.SbdjApi;
import sbgl3.yunda.module.sbdj.entity.EquipResponse;
import sbgl3.yunda.module.sbdj.entity.EquipmentUnionRFID;
import sbgl3.yunda.module.sbdj.mvp.contract.SbdjMainContract;


@ActivityScope
public class SbdjMainModel extends BaseModel implements SbdjMainContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public SbdjMainModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponsBean<List<EquipmentUnionRFID>>> getEquipments(int start, int limit, String entityJson) {
        return mRepositoryManager.obtainRetrofitService(SbdjApi.class).getEquipInfos(start,limit,entityJson)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseResponsBean> RegisterEquip(String entityJson) {
        return mRepositoryManager.obtainRetrofitService(SbdjApi.class).registerEquip(entityJson)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<EquipResponse> UpLoadRegister(String equipmentCode) {
        return mRepositoryManager.obtainRetrofitService(SbdjApi.class).upLoadEquip(equipmentCode)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseResponsBean> UnRegisterEquip(String ids) {
        return mRepositoryManager.obtainRetrofitService(SbdjApi.class).UnregisterEquip(ids)
                .subscribeOn(Schedulers.io());
    }
}