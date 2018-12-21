package sbgl3.yunda.module.tpgzpg.mvp.presenter;

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

import sbgl3.yunda.module.tpgzpg.mvp.contract.BackDispatcherContract;


@ActivityScope
public class BackDispatcherPresenter extends BasePresenter<BackDispatcherContract.Model, BackDispatcherContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public BackDispatcherPresenter(BackDispatcherContract.Model model, BackDispatcherContract.View rootView) {
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

    public void back(String ids,String backReason,String backPersonDuty){
        mModel.back(ids, backReason,backPersonDuty)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponsBean baseResponsBean) {
                        mRootView.hideLoading();
                        if (null!=baseResponsBean){
                            if (baseResponsBean.getSuccess()){
                                mRootView.backSuccess();
                            } else {
                                mRootView.backFail();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mRootView.hideLoading();
                        ToastUtils.showShort("提交失败" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
