package sbgl3.yunda.module.evaluate.mvp.presenter;

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

import sbgl3.yunda.module.evaluate.mvp.contract.EquipmentEvaluateResultEditContract;


@ActivityScope
public class EquipmentEvaluateResultEditPresenter extends BasePresenter<EquipmentEvaluateResultEditContract.Model, EquipmentEvaluateResultEditContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public EquipmentEvaluateResultEditPresenter(EquipmentEvaluateResultEditContract.Model model, EquipmentEvaluateResultEditContract.View rootView) {
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
    public void submitEvaluateResult(String entityJson){
        if (null!=mModel){
            mModel.submitEvaluateResult(entityJson).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResponsBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseResponsBean baseResponsBean) {
                            if (null!=mRootView){
                                mRootView.hideLoading();
                            }
                            if (null != baseResponsBean){
                                if (baseResponsBean.getSuccess()){
                                    ToastUtils.showShort("提交成功！");
                                } else {
                                    ToastUtils.showShort("提交失败！");
                                }
                            } else {
                                ToastUtils.showShort("提交失败！");
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (null!=mRootView){
                                mRootView.hideLoading();
                            }
                            if (null!=e.getMessage()){
                                ToastUtils.showShort("提交失败！"+e.getMessage());
                            }
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }
}
