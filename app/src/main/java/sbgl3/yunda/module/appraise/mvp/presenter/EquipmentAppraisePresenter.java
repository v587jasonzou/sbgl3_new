package sbgl3.yunda.module.appraise.mvp.presenter;

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

import sbgl3.yunda.module.appraise.entry.EquipmentAppraiserBean;
import sbgl3.yunda.module.appraise.mvp.contract.EquipmentAppraiseContract;


@ActivityScope
public class EquipmentAppraisePresenter extends BasePresenter<EquipmentAppraiseContract.Model, EquipmentAppraiseContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public EquipmentAppraisePresenter(EquipmentAppraiseContract.Model model, EquipmentAppraiseContract.View rootView) {
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

    public void getAppraiseEquipment(String planStatus,int start, int limit, String entityJson,final Boolean isLoadMore){
        if (null!=mModel){
            mModel.getAppraiseEquipment(planStatus,start,limit,entityJson)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResponsBean<List<EquipmentAppraiserBean>>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseResponsBean<List<EquipmentAppraiserBean>> listBaseResponsBean) {
                            if (null!=mRootView){
                                mRootView.hideLoading();
                                if(listBaseResponsBean!=null){
                                    if (null != listBaseResponsBean.getRoot()) {
                                        mRootView.loadsuccess();
                                        if(!isLoadMore){
                                            mRootView.LoadData(listBaseResponsBean.getRoot());
                                        }else {
                                            mRootView.LoadMoreData(listBaseResponsBean.getRoot());
                                        }
                                    } else {
                                        ToastUtils.showShort("加载失败，请重试！");
                                    }
                                }else {
                                    String msg = "加载失败，请重试！";
                                    if(listBaseResponsBean.getErrMsg()!=null){
                                        msg = msg +listBaseResponsBean.getErrMsg();
                                    }
                                    ToastUtils.showShort(msg);
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (null!=mRootView){
                                mRootView.hideLoading();
                            }
                            if (null!=e.getMessage()){
                                ToastUtils.showShort("加载失败，请重试！"+e.getMessage());
                            }
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
        }
}
