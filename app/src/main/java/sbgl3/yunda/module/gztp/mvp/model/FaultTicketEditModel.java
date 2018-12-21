package sbgl3.yunda.module.gztp.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.base.AppConstant;
import com.jess.arms.base.entry.BaseResponsBean;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.utils.utilcode.util.ImageUtils;
import com.jess.arms.utils.utilcode.util.StringUtils;
import com.lzy.imagepicker.bean.ImageItem;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import sbgl3.yunda.entry.ImageUploadResponse;
import sbgl3.yunda.globle.GlobleApi;
import sbgl3.yunda.module.gztp.FaultTicketApi;
import sbgl3.yunda.module.gztp.entry.FaultOrder;
import sbgl3.yunda.module.gztp.entry.ResponsEquip;
import sbgl3.yunda.module.gztp.mvp.contract.FaultTicketEditContract;


@ActivityScope
public class FaultTicketEditModel extends BaseModel implements FaultTicketEditContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public FaultTicketEditModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<FaultOrder> getFaultOrderNo() {
        return mRepositoryManager.obtainRetrofitService(FaultTicketApi.class).getFaultNo()
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
    public Observable<BaseResponsBean> AddOrder(String filePathArray, String entityJson) {
        return mRepositoryManager.obtainRetrofitService(FaultTicketApi.class).AddFaultOrder(filePathArray,entityJson)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<ResponsEquip> getEquipInfo(String equipmentCode, String equipmentType) {
        return mRepositoryManager.obtainRetrofitService(FaultTicketApi.class).getEquipInfo(equipmentCode,equipmentType)
                .subscribeOn(Schedulers.io());
    }
}