package sbgl3.yunda.module.setting.mvp.ui.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.utilcode.util.SPUtils;
import com.jess.arms.utils.utilcode.util.ToastUtils;
import com.liulishuo.magicprogresswidget.MagicProgressCircle;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import me.jessyan.progressmanager.ProgressListener;
import me.jessyan.progressmanager.ProgressManager;
import me.jessyan.progressmanager.body.ProgressInfo;
import sbgl3.yunda.MyApp;
import sbgl3.yunda.R;
import sbgl3.yunda.module.setting.di.component.DaggerSettingComponent;
import sbgl3.yunda.module.setting.di.module.SettingModule;
import sbgl3.yunda.module.setting.mvp.contract.SettingContract;
import sbgl3.yunda.module.setting.mvp.presenter.SettingPresenter;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class SettingActivity extends BaseActivity<SettingPresenter> implements SettingContract.View {

    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.cvChangeServer)
    CardView cvChangeServer;
    @BindView(R.id.cvChangeModel)
    CardView cvChangeModel;
    @BindView(R.id.cvDownloadData)
    CardView cvDownloadData;
    @BindView(R.id.tvVersion)
    TextView tvVersion;
    MagicProgressCircle mcProgress;
    TextView tvProgressInfo;
    TextView tvPercent;
    AlertDialog progressDialog;
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSettingComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .settingModule(new SettingModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_setting; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTranslucentStatus();
        setSupportActionBar(menuTp);
        menuTp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_progress,null);
        mcProgress = (MagicProgressCircle)view.findViewById(R.id.mcProgress);
        tvProgressInfo = (TextView)view.findViewById(R.id.tvProgressInfo);
        tvPercent = (TextView)view.findViewById(R.id.tvPercent);
        progressDialog = new AlertDialog.Builder(this).setView(view).create();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }


    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
    @OnClick(R.id.cvChangeServer)
    void toChangeServer(){
        ArmsUtils.startActivity(new Intent(this,ChangeServerActivity.class));
    }
    @OnClick(R.id.cvDownloadData)
    void DownLoadData(){
        if(mPresenter!=null){
            String url = SPUtils.getInstance().getString("BaseUrl");
            ProgressManager.getInstance().addResponseListener(url+"pda/download.do", new ProgressListener() {
                @Override
                public void onProgress(ProgressInfo progressInfo) {
                    Log.e("progress","id:"+progressInfo.getId()+" percent:"+progressInfo.getPercent()+"  lenth:"+progressInfo.getContentLength()
                            +"  curentByte:"+progressInfo.getCurrentbytes());
                    float percent = ((float)progressInfo.getPercent())/100;
                    mcProgress.setSmoothPercent(percent);
                    tvPercent.setText(progressInfo.getPercent()+"%");
                }

                @Override
                public void onError(long id, Exception e) {

                }
            });
//            mPresenter.UploadDatas("com.yunda.sbgl.repair.inspect.entity.InspectPlanEquipment", "100000", "0","InspectPlanEquipment");
            progressDialog.show();
            mcProgress.setPercent(0);
            tvPercent.setText("0%");
            tvProgressInfo.setText("下载中请稍等...");
            tvProgressInfo.setTextColor(ContextCompat.getColor(this,R.color.red));
            mPresenter.UploadDatas("com.yunda.sbgl.repair.inspect.entity.InspectRecord", "100000", "0","InspectPlanEquipment");
        }
    }

    @Override
    public void OnDownLoadSuccess(String type) {
        tvProgressInfo.setText("完成");
        tvProgressInfo.setTextColor(ContextCompat.getColor(this,R.color.text_color_green));
        Observable.just(1).observeOn(AndroidSchedulers.mainThread()).delay(1, TimeUnit.SECONDS)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void OnDownLoadFaild(String type) {

    }
}
