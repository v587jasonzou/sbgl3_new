package sbgl3.yunda.module.equipinfo.mvp.presenter;

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

import sbgl3.yunda.module.equipinfo.entity.EquipmentPrimaryInfo;
import sbgl3.yunda.module.equipinfo.mvp.contract.EquipListContract;


@ActivityScope
public class EquipListPresenter extends BasePresenter<EquipListContract.Model, EquipListContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public EquipListPresenter(EquipListContract.Model model, EquipListContract.View rootView) {
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
    public void getEquipList(int start, int limit, String entityJson,boolean isLoadMore){
        mModel.getEquipList(start,limit,entityJson).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponsBean<List<EquipmentPrimaryInfo>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponsBean<List<EquipmentPrimaryInfo>> listBaseResponsBean) {
                        mRootView.hideLoading();
                        if(listBaseResponsBean!=null&&listBaseResponsBean.getRoot()!=null){
                            if(isLoadMore){
                                mRootView.OnLoadMoreDataSuceess(listBaseResponsBean.getRoot());
                            }else {
                                mRootView.OnLoadDataSuccess(listBaseResponsBean.getRoot());
                            }
                        }else {
                            ToastUtils.showShort("获取信息失败，请重试");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showShort("获取信息失败，请重试"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
