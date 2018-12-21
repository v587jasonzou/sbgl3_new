package sbgl3.yunda.module.appraise.mvp.presenter;

import android.app.Application;

import com.jess.arms.base.entry.BaseResponsBean;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.utilcode.util.ToastUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import sbgl3.yunda.module.appraise.entry.AppraiseTemplateBean;
import sbgl3.yunda.module.appraise.mvp.contract.AppraiseTemplateContract;


@ActivityScope
public class AppraiseTemplatePresenter extends BasePresenter<AppraiseTemplateContract.Model, AppraiseTemplateContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public AppraiseTemplatePresenter(AppraiseTemplateContract.Model model, AppraiseTemplateContract.View rootView) {
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

    public void getTemplate(int start, int limit, String sort, String whereListJson, final boolean isLoadMore){
        if (null!=mModel){
            mModel.getTemplate(start,limit,sort,whereListJson)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResponsBean<List<AppraiseTemplateBean>>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseResponsBean<List<AppraiseTemplateBean>> listBaseResponsBean) {
                            if (null!=mRootView){
                                mRootView.hideLoading();
                                if (null != listBaseResponsBean){
                                    if (null != listBaseResponsBean.getRoot()){
                                        mRootView.loadSuccess();
                                        if (!isLoadMore){
                                            mRootView.LoadData(listBaseResponsBean.getRoot());
                                        } else {
                                            mRootView.LoadMoreData(listBaseResponsBean.getRoot());
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
                            if(null!=mRootView){
                                mRootView.hideLoading();
                            }
                            if (null!=e.getMessage()){
                                ToastUtils.showShort("加载失败，请重试！"+e.getMessage());
                            }
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    public void addAppraiseTemplate(String idx, String templateIdx){
        if (null!=mModel){
            mModel.addAppraiseTemplate(idx, templateIdx)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResponsBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseResponsBean baseResponsBean) {
                            if (null != baseResponsBean){
                                if (baseResponsBean.getSuccess()){
                                    ToastUtils.showShort("添加成功！");
                                    if (null!=mRootView){
                                        mRootView.addSuccess();
                                    }
                                } else {
                                    ToastUtils.showShort("添加失败，请重试！");
                                }
                            } else {
                                ToastUtils.showShort("添加失败，请重试！");
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (e.getMessage()!=null){
                                ToastUtils.showShort("添加失败！"+e.getMessage());
                            }
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    public void startUp(String appraisePlanIdx, int planType) {
        if (null!=mModel){
            mModel.startUp(appraisePlanIdx, planType)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResponsBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseResponsBean baseResponsBean) {
                            if (baseResponsBean .getSuccess()){

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
