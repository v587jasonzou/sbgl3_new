package sbgl3.yunda.module.ysrqr.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.utilcode.util.SPUtils;
import com.jess.arms.utils.utilcode.util.ToastUtils;
import com.speedata.utils.ProgressDialogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.HttpUrl;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import sbgl3.yunda.R;
import sbgl3.yunda.SysInfo;
import sbgl3.yunda.module.ysrqr.di.component.DaggerYsrConfirmSubmitComponent;
import sbgl3.yunda.module.ysrqr.di.module.YsrConfirmSubmitModule;
import sbgl3.yunda.module.ysrqr.mvp.contract.YsrConfirmSubmitContract;
import sbgl3.yunda.module.ysrqr.mvp.presenter.YsrConfirmSubmitPresenter;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * <li>标题: 设备管理系统
 * <li>说明: 提交验收评语界面
 * <li>创建人：刘欢
 * <li>创建日期：2018年9月29日
 * <li>修改人:
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 *
 * @author 系统集成事业部设备管理系统项目组
 * @version 3.0
 */
public class YsrConfirmSubmitActivity extends BaseActivity<YsrConfirmSubmitPresenter> implements YsrConfirmSubmitContract.View {

    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.etReviews)
    AutoCompleteTextView etReviews;
    @BindView(R.id.tvAcceptor)
    EditText tvAcceptor;
    @BindView(R.id.cvEditContent)
    CardView cvEditContent;
    ArrayAdapter<String> adapter;
    List<String> preReviews = new ArrayList<String>();
    String idx = "";

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerYsrConfirmSubmitComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .ysrConfirmSubmitModule(new YsrConfirmSubmitModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_ysr_confirm_submit; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
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
        tvAcceptor.setText(SysInfo.empname);
        loadReviews();
        etReviews.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ((AutoCompleteTextView)v).showDropDown();
                }
            }
        });

        etReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AutoCompleteTextView)v).showDropDown();
            }
        });

        adapter = new ArrayAdapter<String>(YsrConfirmSubmitActivity.this, android.R.layout.simple_list_item_1, preReviews);
        etReviews.setAdapter(adapter);
        if (null != getIntent()){
            idx = getIntent().getStringExtra("idx");
        }
    }

    private void loadReviews() {
        preReviews.clear();
        preReviews.add("合格");
        preReviews.add("不合格");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 绑定toobar跟menu
        getMenuInflater().inflate(R.menu.change_server_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.iv_right) {
            if (etReviews.getText() == null || etReviews.getText().toString().equals("")) {
                ToastUtils.showShort("验收评语不能为空！");
                return false;
            }
            if (tvAcceptor.getText() == null || tvAcceptor.getText().toString().equals("")) {
                ToastUtils.showShort("验收签名不能为空！");
                return false;
            }
            // 提交验收评语
            if (null != mPresenter){
                showLoading();
                mPresenter.confirm(JSON.toJSONString(new String[]{idx}),etReviews.getText().toString(),tvAcceptor.getText().toString());
            }
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void showLoading() {
        ProgressDialogUtils.showProgressDialog(this,"正在提交，请稍后.....");
    }

    @Override
    public void hideLoading() {
        ProgressDialogUtils.dismissProgressDialog();
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
    public void submitSuccess() {
        ToastUtils.showShort("提交成功！");
        refreshEquipmentList();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void submitFail() {
        ToastUtils.showShort("提交失败，请重试！");
    }

    /**
     * <li>说明：刷新列表数据
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月29日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    private void refreshEquipmentList(){
        Intent intent = new Intent();
        intent.setAction("action.refreshYsrqrEuipmentList");
        sendBroadcast(intent);
    }
}
