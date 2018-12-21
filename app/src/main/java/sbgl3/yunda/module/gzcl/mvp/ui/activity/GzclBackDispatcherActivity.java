package sbgl3.yunda.module.gzcl.mvp.ui.activity;

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

import com.alibaba.fastjson.JSON;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.utilcode.util.ToastUtils;
import com.speedata.utils.ProgressDialogUtils;

import butterknife.BindView;
import sbgl3.yunda.SysInfo;
import sbgl3.yunda.module.gzcl.di.component.DaggerGzclBackDispatcherComponent;
import sbgl3.yunda.module.gzcl.di.module.GzclBackDispatcherModule;
import sbgl3.yunda.module.gzcl.mvp.contract.GzclBackDispatcherContract;
import sbgl3.yunda.module.gzcl.mvp.presenter.GzclBackDispatcherPresenter;

import sbgl3.yunda.R;


import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * <li>说明: 故障处理---退回调度界面
 * <li>创建人：刘欢
 * <li>创建日期：2018年10月12日
 * <li>修改人：
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 *
 * @author 信息系统事业部设备管理系统项目组
 * @version 3.0
 */
public class GzclBackDispatcherActivity extends BaseActivity<GzclBackDispatcherPresenter> implements GzclBackDispatcherContract.View {

    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.etBackReason)
    EditText etBackReason;
    @BindView(R.id.cvBackReason)
    CardView cvBackReason;
    String idx;
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerGzclBackDispatcherComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .gzclBackDispatcherModule(new GzclBackDispatcherModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_back_dispatcher; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
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
        if (null != getIntent()) {
            idx = getIntent().getStringExtra("idx");
        }
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
            if (etBackReason.getText() == null || etBackReason.getText().toString().equals("")) {
                ToastUtils.showShort("请先输入退回原因！");
                return false;
            }
            // 提交退回原因
            if (null != mPresenter) {
                showLoading();
                mPresenter.back(idx, etBackReason.getText().toString(),"工人");
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showLoading() {
        ProgressDialogUtils.showProgressDialog(this, "正在提交，请稍后.....");
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
    public void backSuccess() {
        ToastUtils.showShort("退回成功！");
        refreshEquipmentList();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void backFail() {
        ToastUtils.showShort("退回失败，请重试！");
    }

    /**
     * <li>说明：刷新列表数据
     * <li>创建人：刘欢
     * <li>创建日期：2018年10月10日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    private void refreshEquipmentList(){
        Intent intent = new Intent();
        intent.setAction("action.backRefreshList");
        sendBroadcast(intent);
    }
}
