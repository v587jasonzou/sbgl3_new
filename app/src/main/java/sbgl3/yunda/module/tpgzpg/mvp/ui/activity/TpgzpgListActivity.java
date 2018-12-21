package sbgl3.yunda.module.tpgzpg.mvp.ui.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import com.jess.arms.widget.autolayout.MarqueeView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.speedata.utils.ProgressDialogUtils;
import com.zhy.autolayout.AutoLinearLayout;

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
import sbgl3.yunda.entry.MessageBean;
import sbgl3.yunda.entry.UserInfo;
import sbgl3.yunda.globle.adapter.AddUserAdapter;
import sbgl3.yunda.globle.adapter.MenuAdapter;
import sbgl3.yunda.module.tpgzpg.di.component.DaggerTpgzpgListComponent;
import sbgl3.yunda.module.tpgzpg.di.module.TpgzpgListModule;
import sbgl3.yunda.module.tpgzpg.entry.FaultOrderBean;
import sbgl3.yunda.module.tpgzpg.mvp.contract.TpgzpgListContract;
import sbgl3.yunda.module.tpgzpg.mvp.presenter.TpgzpgListActivityPresenter;
import sbgl3.yunda.module.tpgzpg.mvp.ui.adapter.FaultOrderAdapter;
import sbgl3.yunda.widget.ClearEditText;
import sbgl3.yunda.widget.MySearchEditText;
import sbgl3.yunda.widget.zxing.android.CaptureActivity;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * <li>说明: 提票工长派工---列表界面
 * <li>创建人：刘欢
 * <li>创建日期：2018年10月8日
 * <li>修改人：
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 * @author 信息系统事业部设备管理系统项目组
 * @version 3.0
 */
public class TpgzpgListActivity extends BaseActivity<TpgzpgListActivityPresenter> implements TpgzpgListContract.View {

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
    @BindView(R.id.tvConfirm)
    TextView tvConfirm;
    @BindView(R.id.tvSelecNumbs1)
    TextView tvSelecNumbs1;
    @BindView(R.id.cvConfirm)
    CardView cvConfirm;
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
    List<FaultOrderBean> selectedEquipments = new ArrayList<>();
    FaultOrderAdapter adapter;   // 列表适配器
    List<Map<String, Object>> whereListJson = new ArrayList<Map<String, Object>>();
    Map<String, Object> queryCondition = new HashMap<String, Object>();
    Dialog userDialog;
    TextView tvUserBack;
    EditText etUserSearch;
    ImageView ivUserSearch;
    AutoLinearLayout ivEditDelete;
    RecyclerView rlChooseUsers;
    SmartRefreshLayout srUserRefresh;
    MarqueeView tvChooseUser;
    TextView tvSelecNumbs,tvUserClear;
    TextView tvUserConfirm;
    CardView cvUserConfirm;
    List<UserInfo> mUsers = new ArrayList<>();
    List<UserInfo> selectedUsers = new ArrayList<>();
    AddUserAdapter userAdapter;
    String queryUserKey = "";

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerTpgzpgListComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .tpgzpgListModule(new TpgzpgListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_tpgzpg_list; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
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
        rlEquipments.setAdapter(adapter);
        adapter.setOnItemClickListener(new DefaultAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int viewType, Object data, int position) {
                Intent intent = new Intent(TpgzpgListActivity.this, TpgzpgEditActivity.class);
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
     * <li>创建日期：2018年10月8日
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
        Map<String, Object> repairTeam = new HashMap<String, Object>();
        repairTeam.put("propName", "repairTeamId");
        repairTeam.put("propValue", SysInfo.orgid);
        repairTeam.put("stringLike", false);

        if (!whereListJson.contains(repairTeam)) {
            whereListJson.add(repairTeam);
        }

        // 添加提票状态过滤条件
        Map<String, Object> state = new HashMap<String, Object>();
        state.put("propName", "state");
        state.put("propValue", "已派工");
        state.put("stringLike", false);

        if (!whereListJson.contains(state)) {
            whereListJson.add(state);
        }

        // 添加故障等级过滤条件
        Map<String, Object> faultLevel = new HashMap<String, Object>();
        faultLevel.put("compare", 9);
        faultLevel.put("propName", "faultLevel");
        faultLevel.put("propValues", new String[]{"一般", "重大", "特大"});
        if (!whereListJson.contains(faultLevel)) {
            whereListJson.add(faultLevel);
        }

        if (null != mPresenter) {
            mPresenter.getFaultEquipments(start, limit, JSON.toJSONString(whereListJson), isLoadMore);
        }
    }

    /**
     * <li>说明：下拉刷新、上拉加载更多
     * <li>创建人：刘欢
     * <li>创建日期：2018年10月8日
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
        menu.getItem(0).setTitle("提票工长派工");
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
     * <li>创建日期：2018年10月8日
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
                    ArmsUtils.configRecycleView(rlPopUpMenu, new GridLayoutManager(TpgzpgListActivity.this, 3, OrientationHelper.VERTICAL, false));
                    menuAdapter = new MenuAdapter(SysInfo.menus, TpgzpgListActivity.this);
                    menuAdapter.setNowData("提票工长派工");
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

    /**
     * <li>说明：人员选择全屏对话框
     * <li>创建人：刘欢
     * <li>创建日期：2018年10月8日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    private void setDialog() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentview = inflater.inflate(R.layout.dialog_add_repair_user, null);
        userDialog = new Dialog(this, R.style.FullScreenStyle);
        Display defaultDisplay = userDialog.getWindow().getWindowManager().getDefaultDisplay();
        int height = defaultDisplay.getHeight();
        int width = defaultDisplay.getWidth();
        contentview.setMinimumHeight(height);
        contentview.setMinimumWidth(width);
        tvUserBack = (TextView) contentview.findViewById(R.id.tvUserBack);
        etUserSearch = (EditText) contentview.findViewById(R.id.etUserSearch);
        ivUserSearch = (ImageView) contentview.findViewById(R.id.ivUserSearch);
        ivEditDelete = (AutoLinearLayout) contentview.findViewById(R.id.ivEditDelete);
        rlChooseUsers = (RecyclerView) contentview.findViewById(R.id.rlChooseUsers);
        srUserRefresh = (SmartRefreshLayout) contentview.findViewById(R.id.srUserRefresh);
        tvChooseUser = (MarqueeView) contentview.findViewById(R.id.tvChooseUser);
        tvSelecNumbs = (TextView) contentview.findViewById(R.id.tvSelecNumbs);
        tvUserConfirm = (TextView) contentview.findViewById(R.id.tvUserConfirm);
        cvUserConfirm = (CardView) contentview.findViewById(R.id.cvUserConfirm);
        tvUserClear = (TextView)contentview.findViewById(R.id.tvUserClear);
        tvUserClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearDialogDatas();
            }
        });
        userDialog.setContentView(contentview);
        ArmsUtils.configRecycleView(rlChooseUsers, new LinearLayoutManager(this));
        userAdapter = new AddUserAdapter(mUsers);
        rlChooseUsers.setAdapter(userAdapter);
        // 刷新
        srUserRefresh.setNoMoreData(true);
        srUserRefresh.setRefreshHeader(new ClassicsHeader(this));
        srUserRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                showLoading();
                if (mPresenter != null) {
                    mPresenter.getSelectUsers(queryUserKey);
                }
            }
        });
        // 返回
        tvUserBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearDialogDatas();
                userDialog.dismiss();
            }
        });
        // 清空人员搜索框并刷新
        ivEditDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etUserSearch.setText("");
                showLoading();
                if (etUserSearch.getText() != null) {
                    queryUserKey = etUserSearch.getText().toString();
                } else {
                    queryUserKey = "";
                }
                if (mPresenter != null) {
                    mPresenter.getSelectUsers(queryUserKey);
                }
            }
        });
        //查询（搜索）人员
        ivUserSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading();
                if(etUserSearch.getText()!=null){
                    queryUserKey = etUserSearch.getText().toString();
                }else {
                    queryUserKey = "";
                }
                if (mPresenter != null) {
                    mPresenter.getSelectUsers(queryUserKey);
                }
            }
        });
        List<String> ids = new ArrayList<String>();
        for (FaultOrderBean fo : mFaultOrderList) {
            if (FaultOrderBean.CHECKED == fo.getChecked()) {
                ids.add(fo.getIdx());
            }
        }
        // 派工
        tvUserConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedUsers == null || selectedUsers.size() <= 0) {
                    ToastUtils.showShort("修理人不能为空！");
                    return;
                }
                FaultOrderBean paramFo = new FaultOrderBean();
                StringBuffer empNameTemp = new StringBuffer();
                StringBuffer empIdTemp = new StringBuffer();
                int i = 0;
                for (UserInfo emp : selectedUsers) {
                    empNameTemp.append(emp.getEmpname());
                    if (i < selectedUsers.size() - 1) {
                        empNameTemp.append(",");
                    }
                    empIdTemp.append(emp.getEmpid()).append(",");
                    i++;
                }
                paramFo.setRepairEmp(empNameTemp.toString());
                paramFo.setRepairEmpId(empIdTemp.toString());
                if (null!=mPresenter){
                    mPresenter.confirm(JSON.toJSONString(ids),JSON.toJSONString(paramFo));
                }
            }
        });
    }

    /**
     * <li>说明：清除dialog数据
     * <li>创建人：刘欢
     * <li>创建日期：2018年10月10日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    public void clearDialogDatas(){
        tvChooseUser.setText("");
        tvSelecNumbs.setText("0");
        selectedUsers.clear();
        for(UserInfo user : mUsers){
            user.setChecked(UserInfo.UNCHECKED);
        }
        userAdapter.notifyDataSetChanged();
    }

    @Override
    public void submitSuccess() {
        ToastUtils.showShort("操作成功！");
        selectedEquipments.clear();
        start = 0;
        isLoadMore = false;
        loadData();
        clearDialogDatas();
        userDialog.dismiss();
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
        intentFilter.addAction("action.refreshList");
        registerReceiver(mBroadcastReceiver, intentFilter);
    }

    // 接收刷新列表的广播
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("action.refreshList")) {
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
        if (null != list) {
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
     * <li>创建日期：2018年10月8日
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
     * <li>创建日期：2018年10月8日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    @OnClick(R.id.ivScan)
    void onScanStart() {
        Intent intent = new Intent(TpgzpgListActivity.this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SCAN);
    }

    @Override
    public void getFixUserSuccess(List<UserInfo> list) {
        srUserRefresh.finishRefresh();
        mUsers.clear();
        if (list.size() > 0) {
            mUsers.addAll(list);
        }
        if (!userDialog.isShowing()) {
            userDialog.show();
        }
        if(selectedUsers.size()>0){
            for(UserInfo muer:mUsers){
                for(UserInfo selectuser:selectedUsers){
                    if(muer.getEmpid().equals(selectuser.getEmpid())){
                        muer.setChecked(UserInfo.CHECKED);
                    }
                }
            }
        }
        userAdapter.notifyDataSetChanged();
    }

    @Subscribe
    public void getChecked(MessageBean messageBean) {
        if (messageBean != null) {
            if (messageBean.getMsgType().equals("UserSelected")) {
                int position = messageBean.getPosition();
                boolean isChecked = messageBean.getSuccess();
                if (isChecked) {
                    int temp = 0;
                    for(UserInfo user:selectedUsers){
                        if(mUsers.get(position).getEmpid().equals(user.getEmpid())){
                            temp++;
                            break;
                        }
                    }
                    if(temp==0){
                        selectedUsers.add(mUsers.get(position));
                    }
                } else {
                    Long empid = mUsers.get(position).getEmpid();
                    for(UserInfo user:selectedUsers){
                        if(user.getEmpid().equals(empid)){
                            selectedUsers.remove(user);
                            break;
                        }
                    }

                }
                if (selectedUsers != null & selectedUsers.size() > 0) {
                    String str = "";
                    for (int i = 0; i < selectedUsers.size(); i++) {
                        if (i == 0) {
                            str = str + selectedUsers.get(i).getEmpname();
                        } else {
                            str = str + " " + selectedUsers.get(i).getEmpname();
                        }
                    }
                    tvChooseUser.setText(str);
                    tvSelecNumbs.setText(selectedUsers.size() + "");
                } else {
                    tvChooseUser.setText("");
                    tvSelecNumbs.setText("0");
                }
            }
        }
    }

    @Subscribe
    public void getCheckedEquipments(MessageBean messageBean) {
        if (messageBean != null) {
            if (messageBean.getMsgType().equals("EquipmentsSelected")) {
                int position = messageBean.getPosition();
                boolean isChecked = messageBean.getSuccess();
                if (isChecked) {
                    int temp = 0;
                    for(FaultOrderBean faultOrder:selectedEquipments){
                        if(mFaultOrderList.get(position).getIdx().equals(faultOrder.getIdx())){
                            temp++;
                            break;
                        }
                    }
                    if(temp==0){
                        selectedEquipments.add(mFaultOrderList.get(position));
                    }
                } else {
                    String idx = mFaultOrderList.get(position).getIdx();
                    for(FaultOrderBean orderBean:selectedEquipments){
                        if(orderBean.getIdx().equals(idx)){
                            selectedEquipments.remove(orderBean);
                            break;
                        }
                    }

                }
                if (selectedEquipments != null & selectedEquipments.size() > 0) {
                    tvSelecNumbs1.setText(selectedEquipments.size() + "");
                } else {
                    tvSelecNumbs1.setText("0");
                }
            }
        }
    }
    /**
     * <li>说明：选择维修人
     * <li>创建人：刘欢
     * <li>创建日期：2018年10月8日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    @OnClick(R.id.tvConfirm)
    void confirm(){
        if (null==selectedEquipments || selectedEquipments.size()==0){
            ToastUtils.showShort("尚未选择任何一条记录！");
            return;
        }
        setDialog();
        if (mUsers.size() == 0) {
            if (mPresenter != null) {
                showLoading();
                mPresenter.getSelectUsers("");
            }
        } else {
            userDialog.show();
        }
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
}
