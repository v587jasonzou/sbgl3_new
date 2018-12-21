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

import sbgl3.yunda.module.evaluate.entry.EquipmentEvaluatePlanBean;
import sbgl3.yunda.module.evaluate.mvp.contract.EquipmentEvaluateDetailEditContract;


@ActivityScope
public class EquipmentEvaluateDetailEditPresenter extends BasePresenter<EquipmentEvaluateDetailEditContract.Model, EquipmentEvaluateDetailEditContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public EquipmentEvaluateDetailEditPresenter(EquipmentEvaluateDetailEditContract.Model model, EquipmentEvaluateDetailEditContract.View rootView) {
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

    public void submitEvaluateDetail(String entityJson, String idx){
        if (null!=mModel){
            mModel.submitEvaluateDetail(entityJson).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResponsBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(BaseResponsBean baseResponsBean) {
                            if (null!=mRootView){
                                mRootView.hideLoading();
                                if (null != baseResponsBean){
                                    if (baseResponsBean.getSuccess()){
                                        ToastUtils.showShort("提交成功！");
                                        // 提交成功后获取下一条评定数据
                                        mModel.getNextData(idx).observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(new Observer<BaseResponsBean<EquipmentEvaluatePlanBean>>() {
                                                    @Override
                                                    public void onSubscribe(Disposable d) {

                                                    }

                                                    @Override
                                                    public void onNext(BaseResponsBean<EquipmentEvaluatePlanBean> equipmentEvaluatePlanBeanBaseResponsBean) {
                                                        if (null != equipmentEvaluatePlanBeanBaseResponsBean){
                                                            mRootView.loadNextData(equipmentEvaluatePlanBeanBaseResponsBean.getEntity());
                                                        }
                                                    }

                                                    @Override
                                                    public void onError(Throwable e) {

                                                    }

                                                    @Override
                                                    public void onComplete() {

                                                    }
                                                });
                                    }
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (null!=mRootView){
                                mRootView.hideLoading();
                            }
                            if (null!=e.getMessage()){
                                ToastUtils.showShort("提交成功！"+e.getMessage());
                            }
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

}
