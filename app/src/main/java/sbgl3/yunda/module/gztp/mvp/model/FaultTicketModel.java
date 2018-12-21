package sbgl3.yunda.module.gztp.mvp.model;

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
import sbgl3.yunda.module.gztp.FaultTicketApi;
import sbgl3.yunda.module.gztp.entry.FaultOrder;
import sbgl3.yunda.module.gztp.mvp.contract.FaultTicketContract;


@ActivityScope
public class FaultTicketModel extends BaseModel implements FaultTicketContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public FaultTicketModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponsBean<List<FaultOrder>>> getFaultTicktList(int start, int limit, String entityJson) {
        return mRepositoryManager.obtainRetrofitService(FaultTicketApi.class).getFaultOrderList(start,limit,entityJson)
                .subscribeOn(Schedulers.io());
    }
}