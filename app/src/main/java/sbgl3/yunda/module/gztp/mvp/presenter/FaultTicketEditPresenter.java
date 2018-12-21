package sbgl3.yunda.module.gztp.mvp.presenter;

import android.app.Application;

import com.jess.arms.base.AppConstant;
import com.jess.arms.base.entry.BaseResponsBean;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.utilcode.util.FileUtils;
import com.jess.arms.utils.utilcode.util.StringUtils;
import com.jess.arms.utils.utilcode.util.ToastUtils;
import com.lzy.imagepicker.bean.ImageItem;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import sbgl3.yunda.entry.ImageUploadResponse;
import sbgl3.yunda.entry.ImagesBean;
import sbgl3.yunda.module.gztp.entry.FaultOrder;
import sbgl3.yunda.module.gztp.entry.ResponsEquip;
import sbgl3.yunda.module.gztp.mvp.contract.FaultTicketEditContract;


@ActivityScope
public class FaultTicketEditPresenter extends BasePresenter<FaultTicketEditContract.Model, FaultTicketEditContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public FaultTicketEditPresenter(FaultTicketEditContract.Model model, FaultTicketEditContract.View rootView) {
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
    public void getFaultTicketNo(){
        mModel.getFaultOrderNo().observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FaultOrder>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(FaultOrder faultOrder) {
                        mRootView.hideLoading();
                        if(faultOrder!=null){
                            if(!StringUtils.isTrimEmpty(faultOrder.getFaultOrderNo())){
                                mRootView.getFaultNoSuccess(faultOrder.getFaultOrderNo());
                            }else {
                                ToastUtils.showShort("获取提票编号失败，请重试");
                            }
                        }else {
                            ToastUtils.showShort("获取提票编号失败，请重试");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mRootView.hideLoading();
                        ToastUtils.showShort("获取提票编号失败，请重试"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public void getEquipInfo(String code,String type){
        if(mModel!=null){
            mModel.getEquipInfo(code,type).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResponsEquip>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(ResponsEquip responsEquip) {
                            mRootView.hideLoading();
                            if(responsEquip!=null&&responsEquip.getSuccess()){
                                mRootView.getEquipSuccess(responsEquip.getEquipment());
                            }else {
                                if(responsEquip!=null&&responsEquip.getErrMsg()!=null){
                                    ToastUtils.showShort(responsEquip.getErrMsg());
                                }else {
                                    ToastUtils.showShort("获取设备信息失败，请重试");
                                }

                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            mRootView.hideLoading();
                            ToastUtils.showShort("获取设备信息失败，请重试"+e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    public void UploadImage(ImageItem item){
        if(mModel.UpLoadImages(item,"e_fault_order")!=null){
            mModel.UpLoadImages(item,"e_fault_order").observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ImageUploadResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(ImageUploadResponse imageUploadResponse) {
                            mRootView.hideLoading();
                            if(imageUploadResponse!=null){
                                if(imageUploadResponse.isSuccess()){
                                    ImagesBean bean = new ImagesBean();
                                    bean.setImagesPath(imageUploadResponse.getFilePath());
                                    if(FileUtils.copyFile(item.path, AppConstant.IMAGE_PATH+FileUtils.getRemoteFileName(imageUploadResponse.getFilePath())+AppConstant.JPG_EXTNAME)){
                                        FileUtils.deleteFile(item.path);
                                        bean.setImagesLocalPath(AppConstant.IMAGE_PATH+FileUtils.getRemoteFileName(imageUploadResponse.getFilePath())+AppConstant.JPG_EXTNAME);
                                    }
                                    ToastUtils.showShort("图片上传成功");
                                    mRootView.UpLoadImagesSuccess(bean);
//                                bean.setImagesLocalPath();
//                                mRootView.UpLoadImagesSuccess();
                                }else {
                                    FileUtils.deleteFile(item.path);
                                    ToastUtils.showShort("由于网络问题，照片上传失败，请检查网络是否通畅，重新拍照上传！");
                                }
                            }else {
                                FileUtils.deleteFile(item.path);
                                ToastUtils.showShort("由于网络问题，照片上传失败，请检查网络是否通畅，重新拍照上传！");
                            }

                        }

                        @Override
                        public void onError(Throwable e) {
                            mRootView.hideLoading();
                            FileUtils.deleteFile(item.path);
                            ToastUtils.showShort("由于网络问题，照片上传失败，请检查网络是否通畅，重新拍照上传！");
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }else {
            FileUtils.deleteFile(item.path);
            mRootView.hideLoading();
            ToastUtils.showShort("由于网络问题，照片上传失败，请检查网络是否通畅，重新拍照上传！");
        }

    }
    public void AddFaultTicket(String filepath,String entityJson){
        if(mModel!=null){
            mModel.AddOrder(filepath,entityJson).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResponsBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseResponsBean baseResponsBean) {
                            mRootView.hideLoading();
                            if(baseResponsBean!=null&&baseResponsBean.getSuccess()){
                                mRootView.addOrderSuccess();
                            }else {
                                if(baseResponsBean!=null&&baseResponsBean.getErrMsg()!=null){
                                    ToastUtils.showShort("提交失败"+baseResponsBean.getErrMsg());
                                }else {
                                    ToastUtils.showShort("提交失败,请重试");
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            mRootView.hideLoading();
                            ToastUtils.showShort("提交失败,请重试"+e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }
}
