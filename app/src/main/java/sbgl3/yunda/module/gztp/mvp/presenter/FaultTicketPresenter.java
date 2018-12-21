package sbgl3.yunda.module.gztp.mvp.presenter;

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

import sbgl3.yunda.module.gztp.entry.FaultOrder;
import sbgl3.yunda.module.gztp.mvp.contract.FaultTicketContract;


@ActivityScope
public class FaultTicketPresenter extends BasePresenter<FaultTicketContract.Model, FaultTicketContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public FaultTicketPresenter(FaultTicketContract.Model model, FaultTicketContract.View rootView) {
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
    public void GetFaultTicketList(int start,int limit,String entityJson,final boolean isLoadmore){
        mModel.getFaultTicktList(start,limit,entityJson).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponsBean<List<FaultOrder>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponsBean<List<FaultOrder>> listBaseResponsBean) {
                        mRootView.hideLoading();
                        if(listBaseResponsBean!=null&&listBaseResponsBean.getRoot()!=null){
                            if(isLoadmore){
                                mRootView.OnLoadMoreTicketListSuccess(listBaseResponsBean.getRoot());
                            }else {
                                mRootView.OnLoadTicketListSuccess(listBaseResponsBean.getRoot());
                            }
                        }else {
                            ToastUtils.showShort("未获取到相关提票信息，请重试");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mRootView.hideLoading();
                        mRootView.OnLoadFaild("未获取到相关提票信息，请重试"+e.getMessage());
                        ToastUtils.showShort("未获取到相关提票信息，请重试"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
