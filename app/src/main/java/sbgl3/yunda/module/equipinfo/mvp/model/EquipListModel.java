package sbgl3.yunda.module.equipinfo.mvp.model;

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
import sbgl3.yunda.module.equipinfo.entity.EquipmentPrimaryInfo;
import sbgl3.yunda.module.equipinfo.mvp.contract.EquipListContract;
import sbgl3.yunda.module.sbdj.SbdjApi;


@ActivityScope
public class EquipListModel extends BaseModel implements EquipListContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public EquipListModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponsBean<List<EquipmentPrimaryInfo>>> getEquipList(int start, int limit, String entityJson) {
        return mRepositoryManager.obtainRetrofitService(EquipApi.class).getEquipLists(start,limit,entityJson)
                .subscribeOn(Schedulers.io());
    }
}