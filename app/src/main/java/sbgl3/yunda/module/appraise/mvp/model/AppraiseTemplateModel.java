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
import sbgl3.yunda.module.appraise.entry.AppraiseTemplateBean;
import sbgl3.yunda.module.appraise.mvp.contract.AppraiseTemplateContract;


@ActivityScope
public class AppraiseTemplateModel extends BaseModel implements AppraiseTemplateContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public AppraiseTemplateModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponsBean<List<AppraiseTemplateBean>>> getTemplate(int start, int limit, String sort, String whereListJson) {
        return mRepositoryManager.obtainRetrofitService(EquipmentAppraiseApi.class).getTemplate(start,limit,sort,whereListJson)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseResponsBean> addAppraiseTemplate(String idx, String templateIdx) {
        return mRepositoryManager.obtainRetrofitService(EquipmentAppraiseApi.class).addAppraiseTemplate(idx, templateIdx)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseResponsBean> startUp(String appraisePlanIdx, int planType) {
        return mRepositoryManager.obtainRetrofitService(EquipmentAppraiseApi.class).startUp(appraisePlanIdx, planType)
                .subscribeOn(Schedulers.io());
    }
}