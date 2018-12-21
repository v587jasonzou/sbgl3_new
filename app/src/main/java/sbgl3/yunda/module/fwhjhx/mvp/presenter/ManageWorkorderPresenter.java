package sbgl3.yunda.module.fwhjhx.mvp.presenter;

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

import sbgl3.yunda.SysInfo;
import com.jess.arms.base.AppConstant;
import sbgl3.yunda.entry.ImageUploadResponse;
import sbgl3.yunda.entry.ImagesBean;
import sbgl3.yunda.entry.UserResponse;
import sbgl3.yunda.module.fwhjhx.mvp.contract.ManageWorkorderContract;
import sbgl3.yunda.module.fwhjhx.mvp.entity.EosDictEntry;
import sbgl3.yunda.module.fwhjhx.mvp.entity.MateriaBean;
import sbgl3.yunda.module.fwhjhx.mvp.entity.RepairHistoryResponse;
import sbgl3.yunda.module.fwhjhx.mvp.entity.WorkOrderResponse;


@ActivityScope
public class ManageWorkorderPresenter extends BasePresenter<ManageWorkorderContract.Model, ManageWorkorderContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public ManageWorkorderPresenter(ManageWorkorderContract.Model model, ManageWorkorderContract.View rootView) {
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
                                            options.inSampleSize = 16;
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
                        }else {
                            mRootView.getImagesSuccess(null);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mRootView.getImagesSuccess(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public void UploadImage(ImageItem item){
        if(mModel.UpLoadImages(item,"e_inspect_record")!=null){
            mModel.UpLoadImages(item,"e_inspect_record").observeOn(AndroidSchedulers.mainThread())
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
    public void getUnits(){
        mModel.getUnits().observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<EosDictEntry>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<EosDictEntry> eosDictEntries) {
                        if(eosDictEntries!=null&&eosDictEntries.size()>0){
                            mRootView.getUnitSuccess(eosDictEntries);
                        }else {
                            mRootView.getUnitSuccess(null);
                            ToastUtils.showShort("获取特殊符号失败");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mRootView.getUnitSuccess(null);
                        ToastUtils.showShort("获取特殊符号失败"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public void getHistorys(int start,int limit,String definidx){
        mModel.getHistory(start,limit,definidx).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RepairHistoryResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RepairHistoryResponse baseResponsBean) {
                        mRootView.hideLoading();
                        if (baseResponsBean!=null){
                            if(baseResponsBean.getHistoryRepairRecords()!=null&&baseResponsBean.getHistoryRepairRecords().size()>0){
                                mRootView.getHistorysSuccess(baseResponsBean.getHistoryRepairRecords());
                            }else {
                                ToastUtils.showShort("获取处理结果记录失败，请重试");
                            }
                        }else {
                            ToastUtils.showShort("获取处理结果记录失败，请重试");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mRootView.hideLoading();
                        ToastUtils.showShort("获取处理结果记录失败，请重试"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public void getSelectUsers(String empName){
        mModel.getUsers(SysInfo.orgid,empName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UserResponse baseResponsBean) {
                        mRootView.hideLoading();
                        if(baseResponsBean!=null){
                            if(baseResponsBean.getUserList()!=null&&baseResponsBean.getUserList().size()>0){
                                mRootView.getFixUserSuccess(baseResponsBean.getUserList());
                            }else {
                                ToastUtils.showShort("无施修人员相关数据");
                            }
                        }else {
                            ToastUtils.showShort("获取施修人员数据失败，请重试");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mRootView.hideLoading();
                        ToastUtils.showShort("获取施修人员数据失败，请重试"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getStuffs(int start,int limit,String entityJson){
        mModel.getStuffs(start,limit,entityJson).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponsBean<List<MateriaBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponsBean<List<MateriaBean>> listBaseResponsBean) {
                        mRootView.hideLoading();
                        if(listBaseResponsBean!=null){
                            if(listBaseResponsBean.getRoot()!=null&&listBaseResponsBean.getRoot().size()>0){
                                mRootView.getStuffSuccess(listBaseResponsBean.getRoot());
                            }else {
                                mRootView.getStuffSuccess(null);
                                ToastUtils.showShort("无相关物料信息");
                            }
                        }else {
                            mRootView.getStuffSuccess(null);
                            ToastUtils.showShort("获取相关物料信息失败，请重试");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mRootView.hideLoading();
                        mRootView.getStuffSuccess(null);
                        ToastUtils.showShort("获取相关物料信息失败，请重试"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public void ConfirmOrder(String idx,String otherWorker,String RepairRecord,String fileArrayPath){
        mModel.ConfirmOrder(idx,otherWorker,RepairRecord,fileArrayPath)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponsBean baseResponsBean) {
                        mRootView.hideLoading();
                        if(baseResponsBean!=null){
                            if(baseResponsBean.getSuccess()){
                                mRootView.confirmSuccess();
                            }else {
                                if(baseResponsBean.getErrMsg()!=null){
                                    ToastUtils.showShort("提交失败"+baseResponsBean.getErrMsg());
                                }else {
                                    ToastUtils.showShort("提交失败,请重试");
                                }

                            }
                        }else {
                            ToastUtils.showShort("提交失败,请重试");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showShort("提交失败,请重试"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public void getNextOrder(String idx){
        mModel.getNextWorkOrder(idx).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WorkOrderResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WorkOrderResponse baseResponsBean) {
                        mRootView.hideLoading();
                        if(baseResponsBean!=null&&baseResponsBean.isSuccess()){
                            mRootView.getNextOrderSuccess(baseResponsBean.getEntity());
                        }else {
                            if(baseResponsBean.getErrMsg()!=null){
                                ToastUtils.showShort("获取下一张工单失败"+baseResponsBean.getErrMsg());
                            }else {
                                ToastUtils.showShort("获取下一张工单失败");
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mRootView.hideLoading();
                        ToastUtils.showShort("获取下一张工单失败"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
