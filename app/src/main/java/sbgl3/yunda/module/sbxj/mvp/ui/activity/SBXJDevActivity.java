package sbgl3.yunda.module.sbxj.mvp.ui.activity;

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
import com.speedata.utils.ProgressDialogUtils;

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
import sbgl3.yunda.entry.EpcDataBase;
import sbgl3.yunda.globle.adapter.MenuAdapter;
import sbgl3.yunda.module.sbxj.di.component.DaggerSBXJDevComponent;
import sbgl3.yunda.module.sbxj.di.module.SBXJDevModule;
import sbgl3.yunda.module.sbxj.entry.InspectPlanEquipmentBean;
import sbgl3.yunda.module.sbxj.mvp.contract.SBXJDevContract;
import sbgl3.yunda.module.sbxj.mvp.presenter.SBXJDevPresenter;
import sbgl3.yunda.module.sbxj.mvp.ui.adapter.InspectPlanEquipAdapter;
import sbgl3.yunda.widget.MySearchEditText;
import sbgl3.yunda.widget.zxing.android.CaptureActivity;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class SBXJDevActivity extends BaseActivity<SBXJDevPresenter> implements SBXJDevContract.View {

    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.etSearch)
    MySearchEditText etSearch;
    @BindView(R.id.rlPlans)
    RecyclerView rlPlans;
    @BindView(R.id.srRefresh)
    SmartRefreshLayout srRefresh;
    @BindView(R.id.gray_layout)
    View gray_layout;
    RecyclerView rlPopUpMenu;
    TextView tvPopUpClose;
    MenuAdapter menuAdapter;
    CustomPopupWindow window;
    boolean isLoadmore = false;
    int start = 0;
    int limit = 8;
    String planIdx;
    List<InspectPlanEquipmentBean> mlist = new ArrayList<>();
    InspectPlanEquipAdapter adapter;
    String querykey = "";
    String queryType = "未巡检";
    boolean isScanEquip = false;
    @BindView(R.id.ivSearch)
    ImageView ivSearch;
    @BindView(R.id.ivScan)
    ImageView ivScan;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSBXJDevComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .sBXJDevModule(new SBXJDevModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_sbxjdev; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        planIdx = getIntent().getStringExtra("planIdx");
        setTranslucentStatus();
        setSupportActionBar(menuTp);
        menuTp.setTitle("巡检设备");
        menuTp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setPopUpMenu();
        List<String> types = new ArrayList<>();
        types.add("未巡检");
        types.add("已巡检");
        types.add("未上传");
        etSearch.setTypes(types);
        etSearch.setOnTypeClickListner(new MySearchEditText.onTypeClickListner() {
            @Override
            public void OnTypeClick(String type) {
                queryType = type;
                isLoadmore = false;
                Loaddata();
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
        ArmsUtils.configRecycleView(rlPlans, new LinearLayoutManager(this));
        adapter = new InspectPlanEquipAdapter(mlist);
        rlPlans.setAdapter(adapter);
        showLoading();
        Loaddata();
        setSrRefresh();
        adapter.setOnItemClickListener(new DefaultAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int viewType, Object data, int position) {
                Intent intent = new Intent(SBXJDevActivity.this,SBXJRecordActivity.class);
                intent.putExtra("Equip",mlist.get(position));
                ArmsUtils.startActivity(intent);
            }
        });
    }

    private void Loaddata() {
        Map<String, Object> map = new HashMap<>();
        map.put("planIdx", planIdx);
        map.put("checkResult", queryType);
        map.put("equipmentIdx", querykey);
        mPresenter.getInspecPlan(start, limit, JSON.toJSONString(map), isLoadmore);
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

    @Override
    public void showLoading() {
        ProgressDialogUtils.showProgressDialog(this, "正在获取巡检设备信息中，请稍等...");
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

    private void setPopUpMenu() {
        if (window == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.view_popup_menu, null);
            window = CustomPopupWindow.builder().contentView(view).customListener(new CustomPopupWindow.CustomPopupWindowListener() {
                @Override
                public void initPopupView(View contentView) {
                    tvPopUpClose = (TextView) contentView.findViewById(R.id.tvPopUpClose);
                    rlPopUpMenu = (RecyclerView) contentView.findViewById(R.id.rlPopUpMenu);
                    ArmsUtils.configRecycleView(rlPopUpMenu, new GridLayoutManager(SBXJDevActivity.this, 3, OrientationHelper.VERTICAL, false));
                    menuAdapter = new MenuAdapter(SysInfo.menus, SBXJDevActivity.this);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEqiupCode(ArrayList<EpcDataBase> list) {
        if (list != null && list.size() > 0) {
            if(list.size()==1){
                etSearch.setText(list.get(0).equipmentCode);
                querykey = list.get(0).equipmentCode;
                isScanEquip = true;
                isLoadmore = false;
                start = 0;
                Loaddata();
            }else {
                ToastUtils.showShort("扫描到多条标签，请重新扫描");
            }

        }else {
            ToastUtils.showShort("未扫描到标签，请重新扫描");
        }
    }

    @Override
    public void LoadData(List<InspectPlanEquipmentBean> list) {
        srRefresh.finishRefresh();
        mlist.clear();
        if (list != null) {
            mlist.addAll(list);
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
            if(list!=null&&list.size()==1){
                Intent intent = new Intent(SBXJDevActivity.this,SBXJRecordActivity.class);
                intent.putExtra("Equip",list.get(0));
                ArmsUtils.startActivity(intent);
            }
        }
    }

    @Override
    public void LoadMoreData(List<InspectPlanEquipmentBean> list) {
        srRefresh.finishLoadMore();
        if (list != null) {
            mlist.addAll(list);
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
    @OnClick(R.id.ivSearch)
    void Search(){
        querykey = etSearch.getSearch();
        Loaddata();
    }
    @OnClick(R.id.ivScan)
    void OnScanStart(){
        Intent intent = new Intent(SBXJDevActivity.this, CaptureActivity.class);
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

    @Override
    protected void onResume() {
        super.onResume();
        Loaddata();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        window.dismiss();
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
