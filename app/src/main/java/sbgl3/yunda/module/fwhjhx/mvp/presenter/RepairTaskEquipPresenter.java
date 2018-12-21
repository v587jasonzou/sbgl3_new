package sbgl3.yunda.module.fwhjhx.mvp.presenter;

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

import sbgl3.yunda.module.fwhjhx.mvp.contract.RepairTaskEquipContract;
import sbgl3.yunda.module.fwhjhx.mvp.entity.RepairTaskList;


@ActivityScope
public class RepairTaskEquipPresenter extends BasePresenter<RepairTaskEquipContract.Model, RepairTaskEquipContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public RepairTaskEquipPresenter(RepairTaskEquipContract.Model model, RepairTaskEquipContract.View rootView) {
        super(model, rootView);
    }
    public void getEquipList(int start,int limit,String entityJson,boolean isLoadmore){
        mModel.getRepairTaskEquips(start,limit,entityJson)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponsBean<List<RepairTaskList>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponsBean<List<RepairTaskList>> listBaseResponsBean) {
                        mRootView.hideLoading();
                        if(listBaseResponsBean!=null&&listBaseResponsBean.getRoot()!=null){
                            if(isLoadmore){
                                mRootView.OnLoadMoreTaskSuceess(listBaseResponsBean.getRoot());
                            }else {
                                mRootView.OnLoadTaskSuccess(listBaseResponsBean.getRoot());
                            }
                        }else {
                            ToastUtils.showShort("获取检修设备失败，请重试");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mRootView.hideLoading();
                        ToastUtils.showShort("获取检修设备失败，请重试"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
