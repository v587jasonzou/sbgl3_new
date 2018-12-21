package sbgl3.yunda.module.tpgzpg.mvp.presenter;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.jess.arms.base.AppConstant;
import com.jess.arms.base.entry.BaseResponsBean;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.utilcode.util.FileUtils;
import com.jess.arms.utils.utilcode.util.ImageUtils;
import com.jess.arms.utils.utilcode.util.ToastUtils;

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
import sbgl3.yunda.entry.ImagesBean;
import sbgl3.yunda.entry.UserResponse;
import sbgl3.yunda.module.tpgzpg.entry.BackInfoBean;
import sbgl3.yunda.module.tpgzpg.entry.FaultOrderBean;
import sbgl3.yunda.module.tpgzpg.mvp.contract.TpgzpgEditContract;


@ActivityScope
public class TpgzpgEditPresenter extends BasePresenter<TpgzpgEditContract.Model, TpgzpgEditContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public TpgzpgEditPresenter(TpgzpgEditContract.Model model, TpgzpgEditContract.View rootView) {
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
        if (null!=mModel){
            mModel.getImages(idx).observeOn(Schedulers.io())
                    .subscribe(new Observer<BaseResponsBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseResponsBean baseResponsBean) {
                            if (null!=mRootView){
                                if(baseResponsBean!=null){
                                    if(baseResponsBean.getSuccess()){
                                        if(baseResponsBean.getIamges()!=null){
                                            Map<String,String> photosStr = baseResponsBean.getIamges();
                                            if (photosStr != null && photosStr.size() > 0) {
                                                byte[] photoByte = null;
                                                Bitmap bitmap = null;
                                                Iterator<Map.Entry<String, String>> it = photosStr.entrySet().iterator();
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
                                                if(null!=images&&images.size()>0){
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
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (null!=mRootView){
                                mRootView.hideLoading();
                                mRootView.getImagesSuccess(null);
                            }
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    public void getBackInfo(String sort, String whereListJson){
        if (null!=mModel){
            mModel.getBackInfo(sort,whereListJson)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResponsBean<List<BackInfoBean>>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseResponsBean<List<BackInfoBean>> listBaseResponsBean) {
                            if (null!=listBaseResponsBean){
                                if (null!=listBaseResponsBean.getRoot()){
                                    if (mRootView!=null){
                                        mRootView.loadBackInfo(listBaseResponsBean.getRoot());
                                    }
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (null!=e.getMessage()){
                                ToastUtils.showShort("加载退回信息失败！"+e.getMessage());
                            }
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    public void getSelectUsers(String empName){
        if (null!=mModel){
            mModel.getUsers(SysInfo.orgid,empName)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<UserResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(UserResponse baseResponsBean) {
                            if (null!=mRootView){
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
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (mRootView!=null){
                                mRootView.hideLoading();
                                ToastUtils.showShort("获取施修人员数据失败，请重试"+e.getMessage());
                            }
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    public void confirm(String ids,String entityJson){
        if (null!=mModel){
            mModel.confirm(ids,entityJson)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResponsBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseResponsBean baseResponsBean) {
                            if (null!=mRootView){
                                mRootView.hideLoading();
                            }
                            if(baseResponsBean!=null){
                                if(baseResponsBean.getSuccess()){
                                    ToastUtils.showShort("操作成功！");
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
    }
}
