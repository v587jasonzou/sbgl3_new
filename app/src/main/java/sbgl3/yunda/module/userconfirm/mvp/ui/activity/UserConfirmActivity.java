package sbgl3.yunda.module.userconfirm.mvp.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.jess.arms.utils.utilcode.util.CacheDiskUtils;
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
import sbgl3.yunda.module.userconfirm.di.component.DaggerUserConfirmComponent;
import sbgl3.yunda.module.userconfirm.di.module.UserConfirmModule;
import sbgl3.yunda.module.userconfirm.entry.FaultOrderBean;
import sbgl3.yunda.module.userconfirm.entry.PlanRepairEquipListBean;
import sbgl3.yunda.module.userconfirm.mvp.contract.UserConfirmContract;
import sbgl3.yunda.module.userconfirm.mvp.presenter.UserConfirmPresenter;
import sbgl3.yunda.module.userconfirm.mvp.ui.adapter.FaultOrderAdapter;
import sbgl3.yunda.module.userconfirm.mvp.ui.adapter.PlanRepairAdapter;
import sbgl3.yunda.widget.MySearchEditText;
import sbgl3.yunda.widget.zxing.android.CaptureActivity;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * <li>标题: 设备管理系统
 * <li>说明: 使用人确认列表界面
 * <li>创建人：刘欢
 * <li>创建日期：2018年9月17日
 * <li>修改人:
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 *
 * @author 系统集成事业部设备管理系统项目组
 * @version 3.0
 */
public class UserConfirmActivity extends BaseActivity<UserConfirmPresenter> implements UserConfirmContract.View {

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
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
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
    int clickPosition=0;
    boolean isScanEquip = false;
    boolean isLoadMore = false;
    String queryKey = "";
    int itemPosition;
    List<PlanRepairEquipListBean> mPlanRepairList = new ArrayList<>();
    List<PlanRepairEquipListBean> allPlanRepairList = new ArrayList<>();
    List<FaultOrderBean> mFaultOrderList = new ArrayList<>();
    List<FaultOrderBean> allFaultOrderList = new ArrayList<>();
    PlanRepairAdapter adapter1;   // 计划修列表适配器
    FaultOrderAdapter adapter2;   // 故障处理列表适配器

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerUserConfirmComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .userConfirmModule(new UserConfirmModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_user_confirm; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
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
        ArmsUtils.configRecyclerView(rlEquipments,new LinearLayoutManager(this));
        adapter1= new PlanRepairAdapter(mPlanRepairList);
        adapter2=new FaultOrderAdapter(mFaultOrderList);
        // 初始状态为计划修
        rlEquipments.setAdapter(adapter1);
        adapter1.setOnItemClickListener(new DefaultAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int viewType, Object data, int position) {
                itemPosition  = position;
                jumpToWorkOrderActivity();
            }
        });
        loadData();
        setSrRefresh();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        // 计划修
                        clickPosition = 0;
                        rlEquipments.setAdapter(adapter1);
                        adapter1.setOnItemClickListener(new DefaultAdapter.OnRecyclerViewItemClickListener() {
                            @Override
                            public void onItemClick(View view, int viewType, Object data, int position) {
                                itemPosition  = position;
                                jumpToWorkOrderActivity();
                            }
                        });
                        isLoadMore = false;
                        start = 0;
                        loadData();
                        break;
                    case 1:
                        // 故障处理
                        clickPosition = 1;
                        rlEquipments.setAdapter(adapter2);
                        adapter2.setOnItemClickListener(new DefaultAdapter.OnRecyclerViewItemClickListener() {
                            @Override
                            public void onItemClick(View view, int viewType, Object data, int position) {
                                // 跳转至故障处理明细界面
                                Intent intent = new Intent(UserConfirmActivity.this, FaultOrderConfirmEditActivity.class);
//                                intent.putExtra("equipment", mFaultOrderList.get(position));
                                intent.putExtra("allFaultOrderList", (Serializable) allFaultOrderList);
                                intent.putExtra("position",position);
                                ArmsUtils.startActivity(intent);
                            }
                        });
                        isLoadMore = false;
                        start = 0;
                        loadData();
                        break;
                    default:
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
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

    /**
     * <li>说明：联网获取数据
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月17日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    public void loadData(){
        showLoading();
        Map<String,Object> map = new HashMap<>();
        if (!StringUtils.isTrimEmpty(etSearch.getSerachData())) {
            map.put("equipmentCode", queryKey);
        }
        map.put("equipmentCode",queryKey);
        if (clickPosition==0){
            // 获取计划修列表
            if (null != mPresenter){
                mPresenter.getPlanRepairEquipments(start,limit, JSON.toJSONString(map),isLoadMore);
                mPresenter.getAllPlanRepairEquipments(JSON.toJSONString(map));
            }
        } else {
            // 获取故障处理列表
            if (null != mPresenter){
                mPresenter.getFaultEquipments(start,limit, JSON.toJSONString(map),isLoadMore);
                mPresenter.getAllFaultEquipments(JSON.toJSONString(map));
            }
        }
    }

    /**
     * <li>说明：界面跳转至计划修工单详情界面
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月17日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    private void jumpToWorkOrderActivity(){
        Intent intent = new Intent(UserConfirmActivity.this, PlanRepairOrderConfirmActivity.class);
        intent.putExtra("allPlanRepairList", (Serializable) allPlanRepairList);
        intent.putExtra("position",itemPosition);
        ArmsUtils.startActivity(intent);
    }

    /**
     * <li>说明：下拉刷新、上拉加载更多
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月17日
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
        menu.getItem(0).setTitle("使用人确认");
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
     * <li>创建日期：2018年9月17日
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
                    ArmsUtils.configRecycleView(rlPopUpMenu, new GridLayoutManager(UserConfirmActivity.this, 3, OrientationHelper.VERTICAL, false));
                    menuAdapter = new MenuAdapter(SysInfo.menus, UserConfirmActivity.this);
                    menuAdapter.setNowData("使用人确认");
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
//                window.setAnimationStyle(R.style.popwin_anim_style);
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
        ButterKnife.bind(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.refreshFaultEuipmentList");
        registerReceiver(mBroadcastReceiver,intentFilter);

    }
    // 接收刷新列表的广播
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("action.refreshFaultEuipmentList")){
                start = 0;
                isLoadMore = false;
                loadData();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }

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
        start = 0;
        isLoadMore = false;
        loadData();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    /**
     * <li>说明：RFID扫描
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月17日
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
                isScanEquip = true;
                isLoadMore = false;
                start = 0;
                loadData();
            } else {
                ToastUtils.showShort("扫描到多条标签，请重新扫描！");
            }
        } else {
            ToastUtils.showShort("未扫描到标签，请重新扫描！");
        }
    }

    /**
     * <li>说明：加载计划修列表数据
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月17日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    @Override
    public void loadData(List<PlanRepairEquipListBean> list) {
        srRefresh.finishRefresh();
        mPlanRepairList.clear();
        if (list.size()>0){
            mPlanRepairList.addAll(list);
        } else {
            loadFail();
        }
        adapter1.notifyDataSetChanged();
        if (list.size() < limit) {
            srRefresh.setNoMoreData(true);
        } else {
            srRefresh.setNoMoreData(false);
        }

    }

    /**
     * <li>说明：加载更多计划修列表数据
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月17日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    @Override
    public void loadMoreData(List<PlanRepairEquipListBean> list) {
        srRefresh.finishLoadMore();
        if (list.size()>0) {
            mPlanRepairList.addAll(list);
        }
        adapter1.notifyDataSetChanged();
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

    /**
     * <li>说明：加载全部计划修待确认数据
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月20日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    @Override
    public void loadAllPlanData(List<PlanRepairEquipListBean> list) {
        allPlanRepairList.clear();
        if (list.size()>0){
            allPlanRepairList.addAll(list);
        }
    }

    /**
     * <li>说明：加载故障处理列表数据
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月17日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    @Override
    public void loadFaultData(List<FaultOrderBean> list) {
        srRefresh.finishRefresh();
        mFaultOrderList.clear();
        if (list.size()>0){
            mFaultOrderList.addAll(list);
        } else {
            loadFail();
        }
        adapter2.notifyDataSetChanged();
        if (list.size() < limit) {
            srRefresh.setNoMoreData(true);
        } else {
            srRefresh.setNoMoreData(false);
        }
    }

    /**
     * <li>说明：加载全部故障处理数据
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月20日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    @Override
    public void loadAllFaultData(List<FaultOrderBean> list) {
        allFaultOrderList.clear();
        if (list.size()>0){
            allFaultOrderList.addAll(list);
        }
    }

    /**
     * <li>说明：加载更多故障处理列表数据
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月17日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    @Override
    public void loadMoreFaultData(List<FaultOrderBean> list) {
        srRefresh.finishLoadMore();
        if (list.size()>0) {
            mFaultOrderList.addAll(list);
        }
        adapter2.notifyDataSetChanged();
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
     * <li>创建日期：2018年9月17日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    @OnClick(R.id.ivSearch)
    void search() {
        queryKey = etSearch.getSerachData();
        start = 0;
        isLoadMore = false;
        loadData();
    }

    /**
     * <li>说明：二维码扫描
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月17日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    @OnClick(R.id.ivScan)
    void onScanStart() {
        Intent intent = new Intent(UserConfirmActivity.this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SCAN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (null != data) {
                queryKey = data.getStringExtra(DECODED_CONTENT_KEY);
                etSearch.setText(queryKey);
                isScanEquip = true;
                start = 0;
                isLoadMore = false;
                loadData();
            }
        }
    }
}
