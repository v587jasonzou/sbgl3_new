package sbgl3.yunda.module.setting.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.BaseApplication;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.module.ClientModule;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.utilcode.util.FileUtils;
import com.jess.arms.utils.utilcode.util.NetworkUtils;
import com.jess.arms.utils.utilcode.util.SPUtils;
import com.jess.arms.utils.utilcode.util.StringUtils;
import com.jess.arms.utils.utilcode.util.ToastUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import sbgl3.yunda.R;
import sbgl3.yunda.config.GlobalConfiguration;
import sbgl3.yunda.constant.ApiUrls;
import sbgl3.yunda.module.setting.di.component.DaggerChangeServerComponent;
import sbgl3.yunda.module.setting.di.module.ChangeServerModule;
import sbgl3.yunda.module.setting.mvp.contract.ChangeServerContract;
import sbgl3.yunda.module.setting.mvp.presenter.ChangeServerPresenter;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class ChangeServerActivity extends BaseActivity<ChangeServerPresenter> implements ChangeServerContract.View {

    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.etServerAddress)
    EditText etServerAddress;
    @BindView(R.id.cvChangeServer)
    CardView cvChangeServer;
    @Inject
    Retrofit retrofit;
    @Inject
    OkHttpClient okHttpClient;
    @Inject
    HttpUrl apiUrl;
    @Inject
    IRepositoryManager repositoryManager;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerChangeServerComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .changeServerModule(new ChangeServerModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_change_server; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
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
        String url = SPUtils.getInstance().getString("BaseUrl");
        if (!StringUtils.isTrimEmpty(url)) {
            etServerAddress.setText(url);
        } else {
            etServerAddress.setText(ApiUrls.BaseUrl);
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 绑定toobar跟menu
        getMenuInflater().inflate(R.menu.change_server_bar_menu, menu);
//        menu.getItem(0).setTitle("设备巡检");
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.iv_right) {
            if (etServerAddress.getText() == null || etServerAddress.getText().toString().equals("")) {
                ToastUtils.showShort("还未输入地址");
                return false;
            }
            String url = etServerAddress.getText().toString();
            String[] urls = url.split(":");
            if (urls == null || !urls[0].contains("http")) {
                url = "http://" + url;
            }
            SPUtils.getInstance().put("BaseUrl", url);
            if (url.charAt(url.length() - 1) != '/') {
                url = url + "/";
            }
            apiUrl = HttpUrl.parse(url);
            repositoryManager.setRetrofit(new Retrofit.Builder().baseUrl(apiUrl).client(okHttpClient)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create()).build());
            ToastUtils.showShort("修改成功");
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
