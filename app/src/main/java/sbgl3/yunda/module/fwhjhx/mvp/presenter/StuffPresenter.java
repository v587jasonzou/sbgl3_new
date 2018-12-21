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

import sbgl3.yunda.module.fwhjhx.mvp.contract.StuffContract;
import sbgl3.yunda.module.fwhjhx.mvp.entity.MateriaBean;
import sbgl3.yunda.module.fwhjhx.mvp.entity.StuffParams;


@ActivityScope
public class StuffPresenter extends BasePresenter<StuffContract.Model, StuffContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public StuffPresenter(StuffContract.Model model, StuffContract.View rootView) {
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
    public void getStuffs(int start,int limit,String entityJson){
        mModel.getStuffs(start,limit,entityJson).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponsBean<List<MateriaBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponsBean<List<MateriaBean>> listBaseResponsBean) {
                        if(listBaseResponsBean!=null){
                            if(listBaseResponsBean.getRoot()!=null&&listBaseResponsBean.getRoot().size()>0){
                                mRootView.getStuffsSucceess(listBaseResponsBean.getRoot());
                            }else {
                                mRootView.getStuffsSucceess(null);
                                ToastUtils.showShort("无相关物料信息");
                            }
                        }else {
                            mRootView.getStuffsSucceess(null);
                            ToastUtils.showShort("获取相关物料信息失败，请重试");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mRootView.getStuffsSucceess(null);
                        ToastUtils.showShort("获取相关物料信息失败，请重试"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public void getStuffParams(int start,int limit,String entityJson){
        mModel.getStuffsParam(start,limit,entityJson).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponsBean<List<StuffParams>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponsBean<List<StuffParams>> listBaseResponsBean) {
                        mRootView.hideLoading();
                        if(listBaseResponsBean!=null){
                            if(listBaseResponsBean.getRoot()!=null&&listBaseResponsBean.getRoot().size()>0){
                                mRootView.getStuffsParamsSuccess(listBaseResponsBean.getRoot());
                            }else {
                                ToastUtils.showShort("无相关物料信息");
                            }
                        }else {
                            ToastUtils.showShort("获取相关物料信息失败，请重试");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mRootView.hideLoading();
                        ToastUtils.showShort("获取相关物料信息失败，请重试"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public void UpdataStuffs(String entityJson,final String type){
        mModel.UpdateStuff(entityJson).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponsBean baseResponsBean) {
                        mRootView.hideLoading();
                        if(baseResponsBean!=null&&baseResponsBean.getSuccess()){
                            mRootView.UpdataStuffSuccess(type);
                        }else {
                            if(type.equals("添加")){
                                ToastUtils.showShort("添加物料提交失败");
                            }else {
                                ToastUtils.showShort("修改物料提交失败");
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mRootView.hideLoading();
                        if(type.equals("添加")){
                            ToastUtils.showShort("添加物料提交失败"+e.getMessage());
                        }else {
                            ToastUtils.showShort("修改物料提交失败"+e.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public void StartWaitStuff(String idx){
        mModel.StartStuffWait(idx).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponsBean baseResponsBean) {
                        mRootView.hideLoading();
                        if(baseResponsBean!=null){
                            if(baseResponsBean.getSuccess()){
                                mRootView.StartStuffWaitSuccess();
                            }else {
                                if(baseResponsBean.getErrMsg()!=null){
                                    ToastUtils.showShort("开始待料失败"+baseResponsBean.getErrMsg());
                                }else {
                                    ToastUtils.showShort("开始待料失败,请重试");
                                }
                            }
                        }else {
                            ToastUtils.showShort("开始待料失败,请重试");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showShort("开始待料失败,请重试"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void EndtWaitStuff(String idx){
        mModel.EndStuffWait(idx).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponsBean baseResponsBean) {
                        mRootView.hideLoading();
                        if(baseResponsBean!=null){
                            if(baseResponsBean.getSuccess()){
                                mRootView.EndStuffWaitSuccess();
                            }else {
                                if(baseResponsBean.getErrMsg()!=null){
                                    ToastUtils.showShort("结束待料失败"+baseResponsBean.getErrMsg());
                                }else {
                                    ToastUtils.showShort("结束待料失败,请重试");
                                }
                            }
                        }else {
                            ToastUtils.showShort("结束待料失败,请重试");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showShort("结束待料失败,请重试"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void DeleteStuff(String ids){
        mModel.DeleteStuff(ids).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponsBean baseResponsBean) {
                        mRootView.hideLoading();
                        if(baseResponsBean!=null){
                            if(baseResponsBean.getSuccess()){
                                mRootView.DeleteStuffSuccess();
                            }else {
                                if(baseResponsBean.getErrMsg()!=null){
                                    ToastUtils.showShort("删除物料失败"+baseResponsBean.getErrMsg());
                                }else {
                                    ToastUtils.showShort("删除物料失败,请重试");
                                }
                            }
                        }else {
                            ToastUtils.showShort("删除物料失败,请重试");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showShort("删除物料失败,请重试"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
