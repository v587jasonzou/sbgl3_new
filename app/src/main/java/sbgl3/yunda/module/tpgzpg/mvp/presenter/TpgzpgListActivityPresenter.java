package sbgl3.yunda.module.tpgzpg.mvp.presenter;

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

import sbgl3.yunda.SysInfo;
import sbgl3.yunda.entry.UserResponse;
import sbgl3.yunda.module.tpgzpg.entry.FaultOrderBean;
import sbgl3.yunda.module.tpgzpg.mvp.contract.TpgzpgListContract;


@ActivityScope
public class TpgzpgListActivityPresenter extends BasePresenter<TpgzpgListContract.Model, TpgzpgListContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public TpgzpgListActivityPresenter(TpgzpgListContract.Model model, TpgzpgListContract.View rootView) {
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

    public void getFaultEquipments(int start, int limit, String entityJson, final boolean isLoadMore){
        if (null!=mModel){
            mModel.getFaultEquipments(start,limit,entityJson)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResponsBean<List<FaultOrderBean>>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseResponsBean<List<FaultOrderBean>> listBaseResponsBean) {
                            if (null!=mRootView){
                                mRootView.hideLoading();
                                if (null!=listBaseResponsBean){
                                    if (null!=listBaseResponsBean.getRoot()){
                                        mRootView.loadSuccess();
                                        if (!isLoadMore){
                                            mRootView.loadFaultData(listBaseResponsBean.getRoot());
                                        } else {
                                            mRootView.loadMoreFaultData(listBaseResponsBean.getRoot());
                                        }
                                    }else if (null!=listBaseResponsBean.getErrMsg()){
                                        ToastUtils.showShort("加载失败，请重试！"+listBaseResponsBean.getErrMsg());
                                    }
                                }else {
                                    ToastUtils.showShort("加载失败，请重试！");
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (null!=mRootView){
                                mRootView.hideLoading();
                            }
                            if (e.getMessage()!=null){
                                ToastUtils.showShort("加载失败，请重试！"+e.getMessage());
                            }
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    public void getSelectUsers(String empName){
        if (null!=mModel){
            mModel.getUsers(SysInfo.orgid,empName)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<UserResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(UserResponse baseResponsBean) {
                            if (null!=mRootView){
                                mRootView.hideLoading();
                                if(baseResponsBean!=null){
                                    if(baseResponsBean.getUserList()!=null){
                                        mRootView.getFixUserSuccess(baseResponsBean.getUserList());
                                    }else {
                                        ToastUtils.showShort("无施修人员相关数据");
                                    }
                                }else {
                                    ToastUtils.showShort("获取施修人员数据失败，请重试");
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (null!=mRootView){
                                mRootView.hideLoading();
                            }
                            ToastUtils.showShort("获取施修人员数据失败，请重试"+e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    public void confirm(String ids,String entityJson){
        if (null!=mModel){
            mModel.confirm(ids,entityJson)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResponsBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseResponsBean baseResponsBean) {
                            if (null!=mRootView){
                                mRootView.hideLoading();
                                if(baseResponsBean!=null){
                                    if(baseResponsBean.getSuccess()){
                                        mRootView.submitSuccess();
                                    }else {
                                        if(baseResponsBean.getErrMsg()!=null){
                                            ToastUtils.showShort("提交失败"+baseResponsBean.getErrMsg());
                                        }else {
                                            ToastUtils.showShort("提交失败,请重试");
                                        }

                                    }
                                }else {
                                    ToastUtils.showShort("提交失败,请重试");
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (null!=mRootView){
                                mRootView.hideLoading();
                            }
                            ToastUtils.showShort("提交失败,请重试"+e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

}
