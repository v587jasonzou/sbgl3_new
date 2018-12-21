package sbgl3.yunda.module.tpgzpg.mvp.model;

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
import sbgl3.yunda.entry.UserResponse;
import sbgl3.yunda.globle.GlobleApi;
import sbgl3.yunda.module.tpgzpg.TpgzpgApi;
import sbgl3.yunda.module.tpgzpg.entry.FaultOrderBean;
import sbgl3.yunda.module.tpgzpg.mvp.contract.TpgzpgListContract;


@ActivityScope
public class TpgzpgListModel extends BaseModel implements TpgzpgListContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public TpgzpgListModel(IRepositoryManager repositoryManager) {
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
        return mRepositoryManager.obtainRetrofitService(TpgzpgApi.class).getFaultEquipments(start,limit,whereListJson)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<UserResponse> getUsers(Long orgId, String empName) {
        return mRepositoryManager.obtainRetrofitService(GlobleApi.class).getUsers(orgId,empName)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseResponsBean> confirm(String ids, String entityJson) {
        return mRepositoryManager.obtainRetrofitService(TpgzpgApi.class).confirm(ids,entityJson)
                .subscribeOn(Schedulers.io());
    }
}