package sbgl3.yunda.module.gzcl.mvp.presenter;

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

import sbgl3.yunda.module.gzcl.entry.FaultOrderBean;
import sbgl3.yunda.module.gzcl.mvp.contract.GzclListContract;


@ActivityScope
public class GzclListPresenter extends BasePresenter<GzclListContract.Model, GzclListContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public GzclListPresenter(GzclListContract.Model model, GzclListContract.View rootView) {
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
                                    }else {
                                        ToastUtils.showShort("加载失败，请重试！");
                                    }
                                }else {
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
}
