package sbgl3.yunda.module.gztp.mvp.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.utilcode.util.ToastUtils;
import com.jess.arms.widget.CustomPopupWindow;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
import sbgl3.yunda.entry.Condition;
import sbgl3.yunda.entry.EpcDataBase;
import sbgl3.yunda.globle.adapter.MenuAdapter;
import sbgl3.yunda.module.gztp.di.component.DaggerFaultTicketComponent;
import sbgl3.yunda.module.gztp.di.module.FaultTicketModule;
import sbgl3.yunda.module.gztp.entry.FaultOrder;
import sbgl3.yunda.module.gztp.mvp.contract.FaultTicketContract;
import sbgl3.yunda.module.gztp.mvp.presenter.FaultTicketPresenter;
import sbgl3.yunda.module.gztp.mvp.ui.adapter.FaultTicketAdapter;
import sbgl3.yunda.module.sbxj.mvp.ui.activity.SBXJRecordActivity;
import sbgl3.yunda.widget.MySearchEditText;
import sbgl3.yunda.widget.zxing.android.CaptureActivity;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class FaultTicketActivity extends BaseActivity<FaultTicketPresenter> implements FaultTicketContract.View {

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
    @BindView(R.id.rlPlans)
    RecyclerView rlPlans;
    @BindView(R.id.srRefresh)
    SmartRefreshLayout srRefresh;
    @BindView(R.id.tvAdd)
    TextView tvAdd;

    ImageView ivMenuClose;
    TextView tvMenu1;
    TextView tvMenu2;
    TextView tvMenu3;
    TextView tvMenu4;
    TextView tvMenu5;
    TextView tvConfirm;
    private List<FaultOrder> datas = new ArrayList<FaultOrder>();
    private int start = 0, limit = 8;
    RecyclerView rlPopUpMenu;
    TextView tvPopUpClose;
    MenuAdapter menuAdapter;
    CustomPopupWindow window;
    CustomPopupWindow typeWindow;
    boolean isLoadmore = false;
    boolean isScanEquip = false;
    String querykey = "";
    // 保存查询参数
    private List<Map<String, Object>> whereListJson = new ArrayList<Map<String, Object>>();
    private Map<String, Object> queryCondition = new HashMap<String, Object>();
    FaultTicketAdapter adapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerFaultTicketComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .faultTicketModule(new FaultTicketModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_fault_ticket; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
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
        isScan = true;
        setPopUpMenu();
        setSrRefresh();
        List<String> types = new ArrayList<>();
        types.add("全部");
        types.add("固资设备");
        types.add("非固资设备");
        etSearch.setTypes(types);
        etSearch.setOnTypeClickListner(new MySearchEditText.onTypeClickListner() {
            @Override
            public void OnTypeClick(String type) {
//                queryType = type;
                isLoadmore = false;
                Loaddata();
            }
        });
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
        queryCondition.put("compare", Condition.SQL);
        if (queryCondition.get("sql") == null ||
                (queryCondition.get("sql") != null && TextUtils.isEmpty(queryCondition.get("sql").toString()))) {
            queryCondition.put("sql", "1 = 1");
        }

        if (!whereListJson.contains(queryCondition)) {
            whereListJson.add(queryCondition);
        }

        // 添加当前登录人过滤条件
        Map<String, Object> submitEmp = new HashMap<String, Object>();
        submitEmp.put("propName", "submitEmpId");
        submitEmp.put("propValue", SysInfo.empid);
        submitEmp.put("stringLike", false);

        if (!whereListJson.contains(submitEmp)) {
            whereListJson.add(submitEmp);
        }

        // 添加提票状态过滤条件
        Map<String, Object> state = new HashMap<String, Object>();
        state.put("propName", "state");
        List<String> mList = new ArrayList<>();
//        mList.add(FaultOrder.STATE_TH);
        mList.add(FaultOrder.STATE_XJ);
        mList.add(FaultOrder.STATE_DDTH);
        state.put("propValues", mList);
//        state.put("stringLike", true);
        state.put("compare", 9);
        if (!whereListJson.contains(state)) {
            whereListJson.add(state);
        }
        Map<String, Object> userworker = new HashMap<String, Object>();
        userworker.put("compare", 21);
        userworker.put("propName", "useWorker");
        if (!whereListJson.contains(userworker)) {
            whereListJson.add(userworker);
        }
        ArmsUtils.configRecycleView(rlPlans, new LinearLayoutManager(this));
        adapter = new FaultTicketAdapter(datas);
        rlPlans.setAdapter(adapter);
        showLoading();
        Loaddata();
        adapter.setOnItemClickListener(new DefaultAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int viewType, Object data, int position) {
                if("新建".equals(datas.get(position).getState())){
                    Intent intent = new Intent(FaultTicketActivity.this,NewBuildTicketActivity.class);
                    intent.putExtra("order",datas.get(position));
                    ArmsUtils.startActivity(intent);
                }else if("调度退回".equals(datas.get(position).getState())){
                    Intent intent = new Intent(FaultTicketActivity.this,BackTicketActivity.class);
                    intent.putExtra("order",datas.get(position));
                    ArmsUtils.startActivity(intent);
                }
//                ToastUtils.showShort(position + "");
            }
        });
    }

    private void setSrRefresh() {
        srRefresh.setRefreshHeader(new ClassicsHeader(this));
        srRefresh.setRefreshFooter(new ClassicsFooter(this));
        srRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                start = 0;
                isLoadmore = false;
                Loaddata();
            }
        });
        srRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                start = start + limit;
                isLoadmore = true;
                Loaddata();
            }
        });
        srRefresh.setEnableAutoLoadMore(false);
    }

    public int popupState = 0;

    private void setPopUpMenu() {
        if (window == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.view_popup_menu, null);
            window = CustomPopupWindow.builder().contentView(view).customListener(new CustomPopupWindow.CustomPopupWindowListener() {
                @Override
                public void initPopupView(View contentView) {
                    tvPopUpClose = (TextView) contentView.findViewById(R.id.tvPopUpClose);
                    rlPopUpMenu = (RecyclerView) contentView.findViewById(R.id.rlPopUpMenu);
                    ArmsUtils.configRecycleView(rlPopUpMenu, new GridLayoutManager(FaultTicketActivity.this, 3, OrientationHelper.VERTICAL, false));
                    menuAdapter = new MenuAdapter(SysInfo.menus, FaultTicketActivity.this);
                    menuAdapter.setNowData("故障提票");
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
        if (typeWindow == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_down_menu, null);
            typeWindow = CustomPopupWindow.builder().contentView(view).customListener(new CustomPopupWindow.CustomPopupWindowListener() {
                @Override
                public void initPopupView(View contentView) {
                    setDownPopupMenu(contentView);
                }
            }).build();
            typeWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    grayLayout.setVisibility(View.GONE);
                    tvMenu1.setBackgroundResource(R.drawable.down_popup_menu_unclick_bg);
                    tvMenu1.setTextColor(ContextCompat.getColor(FaultTicketActivity.this, R.color.title_text_color));
                    tvMenu2.setBackgroundResource(R.drawable.down_popup_menu_unclick_bg);
                    tvMenu2.setTextColor(ContextCompat.getColor(FaultTicketActivity.this, R.color.title_text_color));
                    tvMenu3.setBackgroundResource(R.drawable.down_popup_menu_unclick_bg);
                    tvMenu3.setTextColor(ContextCompat.getColor(FaultTicketActivity.this, R.color.title_text_color));
                    tvMenu4.setBackgroundResource(R.drawable.down_popup_menu_unclick_bg);
                    tvMenu4.setTextColor(ContextCompat.getColor(FaultTicketActivity.this, R.color.title_text_color));
                    tvMenu5.setBackgroundResource(R.drawable.down_popup_menu_unclick_bg);
                    tvMenu5.setTextColor(ContextCompat.getColor(FaultTicketActivity.this, R.color.title_text_color));
                    popupState = 0;
                }
            });
            typeWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            typeWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            typeWindow.setAnimationStyle(R.style.anim_menu_bottombar);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                typeWindow.setElevation(2.0f);
//                window.setAnimationStyle(R.style.popwin_anim_style);
            }
        }
    }

    private void setDownPopupMenu(View contentView) {
        ivMenuClose = contentView.findViewById(R.id.ivMenuClose);
        tvMenu1 = contentView.findViewById(R.id.tvMenu1);
        tvMenu2 = contentView.findViewById(R.id.tvMenu2);
        tvMenu3 = contentView.findViewById(R.id.tvMenu3);
        tvMenu4 = contentView.findViewById(R.id.tvMenu4);
        tvMenu5 = contentView.findViewById(R.id.tvMenu5);
        tvConfirm = contentView.findViewById(R.id.tvConfirm);
        ivMenuClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeWindow.dismiss();
            }
        });
        tvMenu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvMenu1.setBackgroundResource(R.drawable.down_popup_menu_click_bg);
                tvMenu1.setTextColor(ContextCompat.getColor(FaultTicketActivity.this, R.color.colorPrimary));
                tvMenu2.setBackgroundResource(R.drawable.down_popup_menu_unclick_bg);
                tvMenu2.setTextColor(ContextCompat.getColor(FaultTicketActivity.this, R.color.title_text_color));
                tvMenu3.setBackgroundResource(R.drawable.down_popup_menu_unclick_bg);
                tvMenu3.setTextColor(ContextCompat.getColor(FaultTicketActivity.this, R.color.title_text_color));
                tvMenu4.setBackgroundResource(R.drawable.down_popup_menu_unclick_bg);
                tvMenu4.setTextColor(ContextCompat.getColor(FaultTicketActivity.this, R.color.title_text_color));
                tvMenu5.setBackgroundResource(R.drawable.down_popup_menu_unclick_bg);
                tvMenu5.setTextColor(ContextCompat.getColor(FaultTicketActivity.this, R.color.title_text_color));
                popupState = 1;
            }
        });
        tvMenu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvMenu2.setBackgroundResource(R.drawable.down_popup_menu_click_bg);
                tvMenu2.setTextColor(ContextCompat.getColor(FaultTicketActivity.this, R.color.colorPrimary));
                tvMenu1.setBackgroundResource(R.drawable.down_popup_menu_unclick_bg);
                tvMenu1.setTextColor(ContextCompat.getColor(FaultTicketActivity.this, R.color.title_text_color));
                tvMenu3.setBackgroundResource(R.drawable.down_popup_menu_unclick_bg);
                tvMenu3.setTextColor(ContextCompat.getColor(FaultTicketActivity.this, R.color.title_text_color));
                tvMenu4.setBackgroundResource(R.drawable.down_popup_menu_unclick_bg);
                tvMenu4.setTextColor(ContextCompat.getColor(FaultTicketActivity.this, R.color.title_text_color));
                tvMenu5.setBackgroundResource(R.drawable.down_popup_menu_unclick_bg);
                tvMenu5.setTextColor(ContextCompat.getColor(FaultTicketActivity.this, R.color.title_text_color));
                popupState = 2;
            }
        });
        tvMenu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvMenu3.setBackgroundResource(R.drawable.down_popup_menu_click_bg);
                tvMenu3.setTextColor(ContextCompat.getColor(FaultTicketActivity.this, R.color.colorPrimary));
                tvMenu2.setBackgroundResource(R.drawable.down_popup_menu_unclick_bg);
                tvMenu2.setTextColor(ContextCompat.getColor(FaultTicketActivity.this, R.color.title_text_color));
                tvMenu1.setBackgroundResource(R.drawable.down_popup_menu_unclick_bg);
                tvMenu1.setTextColor(ContextCompat.getColor(FaultTicketActivity.this, R.color.title_text_color));
                tvMenu4.setBackgroundResource(R.drawable.down_popup_menu_unclick_bg);
                tvMenu4.setTextColor(ContextCompat.getColor(FaultTicketActivity.this, R.color.title_text_color));
                tvMenu5.setBackgroundResource(R.drawable.down_popup_menu_unclick_bg);
                tvMenu5.setTextColor(ContextCompat.getColor(FaultTicketActivity.this, R.color.title_text_color));
                popupState = 3;
            }
        });
        tvMenu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvMenu4.setBackgroundResource(R.drawable.down_popup_menu_click_bg);
                tvMenu4.setTextColor(ContextCompat.getColor(FaultTicketActivity.this, R.color.colorPrimary));
                tvMenu2.setBackgroundResource(R.drawable.down_popup_menu_unclick_bg);
                tvMenu2.setTextColor(ContextCompat.getColor(FaultTicketActivity.this, R.color.title_text_color));
                tvMenu3.setBackgroundResource(R.drawable.down_popup_menu_unclick_bg);
                tvMenu3.setTextColor(ContextCompat.getColor(FaultTicketActivity.this, R.color.title_text_color));
                tvMenu1.setBackgroundResource(R.drawable.down_popup_menu_unclick_bg);
                tvMenu1.setTextColor(ContextCompat.getColor(FaultTicketActivity.this, R.color.title_text_color));
                tvMenu5.setBackgroundResource(R.drawable.down_popup_menu_unclick_bg);
                tvMenu5.setTextColor(ContextCompat.getColor(FaultTicketActivity.this, R.color.title_text_color));
                popupState = 4;
            }
        });
        tvMenu5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvMenu5.setBackgroundResource(R.drawable.down_popup_menu_click_bg);
                tvMenu5.setTextColor(ContextCompat.getColor(FaultTicketActivity.this, R.color.colorPrimary));
                tvMenu2.setBackgroundResource(R.drawable.down_popup_menu_unclick_bg);
                tvMenu2.setTextColor(ContextCompat.getColor(FaultTicketActivity.this, R.color.title_text_color));
                tvMenu3.setBackgroundResource(R.drawable.down_popup_menu_unclick_bg);
                tvMenu3.setTextColor(ContextCompat.getColor(FaultTicketActivity.this, R.color.title_text_color));
                tvMenu4.setBackgroundResource(R.drawable.down_popup_menu_unclick_bg);
                tvMenu4.setTextColor(ContextCompat.getColor(FaultTicketActivity.this, R.color.title_text_color));
                tvMenu1.setBackgroundResource(R.drawable.down_popup_menu_unclick_bg);
                tvMenu1.setTextColor(ContextCompat.getColor(FaultTicketActivity.this, R.color.title_text_color));
                popupState = 5;
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (popupState) {
                    case 0:
                        ToastUtils.showShort("还未选择提票类型！");
                        break;
                    case 1:
                        Intent intent = new Intent(FaultTicketActivity.this, FaultTicketEditActivity.class);
                        intent.putExtra("equipType", "固资设备");
                        ArmsUtils.startActivity(intent);
                        typeWindow.dismiss();
                        break;
                    case 2:
                        Intent intent1 = new Intent(FaultTicketActivity.this, FaultTicketEditActivity.class);
                        intent1.putExtra("equipType", "非固资设备");
                        ArmsUtils.startActivity(intent1);
                        typeWindow.dismiss();
                        break;
                    case 3:
                        Intent intent2 = new Intent(FaultTicketActivity.this, FaultTicketEditActivity.class);
                        intent2.putExtra("equipType", "工具仪器");
                        ArmsUtils.startActivity(intent2);
                        typeWindow.dismiss();
                        break;
                    case 4:
                        Intent intent3 = new Intent(FaultTicketActivity.this, FaultTicketEditActivity.class);
                        intent3.putExtra("equipType", "设施");
                        ArmsUtils.startActivity(intent3);
                        typeWindow.dismiss();
                        break;
                    case 5:
                        Intent intent4 = new Intent(FaultTicketActivity.this, FaultTicketEditActivity.class);
                        intent4.putExtra("equipType", "其他设备");
                        ArmsUtils.startActivity(intent4);
                        typeWindow.dismiss();
                        break;
                }
            }
        });
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

    private void Loaddata() {
        String queryKey = etSearch.getSearch();
        if (!TextUtils.isEmpty(queryKey)) {
            queryCondition.put("sql", "(EQUIPMENT_CODE like '%" + queryKey
                    + "%' or EQUIPMENT_NAME like '%" + queryKey
                    + "%' or FAULT_ORDER_NO like '%" + queryKey + "%')");
        } else {
            queryCondition.put("sql", "1 = 1");
        }
        if(mPresenter!=null)
        mPresenter.GetFaultTicketList(start, limit, JSON.toJSONString(whereListJson), isLoadmore);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        window.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 绑定toobar跟menu
        getMenuInflater().inflate(R.menu.base_bar_menu, menu);
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

    @OnClick(R.id.tvAdd)
    void ShowDownPopUpMenu() {
        if (typeWindow.isShowing()) {
            typeWindow.dismiss();
        } else {
            typeWindow.showAtLocation(findViewById(R.id.layout_main), Gravity.BOTTOM, 0, 0);
            grayLayout.setVisibility(View.VISIBLE);
        }
    }

    private boolean isFront = false;

    @Override
    public void onResume() {
        super.onResume();
        isFront = true;
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        showLoading();
        start=0;
        isLoadmore = false;
        Loaddata();

    }

    @Override
    public void onPause() {
        super.onPause();
        isFront = false;
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEqiupCode(ArrayList<EpcDataBase> list) {
        if (isFront) {
            if (list != null && list.size() > 0) {
                if (list.size() == 1) {
                    etSearch.setText(list.get(0).equipmentCode);
                    querykey = list.get(0).equipmentCode;
                    isScanEquip = true;
                    isLoadmore = false;
                    start = 0;
                    Loaddata();
                } else {
                    ToastUtils.showShort("扫描到多条标签，请重新扫描");
                }

            } else {
                ToastUtils.showShort("未扫描到标签，请重新扫描");
            }
        }

    }

    @Override
    public void OnLoadTicketListSuccess(List<FaultOrder> list) {
        srRefresh.finishRefresh();
        datas.clear();
        if (list != null && list.size() > 0) {
            datas.addAll(list);
        } else {
            ToastUtils.showShort("未找到相关提票");
        }
        adapter.notifyDataSetChanged();
        if (list.size() < limit) {
            srRefresh.setNoMoreData(true);
        } else {
            srRefresh.setNoMoreData(false);
        }
        if (isScanEquip) {
            querykey = "";
            isScanEquip = false;
            if (list != null && list.size() == 1) {
                Intent intent = new Intent(FaultTicketActivity.this, FaultTicketEditActivity.class);
                intent.putExtra("FaultOrder", list.get(0));
                ArmsUtils.startActivity(intent);
            }
        }
    }

    @Override
    public void OnLoadMoreTicketListSuccess(List<FaultOrder> list) {
        srRefresh.finishLoadMore();
        if (list != null) {
            datas.addAll(list);
        }
        adapter.notifyDataSetChanged();
        Observable.timer(1500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Long>() {
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
    public void OnLoadFaild(String msg) {

    }

    @OnClick(R.id.ivSearch)
    void Search() {
        querykey = etSearch.getSearch();
        Loaddata();
    }

    @OnClick(R.id.ivScan)
    void OnScanStart() {
        Intent intent = new Intent(FaultTicketActivity.this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SCAN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(DECODED_CONTENT_KEY);
                querykey = content;
                isScanEquip = true;
                start = 0;
                isLoadmore = false;
                Loaddata();
            }
        }
    }
}
