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

import sbgl3.yunda.module.userconfirm.entry.PlanRepairEquipListBean;
import sbgl3.yunda.module.userconfirm.entry.RepairScopeCase;
import sbgl3.yunda.module.userconfirm.entry.RepairWorkOrder;
import sbgl3.yunda.module.userconfirm.mvp.contract.PlanRepairOrderConfirmContract;


@ActivityScope
public class PlanRepairOrderConfirmPresenter extends BasePresenter<PlanRepairOrderConfirmContract.Model, PlanRepairOrderConfirmContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    PlanRepairEquipListBean repairEquipListBean;

    @Inject
    public PlanRepairOrderConfirmPresenter(PlanRepairOrderConfirmContract.Model model, PlanRepairOrderConfirmContract.View rootView) {
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

    public void getRepairScopes(int start, int limit, String entityJson){
        if (null!=mModel){
            mModel.getRepairScopes(start,limit,entityJson)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResponsBean<List<RepairScopeCase>>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseResponsBean<List<RepairScopeCase>> listBaseResponsBean) {
                            if (null!=mRootView){
                                mRootView.hideLoading();
                                if (null!=listBaseResponsBean){
                                    if (listBaseResponsBean.getRoot()!=null){
                                        mRootView.loadRepairScope(listBaseResponsBean.getRoot());
                                    }
                                }else {
                                    ToastUtils.showShort("加载失败，请重试");
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (null!=mRootView){
                                mRootView.hideLoading();
                            }
                            if (null!=e.getMessage()){
                                ToastUtils.showShort("加载失败，请重试"+e.getMessage());
                            }
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    public void getWorkOrders(int start, int limit, String entityJson){
        if (null!=mModel){
            mModel.getWorkOrders(start,limit,entityJson)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResponsBean<List<RepairWorkOrder>>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseResponsBean<List<RepairWorkOrder>> listBaseResponsBean) {
                            if (null!=mRootView){
                                mRootView.hideLoading();
                            }
                            if (null!=listBaseResponsBean){
                                if (null!=mRootView){
                                    mRootView.loadWorkOrder(listBaseResponsBean.getRoot());
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (null!=mRootView){
                                mRootView.hideLoading();
                            }

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    public void planRepairConfirm(String ids){
        if (null!=mModel){
            mModel.planRepairConfirm(ids)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResponsBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseResponsBean baseResponsBean) {
                            if (null!=baseResponsBean){
                                if (baseResponsBean.getSuccess()){
                                    ToastUtils.showShort("操作成功！");
                                    if (null!=mRootView){
                                        repairEquipListBean = mRootView.loadNextData();
                                        mRootView.getNextDataSuccess(repairEquipListBean);
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
