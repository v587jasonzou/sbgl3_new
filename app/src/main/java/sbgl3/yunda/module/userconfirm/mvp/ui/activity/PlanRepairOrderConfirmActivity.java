package sbgl3.yunda.module.userconfirm.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.speedata.utils.ProgressDialogUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import sbgl3.yunda.R;
import sbgl3.yunda.module.userconfirm.di.component.DaggerPlanRepairOrderConfirmComponent;
import sbgl3.yunda.module.userconfirm.di.module.PlanRepairOrderConfirmModule;
import sbgl3.yunda.module.userconfirm.entry.FaultOrderBean;
import sbgl3.yunda.module.userconfirm.entry.PlanRepairEquipListBean;
import sbgl3.yunda.module.userconfirm.entry.RepairScopeCase;
import sbgl3.yunda.module.userconfirm.entry.RepairWorkOrder;
import sbgl3.yunda.module.userconfirm.mvp.contract.PlanRepairOrderConfirmContract;
import sbgl3.yunda.module.userconfirm.mvp.presenter.PlanRepairOrderConfirmPresenter;
import sbgl3.yunda.module.userconfirm.mvp.ui.adapter.ScopeWorkOrderAdapter;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * <li>标题: 设备管理系统
 * <li>说明: 使用人确认---计划修工单确认界面
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
public class PlanRepairOrderConfirmActivity extends BaseActivity<PlanRepairOrderConfirmPresenter> implements PlanRepairOrderConfirmContract.View {


    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.tvEquipCode)
    TextView tvEquipCode;
    @BindView(R.id.tvEquipName)
    TextView tvEquipName;
    @BindView(R.id.tvUsePlace)
    TextView tvUsePlace;
    @BindView(R.id.tvEquipModel)
    TextView tvEquipModel;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.rlCheckItems)
    ExpandableListView rlCheckItems;
    @BindView(R.id.srRefresh)
    SmartRefreshLayout srRefresh;
    @BindView(R.id.llNoData)
    LinearLayout llNoData;
    @BindView(R.id.tvUsePerson)
    TextView tvUsePerson;
    @BindView(R.id.tvConfirm)
    TextView tvConfirm;
    @BindView(R.id.cvConfirm)
    CardView cvConfirm;
    List<PlanRepairEquipListBean> mPlanRepairEquipList = new ArrayList<>();
    PlanRepairEquipListBean planRepairEquipListBean;
    PlanRepairEquipListBean nextPlanRepairEquipListBean;
    List<RepairScopeCase> parents = new ArrayList<>();
    List<String> ids = new ArrayList<>();
    ScopeWorkOrderAdapter adapter;
    int expandPosition;
    int position;
    int start = 0;
    int limit = 200;
    /** 检修类型 1、机械，2、电气*/
    int repairType = 1;
    int currentPoistion = 0;
    /** 记录未确认工单数量*/
    int UnconfirmNum=0;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPlanRepairOrderConfirmComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .planRepairOrderConfirmModule(new PlanRepairOrderConfirmModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_plan_repair_order_confirm; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
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
        adapter = new ScopeWorkOrderAdapter(this, parents);
        rlCheckItems.setAdapter(adapter);
        rlCheckItems.setGroupIndicator(null);
        rlCheckItems.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                expandPosition = groupPosition;
                if (parents.get(groupPosition).getWorkOrders() == null || parents.get(groupPosition).getWorkOrders().size() == 0) {
                    loadWorkOrders();
                }
            }
        });
        adapter.setMyClickListner(new ScopeWorkOrderAdapter.MyOnItemclickListner() {
            @Override
            public void OnParentClick(int parentPosition, boolean isExpand) {
                if (isExpand) {
                    rlCheckItems.collapseGroup(parentPosition);
                } else {
                    rlCheckItems.expandGroup(parentPosition);
                }
            }

        });
        if (getIntent() != null) {
            mPlanRepairEquipList = (ArrayList<PlanRepairEquipListBean>) getIntent().getSerializableExtra("allPlanRepairList");
            UnconfirmNum = mPlanRepairEquipList.size();
            position = getIntent().getIntExtra("position", 0);
            planRepairEquipListBean = mPlanRepairEquipList.get(position);
            setData(planRepairEquipListBean);
        }
        loadRepairScopes();
        srRefresh.setRefreshHeader(new ClassicsHeader(this));
        srRefresh.setRefreshFooter(new ClassicsFooter(this));
        srRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                loadRepairScopes();
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        repairType = 1;
                        loadRepairScopes();
                        break;
                    case 1:
                        repairType = 2;
                        loadRepairScopes();
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
    }

    /**
     * <li>说明：联网获取检修范围
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月20日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    private void loadRepairScopes() {
        showLoading();
        RepairScopeCase scopeCase = new RepairScopeCase();
        scopeCase.setTaskListIdx(planRepairEquipListBean.getIdx());
        scopeCase.setRepairType(repairType);
        if (null != mPresenter) {
            mPresenter.getRepairScopes(start, limit, JSON.toJSONString(scopeCase));
        }
    }

    /**
     * <li>说明：联网获取作业工单
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月20日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    private void loadWorkOrders() {
        showLoading();
        RepairWorkOrder workOrder = new RepairWorkOrder();
        workOrder.setScopeCaseIdx(parents.get(expandPosition).getIdx());
        if (mPresenter != null) {
            mPresenter.getWorkOrders(start, limit, JSON.toJSONString(workOrder));
        }
    }

    @Override
    public void showLoading() {
        ProgressDialogUtils.showProgressDialog(this, "正在加载中，请稍后....");
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
    }

    /**
     * <li>说明：加载检修范围
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月25日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    @Override
    public void loadRepairScope(List<RepairScopeCase> list) {
        srRefresh.finishRefresh();
        parents.clear();
        if (list.size() > 0) {
            loadSuccess();
            parents.addAll(list);
            adapter.notifyDataSetChanged();
        } else {
            loadFail();
        }
        if (list.size() < limit) {
            srRefresh.setNoMoreData(true);
        } else {
            srRefresh.setNoMoreData(false);
        }
    }

    /**
     * <li>说明：加载作业工单
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月20日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    @Override
    public void loadWorkOrder(List<RepairWorkOrder> list) {
        if (list.size() > 0) {
            if (parents.get(expandPosition).getWorkOrders() == null) {
                List<RepairWorkOrder> orders = new ArrayList<>();
                orders.addAll(list);
                parents.get(expandPosition).setWorkOrders(orders);
            } else {
                parents.get(expandPosition).getWorkOrders().clear();
                parents.get(expandPosition).getWorkOrders().addAll(list);
            }

        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getNextDataSuccess(PlanRepairEquipListBean bean) {
        if (null!=bean){
            planRepairEquipListBean = bean;
            setData(planRepairEquipListBean);
            loadRepairScopes();
        } else {
            ToastUtils.showShort("所有工单已处理完成！");
            refreshEquipmentList();
            finish();
        }
    }

    /**
     * <li>说明：获取下一条待确认数据
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月25日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    @Override
    public PlanRepairEquipListBean loadNextData() {
        planRepairEquipListBean.setConfirm(true);
        UnconfirmNum--;
        for (int i = currentPoistion; i < mPlanRepairEquipList.size(); i++){
            if (!mPlanRepairEquipList.get(i).isConfirm()){
                nextPlanRepairEquipListBean = mPlanRepairEquipList.get(i);
                break;
            } else {
                continue;
            }
        }
        currentPoistion++;
        if (UnconfirmNum ==0){
            return null;
        } else {
            return nextPlanRepairEquipListBean;
        }
    }

    /**
     * <li>说明：确认
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月21日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    @OnClick(R.id.tvConfirm)
    void confirm() {
        ids.clear();
        ids.add(planRepairEquipListBean.getIdx());
        if (null != mPresenter) {
            mPresenter.planRepairConfirm(JSON.toJSONString(ids));
            refreshEquipmentList();
            //finish();
        }
    }

    /**
     * <li>说明：刷新列表数据
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月12日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    private void refreshEquipmentList() {
        Intent intent = new Intent();
        intent.setAction("action.refreshFaultEuipmentList");
        sendBroadcast(intent);
    }

    public void loadFail() {
        srRefresh.setVisibility(View.GONE);
        llNoData.setVisibility(View.VISIBLE);
    }

    public void loadSuccess() {
        srRefresh.setVisibility(View.VISIBLE);
        llNoData.setVisibility(View.GONE);
    }

    /**
     * <li>说明：界面数据显示
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月21日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    private void setData(PlanRepairEquipListBean bean) {
        if (null != bean.getEquipmentCode()) {
            tvEquipCode.setText(bean.getEquipmentCode());
        } else {
            tvEquipCode.setText("");
        }
        if (null != bean.getEquipmentName()) {
            tvEquipName.setText(bean.getEquipmentName());
        } else {
            tvEquipName.setText("");
        }
        if (null != bean.getUsePlace()) {
            tvUsePlace.setText(bean.getUsePlace());
        } else {
            tvUsePlace.setText("");
        }
        if (null != bean.getSpecification()) {
            if (null != bean.getModel()) {
                tvEquipModel.setText(bean.getSpecification() + "/" + bean.getModel());
            } else {
                tvEquipModel.setText(bean.getSpecification().toString());
            }
        } else {
            if (null != bean.getModel()) {
                tvEquipModel.setText(bean.getModel().toString());
            } else {
                tvEquipModel.setText("");
            }
        }
        if (null != bean.getUsePerson()) {
            tvUsePerson.setText(bean.getUsePerson());
        } else {
            tvUsePerson.setText("");
        }
    }
}
