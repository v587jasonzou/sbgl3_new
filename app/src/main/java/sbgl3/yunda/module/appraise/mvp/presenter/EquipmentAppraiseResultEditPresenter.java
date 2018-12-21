package sbgl3.yunda.module.appraise.mvp.presenter;

import android.app.Application;

import com.jess.arms.base.entry.BaseResponsBean;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.utilcode.util.ToastUtils;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import sbgl3.yunda.module.appraise.mvp.contract.EquipmentAppraiseResultEditContract;


@ActivityScope
public class EquipmentAppraiseResultEditPresenter extends BasePresenter<EquipmentAppraiseResultEditContract.Model, EquipmentAppraiseResultEditContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public EquipmentAppraiseResultEditPresenter(EquipmentAppraiseResultEditContract.Model model, EquipmentAppraiseResultEditContract.View rootView) {
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

    public void submitAppraiseResult(String entityJson) {
        if (null!=mModel){
            mModel.submitAppraiseResult(entityJson).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResponsBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseResponsBean baseResponsBean) {
                            if (null!= mRootView){
                                mRootView.hideLoading();
                            }
                            if(baseResponsBean!=null){
                                if(baseResponsBean.getSuccess()){
                                    ToastUtils.showShort("提交成功！");
                                }else if (baseResponsBean.getErrMsg()!=null){
                                    ToastUtils.showShort("提交失败，请重试！"+baseResponsBean.getErrMsg());
                                }
                            } else {
                                ToastUtils.showShort("提交失败，请重试！");
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (null!=mRootView){
                                mRootView.hideLoading();
                            }
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }
}
