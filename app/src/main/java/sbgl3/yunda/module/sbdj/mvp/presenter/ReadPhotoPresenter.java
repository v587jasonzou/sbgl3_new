package sbgl3.yunda.module.sbdj.mvp.presenter;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.jess.arms.base.entry.BaseResponsBean;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.utilcode.util.FileUtils;
import com.jess.arms.utils.utilcode.util.ImageUtils;
import com.jess.arms.utils.utilcode.util.ToastUtils;
import com.lzy.imagepicker.bean.ImageItem;

import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.jess.arms.base.AppConstant;
import sbgl3.yunda.entry.ImageUploadResponse;
import sbgl3.yunda.entry.ImagesBean;
import sbgl3.yunda.module.sbdj.mvp.contract.ReadPhotoContract;


@ActivityScope
public class ReadPhotoPresenter extends BasePresenter<ReadPhotoContract.Model, ReadPhotoContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public ReadPhotoPresenter(ReadPhotoContract.Model model, ReadPhotoContract.View rootView) {
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

    public void getImages(String idx){
        mModel.getImages(idx).observeOn(Schedulers.io())
                .subscribe(new Observer<BaseResponsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponsBean baseResponsBean) {
                        if(baseResponsBean!=null){
                            if(baseResponsBean.getSuccess()){
                                if(baseResponsBean.getIamges()!=null){
                                    Map<String,String> photosStr = baseResponsBean.getIamges();
                                    if (photosStr != null && photosStr.size() > 0) {
                                        BitmapFactory.Options options = new BitmapFactory.Options();
                                        Bitmap thumbnail = null;
                                        HashMap<String, Object> image = null;
                                        byte[] photoByte = null;
                                        File file = null;
                                        OutputStream out = null;
                                        Bitmap bitmap = null;
                                        Iterator<Map.Entry<String, String>> it = photosStr.entrySet().iterator();
                                        int i = 0;
                                        List<ImagesBean> images = new ArrayList<>();
                                        while (it.hasNext()) {
                                            Map.Entry<String, String> entry = it.next();
                                            photoByte = Base64.decode(entry.getValue(), Base64.DEFAULT);
                                            bitmap =  ImageUtils.getBitmap(photoByte,0);
                                            String SavePath = AppConstant.IMAGE_PATH + FileUtils.getRemoteFileName(entry.getKey())+AppConstant.JPG_EXTNAME;
                                            ImagesBean bean = new ImagesBean();
                                            if(ImageUtils.save(bitmap,SavePath,Bitmap.CompressFormat.JPEG)){
                                                bean.setImagesLocalPath(SavePath);
                                            }
//                                            try {
//                                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
//                                                out.flush();
//                                            } catch (Exception e) {
//                                                Log.e("error",e.getMessage());
//                                            }
                                            bean.setImagesPath(entry.getKey());
//                                            bean.setImageBitmap(bitmap);
                                            images.add(bean);
                                        }
                                        if(images.size()>0){
                                            mRootView.getImagesSuccess(images);
                                        }else {
                                            mRootView.getImagesSuccess(null);
                                        }
                                    }else {
                                        mRootView.getImagesSuccess(null);
                                    }
                                }else {

                                    mRootView.getImagesSuccess(null);
                                }
                            }else {
                                mRootView.getImagesSuccess(null);
                            }
                            mRootView.hideLoading();
                        }else {
                            mRootView.hideLoading();
                        }
                        mRootView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mRootView.hideLoading();
                        mRootView.getImagesSuccess(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public void UploadImage(ImageItem item){
        if(mModel.UpLoadImages(item,"e_equipment_info")!=null){
            mModel.UpLoadImages(item,"e_equipment_info").observeOn(AndroidSchedulers.mainThread())
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
                                    if(FileUtils.copyFile(item.path,AppConstant.IMAGE_PATH+FileUtils.getRemoteFileName(imageUploadResponse.getFilePath())+AppConstant.JPG_EXTNAME)){
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
    public void ConfirmRecord(String idx,String tableName,String filePathArray){
        mModel.ConfirmImages(idx,tableName,filePathArray).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponsBean baseResponsBean) {
                        mRootView.hideLoading();
                        if(baseResponsBean!=null){
                            if(baseResponsBean.getSuccess()){
                                mRootView.ConfirmImageSuccess();
                            }else {
                                if(baseResponsBean.getErrMsg()!=null){
                                    ToastUtils.showShort("提交失败请重试"+baseResponsBean.getErrMsg());
                                }else {
                                    ToastUtils.showShort("提交失败请重试");
                                }

                            }
                        }else {
                            ToastUtils.showShort("提交失败请重试");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mRootView.hideLoading();
                        ToastUtils.showShort("提交失败请重试"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
