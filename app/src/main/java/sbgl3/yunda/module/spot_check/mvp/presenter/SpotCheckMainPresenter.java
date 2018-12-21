package sbgl3.yunda.module.spot_check.mvp.presenter;

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

import sbgl3.yunda.module.spot_check.entity.SpotCheckResponse;
import sbgl3.yunda.module.spot_check.mvp.contract.SpotCheckMainContract;


@ActivityScope
public class SpotCheckMainPresenter extends BasePresenter<SpotCheckMainContract.Model, SpotCheckMainContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public SpotCheckMainPresenter(SpotCheckMainContract.Model model, SpotCheckMainContract.View rootView) {
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
    public void getSpotCheck(String code){
        mModel.getSpotCheck(code).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SpotCheckResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SpotCheckResponse baseResponsBean) {
                        mRootView.hideLoading();
                        if(baseResponsBean!=null&&baseResponsBean.getSuccess()){
                            mRootView.getSpotCheckSuccess(baseResponsBean.getEntity());
                        }else {
                            mRootView.getSpotCheckSuccess(null);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mRootView.hideLoading();
                        ToastUtils.showShort("获取点检信息失败"+e.getMessage());
                        mRootView.getSpotCheckSuccess(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public void ChangeItemStatus(String idx,String statu){
        mModel.ChangeItemStatus(idx,statu).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponsBean baseResponsBean) {
                        mRootView.hideLoading();
                        if(baseResponsBean!=null&&baseResponsBean.getSuccess()){
                            mRootView.OnItemStatusChangeSuccess();
                        }else {
                            if(baseResponsBean.getErrMsg()!=null){
                                ToastUtils.showShort("当前点检项状态改变失败，"+baseResponsBean.getErrMsg());
                            }else {
                                ToastUtils.showShort("当前点检项状态改变失败，请重试");
                            }

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mRootView.hideLoading();
                        ToastUtils.showShort("当前点检项状态改变失败，请重试"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void ConfirmSpot(String entityJson){
        mModel.ConfirmSpot(entityJson).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponsBean baseResponsBean) {
                        mRootView.hideLoading();
                        if(baseResponsBean!=null){
                            if(baseResponsBean.getSuccess()){
                                mRootView.CofirmSpotSuccess();
                            }else {
                                if(baseResponsBean.getErrMsg()!=null){
                                    ToastUtils.showShort("提交失败"+baseResponsBean.getErrMsg());
                                }else {
                                    ToastUtils.showShort("提交失败，请重试");
                                }
                            }


                        }else {
                            ToastUtils.showShort("提交失败，请重试");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mRootView.hideLoading();
                        ToastUtils.showShort("提交失败，请重试"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public void StartEquip(String idx){
        mModel.StartEquip(idx).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponsBean baseResponsBean) {
                        mRootView.hideLoading();
                        if(baseResponsBean!=null){
                            if(baseResponsBean.getSuccess()){
                                mRootView.StartEquipSuccess();
                            }else {
                                if(baseResponsBean.getErrMsg()!=null){
                                    ToastUtils.showShort("开始设备失败"+baseResponsBean.getErrMsg());
                                }else{
                                    ToastUtils.showShort("开始设备失败，请重试");
                                }
                            }

                        }else {
                            ToastUtils.showShort("开始设备失败，请重试");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mRootView.hideLoading();
                        ToastUtils.showShort("开始设备失败，请重试"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void EndEquip(String idx){
        mModel.EndEquip(idx).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SpotCheckResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SpotCheckResponse spotCheckResponse) {
                        mRootView.hideLoading();
                        if(spotCheckResponse!=null&&spotCheckResponse.getSuccess()){
                            mRootView.EndEquipSuccess(spotCheckResponse.getEntity());
                        }else {
                            ToastUtils.showShort("停止失败，请重试");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mRootView.hideLoading();
                        ToastUtils.showShort("停止失败，请重试"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
