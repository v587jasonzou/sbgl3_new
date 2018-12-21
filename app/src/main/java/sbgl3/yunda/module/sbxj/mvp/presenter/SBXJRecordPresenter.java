package sbgl3.yunda.module.sbxj.mvp.presenter;

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

import sbgl3.yunda.module.sbxj.entry.InspectRecord;
import sbgl3.yunda.module.sbxj.mvp.contract.SBXJRecordContract;


@ActivityScope
public class SBXJRecordPresenter extends BasePresenter<SBXJRecordContract.Model, SBXJRecordContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public SBXJRecordPresenter(SBXJRecordContract.Model model, SBXJRecordContract.View rootView) {
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
    public void UpLoadRecordTime(String ids){
        mModel.UpLoadRecordTime(ids).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponsBean baseResponsBean) {
                        if(baseResponsBean!=null){
                            if(baseResponsBean.getSuccess()!=null){
                                if(baseResponsBean.getSuccess()){
                                    ToastUtils.showShort("更新开工时间成功");
                                }else {
                                    ToastUtils.showShort("更新开工时间失败"+baseResponsBean.getErrMsg());
                                }
                            }else {
                                ToastUtils.showShort("更新开工时间失败");
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showShort("更新开工时间失败"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public void getInspectRecords(int start,int limit,String entityJson,final boolean isLoadMore){
        mModel.GetInspectRecords(start,limit,entityJson).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponsBean<List<InspectRecord>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponsBean<List<InspectRecord>> listBaseResponsBean) {
                        if(listBaseResponsBean!=null){
                            if(listBaseResponsBean.getRoot()!=null){
                                if(isLoadMore){
                                    mRootView.LoadMoreData(listBaseResponsBean.getRoot());
                                }else {
                                    mRootView.LoadData(listBaseResponsBean.getRoot());
                                }
                            }else {
                                ToastUtils.showShort("获取巡检范围失败，请重试");
                            }
                        }else {
                            ToastUtils.showShort("获取巡检范围失败，请重试");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showShort("获取巡检范围失败，请重试"+e.getMessage());
                        mRootView.hideLoading();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public void ConfirmRecords(String ids,String checkResult){
        mModel.ConfirmRecords(ids,checkResult).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponsBean baseResponsBean) {
                        mRootView.hideLoading();
                        if(baseResponsBean!=null){
                            if(baseResponsBean.getSuccess()){
                                mRootView.showMessage("提交成功");
                                mRootView.SubmitRecordSuccess();
                            }else {
                                if(baseResponsBean.getErrMsg()!=null){
                                    mRootView.showMessage("提交失败，请重试"+baseResponsBean.getErrMsg());
                                }else {
                                    mRootView.showMessage("提交失败，请重试");
                                }

                            }
                        }else {
                            mRootView.showMessage("提交失败，请重试");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mRootView.hideLoading();
                        mRootView.showMessage("提交失败，请重试"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void ConfirmEquipPlan(String entityJson){
        mModel.ConfirmEquipPlan(entityJson).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponsBean baseResponsBean) {
                        mRootView.hideLoading();
                        if(baseResponsBean!=null){
                            if(baseResponsBean.getSuccess()){
                                mRootView.showMessage("提交成功");
                                mRootView.SubmitPlanSuccess();
                            }else {
                                if(baseResponsBean.getErrMsg()!=null){
                                    mRootView.showMessage("提交失败，请重试"+baseResponsBean.getErrMsg());
                                }else {
                                    mRootView.showMessage("提交失败，请重试");
                                }

                            }
                        }else {
                            mRootView.showMessage("提交失败，请重试");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mRootView.hideLoading();
                        mRootView.showMessage("提交失败，请重试"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
