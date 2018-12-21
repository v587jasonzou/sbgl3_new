package sbgl3.yunda.module.fwhjhx.mvp.model;

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
import sbgl3.yunda.module.fwhjhx.RepairApi;
import sbgl3.yunda.module.fwhjhx.mvp.contract.StuffContract;
import sbgl3.yunda.module.fwhjhx.mvp.entity.MateriaBean;
import sbgl3.yunda.module.fwhjhx.mvp.entity.StuffParams;


@ActivityScope
public class StuffModel extends BaseModel implements StuffContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public StuffModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponsBean<List<MateriaBean>>> getStuffs(int start, int limit, String entityJson) {
        return mRepositoryManager.obtainRetrofitService(RepairApi.class).getStuffs(start,limit,entityJson)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseResponsBean<List<StuffParams>>> getStuffsParam(int start, int limit, String entityJson) {
        return mRepositoryManager.obtainRetrofitService(RepairApi.class).getStuffParams(start,limit,entityJson)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseResponsBean> UpdateStuff(String entityJson) {
        return mRepositoryManager.obtainRetrofitService(RepairApi.class).getUpdateStuff(entityJson)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseResponsBean> StartStuffWait(String idx) {
        return mRepositoryManager.obtainRetrofitService(RepairApi.class).StartWaitStuff(idx)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseResponsBean> EndStuffWait(String idx) {
        return mRepositoryManager.obtainRetrofitService(RepairApi.class).EndWaitStuff(idx)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseResponsBean> DeleteStuff(String ids) {
        return mRepositoryManager.obtainRetrofitService(RepairApi.class).DeleteStuff(ids)
                .subscribeOn(Schedulers.io());
    }
}