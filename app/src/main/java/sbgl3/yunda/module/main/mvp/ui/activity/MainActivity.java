package sbgl3.yunda.module.main.mvp.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.speedata.utils.ProgressDialogUtils;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sbgl3.yunda.R;
import sbgl3.yunda.SysInfo;
import sbgl3.yunda.entry.EpcDataBase;
import sbgl3.yunda.module.appraise.mvp.ui.activity.EquipmentAppraiseActivity;
import sbgl3.yunda.module.equipinfo.mvp.ui.activity.EquipListActivity;
import sbgl3.yunda.module.evaluate.mvp.ui.activity.EquipmentEvaluateActivity;
import sbgl3.yunda.module.fwhjhx.mvp.ui.activity.RepairTaskEquipActivity;
import sbgl3.yunda.module.gzcl.mvp.ui.activity.GzclListActivity;
import sbgl3.yunda.module.gzqr.mvp.ui.activity.GzqrEquipmentListActivity;
import sbgl3.yunda.module.gztp.mvp.ui.activity.FaultTicketActivity;
import sbgl3.yunda.module.login.Entry.LoginReponsBody;
import sbgl3.yunda.module.main.adapter.MainMenuAdapter;
import sbgl3.yunda.module.main.adapter.SimpleItemTouchHelperCallback;
import sbgl3.yunda.module.main.di.component.DaggerMainComponent;
import sbgl3.yunda.module.main.di.module.MainModule;
import sbgl3.yunda.module.main.mvp.contract.MainContract;
import sbgl3.yunda.module.main.mvp.presenter.MainPresenter;
import sbgl3.yunda.module.sbdj.mvp.ui.activity.SbdjMainActivity;
import sbgl3.yunda.module.sbxj.mvp.ui.activity.SBXJActivity;
import sbgl3.yunda.module.spot_check.mvp.ui.activity.SpotCheckMainActivity;
import sbgl3.yunda.module.tpgzpg.mvp.ui.activity.TpgzpgListActivity;
import sbgl3.yunda.module.userconfirm.mvp.ui.activity.UserConfirmActivity;
import sbgl3.yunda.module.ysrqr.mvp.ui.activity.YsrqrEquipmentListActivity;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.tvUserRole)
    TextView tvUserRole;
    @BindView(R.id.tvUsername)
    TextView tvUsername;
    @BindView(R.id.tvEmpId)
    TextView tvEmpId;
    @BindView(R.id.rlMenus)
    RecyclerView rlMenus;
    @BindView(R.id.tvChangeUser)
    TextView tvChangeUser;
    @BindView(R.id.srRefresh)
    SmartRefreshLayout srRefresh;
    ArrayList<LoginReponsBody.TodoJobBean> todos = null;
    MainMenuAdapter adapter;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }
    @OnClick(R.id.tvChangeUser)
    void BackUser() {
        new AlertDialog.Builder(MainActivity.this).setTitle("提示")
                .setMessage("确定切换账户吗？")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).show();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEqiupCode (ArrayList<EpcDataBase> list) {
        if(list!=null&&list.size()>0){
            ToastUtils.showShort(list.get(0).equipmentCode);
        }
    }
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTranslucentStatus();
        tvUserRole.setText(SysInfo.user.getOrgname());
        tvEmpId.setText(SysInfo.empid + "");
        tvUsername.setText(SysInfo.empname);
        if(mPresenter!=null){
            mPresenter.getmenus();
        }
        ArmsUtils.configRecycleView(rlMenus, new LinearLayoutManager(this));
        adapter = new MainMenuAdapter(SysInfo.menus);
        rlMenus.setAdapter(adapter);
        //先实例化Callback
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        //用Callback构造ItemtouchHelper
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        //调用ItemTouchHelper的attachToRecyclerView方法建立联系
        touchHelper.attachToRecyclerView(rlMenus);
        srRefresh.setRefreshHeader(new ClassicsHeader(this));
//        srRefresh.setRefreshFooter(new ClassicsFooter(this));
        srRefresh.setNoMoreData(true);
        srRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                if(mPresenter!=null){
                    mPresenter.getmenus();
                }
            }
        });
        adapter.setOnItemClickListener(new DefaultAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int viewType, Object data, int position) {
                if(SysInfo.menus.get(position).getFuncname().equals("设备巡检")){
                    Intent intent = new Intent(MainActivity.this, SBXJActivity.class);
                    ArmsUtils.startActivity(intent);
                }
                if (SysInfo.menus.get(position).getFuncname().equals("设备鉴定")){
                    Intent intent = new Intent(MainActivity.this, EquipmentAppraiseActivity.class);
                    ArmsUtils.startActivity(intent);
                }
                if (SysInfo.menus.get(position).getFuncname().equals("设备评定")){
                    Intent intent = new Intent(MainActivity.this, EquipmentEvaluateActivity.class);
                    ArmsUtils.startActivity(intent);
                }
                if(SysInfo.menus.get(position).getFuncname().equals("设备登记")){
                    Intent intent = new Intent(MainActivity.this, SbdjMainActivity.class);
                    ArmsUtils.startActivity(intent);
                }
                if(SysInfo.menus.get(position).getFuncname().equals("设备信息查看")){
                    Intent intent = new Intent(MainActivity.this, EquipListActivity.class);
                    ArmsUtils.startActivity(intent);
                }
                if(SysInfo.menus.get(position).getFuncname().equals("计划检修")){
                    Intent intent = new Intent(MainActivity.this, RepairTaskEquipActivity.class);
                    ArmsUtils.startActivity(intent);
                }
                if (SysInfo.menus.get(position).getFuncname().equals("使用人确认")){
                    Intent intent = new Intent(MainActivity.this, UserConfirmActivity.class);
                    ArmsUtils.startActivity(intent);
                }
                if (SysInfo.menus.get(position).getFuncname().equals("维修工长确认")){
                    Intent intent = new Intent(MainActivity.this, GzqrEquipmentListActivity.class);
                    ArmsUtils.startActivity(intent);
                }
                if (SysInfo.menus.get(position).getFuncname().equals("验收人确认")){
                    Intent intent = new Intent(MainActivity.this, YsrqrEquipmentListActivity.class);
                    ArmsUtils.startActivity(intent);
                }
                if(SysInfo.menus.get(position).getFuncname().equals("设备点检")){
                    Intent intent = new Intent(MainActivity.this, SpotCheckMainActivity.class);
                    ArmsUtils.startActivity(intent);
                }
                if (SysInfo.menus.get(position).getFuncname().equals("提票工长派工")){
                    Intent intent = new Intent(MainActivity.this, TpgzpgListActivity.class);
                    ArmsUtils.startActivity(intent);
                }
                if (SysInfo.menus.get(position).getFuncname().equals("故障处理")){
                    Intent intent = new Intent(MainActivity.this, GzclListActivity.class);
                    ArmsUtils.startActivity(intent);
                }
                if (SysInfo.menus.get(position).getFuncname().equals("故障提票")){
                    Intent intent = new Intent(MainActivity.this, FaultTicketActivity.class);
                    ArmsUtils.startActivity(intent);
                }

            }
        });
        isScan = true;
        setOnScanCallBack(new OnScanCallBack() {
            @Override
            public void onKeyDawn() {
                EventBus.getDefault().post("onKeyDown");
            }

            @Override
            public void onKeyUp() {
                EventBus.getDefault().post("onKeyUp");
            }
        });
    }

    @Override
    public void showLoading() {
        ProgressDialogUtils.showProgressDialog(this, "加载中，请稍后...");
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
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void notifyAdapter() {
        srRefresh.finishRefresh();
        adapter.notifyDataSetChanged();
    }
    boolean mBackKeyPressed = false;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (!mBackKeyPressed) {
                Toast.makeText(this, "再按一次退出主页", Toast.LENGTH_SHORT).show();
                mBackKeyPressed = true;
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        mBackKeyPressed = false;
                    }
                }, 2000);
                return true;
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mPresenter!=null){
            mPresenter.getmenus();
        }
    }
}
