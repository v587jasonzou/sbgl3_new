package sbgl3.yunda.module.fwhjhx.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.base.entry.BaseResponsBean;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.utils.utilcode.util.ImageUtils;
import com.jess.arms.utils.utilcode.util.StringUtils;
import com.lzy.imagepicker.bean.ImageItem;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import com.jess.arms.base.AppConstant;
import sbgl3.yunda.entry.ImageUploadResponse;
import sbgl3.yunda.entry.UserResponse;
import sbgl3.yunda.globle.GlobleApi;
import sbgl3.yunda.module.fwhjhx.RepairApi;
import sbgl3.yunda.module.fwhjhx.mvp.contract.ManageWorkorderContract;
import sbgl3.yunda.module.fwhjhx.mvp.entity.EosDictEntry;
import sbgl3.yunda.module.fwhjhx.mvp.entity.MateriaBean;
import sbgl3.yunda.module.fwhjhx.mvp.entity.RepairHistoryResponse;
import sbgl3.yunda.module.fwhjhx.mvp.entity.WorkOrderResponse;


@ActivityScope
public class ManageWorkorderModel extends BaseModel implements ManageWorkorderContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public ManageWorkorderModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponsBean> getImages(String idx) {
        return mRepositoryManager.obtainRetrofitService(GlobleApi.class).getImages(idx)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<ImageUploadResponse> UpLoadImages(ImageItem image, String tableName) {
        String str = null;
        if(image!=null&&!StringUtils.isTrimEmpty(image.path)){
            str = ImageUtils.getBase64StringFromImg(image.path);
        }
        if(str!=null){
            return mRepositoryManager.obtainRetrofitService(GlobleApi.class).UpLoadImages(str, AppConstant.JPG_EXTNAME,tableName)
                    .subscribeOn(Schedulers.io());
        }else {
            return null;
        }
    }

    @Override
    public Observable<BaseResponsBean> ConfirmOrder(String idx, String OtherWorker, String RepairRecord, String filePathArray) {
        return mRepositoryManager.obtainRetrofitService(RepairApi.class).ConfirmOrderNew(idx,OtherWorker,
                RepairRecord,filePathArray).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<EosDictEntry>> getUnits() {
        return mRepositoryManager.obtainRetrofitService(RepairApi.class).getUnits()
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<RepairHistoryResponse> getHistory(int start, int limit, String definidx) {
        return mRepositoryManager.obtainRetrofitService(RepairApi.class).getHistorys(start,limit,definidx)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<UserResponse> getUsers(Long orgId, String empName) {
        return mRepositoryManager.obtainRetrofitService(GlobleApi.class).getUsers(orgId,empName)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseResponsBean<List<MateriaBean>>> getStuffs(int start, int limit, String entityJson) {
        return mRepositoryManager.obtainRetrofitService(RepairApi.class).getStuffs(start,limit,entityJson)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<WorkOrderResponse> getNextWorkOrder(String idx) {
        return mRepositoryManager.obtainRetrofitService(RepairApi.class).getNextOrder(idx)
                .subscribeOn(Schedulers.io());
    }
}