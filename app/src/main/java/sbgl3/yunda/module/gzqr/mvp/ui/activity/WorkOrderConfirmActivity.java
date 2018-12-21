package sbgl3.yunda.module.gzqr.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import sbgl3.yunda.SysInfo;
import sbgl3.yunda.module.gzqr.di.component.DaggerWorkOrderConfirmComponent;
import sbgl3.yunda.module.gzqr.di.module.WorkOrderConfirmModule;
import sbgl3.yunda.module.gzqr.entry.PlanRepairEquipListBean;
import sbgl3.yunda.module.gzqr.entry.RepairScopeCase;
import sbgl3.yunda.module.gzqr.entry.RepairWorkOrder;
import sbgl3.yunda.module.gzqr.mvp.contract.WorkOrderConfirmContract;
import sbgl3.yunda.module.gzqr.mvp.presenter.WorkOrderConfirmPresenter;
import sbgl3.yunda.module.gzqr.mvp.ui.adapter.ScopeWorkOrderAdapter;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * <li>标题: 设备管理系统
 * <li>说明: 维修工长工单确认界面
 * <li>创建人：刘欢
 * <li>创建日期：2018年9月27日
 * <li>修改人:
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 *
 * @author 系统集成事业部设备管理系统项目组
 * @version 3.0
 */
public class WorkOrderConfirmActivity extends BaseActivity<WorkOrderConfirmPresenter> implements WorkOrderConfirmContract.View {

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
    @BindView(R.id.rlCheckItems)
    ExpandableListView rlCheckItems;
    @BindView(R.id.srRefresh)
    SmartRefreshLayout srRefresh;
    @BindView(R.id.llNoData)
    LinearLayout llNoData;
    @BindView(R.id.tvRepairHear)
    TextView tvRepairHear;
    @BindView(R.id.tvConfirm)
    TextView tvConfirm;
    @BindView(R.id.cvConfirm)
    CardView cvConfirm;
    List<PlanRepairEquipListBean> mPlanRepairEquipList = new ArrayList<>();
    PlanRepairEquipListBean planRepairEquipListBean;
    PlanRepairEquipListBean nextPlanRepairEquipListBean;
    List<RepairScopeCase> parents = new ArrayList<>();
    ScopeWorkOrderAdapter adapter;
    int expandPosition;
    int position;
    int currentPoistion = 0;
    /** 记录未确认的工单数量*/
    int UnconfirmNum = 0;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerWorkOrderConfirmComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .workOrderConfirmModule(new WorkOrderConfirmModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_work_order_confirm; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
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
 //       adapter.setMyClickListner(this);
        if (null != getIntent()) {
            mPlanRepairEquipList = (ArrayList<PlanRepairEquipListBean>)getIntent().getSerializableExtra("allPlanRepairList");
            UnconfirmNum = mPlanRepairEquipList.size();
            position = getIntent().getIntExtra("position", 0);
            planRepairEquipListBean = mPlanRepairEquipList.get(position);
            if (null!=planRepairEquipListBean){
                setData(planRepairEquipListBean);
            }
        }
        loadRepairScopes();
        setSrRefresh();
    }

    /**
     * <li>说明：联网获取检修范围
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月27日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    private void loadRepairScopes() {
        showLoading();
        RepairScopeCase scopeCase = new RepairScopeCase();
        scopeCase.setTaskListIdx(planRepairEquipListBean.getIdx());
        if (null != mPresenter) {
            mPresenter.getRepairScopes(JSON.toJSONString(scopeCase), SysInfo.orgid);
        }
    }

    /**
     * <li>说明：联网获取作业工单
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月27日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    private void loadWorkOrders() {
        showLoading();
        RepairWorkOrder workOrder = new RepairWorkOrder();
        workOrder.setScopeCaseIdx(parents.get(expandPosition).getIdx());
        if (mPresenter != null) {
            mPresenter.getWorkOrders(JSON.toJSONString(workOrder));
        }
    }

    /**
     * <li>说明：下拉刷新、上拉加载更多
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月27日
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
                loadRepairScopes();
            }
        });
    }
    @Override
    public void showLoading() {
        ProgressDialogUtils.showProgressDialog(this,"正在加载中，请稍等...");
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

    /**
     * <li>说明：界面数据显示
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月27日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    private void setData (PlanRepairEquipListBean bean){
        if (null!=bean){
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

            tvRepairHear.setText(SysInfo.empname);
        }
    }

    /**
     * <li>说明：加载检修范围
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月27日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    @Override
    public void loadRepairScope(List<RepairScopeCase> list) {
        parents.clear();
        srRefresh.finishRefresh();
        if (null!=list&&list.size()>0){
            loadSuccess();
            parents.addAll(list);
            adapter.notifyDataSetChanged();
        } else {
            loadFail();
        }
        if (list.size() < 200) {
            srRefresh.setNoMoreData(true);
        } else {
            srRefresh.setNoMoreData(false);
        }
    }

    /**
     * <li>说明：加载作业工单
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月27日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    @Override
    public void loadWorkOrder(List<RepairWorkOrder> list) {
        if (list != null && list.size() > 0) {
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
            finish();
        }
    }

    /**
     * <li>说明：获取下一条待确认数据
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月27日
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
        if (null != mPresenter) {
            mPresenter.confirmSave(JSON.toJSONString(new String[]{planRepairEquipListBean.getIdx()}));
            //refreshEquipmentList();
            //finish();
        }
    }

    public void loadFail() {
        srRefresh.setVisibility(View.GONE);
        llNoData.setVisibility(View.VISIBLE);
    }

    public void loadSuccess() {
        srRefresh.setVisibility(View.VISIBLE);
        llNoData.setVisibility(View.GONE);
    }
}
