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
import sbgl3.yunda.module.sbxj.SbxjApi;
import sbgl3.yunda.module.sbxj.entry.InspectPlanEquipmentBean;
import sbgl3.yunda.module.sbxj.mvp.contract.SBXJDevContract;


@ActivityScope
public class SBXJDevModel extends BaseModel implements SBXJDevContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public SBXJDevModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponsBean<List<InspectPlanEquipmentBean>>> getInspecPlanEquip(int start, int limit, String entityJson) {
        return mRepositoryManager.obtainRetrofitService(SbxjApi.class).getInspectPlanEquip(start,limit,entityJson)
                .subscribeOn(Schedulers.io());
    }
}