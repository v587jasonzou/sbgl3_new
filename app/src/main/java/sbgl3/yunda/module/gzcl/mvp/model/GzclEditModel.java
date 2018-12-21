package sbgl3.yunda.module.gzcl.mvp.model;

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

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import sbgl3.yunda.entry.ImageUploadResponse;
import sbgl3.yunda.globle.GlobleApi;
import sbgl3.yunda.module.fwhjhx.RepairApi;
import sbgl3.yunda.module.fwhjhx.mvp.entity.MateriaBean;
import sbgl3.yunda.module.gzcl.GzclApi;
import sbgl3.yunda.module.gzcl.mvp.contract.GzclEditContract;
import sbgl3.yunda.module.tpgzpg.TpgzpgApi;
import sbgl3.yunda.module.tpgzpg.entry.BackInfoBean;


@ActivityScope
public class GzclEditModel extends BaseModel implements GzclEditContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public GzclEditModel(IRepositoryManager repositoryManager) {
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
    public Observable<BaseResponsBean<List<BackInfoBean>>> getBackInfo(String sort, String whereListJson) {
        return mRepositoryManager.obtainRetrofitService(TpgzpgApi.class).getBackInfo(sort,whereListJson)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<BaseResponsBean<List<MateriaBean>>> getStuffs(int start, int limit, String entityJson) {
        return mRepositoryManager.obtainRetrofitService(RepairApi.class).getStuffs(start,limit,entityJson)
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
    public Observable<BaseResponsBean> submitOrder(String idx, String entityJson, String filePathArray) {
        return mRepositoryManager.obtainRetrofitService(GzclApi.class).submitOrder(idx,entityJson,filePathArray)
                .subscribeOn(Schedulers.io());
    }
}