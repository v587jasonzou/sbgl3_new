package sbgl3.yunda.module.evaluate.mvp.presenter;

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

import sbgl3.yunda.module.appraise.entry.AppraiseTemplateBean;
import sbgl3.yunda.module.evaluate.entry.EvaluateTemplateBean;
import sbgl3.yunda.module.evaluate.mvp.contract.EvaluateTemplateContract;


@ActivityScope
public class EvaluateTemplatePresenter extends BasePresenter<EvaluateTemplateContract.Model, EvaluateTemplateContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public EvaluateTemplatePresenter(EvaluateTemplateContract.Model model, EvaluateTemplateContract.View rootView) {
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
                    .subscribe(new Observer<BaseResponsBean<List<EvaluateTemplateBean>>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseResponsBean<List<EvaluateTemplateBean>> listBaseResponsBean) {
                            if (null!=mRootView){
                                mRootView.hideLoading();
                                if (null != listBaseResponsBean){
                                    if (null!=listBaseResponsBean.getRoot()){
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
                            if (null!=mRootView){
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

    public void addEvaluateTemplate(String idx, String templateIdx){
        if (null!=mModel){
            mModel.addEvaluateTemplate(idx, templateIdx)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResponsBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseResponsBean baseResponsBean) {
                            if (null != baseResponsBean){
                                if (baseResponsBean.getSuccess()){
                                    if(null!=mRootView){
                                        mRootView.addSuccess();
                                    }
                                    ToastUtils.showShort("添加成功！");
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
