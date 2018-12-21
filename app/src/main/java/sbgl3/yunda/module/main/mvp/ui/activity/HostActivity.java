package sbgl3.yunda.module.main.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.utilcode.util.BarUtils;
import com.jess.arms.utils.utilcode.util.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import sbgl3.yunda.R;
import sbgl3.yunda.entry.MessageBean;
import sbgl3.yunda.module.login.mvp.ui.activity.LoginActivityActivity;
import sbgl3.yunda.service.ScanService;

public class HostActivity extends AppCompatActivity {
    ImageView ivLogo;
    TextView tvTitle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);
        BarUtils.setStatusBarAlpha(this);
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        Observable.just(1).subscribeOn(Schedulers.io()).delay(1,TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                Intent serviceIntent = new Intent(getApplicationContext(), ScanService.class);
                startService(serviceIntent);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Finish(MessageBean bean){
        if(bean!=null){
            if(bean.getMsgType().equals("startFinish")){
                ArmsUtils.startActivity(new Intent(this, LoginActivityActivity.class));
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }
}
