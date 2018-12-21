package sbgl3.yunda.module.evaluate.mvp.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.utilcode.util.StringUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.speedata.utils.ProgressDialogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import sbgl3.yunda.R;
import sbgl3.yunda.module.evaluate.di.component.DaggerEquipmentEvaluateCheckItemComponent;
import sbgl3.yunda.module.evaluate.di.module.EquipmentEvaluateCheckItemModule;
import sbgl3.yunda.module.evaluate.entry.EquipmentEvaluatePlanBean;
import sbgl3.yunda.module.evaluate.mvp.contract.EquipmentEvaluateCheckItemContract;
import sbgl3.yunda.module.evaluate.mvp.presenter.EquipmentEvaluateCheckItemPresenter;
import sbgl3.yunda.module.evaluate.mvp.ui.adapter.CheckItemsAdapter;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static sbgl3.yunda.module.evaluate.mvp.ui.activity.EquipmentEvaluateResultEditActivity.equipmentEvaluateBean;


/**
 * <li>标题: 设备管理系统
 * <li>说明: 设备评定检查项列表界面
 * <li>创建人：刘欢
 * <li>创建日期：2018/9/14
 * <li>修改人:
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 *
 * @author 系统集成事业部设备管理系统项目组
 * @version 3.0
 */
public class EquipmentEvaluateCheckItemActivity extends BaseActivity<EquipmentEvaluateCheckItemPresenter> implements EquipmentEvaluateCheckItemContract.View {

    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.tvEquipCode)
    TextView tvEquipCode;
    @BindView(R.id.tvEquipName)
    TextView tvEquipName;
    @BindView(R.id.tvUsePlace)
    TextView tvUsePlace;
    @BindView(R.id.tvTemplate)
    TextView tvTemplate;
    @BindView(R.id.rlCheckItems)
    RecyclerView rlCheckItems;
    @BindView(R.id.srRefresh)
    SmartRefreshLayout srRefresh;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.llNoData)
    LinearLayout llNoData;
    int start = 0;
    int limit = 8;
    boolean isLoadMore = false;
    /** 设备评定*/
    public static final int ITEM_TYPE_SBPD = 4;
    /** 附属电器*/
    public static final int ITEM_TYPE_FSDQ = 5;
    public static int itemType = ITEM_TYPE_SBPD;
    List<EquipmentEvaluatePlanBean> mList = new ArrayList<EquipmentEvaluatePlanBean>();
    CheckItemsAdapter adapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerEquipmentEvaluateCheckItemComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .equipmentEvaluateCheckItemModule(new EquipmentEvaluateCheckItemModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_equipment_evaluate_check_item; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTranslucentStatus();
        setSupportActionBar(menuTp);
        menuTp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (null != equipmentEvaluateBean) {
            if (null!=equipmentEvaluateBean.getEquipmentName()) {
                tvEquipName.setText(equipmentEvaluateBean.getEquipmentName());
            } else {
                tvEquipName.setText("");
            }
            if (null!=equipmentEvaluateBean.getEquipmentCode()) {
                tvEquipCode.setText(equipmentEvaluateBean.getEquipmentCode());
            } else {
                tvEquipCode.setText("");
            }
            if (null!=equipmentEvaluateBean.getUsePlace()) {
                tvUsePlace.setText(equipmentEvaluateBean.getUsePlace());
            } else {
                tvUsePlace.setText("");
            }
            if (null!=equipmentEvaluateBean.getTemplateName()) {
                tvTemplate.setText(equipmentEvaluateBean.getTemplateName() + "【" + equipmentEvaluateBean.getTemplateNo() + "】");
            } else {
                tvTemplate.setText("");
            }
        }
        ArmsUtils.configRecycleView(rlCheckItems, new LinearLayoutManager(this));
        adapter = new CheckItemsAdapter(this, mList);
        rlCheckItems.setAdapter(adapter);
        loadData();
        setSrRefresh();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        itemType = ITEM_TYPE_SBPD;
                        isLoadMore = false;
                        start = 0;
                        loadData();
                        break;
                    case 1:
                        itemType = ITEM_TYPE_FSDQ;
                        isLoadMore = false;
                        start = 0;
                        loadData();
                        break;
                    default:
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //onTabSelected(tab);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        adapter.setOnItemClickListener(new DefaultAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int viewType, Object data, int position) {
                Intent intent = new Intent(EquipmentEvaluateCheckItemActivity.this, EquipmentEvaluateDetailEditActivity.class);
                intent.putExtra("evaluatePlan", mList.get(position));
                ArmsUtils.startActivity(intent);
            }
        });
    }

    /**
     * <li>说明：联网获取数据
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月14日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    private void loadData() {
        showLoading();
        Map<String, Object> map = new HashMap<>();
        map.put("planIdx", equipmentEvaluateBean.getAppraisePlanIdx());
        map.put("itemType", itemType);
        if (null != mPresenter)
            mPresenter.getEvaluatePlan(start, limit, JSON.toJSONString(map), isLoadMore);

    }

    /**
     * <li>说明：下拉刷新上拉加载更多
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月14日
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
    public void showLoading() {
        ProgressDialogUtils.showProgressDialog(this,"正在加载，请稍等.....");

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
        intentFilter.addAction("action.refreshCheckItemsList");
        registerReceiver(mRefreshBroadcastReceiver, intentFilter);
    }
    // 接收广播刷新列表
    private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("action.refreshCheckItemsList")) {
                loadData();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mRefreshBroadcastReceiver);
    }

    /**
     * <li>说明：加载列表数据
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月14日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    @Override
    public void LoadData(List<EquipmentEvaluatePlanBean> list) {
        srRefresh.finishRefresh();
        mList.clear();
        if (list.size()>0) {
            mList.addAll(list);
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

    /**
     * <li>说明：加载更多
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月14日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    @Override
    public void LoadMoreData(List<EquipmentEvaluatePlanBean> list) {
        srRefresh.finishLoadMore();
        if (list.size()>0) {
            mList.addAll(list);
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

}
