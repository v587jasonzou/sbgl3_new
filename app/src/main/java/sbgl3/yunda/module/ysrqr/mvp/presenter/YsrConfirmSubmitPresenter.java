package sbgl3.yunda.module.ysrqr.mvp.presenter;

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

import sbgl3.yunda.module.ysrqr.mvp.contract.YsrConfirmSubmitContract;


@ActivityScope
public class YsrConfirmSubmitPresenter extends BasePresenter<YsrConfirmSubmitContract.Model, YsrConfirmSubmitContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public YsrConfirmSubmitPresenter(YsrConfirmSubmitContract.Model model, YsrConfirmSubmitContract.View rootView) {
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

    public void confirm(String ids,String acceptanceReviews, String acceptorNames){
        if (null!=mModel){
            mModel.confirm(ids, acceptanceReviews, acceptorNames)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResponsBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseResponsBean baseResponsBean) {
                            if (null!=mRootView){
                                mRootView.hideLoading();
                                if (null!=baseResponsBean){
                                    if (baseResponsBean.getSuccess()){
                                        mRootView.submitSuccess();
                                    } else {
                                        mRootView.submitFail();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (null!=mRootView){
                                mRootView.hideLoading();
                            }
                            ToastUtils.showShort("提交失败" + e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }
}
