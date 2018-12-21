package sbgl3.yunda.module.ysrqr.mvp.presenter;

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

import sbgl3.yunda.module.ysrqr.entry.PlanRepairEquipListBean;
import sbgl3.yunda.module.ysrqr.mvp.contract.YsrqrEquipmentListContract;


@ActivityScope
public class YsrqrEquipmentListPresenter extends BasePresenter<YsrqrEquipmentListContract.Model, YsrqrEquipmentListContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public YsrqrEquipmentListPresenter(YsrqrEquipmentListContract.Model model, YsrqrEquipmentListContract.View rootView) {
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

    public void getEquipments(int start, int limt, String entityJson,final boolean isLoadMore){
        if (null!=mModel){
            mModel.getEquipments(start,limt,entityJson)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResponsBean<List<PlanRepairEquipListBean>>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseResponsBean<List<PlanRepairEquipListBean>> listBaseResponsBean) {
                            if (null!=mRootView){
                                mRootView.hideLoading();
                                if (null!=listBaseResponsBean){
                                    if (null!=listBaseResponsBean.getRoot()){
                                        mRootView.loadSuccess();
                                        if (!isLoadMore){
                                            mRootView.loadData(listBaseResponsBean.getRoot());
                                        } else {
                                            mRootView.loadMoreData(listBaseResponsBean.getRoot());
                                        }
                                    } else {
                                        ToastUtils.showShort("加载失败，请重试！");
                                    }
                                } else {
                                    String msg = "加载失败，请重试！";
                                    if(listBaseResponsBean.getErrMsg()!=null){
                                        msg = msg +listBaseResponsBean.getErrMsg();
                                    }
                                    ToastUtils.showShort(msg);
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (null!=mRootView){
                                mRootView.hideLoading();
                            }
                            ToastUtils.showShort("加载失败，请重试！" + e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
        }

    public void getAllEquipments(String entityJson){
        if (null!=mModel){
            mModel.getAllEquipments(entityJson)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResponsBean<List<PlanRepairEquipListBean>>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseResponsBean<List<PlanRepairEquipListBean>> listBaseResponsBean) {
                            if(null != listBaseResponsBean){
                                if (null!=listBaseResponsBean.getRoot()){
                                    if (null!=mRootView){
                                        mRootView.loadAllData(listBaseResponsBean.getRoot());
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
