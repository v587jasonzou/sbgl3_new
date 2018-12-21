package sbgl3.yunda.module.gzcl.mvp.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.utilcode.util.StringUtils;
import com.jess.arms.utils.utilcode.util.ToastUtils;
import com.jess.arms.widget.CustomPopupWindow;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.speedata.utils.ProgressDialogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import sbgl3.yunda.R;
import sbgl3.yunda.SysInfo;
import sbgl3.yunda.entry.EpcDataBase;
import sbgl3.yunda.globle.adapter.MenuAdapter;
import sbgl3.yunda.module.gzcl.di.component.DaggerGzclListComponent;
import sbgl3.yunda.module.gzcl.di.module.GzclListModule;
import sbgl3.yunda.module.gzcl.entry.FaultOrderBean;
import sbgl3.yunda.module.gzcl.mvp.contract.GzclListContract;
import sbgl3.yunda.module.gzcl.mvp.presenter.GzclListPresenter;
import sbgl3.yunda.module.gzcl.mvp.ui.adapter.FaultOrderAdapter;
import sbgl3.yunda.module.tpgzpg.mvp.ui.activity.TpgzpgEditActivity;
import sbgl3.yunda.module.tpgzpg.mvp.ui.activity.TpgzpgListActivity;
import sbgl3.yunda.widget.MySearchEditText;
import sbgl3.yunda.widget.zxing.android.CaptureActivity;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * <li>标题: 设备管理系统
 * <li>说明: 故障处理列表界面
 * <li>创建人：刘欢
 * <li>创建日期：2018/10/11
 * <li>修改人:
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 *
 * @author 系统集成事业部设备管理系统项目组
 * @version 3.0
 */
public class GzclListActivity extends BaseActivity<GzclListPresenter> implements GzclListContract.View {

    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.etSearch)
    MySearchEditText etSearch;
    @BindView(R.id.ivSearch)
    ImageView ivSearch;
    @BindView(R.id.ivScan)
    ImageView ivScan;
    @BindView(R.id.gray_layout)
    View grayLayout;
    @BindView(R.id.rlEquipments)
    RecyclerView rlEquipments;
    @BindView(R.id.srRefresh)
    SmartRefreshLayout srRefresh;
    @BindView(R.id.llNoData)
    LinearLayout llNoData;
    CustomPopupWindow window;
    TextView tvPopUpClose;
    RecyclerView rlPopUpMenu;
    MenuAdapter menuAdapter;
    int start = 0;
    int limit = 8;
    boolean isScanEquip = false;
    boolean isLoadMore = false;
    String queryKey = "";
    List<FaultOrderBean> mFaultOrderList = new ArrayList<>();
    FaultOrderAdapter adapter;   // 列表适配器
    List<Map<String, Object>> whereListJson = new ArrayList<Map<String, Object>>();
    Map<String, Object> queryCondition = new HashMap<String, Object>();

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerGzclListComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .gzclListModule(new GzclListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_gzcl_list; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
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
        setPupUpMenu();
        ArmsUtils.configRecyclerView(rlEquipments, new LinearLayoutManager(this));
        adapter = new FaultOrderAdapter(mFaultOrderList);
        // 初始状态为计划修
        rlEquipments.setAdapter(adapter);
        adapter.setOnItemClickListener(new DefaultAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int viewType, Object data, int position) {
                Intent intent = new Intent(GzclListActivity.this, GzclEditActivity.class);
                intent.putExtra("mFaultOrderList", (Serializable) mFaultOrderList);
                intent.putExtra("position", position);
                ArmsUtils.startActivity(intent);
            }
        });
        loadData();
        setSrRefresh();
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

    /**
     * <li>说明：联网获取数据
     * <li>创建人：刘欢
     * <li>创建日期：2018年10月11日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    public void loadData() {
        showLoading();
        queryCondition.put("compare", 30);
        if (queryCondition.get("sql") == null ||
                (queryCondition.get("sql") != null && TextUtils.isEmpty(queryCondition.get("sql").toString()))) {
            queryCondition.put("sql", "1 = 1");
        }
        if (!whereListJson.contains(queryCondition)) {
            whereListJson.add(queryCondition);
        }

        // 添加当前登录人过滤条件
        Map<String, Object> repairEmp = new HashMap<String, Object>();
        repairEmp.put("compare", 30);
        repairEmp.put("sql", "concat(',', repair_emp_id, ',') like '%," + SysInfo.empid + ",%'");

        if (!whereListJson.contains(repairEmp)) {
            whereListJson.add(repairEmp);
        }

        // 添加提票状态过滤条件
        Map<String, Object> state = new HashMap<String, Object>();
        state.put("propName", "state");
        state.put("propValue", "处理中");
        state.put("stringLike", false);
        if (!whereListJson.contains(state)) {
            whereListJson.add(state);
        }
        if (null != mPresenter) {
            mPresenter.getFaultEquipments(start, limit, JSON.toJSONString(whereListJson), isLoadMore);
        }
    }

    /**
     * <li>说明：下拉刷新、上拉加载更多
     * <li>创建人：刘欢
     * <li>创建日期：2018年10月11日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    private void setSrRefresh() {
        srRefresh.setRefreshHeader(new ClassicsHeader(this));
        srRefresh.setRefreshFooter(new ClassicsFooter(this));
        srRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                start = 0;
                isLoadMore = false;
                if (!StringUtils.isTrimEmpty(etSearch.getSerachData())) {
                    queryKey = StringUtils.null2Length0(etSearch.getSerachData().trim());
                }
                if (!TextUtils.isEmpty(queryKey)) {
                    queryCondition.put("sql", "(EQUIPMENT_CODE like '%" + queryKey
                            + "%' or EQUIPMENT_NAME like '%" + queryKey
                            + "%' or FAULT_ORDER_NO like '%" + queryKey + "%')");
                } else {
                    queryCondition.put("sql", "1 = 1");
                }
                loadData();
            }
        });
        srRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                start = start + limit;
                isLoadMore = true;
                loadData();
            }
        });
        srRefresh.setEnableAutoLoadMore(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.base_bar_menu, menu);
        menu.getItem(0).setTitle("故障处理");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.iv_more) {
            if (window.isShowing()) {
                window.dismiss();
            } else {
                window.showAsDropDown(menuTp);
                grayLayout.setVisibility(View.VISIBLE);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * <li>说明：菜单窗口
     * <li>创建人：刘欢
     * <li>创建日期：2018年10月11日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    private void setPupUpMenu() {
        if (null == window) {
            View view = LayoutInflater.from(this).inflate(R.layout.view_popup_menu, null);
            window = CustomPopupWindow.builder().contentView(view).customListener(new CustomPopupWindow.CustomPopupWindowListener() {
                @Override
                public void initPopupView(View contentView) {
                    tvPopUpClose = (TextView) contentView.findViewById(R.id.tvPopUpClose);
                    rlPopUpMenu = (RecyclerView) contentView.findViewById(R.id.rlPopUpMenu);
                    ArmsUtils.configRecycleView(rlPopUpMenu, new GridLayoutManager(GzclListActivity.this, 3, OrientationHelper.VERTICAL, false));
                    menuAdapter = new MenuAdapter(SysInfo.menus, GzclListActivity.this);
                    menuAdapter.setNowData("故障处理");
                    rlPopUpMenu.setAdapter(menuAdapter);
                    tvPopUpClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            window.dismiss();
                        }
                    });
                }
            }).parentView(rlEquipments).build();
            window.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    grayLayout.setVisibility(View.GONE);
                }
            });
            window.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            window.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setElevation(2.0f);
            }
        }
    }


    @Override
    public void showLoading() {
        ProgressDialogUtils.showProgressDialog(this, "正在加载中，请稍等....");
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
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.backRefreshList");
        registerReceiver(mBroadcastReceiver, intentFilter);
    }

    // 接收刷新列表的广播
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("action.backRefreshList")) {
                start = 0;
                isLoadMore = false;
                loadData();
            }
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        window.dismiss();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }

    /**
     * <li>说明：RFID扫描
     * <li>创建人：刘欢
     * <li>创建日期：2018年10月11日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEqiupCode(ArrayList<EpcDataBase> list) {
        if (list != null && list.size() > 0) {
            if (list.size() == 1) {
                etSearch.setText(list.get(0).equipmentCode);
                queryKey = list.get(0).equipmentCode;
                etSearch.setText(queryKey);
                queryCondition.put("sql", "EQUIPMENT_CODE = '" + queryKey + "'");
                isScanEquip = true;
                isLoadMore = false;
                start = 0;
                loadData();
            } else {
                ToastUtils.showShort("扫描到多条标签，请重新扫描！");
                String codes = "";
                for (int i = 0; i < list.size(); i++) {
                    if (!TextUtils.isEmpty(list.get(i).equipmentCode) && !"null".equals(list.get(i).equipmentCode)) {
                        codes += list.get(i).equipmentCode + "','";
                    }
                }
                if (!TextUtils.isEmpty(codes)) {
                    codes = "'" + codes.substring(0, codes.length() - 3) + "'";
                }
                queryCondition.put("sql", "EQUIPMENT_CODE in (" + codes + ")");
            }
        } else {
            ToastUtils.showShort("未扫描到标签，请重新扫描！");
        }
    }

    @Override
    public void loadFaultData(List<FaultOrderBean> list) {
        srRefresh.finishRefresh();
        mFaultOrderList.clear();
        if (list.size()>0) {
            mFaultOrderList.addAll(list);
        } else {
            loadFail();
        }
        adapter.notifyDataSetChanged();
        if (list.size() < limit) {
            srRefresh.setNoMoreData(true);
        } else {
            srRefresh.setNoMoreData(false);
        }
    }



    @Override
    public void loadMoreFaultData(List<FaultOrderBean> list) {
        srRefresh.finishLoadMore();
        if (list.size()>0) {
            mFaultOrderList.addAll(list);
        }
        adapter.notifyDataSetChanged();
        Observable.timer(1500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        if (list.size() < limit) {
                            srRefresh.setNoMoreData(true);
                        } else {
                            srRefresh.setNoMoreData(false);
                        }
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
    public void loadFail() {
        srRefresh.setVisibility(View.GONE);
        llNoData.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadSuccess() {
        srRefresh.setVisibility(View.VISIBLE);
        llNoData.setVisibility(View.GONE);
    }

    /**
     * <li>说明：搜索查询
     * <li>创建人：刘欢
     * <li>创建日期：2018年10月11日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    @OnClick(R.id.ivSearch)
    void search() {
        queryKey = etSearch.getSerachData();
        if (!TextUtils.isEmpty(queryKey)) {
            queryCondition.put("sql", "(EQUIPMENT_CODE like '%" + queryKey
                    + "%' or EQUIPMENT_NAME like '%" + queryKey
                    + "%' or FAULT_ORDER_NO like '%" + queryKey + "%')");
        } else {
            queryCondition.put("sql", "1 = 1");
        }
        start = 0;
        isLoadMore = false;
        loadData();
    }

    /**
     * <li>说明：二维码扫描
     * <li>创建人：刘欢
     * <li>创建日期：2018年10月11日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    @OnClick(R.id.ivScan)
    void onScanStart() {
        Intent intent = new Intent(GzclListActivity.this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SCAN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (null != data) {
                queryKey = data.getStringExtra(DECODED_CONTENT_KEY);
                etSearch.setText(queryKey);
                if (!TextUtils.isEmpty(queryKey)) {
                    queryCondition.put("sql", "(EQUIPMENT_CODE like '%" + queryKey
                            + "%' or EQUIPMENT_NAME like '%" + queryKey
                            + "%' or FAULT_ORDER_NO like '%" + queryKey + "%')");
                } else {
                    queryCondition.put("sql", "1 = 1");
                }
                isScanEquip = true;
                start = 0;
                isLoadMore = false;
                loadData();
            }
        }
    }
}
