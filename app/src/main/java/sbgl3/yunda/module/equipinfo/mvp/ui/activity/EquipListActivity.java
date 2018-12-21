package sbgl3.yunda.module.equipinfo.mvp.ui.activity;

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
import sbgl3.yunda.entry.ItemClickMessage;
import sbgl3.yunda.globle.adapter.MenuAdapter;
import sbgl3.yunda.module.equipinfo.di.component.DaggerEquipListComponent;
import sbgl3.yunda.module.equipinfo.di.module.EquipListModule;
import sbgl3.yunda.module.equipinfo.entity.EquipmentPrimaryInfo;
import sbgl3.yunda.module.equipinfo.mvp.contract.EquipListContract;
import sbgl3.yunda.module.equipinfo.mvp.presenter.EquipListPresenter;
import sbgl3.yunda.module.equipinfo.mvp.ui.adapter.EquipListAdapter;
import sbgl3.yunda.module.sbdj.entity.EquipmentUnionRFID;
import sbgl3.yunda.module.sbdj.mvp.ui.activity.ReadPhotoActivity;
import sbgl3.yunda.module.sbdj.mvp.ui.activity.SbdjMainActivity;
import sbgl3.yunda.module.sbdj.mvp.ui.adapter.EquipAdapter;
import sbgl3.yunda.module.sbxj.mvp.ui.activity.SBXJDevActivity;
import sbgl3.yunda.module.sbxj.mvp.ui.activity.SBXJRecordActivity;
import sbgl3.yunda.widget.MySearchEditText;
import sbgl3.yunda.widget.zxing.android.CaptureActivity;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 设备信息查看-设备列表
 * @author 周雪巍
 * @time 2018/09/12
 * */
public class EquipListActivity extends BaseActivity<EquipListPresenter> implements EquipListContract.View {

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
    @BindView(R.id.rlEquips)
    RecyclerView rlEquips;
    @BindView(R.id.srRefresh)
    SmartRefreshLayout srRefresh;
    List<EquipmentPrimaryInfo> list = new ArrayList<>();
    EquipListAdapter adapter;
    boolean isLoadmore = false;
    int start = 0;
    int limit = 8;
    String querykey = "";
    String queryType = "";
    boolean isScanEquip = false;
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerEquipListComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .equipListModule(new EquipListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_equip_list; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
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
        setPopUpMenu();
        List<String> types = new ArrayList<>();
        types.add("全部");
        types.add("固资设备");
        types.add("非固资设备");
        etSearch.setTypes(types);
        etSearch.setOnTypeClickListner(new MySearchEditText.onTypeClickListner() {
            @Override
            public void OnTypeClick(String type) {
                if(type.equals("全部")){
                    queryType = "";
                }else if(type.equals("固资设备")) {
                    queryType = "1";
                }else {
                    queryType = "2";
                }
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
        ArmsUtils.configRecycleView(rlEquips, new LinearLayoutManager(this));
        adapter = new EquipListAdapter(list);
        rlEquips.setAdapter(adapter);
        showLoading();
        Loaddata();
        setSrRefresh();
    }
    //设置上下拉刷新逻辑
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
    //加载数据
    private void Loaddata() {
        Map<String, Object> map = new HashMap<>();
        map.put("tag", queryType);
        map.put("equipmentCode", querykey);
        if(mPresenter!=null)
            mPresenter.getEquipList(start, limit, JSON.toJSONString(map), isLoadmore);
    }
    RecyclerView rlPopUpMenu;
    TextView tvPopUpClose;
    MenuAdapter menuAdapter;
    CustomPopupWindow window;
    //设置快捷菜单弹出框
    private void setPopUpMenu() {
        if (window == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.view_popup_menu, null);
            window = CustomPopupWindow.builder().contentView(view).customListener(new CustomPopupWindow.CustomPopupWindowListener() {
                @Override
                public void initPopupView(View contentView) {
                    tvPopUpClose = (TextView) contentView.findViewById(R.id.tvPopUpClose);
                    rlPopUpMenu = (RecyclerView) contentView.findViewById(R.id.rlPopUpMenu);
                    ArmsUtils.configRecycleView(rlPopUpMenu, new GridLayoutManager(EquipListActivity.this, 3, OrientationHelper.VERTICAL, false));
                    menuAdapter = new MenuAdapter(SysInfo.menus, EquipListActivity.this);
                    menuAdapter.setNowData("设备信息查看");
                    rlPopUpMenu.setAdapter(menuAdapter);
                    tvPopUpClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            window.dismiss();
                        }
                    });
                }
            }).parentView(rlEquips).build();
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
        ProgressDialogUtils.showProgressDialog(this,"设备信息加载中，请稍后");
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
    //recycleview子项点击事件
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Itemclick(ItemClickMessage message){
        if(message!=null){
            int position = message.getPosition();
            if("EquipClick".equals(message.getType())){
                if(position>=0){
                    Intent intent = new Intent(EquipListActivity.this,EquipInfoActivity.class);
                    intent.putExtra("Equip",list.get(position));
                    ArmsUtils.startActivity(intent);
                }
            }
        }
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
    //RFID扫描成功回调
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEqiupCode(ArrayList<EpcDataBase> list) {
        if (list != null && list.size() > 0) {
            if(list.size()==1){
                etSearch.setText(list.get(0).equipmentCode);
                querykey = list.get(0).equipmentCode;
                isScanEquip = true;
                start = 0;
                isLoadmore = false;
                Loaddata();
            }else {
                ToastUtils.showShort("扫描到多条标签，请重新扫描");
            }

        }else {
            ToastUtils.showShort("未扫描到标签，请重新扫描");
        }
    }
    @Override
    public void OnLoadDataSuccess(List<EquipmentPrimaryInfo> list) {
        srRefresh.finishRefresh();
        this.list.clear();
        if (list != null) {
            this.list.addAll(list);
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
                Intent intent = new Intent(EquipListActivity.this,EquipInfoActivity.class);
                intent.putExtra("Equip",list.get(0));
                ArmsUtils.startActivity(intent);
            }
        }
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
    @Override
    public void OnLoadMoreDataSuceess(List<EquipmentPrimaryInfo> list) {
        srRefresh.finishLoadMore();
        if (list != null) {
            this.list.addAll(list);
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
        start = 0;
        isLoadmore = false;
        Loaddata();
    }
    @OnClick(R.id.ivScan)
    void OnScanStart(){
        Intent intent = new Intent(EquipListActivity.this, CaptureActivity.class);
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
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
        window.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
