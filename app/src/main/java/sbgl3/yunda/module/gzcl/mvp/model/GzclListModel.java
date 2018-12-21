package sbgl3.yunda.module.gzcl.mvp.model;

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
import sbgl3.yunda.module.gzcl.GzclApi;
import sbgl3.yunda.module.gzcl.entry.FaultOrderBean;
import sbgl3.yunda.module.gzcl.mvp.contract.GzclListContract;


@ActivityScope
public class GzclListModel extends BaseModel implements GzclListContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public GzclListModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponsBean<List<FaultOrderBean>>> getFaultEquipments(int start, int limit, String whereListJson) {
        return mRepositoryManager.obtainRetrofitService(GzclApi.class).getFaultEquipments(start,limit,whereListJson)
                .subscribeOn(Schedulers.io());
    }
}