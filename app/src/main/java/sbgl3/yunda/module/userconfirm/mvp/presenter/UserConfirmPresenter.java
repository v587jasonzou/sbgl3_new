package sbgl3.yunda.module.userconfirm.mvp.presenter;

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
import sbgl3.yunda.module.userconfirm.entry.FaultOrderBean;
import sbgl3.yunda.module.userconfirm.entry.PlanRepairEquipListBean;
import sbgl3.yunda.module.userconfirm.mvp.contract.UserConfirmContract;


@ActivityScope
public class UserConfirmPresenter extends BasePresenter<UserConfirmContract.Model, UserConfirmContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public UserConfirmPresenter(UserConfirmContract.Model model, UserConfirmContract.View rootView) {
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

    public void getPlanRepairEquipments(int start, int limit, String entityJson, final boolean isLoadMore){
        if (null!=mModel){
            mModel.getPlanRepairEquipments(start,limit,entityJson)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResponsBean<List<PlanRepairEquipListBean>>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseResponsBean<List<PlanRepairEquipListBean>> listBaseResponsBean) {
                            if (null!=mRootView){
                                mRootView.hideLoading();
                                if (null != listBaseResponsBean){
                                    if (null != listBaseResponsBean.getRoot()){
                                        mRootView.loadSuccess();
                                        if (!isLoadMore){
                                            mRootView.loadData(listBaseResponsBean.getRoot());
                                        } else {
                                            mRootView.loadMoreData(listBaseResponsBean.getRoot());
                                        }
                                    } else if (listBaseResponsBean.getErrMsg()!=null){
                                        ToastUtils.showShort("加载失败！"+listBaseResponsBean.getErrMsg());
                                    }
                                } else {
                                    ToastUtils.showShort("加载失败！");
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (null!=mRootView){
                                mRootView.hideLoading();
                                ToastUtils.showShort("加载失败！"+e);
                            }
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    public void getAllPlanRepairEquipments(String entityJson){
        if (null!=mModel){
            mModel.getAllPlanRepairEquipments(entityJson)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResponsBean<List<PlanRepairEquipListBean>>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseResponsBean<List<PlanRepairEquipListBean>> listBaseResponsBean) {
                            if (null != listBaseResponsBean){
                                if (null != listBaseResponsBean.getRoot()){
                                    if (null!=mRootView){
                                        mRootView.loadAllPlanData(listBaseResponsBean.getRoot());
                                    }
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

    public void getAllFaultEquipments(String entityJson){
        if (null!=mModel){
            mModel.getAllFaultEquipments(entityJson)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResponsBean<List<FaultOrderBean>>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseResponsBean<List<FaultOrderBean>> listBaseResponsBean) {
                            if (null!=listBaseResponsBean){
                                if (null!=listBaseResponsBean.getRoot()){
                                    if (mRootView!=null){
                                        mRootView.loadAllFaultData(listBaseResponsBean.getRoot());
                                    }
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
