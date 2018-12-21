package sbgl3.yunda.module.fwhjhx.mvp.presenter;

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

import sbgl3.yunda.module.fwhjhx.mvp.contract.FwhRepaiScopsContract;
import sbgl3.yunda.module.fwhjhx.mvp.entity.RepairScopeCase;
import sbgl3.yunda.module.fwhjhx.mvp.entity.RepairWorkOrder;


@ActivityScope
public class FwhRepaiScopsPresenter extends BasePresenter<FwhRepaiScopsContract.Model, FwhRepaiScopsContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public FwhRepaiScopsPresenter(FwhRepaiScopsContract.Model model, FwhRepaiScopsContract.View rootView) {
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
    public void getRepairScops(String entityJson){
        mModel.getRepairScopes(entityJson).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponsBean<List<RepairScopeCase>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponsBean<List<RepairScopeCase>> listBaseResponsBean) {
                        mRootView.hideLoading();
                        if(listBaseResponsBean!=null&&listBaseResponsBean.getRoot()!=null){
                            mRootView.OnLoadRepairScopeSuccess(listBaseResponsBean.getRoot());
                        }else {
                            ToastUtils.showShort("获取检修范围失败，请重试");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mRootView.hideLoading();
                        ToastUtils.showShort("获取检修范围失败，请重试"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getWorkOrders(String entityJson){
        mModel.getWorkOrders(entityJson).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponsBean<List<RepairWorkOrder>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponsBean<List<RepairWorkOrder>> listBaseResponsBean) {
                        mRootView.hideLoading();
                        if(listBaseResponsBean!=null&&listBaseResponsBean.getRoot()!=null){
                            mRootView.OnLoadOrderSuccess(listBaseResponsBean.getRoot());
                        }else {
                            ToastUtils.showShort("获取检修工单失败，请重试");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mRootView.hideLoading();
                        ToastUtils.showShort("获取检修工单失败，请重试"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public void UpdataTime(String ids){
        mModel.Updatatime(ids).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponsBean baseResponsBean) {
                        if(baseResponsBean==null||!baseResponsBean.getSuccess()){
                            ToastUtils.showShort("更新开工时间失败");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showShort("更新开工时间失败"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public void ConfirmAll(String jsibData){
        mModel.ConfirmAll(jsibData).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponsBean baseResponsBean) {
                        mRootView.hideLoading();
                        if(baseResponsBean!=null){
                            if(baseResponsBean.getSuccess()){
                                mRootView.ConfirmAllSuccess();
                            }else {
                                if(baseResponsBean.getErrMsg()!=null){
                                    ToastUtils.showShort("确认失败，"+baseResponsBean.getErrMsg());
                                }else {
                                    ToastUtils.showShort("确认失败，请重试");
                                }
                            }

                        }else {
                            ToastUtils.showShort("确认失败，请重试");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mRootView.hideLoading();
                        ToastUtils.showShort("确认失败，请重试"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void Confirm(String jsibData){
        mModel.Confirm(jsibData).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponsBean baseResponsBean) {
                        mRootView.hideLoading();
                        if(baseResponsBean!=null){
                            if(baseResponsBean.getSuccess()){
                                mRootView.ConfirmSuccess();
                            }else {
                                if(baseResponsBean.getErrMsg()!=null){
                                    ToastUtils.showShort("确认失败，"+baseResponsBean.getErrMsg());
                                }else {
                                    ToastUtils.showShort("确认失败，请重试");
                                }
                            }

                        }else {
                            ToastUtils.showShort("确认失败，请重试");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mRootView.hideLoading();
                        ToastUtils.showShort("确认失败，请重试"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
