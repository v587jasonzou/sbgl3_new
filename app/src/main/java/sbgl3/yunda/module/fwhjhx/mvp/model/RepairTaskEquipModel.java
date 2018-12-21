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
import sbgl3.yunda.module.equipinfo.EquipApi;
import sbgl3.yunda.module.fwhjhx.RepairApi;
import sbgl3.yunda.module.fwhjhx.mvp.contract.RepairTaskEquipContract;
import sbgl3.yunda.module.fwhjhx.mvp.entity.RepairTaskList;


@ActivityScope
public class RepairTaskEquipModel extends BaseModel implements RepairTaskEquipContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public RepairTaskEquipModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponsBean<List<RepairTaskList>>> getRepairTaskEquips(int start, int limit, String entityJson) {
        return mRepositoryManager.obtainRetrofitService(RepairApi.class).getEquipLists(start,limit,entityJson)
                .subscribeOn(Schedulers.io());
    }
}