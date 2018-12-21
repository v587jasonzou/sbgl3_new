package sbgl3.yunda.module.gzqr.mvp.presenter;

import android.app.Application;

import com.jess.arms.base.entry.BaseResponsBean;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import sbgl3.yunda.module.gzqr.entry.PlanRepairEquipListBean;
import sbgl3.yunda.module.gzqr.mvp.contract.GzqrEquipmentListContract;


@ActivityScope
public class GzqrEquipmentListPresenter extends BasePresenter<GzqrEquipmentListContract.Model, GzqrEquipmentListContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public GzqrEquipmentListPresenter(GzqrEquipmentListContract.Model model, GzqrEquipmentListContract.View rootView) {
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

    public void getEquipments(int start, int limt, String entityJson,Long orgId,final boolean isLoadMore){
        if (null!=mModel){
            mModel.getEquipments(start,limt,entityJson,orgId)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResponsBean<List<PlanRepairEquipListBean>>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseResponsBean<List<PlanRepairEquipListBean>> listBaseResponsBean) {
                            if (null!=mRootView){
                                mRootView.hideLoading();
                                if(null != listBaseResponsBean){
                                    if (null!=listBaseResponsBean.getRoot()&&listBaseResponsBean.getRoot().size()>0){
                                        if (!isLoadMore){
                                            mRootView.loadData(listBaseResponsBean.getRoot());
                                        } else {
                                            mRootView.loadMoreData(listBaseResponsBean.getRoot());
                                        }
                                    } else {
                                        mRootView.loadFail();
                                    }
                                } else {
                                    mRootView.loadFail();
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
}
