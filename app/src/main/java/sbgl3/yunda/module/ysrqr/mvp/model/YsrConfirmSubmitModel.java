package sbgl3.yunda.module.ysrqr.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.base.entry.BaseResponsBean;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import sbgl3.yunda.module.ysrqr.YsrqrApi;
import sbgl3.yunda.module.ysrqr.mvp.contract.YsrConfirmSubmitContract;


@ActivityScope
public class YsrConfirmSubmitModel extends BaseModel implements YsrConfirmSubmitContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public YsrConfirmSubmitModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponsBean> confirm(String ids,String acceptanceReviews, String acceptorNames) {
        return mRepositoryManager.obtainRetrofitService(YsrqrApi.class).confirm(ids, acceptanceReviews, acceptorNames)
                .subscribeOn(Schedulers.io());
    }
}