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

import okhttp3.ResponseBody;
import sbgl3.yunda.module.appraise.entry.EquipmentAppraisePlanBean;
import sbgl3.yunda.module.appraise.mvp.contract.EquipmentAppraiseDetailEditContract;


@ActivityScope
public class EquipmentAppraiseDetailEditPresenter extends BasePresenter<EquipmentAppraiseDetailEditContract.Model, EquipmentAppraiseDetailEditContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public EquipmentAppraiseDetailEditPresenter(EquipmentAppraiseDetailEditContract.Model model, EquipmentAppraiseDetailEditContract.View rootView) {
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

    public void submitAppraiseDetail (String entityJson, String idx){
        if (null!=mModel){
            mModel.submitAppraiseDetail(entityJson).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResponsBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseResponsBean baseResponsBean) {
                            if (null!=mRootView){
                                mRootView.hideLoading();
                                if (baseResponsBean != null){
                                    if (baseResponsBean.getSuccess()){
                                        ToastUtils.showShort("提交成功！");
                                        // 提交成功后获取下一条鉴定数据
                                        mModel.getNextData(idx).observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(new Observer<BaseResponsBean<EquipmentAppraisePlanBean>>() {
                                                    @Override
                                                    public void onSubscribe(Disposable d) {
                                                    }


                                                    @Override
                                                    public void onNext(BaseResponsBean<EquipmentAppraisePlanBean> equipmentAppraisePlanBeanBaseResponsBean) {
                                                        if (null != equipmentAppraisePlanBeanBaseResponsBean){
                                                            mRootView.loadNextData(equipmentAppraisePlanBeanBaseResponsBean.getEntity());
                                                        }
                                                    }

                                                    @Override
                                                    public void onError(Throwable e) {

                                                    }

                                                    @Override
                                                    public void onComplete() {

                                                    }
                                                });
                                    } else {
                                        ToastUtils.showShort("提交失败，请重试！");
                                    }
                                } else {
                                    ToastUtils.showShort("提交失败，请重试！");
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (null!=mRootView){
                                mRootView.hideLoading();
                            }
                            if (e.getMessage()!=null){
                                ToastUtils.showShort("提交失败，请重试！");
                            }
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }
    public void getNextData (String idx){
        if (null!=mModel){
            mModel.getNextData(idx).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResponsBean<EquipmentAppraisePlanBean>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseResponsBean<EquipmentAppraisePlanBean> equipmentAppraisePlanBeanBaseResponsBean) {
                            if (null != equipmentAppraisePlanBeanBaseResponsBean){
                                if (null!=mRootView){
                                    mRootView.loadNextData(equipmentAppraisePlanBeanBaseResponsBean.getEntity());
                                }
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
