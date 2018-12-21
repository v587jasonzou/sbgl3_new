package sbgl3.yunda.module.gzqr.mvp.model;

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
import sbgl3.yunda.module.gzqr.GzqrApi;
import sbgl3.yunda.module.gzqr.entry.PlanRepairEquipListBean;
import sbgl3.yunda.module.gzqr.mvp.contract.GzqrEquipmentListContract;


@ActivityScope
public class GzqrEquipmentListModel extends BaseModel implements GzqrEquipmentListContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public GzqrEquipmentListModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponsBean<List<PlanRepairEquipListBean>>> getEquipments(int start, int limit, String entityJson,Long orgId) {
        return mRepositoryManager.obtainRetrofitService(GzqrApi.class).getEquipments(start,limit,entityJson,orgId)
                .subscribeOn(Schedulers.io());
    }
}