package sbgl3.yunda.module.sbxj.mvp.presenter;

import android.app.Application;

import com.jess.arms.base.entry.BaseResponsBean;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.utilcode.util.ToastUtils;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import sbgl3.yunda.module.sbxj.entry.InspectPlanBean;
import sbgl3.yunda.module.sbxj.entry.InspectPlanEquipmentBean;
import sbgl3.yunda.module.sbxj.mvp.contract.SBXJDevContract;


@ActivityScope
public class SBXJDevPresenter extends BasePresenter<SBXJDevContract.Model, SBXJDevContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public SBXJDevPresenter(SBXJDevContract.Model model, SBXJDevContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
    public void getInspecPlan(int start, int limit, String entityJson,final Boolean isLoadMore){
        mModel.getInspecPlanEquip(start,limit,entityJson)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponsBean<List<InspectPlanEquipmentBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponsBean<List<InspectPlanEquipmentBean>> listBaseResponsBean) {
                        if(mRootView!=null){
                            mRootView.hideLoading();
                        }
                        if(listBaseResponsBean!=null){
                            if(!isLoadMore){
                                mRootView.LoadData(listBaseResponsBean.getRoot());
                            }else {
                                mRootView.LoadMoreData(listBaseResponsBean.getRoot());
                            }
                        }else {
                            String msg = "获取巡检计划设备失败，请重试";
                            if(listBaseResponsBean.getErrMsg()!=null){
                                msg = msg +listBaseResponsBean.getErrMsg();
                            }
                            ToastUtils.showShort(msg);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(mRootView!=null){
                            mRootView.hideLoading();
                        }
                        ToastUtils.showShort("获取巡检计划设备失败，请重试"+ e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
