package sbgl3.yunda;

import android.content.Intent;


import com.jess.arms.base.BaseApplication;
import com.lzy.imagepicker.ImagePicker;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.progressmanager.ProgressManager;
import sbgl3.yunda.service.ScanService;
import sbgl3.yunda.tools.crash.AndroidCrash;
import sbgl3.yunda.widget.GlideImageLoader;

public class MyApp extends BaseApplication {
    public Intent serviceIntent;
    @Override
    public void onCreate() {
        super.onCreate();
//        if(serviceIntent==null){
//            serviceIntent = new Intent(getApplicationContext(), ScanService.class);
//        }
//        startService(serviceIntent);
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(false);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(false); //是否按矩形区域保存
        imagePicker.setSelectLimit(9);    //选中数量限制
        ProgressManager.getInstance().setRefreshTime(50);
        //全局崩溃监控
        AndroidCrash.getInstance().init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
