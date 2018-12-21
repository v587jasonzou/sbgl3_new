package sbgl3.yunda.module.evaluate.mvp.model;

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
import sbgl3.yunda.module.evaluate.EquipmentEvaluateApi;
import sbgl3.yunda.module.evaluate.entry.EvaluateTemplateBean;
import sbgl3.yunda.module.evaluate.mvp.contract.EvaluateTemplateContract;


@ActivityScope
public class EvaluateTemplateModel extends BaseModel implements EvaluateTemplateContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public EvaluateTemplateModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponsBean<List<EvaluateTemplateBean>>> getTemplate(int start, int limit, String sort, String whereListJson) {
        return mRepositoryManager.obtainRetrofitService(EquipmentEvaluateApi.class).getTemplate(start, limit, sort,whereListJson)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseResponsBean> addEvaluateTemplate(String idx, String templateIdx) {
        return mRepositoryManager.obtainRetrofitService(EquipmentEvaluateApi.class).addEvaluateTemplate(idx, templateIdx)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseResponsBean> startUp(String appraisePlanIdx, int planType) {
        return mRepositoryManager.obtainRetrofitService(EquipmentEvaluateApi.class).startUp(appraisePlanIdx,planType)
                .subscribeOn(Schedulers.io());
    }
}