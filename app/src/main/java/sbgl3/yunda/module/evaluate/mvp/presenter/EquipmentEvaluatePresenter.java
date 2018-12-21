package sbgl3.yunda.module.evaluate.mvp.presenter;

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
import sbgl3.yunda.module.evaluate.entry.EquipmentEvaluateBean;
import sbgl3.yunda.module.evaluate.mvp.contract.EquipmentEvaluateContract;


@ActivityScope
public class EquipmentEvaluatePresenter extends BasePresenter<EquipmentEvaluateContract.Model, EquipmentEvaluateContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public EquipmentEvaluatePresenter(EquipmentEvaluateContract.Model model, EquipmentEvaluateContract.View rootView) {
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

    public void getEvaluateEquipment(String planStatus,int start, int limit, String entityJson,final Boolean isLoadMore){
        if (null!=mModel){
            mModel.getEvaluateEquipment(planStatus,start,limit,entityJson)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResponsBean<List<EquipmentEvaluateBean>>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseResponsBean<List<EquipmentEvaluateBean>> listBaseResponsBean) {
                            if (null!=mRootView){
                                mRootView.hideLoading();
                                if(listBaseResponsBean!=null){
                                    if (null != listBaseResponsBean.getRoot()){
                                        mRootView.loadSuccess();
                                        if(!isLoadMore){
                                            mRootView.loadData(listBaseResponsBean.getRoot());
                                        }else {
                                            mRootView.loadMoreData(listBaseResponsBean.getRoot());
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
