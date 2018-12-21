package sbgl3.yunda.module.sbdj.mvp.presenter;

import android.app.Application;

import com.jess.arms.base.entry.BaseResponsBean;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.utilcode.util.ToastUtils;

import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import sbgl3.yunda.module.sbdj.entity.EquipResponse;
import sbgl3.yunda.module.sbdj.entity.EquipmentUnionRFID;
import sbgl3.yunda.module.sbdj.mvp.contract.SbdjMainContract;


@ActivityScope
public class SbdjMainPresenter extends BasePresenter<SbdjMainContract.Model, SbdjMainContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public SbdjMainPresenter(SbdjMainContract.Model model, SbdjMainContract.View rootView) {
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
    public void getEquipmentInfos(int start, int limit, String entityJson,boolean isLoadMore){
        mModel.getEquipments(start,limit,entityJson).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponsBean<List<EquipmentUnionRFID>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponsBean<List<EquipmentUnionRFID>> listBaseResponsBean) {
                        mRootView.hideLoading();
                        if (listBaseResponsBean!=null){
                            if(listBaseResponsBean.getRoot()!=null&&listBaseResponsBean.getRoot().size()>0){
                                if(isLoadMore){
                                    mRootView.OnLoadMoreEquipsSuccess(listBaseResponsBean.getRoot());
                                }else {
                                    mRootView.OnLoadEquipsSuccess(listBaseResponsBean.getRoot());
                                }
                            }else {
                                if(listBaseResponsBean.getErrMsg()!=null){
                                    ToastUtils.showShort("获取设备信息失败"+listBaseResponsBean.getErrMsg());
                                }else {
                                    ToastUtils.showShort("获取设备信息失败，请重试");
                                }
                            }
                        }else {
                            ToastUtils.showShort("获取设备信息失败，请重试");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mRootView.hideLoading();
                        ToastUtils.showShort("获取设备信息失败，请重试"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public void RegisterEquip(String entityJson){
        mModel.RegisterEquip(entityJson).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponsBean baseResponsBean) {
                        mRootView.hideLoading();
                        if(baseResponsBean!=null&&baseResponsBean.getSuccess()!=null){
                            if(baseResponsBean.getSuccess()){
                                mRootView.OnRegisterSuccess();
                            }else {
                                if(baseResponsBean.getErrMsg()!=null){
                                    mRootView.OnRegisterFaild("设备登记失败，"+baseResponsBean.getErrMsg());
                                }else {
                                    mRootView.OnRegisterFaild("设备登记失败，");
                                }
                            }
                        }else {
                            mRootView.OnRegisterFaild("设备登记失败，");
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        mRootView.hideLoading();
                        mRootView.OnRegisterFaild("设备登记失败，"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public void UpLoadEquipInfo(String equipcode){
        mModel.UpLoadRegister(equipcode).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<EquipResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(EquipResponse stringObjectMap) {
                        if (stringObjectMap!=null){
                            mRootView.OnUpLoadResSuccess(stringObjectMap);
                        }else {
                            mRootView.hideLoading();
                            ToastUtils.showShort("获取设备信息失败");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mRootView.hideLoading();
                        ToastUtils.showShort("获取设备信息失败");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public void UnregisterEquip(String ids){
        mModel.UnRegisterEquip(ids).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponsBean baseResponsBean) {
                        if(baseResponsBean!=null){
                            if(baseResponsBean.getSuccess()){
                                mRootView.UnRegiterSuccess();
                            }else {
                                mRootView.UnRegisterFaild("解绑失败");
                            }
                        }else {
                            mRootView.UnRegisterFaild("解绑失败");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mRootView.UnRegisterFaild("解绑失败");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
