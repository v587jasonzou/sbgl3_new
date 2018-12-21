package sbgl3.yunda.widget;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.jess.arms.base.delegate.AppDelegate;
import com.jess.arms.base.entry.BaseResponsBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.utilcode.util.FileUtils;
import com.jess.arms.utils.utilcode.util.ToastUtils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sbgl3.yunda.R;
import sbgl3.yunda.entry.ImagesBean;
import sbgl3.yunda.globle.GlobleApi;
import sbgl3.yunda.module.sbxj.di.component.DaggerTakephotoComponent;
import sbgl3.yunda.module.sbxj.di.module.TakephotoModule;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * 拍照及照片查看相关类-查看照片页面
 * @author 周雪巍
 * @time 2018/09/12
 * */
@ActivityScope
public class PhotoReadActivity extends AppCompatActivity {
    @Inject
    IRepositoryManager repositoryManager;
    ViewPager viewPager;
    Toolbar menu_tp;
    ImageView iv_delete;
    ArrayList<ImagesBean> images = new ArrayList<>();
    int ppsition;
    MyPhotoAdapter adapter;
    String state = "0";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerPhotoReadActivityComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(ArmsUtils.obtainAppComponentFromContext(this))
                .photoReadActivityModule(new PhotoReadActivityModule())
                .build()
                .inject(this);
        setContentView(R.layout.activity_photo_preview);
        final Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ppsition = bundle.getInt("position",0);
        state = bundle.getString("state");
        images = (ArrayList<ImagesBean>) bundle.getSerializable("images");
//        if("1".equals(state)){
//            for(int i =0 ;i<images.size();i++){
//                images.get(i).path = Utils.getImageBase64(i+"");
//            }
//        }
        if(images!=null&&images.size()>0){
            if("0".equals(images.get(images.size()-1).getImagesName())){
                images.remove(images.size()-1);
            }
        }
        adapter = new MyPhotoAdapter(this,ppsition,images,state);
        viewPager = (ViewPager) findViewById(R.id.iv_veiewPger);
        menu_tp = (Toolbar) findViewById(R.id.menu_tp);
        menu_tp.setTitle("（"+(ppsition+1)+"/"+images.size()+"）");
        iv_delete = (ImageView)findViewById(R.id.iv_delete);
        if(!"0".equals(state)){
            iv_delete.setVisibility(View.GONE);
        }
        if("order".equals(state)){
            iv_delete.setVisibility(View.VISIBLE);
        }
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(ppsition);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ppsition = position;
                menu_tp.setTitle("（"+(ppsition+1)+"/"+images.size()+"）");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 new AlertDialog.Builder(PhotoReadActivity.this).setTitle("提示")
                         .setMessage("确定删除当前照片？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         if(state.equals("order")){
                             Observable.create(new ObservableOnSubscribe<String>() {
                                 @Override
                                 public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                                     repositoryManager.obtainRetrofitService(GlobleApi.class).DeleteImages(images.get(ppsition).getImagesPath())
                                             .enqueue(new Callback<BaseResponsBean>() {
                                                 @Override
                                                 public void onResponse(Call<BaseResponsBean> call, Response<BaseResponsBean> response) {
                                                     if(response!=null&&response.body()!=null){
                                                         if(response.body().getSuccess()){
                                                             if(images.get(ppsition).getImagesLocalPath()!=null){
                                                                 if(FileUtils.deleteFile(images.get(ppsition).getImagesLocalPath())){
                                                                     emitter.onNext("success");
                                                                 }
                                                             }
                                                         }else {
                                                             emitter.onNext("删除照片失败");
                                                         }
                                                     }else {
                                                         emitter.onNext("删除照片失败");
                                                     }
                                                 }

                                                 @Override
                                                 public void onFailure(Call<BaseResponsBean> call, Throwable t) {
                                                     emitter.onNext("删除照片失败");
                                                 }
                                             });
                                 }
                             }).subscribeOn(Schedulers.io())
                                     .observeOn(AndroidSchedulers.mainThread())
                                     .subscribe(new Observer<String>() {
                                         @Override
                                         public void onSubscribe(Disposable d) {

                                         }

                                         @Override
                                         public void onNext(String s) {
                                             if(s.equals("success")){
                                                 ToastUtils.showShort("删除照片成功");
                                                 if(images.size()>1){
                                                     images.remove(ppsition);
                                                     adapter.notifyDataSetChanged();
                                                     menu_tp.setTitle("（"+(ppsition+1)+"/"+images.size()+"）");
                                                 }else {
                                                     images.remove(ppsition);
                                                     Intent intent1 = new Intent();
                                                     intent1.putExtra("images",images);
                                                     setResult(ImagePicker.RESULT_CODE_ITEMS,intent1);
                                                     finish();
                                                 }
                                             }else {
                                                 ToastUtils.showShort(s);
                                             }
                                         }

                                         @Override
                                         public void onError(Throwable e) {

                                         }

                                         @Override
                                         public void onComplete() {

                                         }
                                     });

                         }else {
                             if(images.size()>1){
                                 images.remove(ppsition);
                                 adapter.notifyDataSetChanged();
                                 menu_tp.setTitle("（"+(ppsition+1)+"/"+images.size()+"）");
                             }else {
                                 images.remove(ppsition);
                                 Intent intent1 = new Intent();
                                 intent1.putExtra("images",images);
                                 setResult(ImagePicker.RESULT_CODE_ITEMS,intent1);
                                 finish();
                             }
                         }

                     }
                 }).setNegativeButton("取消",null).show();
            }
        });
        setSupportActionBar(menu_tp);
        menu_tp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.putExtra("images",images);
                setResult(ImagePicker.RESULT_CODE_ITEMS,intent1);
                finish();
            }
        });
    }
}
