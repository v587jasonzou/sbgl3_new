package sbgl3.yunda.module.sbdj.mvp.ui.activity;

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
import com.jess.arms.utils.utilcode.util.StringUtils;
import com.jess.arms.utils.utilcode.util.ToastUtils;
import com.jess.arms.widget.CustomPopupWindow;
import com.loopj.android.http.RequestParams;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.speedata.utils.ProgressDialogUtils;

import net.lemonsoft.lemonhello.LemonHello;
import net.lemonsoft.lemonhello.LemonHelloAction;
import net.lemonsoft.lemonhello.LemonHelloInfo;
import net.lemonsoft.lemonhello.LemonHelloView;
import net.lemonsoft.lemonhello.interfaces.LemonHelloActionDelegate;

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
import sbgl3.yunda.entry.MessageBean;
import sbgl3.yunda.globle.adapter.MenuAdapter;
import sbgl3.yunda.module.sbdj.di.component.DaggerSbdjMainComponent;
import sbgl3.yunda.module.sbdj.di.module.SbdjMainModule;
import sbgl3.yunda.module.sbdj.entity.EquipResponse;
import sbgl3.yunda.module.sbdj.entity.EquipmentUnionRFID;
import sbgl3.yunda.module.sbdj.mvp.contract.SbdjMainContract;
import sbgl3.yunda.module.sbdj.mvp.presenter.SbdjMainPresenter;
import sbgl3.yunda.module.sbdj.mvp.ui.adapter.EquipAdapter;
import sbgl3.yunda.module.sbxj.mvp.ui.activity.SBXJDevActivity;
import sbgl3.yunda.widget.MySearchEditText;
import sbgl3.yunda.widget.zxing.android.CaptureActivity;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class SbdjMainActivity extends BaseActivity<SbdjMainPresenter> implements SbdjMainContract.View {

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
    EquipAdapter adapter;
    boolean isLoadmore = false;
    int start = 0;
    int limit = 8;
    String querykey = "";
    String queryType = "";
    List<EquipmentUnionRFID> mList = new ArrayList<>();
    EquipmentUnionRFID selectEquip = null;
    RecyclerView rlPopUpMenu;
    TextView tvPopUpClose;
    MenuAdapter menuAdapter;
    CustomPopupWindow window;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSbdjMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .sbdjMainModule(new SbdjMainModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_sbdj_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTranslucentStatus();
        setSupportActionBar(menuTp);
        menuTp.setTitle("设备登记");
        menuTp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setPopUpMenu();
        List<String> types = new ArrayList<>();
        types.add("全部");
        types.add("已登记");
        types.add("未登记");
        etSearch.setTypes(types);
        etSearch.setOnTypeClickListner(new MySearchEditText.onTypeClickListner() {
            @Override
            public void OnTypeClick(String type) {
                if(type.equals("全部")){
                    queryType = "";
                }else {
                    queryType = type;
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
        adapter = new EquipAdapter(mList,this);
        rlEquips.setAdapter(adapter);
        showLoading();
        Loaddata();
        setSrRefresh();
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

    private void Loaddata() {
        Map<String, Object> map = new HashMap<>();
//        map.put("checkResult", queryType);
        map.put("equipmentCode", querykey);
        if(mPresenter!=null)
        mPresenter.getEquipmentInfos(start, limit, JSON.toJSONString(map), isLoadmore);
    }

    @Override
    public void showLoading() {
        ProgressDialogUtils.showProgressDialog(this,"正在处理中，请稍后...");
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
    protected void onStop() {
        super.onStop();
        window.dismiss();
        if(EventBus.getDefault().isRegistered(this)){
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
    public void OnLoadEquipsSuccess(List<EquipmentUnionRFID> list) {
        srRefresh.finishRefresh();
        mList.clear();
        if (list != null) {
            mList.addAll(list);
        }
        adapter.notifyDataSetChanged();
        if (list.size() < limit) {
            srRefresh.setNoMoreData(true);
        } else {
            srRefresh.setNoMoreData(false);
        }
    }

    @Override
    public void OnLoadMoreEquipsSuccess(List<EquipmentUnionRFID> list) {
        srRefresh.finishLoadMore();
        if (list != null) {
            mList.addAll(list);
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
        ToastUtils.showShort(msg);
    }

    @Override
    public void OnRegisterSuccess() {
        ToastUtils.showShort("设备登记成功");
        Loaddata();
    }

    @Override
    public void OnRegisterFaild(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void OnUpLoadResSuccess(EquipResponse stringObjectMap) {
        if(stringObjectMap.getEntity()!=null){
            LemonHello.getWarningHello("温馨提示","您选择的设备已与"+stringObjectMap.getEntity()
            .getEquipmentName()+"("+stringObjectMap.getEntity().getEquipmentCode()+")"+"绑定"+
            "是否重新绑定？").addAction(new LemonHelloAction("确定", new LemonHelloActionDelegate() {
                @Override
                public void onClick(LemonHelloView lemonHelloView, LemonHelloInfo lemonHelloInfo, LemonHelloAction lemonHelloAction) {
                    if(mPresenter!=null){
                        mPresenter.UnregisterEquip(stringObjectMap.getEntity().getEquipmentCode());
                    }
                    lemonHelloView.hide();
                }
            })).addAction(new LemonHelloAction("取消", new LemonHelloActionDelegate() {
                        @Override
                        public void onClick(LemonHelloView lemonHelloView, LemonHelloInfo lemonHelloInfo, LemonHelloAction lemonHelloAction) {
                            lemonHelloView.hide();
                        }
                    })).show(this);
        }else {
            MessageBean messageBean = new MessageBean();
            messageBean.setMsgType("regcard");
            messageBean.setMsgInfo(selectEquip.getEquipmentCode());
            EventBus.getDefault().post(messageBean);
        }
    }

    @Override
    public void UnRegiterSuccess() {
        MessageBean messageBean = new MessageBean();
        messageBean.setMsgType("regcard");
        messageBean.setMsgInfo(selectEquip.getEquipmentCode());
        EventBus.getDefault().post(messageBean);
    }

    @Override
    public void UnRegisterFaild(String msg) {

    }

    @Override
    public void OnUpLoadResFaild(String msg) {
        ToastUtils.showShort(msg);
    }

    private void setPopUpMenu() {
        if (window == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.view_popup_menu, null);
            window = CustomPopupWindow.builder().contentView(view).customListener(new CustomPopupWindow.CustomPopupWindowListener() {
                @Override
                public void initPopupView(View contentView) {
                    tvPopUpClose = (TextView) contentView.findViewById(R.id.tvPopUpClose);
                    rlPopUpMenu = (RecyclerView) contentView.findViewById(R.id.rlPopUpMenu);
                    ArmsUtils.configRecycleView(rlPopUpMenu, new GridLayoutManager(SbdjMainActivity.this, 3, OrientationHelper.VERTICAL, false));
                    menuAdapter = new MenuAdapter(SysInfo.menus, SbdjMainActivity.this);
                    menuAdapter.setNowData("设备登记");
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
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCode(ArrayList<EpcDataBase> list) {
        if (list.size() > 1) {
            ToastUtils.showShort("扫描到多张RFID标签，请移开多余标签后重新扫描！");
            return;
        }

        if (selectEquip == null) {
            ToastUtils.showShort("请先选中一条需要登记的设备！");
            return;
        }
        // 获取设备编码
        final String equipmentCode = selectEquip.getEquipmentCode();
        if (list.size() == 1) {
            final String tempCode = list.get(0).equipmentCode;

            if (StringUtils.isTrimEmpty(tempCode)) {
                MessageBean messageBean = new MessageBean();
                messageBean.setMsgType("regcard");
                messageBean.setMsgInfo(equipmentCode);
                EventBus.getDefault().post(messageBean);
                return;
            }

            // 如果当前RFID已经与选择设备关联，但是数据异常显示为未登记 则绑定设备,否则提示已绑定，返回。
            if (!StringUtils.isTrimEmpty(tempCode) && equipmentCode.equals(tempCode)) {
                if(selectEquip.getRfidCode() == null) {
                    MessageBean messageBean = new MessageBean();
                    messageBean.setMsgType("regcard");
                    messageBean.setMsgInfo(equipmentCode);
                    EventBus.getDefault().post(messageBean);
                    return;
                }else{
                    ToastUtils.showShort("您选择的设备已经绑定了这条RFID,请选择其他设备！");
                    return;
                }
            }

            // 当扫描的RFID已经和其他设备关联时打开弹窗，让用户选择
            if (!StringUtils.isTrimEmpty(tempCode) && !equipmentCode.equals(tempCode)) {
                // 验证RFID的编码是否是设备的编码
                if(mPresenter!=null){
                    mPresenter.UpLoadEquipInfo(tempCode);
                }
            }
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Itemclick(ItemClickMessage message){
        if(message!=null){
            int position = message.getPosition();
            if("content".equals(message.getType())){
                if(position>=0){
                    for(EquipmentUnionRFID bean:mList){
                        bean.setStatus(0);
                    }
                    mList.get(position).setStatus(1);
                    adapter.notifyDataSetChanged();
                    selectEquip = mList.get(position);
                }

            }else if("image".equals(message.getType())){
                EquipmentUnionRFID bean = mList.get(position);
                Intent intent = new Intent(SbdjMainActivity.this,ReadPhotoActivity.class);
                intent.putExtra("equip",bean);
                intent.putExtra("isAdd",true);
                ArmsUtils.startActivity(intent);
            }
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void regSuccess(MessageBean message){
        if(message!=null){
            if (message.getSuccess()){
                String code = message.getMsgInfo();
                Map<String, String> devCodeMap = new HashMap<String, String>();
                devCodeMap.put("rfidCode",code);
                if(mPresenter!=null){
                    showLoading();
                    mPresenter.RegisterEquip(JSON.toJSONString(devCodeMap));
                }
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

    @OnClick(R.id.ivSearch)
    void Search(){
        querykey = etSearch.getSearch();
        start = 0;
        isLoadmore = false;
        Loaddata();
    }
    @OnClick(R.id.ivScan)
    void OnScanStart(){
        Intent intent = new Intent(SbdjMainActivity.this, CaptureActivity.class);
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
                Loaddata();
            }
        }
    }
}
