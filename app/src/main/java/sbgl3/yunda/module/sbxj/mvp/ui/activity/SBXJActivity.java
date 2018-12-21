package sbgl3.yunda.module.sbxj.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.RotateAnimation;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.utilcode.util.ScreenUtils;
import com.jess.arms.widget.CustomPopupWindow;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.speedata.utils.ProgressDialogUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import sbgl3.yunda.R;
import sbgl3.yunda.SysInfo;
import sbgl3.yunda.globle.adapter.MenuAdapter;
import sbgl3.yunda.module.sbxj.di.component.DaggerSBXJComponent;
import sbgl3.yunda.module.sbxj.di.module.SBXJModule;
import sbgl3.yunda.module.sbxj.entry.InspectPlanBean;
import sbgl3.yunda.module.sbxj.mvp.contract.SBXJContract;
import sbgl3.yunda.module.sbxj.mvp.presenter.SBXJPresenter;
import sbgl3.yunda.module.sbxj.mvp.ui.adapter.InspectPlanAdapter;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class SBXJActivity extends BaseActivity<SBXJPresenter> implements SBXJContract.View {

    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.rlPlans)
    RecyclerView rlPlans;
    @BindView(R.id.srRefresh)
    SmartRefreshLayout srRefresh;
    @BindView(R.id.gray_layout)
    View gray_layout;
    List<InspectPlanBean> inspectPlanBeans = new ArrayList<>();
    InspectPlanAdapter adapter;
    RecyclerView rlPopUpMenu;
    TextView tvPopUpClose;
    MenuAdapter menuAdapter;
    CustomPopupWindow window;
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSBXJComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .sBXJModule(new SBXJModule(this))
                .build()
                .inject(this);
    }


    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_sbxj; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTranslucentStatus();
        setSupportActionBar(menuTp);
        menuTp.setTitle("设备巡检");
        menuTp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ArmsUtils.configRecycleView(rlPlans, new LinearLayoutManager(this));
        adapter = new InspectPlanAdapter(inspectPlanBeans);
        rlPlans.setAdapter(adapter);
        adapter.setOnItemClickListener(new DefaultAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int viewType, Object data, int position) {
                Intent intent = new Intent(SBXJActivity.this,SBXJDevActivity.class);
                intent.putExtra("planIdx",inspectPlanBeans.get(position).getIdx());
                ArmsUtils.startActivity(intent);
            }
        });
        srRefresh.setRefreshHeader(new ClassicsHeader(this));
        srRefresh.setRefreshFooter(new ClassicsFooter(this));
        srRefresh.setNoMoreData(true);
        srRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                Loaddata();
            }
        });
        Loaddata();
        setPopUpMenu();
    }



    private void setPopUpMenu() {
        if (window == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.view_popup_menu, null);
            window = CustomPopupWindow.builder().contentView(view).customListener(new CustomPopupWindow.CustomPopupWindowListener() {
                @Override
                public void initPopupView(View contentView) {
                    tvPopUpClose = (TextView) contentView.findViewById(R.id.tvPopUpClose);
                    rlPopUpMenu = (RecyclerView) contentView.findViewById(R.id.rlPopUpMenu);
                    ArmsUtils.configRecycleView(rlPopUpMenu, new GridLayoutManager(SBXJActivity.this, 3, OrientationHelper.VERTICAL, false));
                    menuAdapter = new MenuAdapter(SysInfo.menus, SBXJActivity.this);
                    menuAdapter.setNowData("设备巡检");
                    rlPopUpMenu.setAdapter(menuAdapter);
                    tvPopUpClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            window.dismiss();
                        }
                    });
                }
            }).parentView(rlPlans).build();
            window.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    gray_layout.setVisibility(View.GONE);
                }
            });
            window.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            window.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setElevation(2.0f);
//                window.setAnimationStyle(R.style.popwin_anim_style);
            }
        }

    }


    private void Loaddata() {
        mPresenter.getInspecPlan(0, 100, "", false);
    }

    @Override
    public void showLoading() {
        ProgressDialogUtils.showProgressDialog(this, "正在获取巡检计划信息，请稍后...");
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
    public void LoadData(List<InspectPlanBean> list) {
        inspectPlanBeans.clear();
        if (list != null && list.size() > 0) {
            inspectPlanBeans.addAll(list);
        }
        adapter.notifyDataSetChanged();
        srRefresh.finishRefresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 绑定toobar跟menu
        getMenuInflater().inflate(R.menu.base_bar_menu, menu);
        menu.getItem(0).setTitle("设备巡检");
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.iv_more) {
            if (window.isShowing()) {
                window.dismiss();
            } else {
                window.showAsDropDown(menuTp);
                gray_layout.setVisibility(View.VISIBLE);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void LoadMoreData(List<InspectPlanBean> list) {

    }

    @Override
    protected void onStop() {
        super.onStop();
        window.dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
