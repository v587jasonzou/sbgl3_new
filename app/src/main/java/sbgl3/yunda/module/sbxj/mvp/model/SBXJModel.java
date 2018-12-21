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
import sbgl3.yunda.module.sbxj.entry.InspectPlanBean;
import sbgl3.yunda.module.sbxj.mvp.contract.SBXJContract;


@ActivityScope
public class SBXJModel extends BaseModel implements SBXJContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public SBXJModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponsBean<List<InspectPlanBean>>> getInspecPlan(int start, int limit, String entityJson) {
        return mRepositoryManager.obtainRetrofitService(SbxjApi.class).getInspectPlan(start,limit,entityJson)
                .subscribeOn(Schedulers.io());

    }
}