package sbgl3.yunda.module.gzqr.mvp.presenter;

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

import sbgl3.yunda.module.gzqr.entry.RepairScopeCase;
import sbgl3.yunda.module.gzqr.entry.RepairWorkOrder;
import sbgl3.yunda.module.gzqr.mvp.contract.WorkOrderConfirmContract;
import sbgl3.yunda.module.gzqr.entry.PlanRepairEquipListBean;


@ActivityScope
public class WorkOrderConfirmPresenter extends BasePresenter<WorkOrderConfirmContract.Model, WorkOrderConfirmContract.View> {
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
    public WorkOrderConfirmPresenter(WorkOrderConfirmContract.Model model, WorkOrderConfirmContract.View rootView) {
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

    public void getRepairScopes(String entityJson, Long orgId){
        if (null!=mModel){
            mModel.getRepairScopes(entityJson,orgId)
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
                                    if (null!=listBaseResponsBean.getRoot()){
                                        mRootView.loadRepairScope(listBaseResponsBean.getRoot());
                                    }
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

    public void getWorkOrders(String entityJson){
        if (null!=mModel){
            mModel.getWorkOrders(entityJson)
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
                                if (null!=listBaseResponsBean.getRoot()){
                                    if (null!=mRootView){
                                        mRootView.loadWorkOrder(listBaseResponsBean.getRoot());
                                    }
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

    public void confirmSave(String ids){
        if (null!=mModel){
            mModel.confirmSave(ids)
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
                            if (null!=e.getMessage()){
                                ToastUtils.showShort("操作失败！"+e.getMessage());
                            }

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }
}
