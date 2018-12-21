package sbgl3.yunda.module.fwhjhx.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
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
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.speedata.utils.ProgressDialogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sbgl3.yunda.R;
import sbgl3.yunda.module.fwhjhx.di.component.DaggerFwhRepaiScopsComponent;
import sbgl3.yunda.module.fwhjhx.di.module.FwhRepaiScopsModule;
import sbgl3.yunda.module.fwhjhx.mvp.contract.FwhRepaiScopsContract;
import sbgl3.yunda.module.fwhjhx.mvp.entity.RepairScopeCase;
import sbgl3.yunda.module.fwhjhx.mvp.entity.RepairTaskList;
import sbgl3.yunda.module.fwhjhx.mvp.entity.RepairWorkOrder;
import sbgl3.yunda.module.fwhjhx.mvp.presenter.FwhRepaiScopsPresenter;
import sbgl3.yunda.module.fwhjhx.mvp.ui.adapter.ScopeWorkOrderAdapter;

import static com.jess.arms.utils.Preconditions.checkNotNull;
/**
 * 计划检修-检修作业范围
 * @author 周雪巍
 * @time 2018/09/12
 * */

public class FwhRepaiScopsActivity extends BaseActivity<FwhRepaiScopsPresenter> implements FwhRepaiScopsContract.View,ScopeWorkOrderAdapter.MyOnItemclickListner {

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
    @BindView(R.id.rlRecords)
    ExpandableListView rlRecords;
    @BindView(R.id.srRefresh)
    SmartRefreshLayout srRefresh;
    @BindView(R.id.cbCheckAll)
    CheckBox cbCheckAll;
    @BindView(R.id.tvConfirm)
    TextView tvConfirm;
    @BindView(R.id.cvConfirm)
    CardView cvConfirm;
    List<RepairScopeCase> parrents = new ArrayList<>();
    ScopeWorkOrderAdapter adapter;
    String idx;
    int expandPosition;
    int collapsePosition;
    RepairTaskList task;
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerFwhRepaiScopsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .fwhRepaiScopsModule(new FwhRepaiScopsModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_fwh_repai_scops; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        task = (RepairTaskList)getIntent().getSerializableExtra("idx");
        if(task!=null){
            idx = task.getIdx();
            tvEquipName.setText(task.getEquipmentName());
            tvEquipCode.setText(task.getEquipmentCode());
            if(task.getUsePlace()!=null){
                tvUsePlace.setText(task.getUsePlace());
            }
            String str = "";
            if(task.getSpecification()!=null){
                str = str+task.getSpecification();
            }
            if(task.getModel()!=null){
                if(task.getSpecification()!=null){
                    str = str+"/"+task.getModel();
                }else {
                    str = task.getModel();
                }
            }
            tvEquipModel.setText(str);
            if(mPresenter!=null){
                mPresenter.UpdataTime(JSON.toJSONString(new String[]{task.getIdx()}));
            }
        }


        setTranslucentStatus();
        setSupportActionBar(menuTp);
        menuTp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adapter = new ScopeWorkOrderAdapter(this,parrents);
        rlRecords.setAdapter(adapter);
        rlRecords.setGroupIndicator(null);
        rlRecords.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                expandPosition = groupPosition;
                if(parrents.get(groupPosition).getWorkOrders()==null||parrents.get(groupPosition).getWorkOrders().size()==0){
                    Map<String,Object> map = new HashMap<>();
                    map.put("scopeCaseIdx",parrents.get(groupPosition).getIdx());
                    if(mPresenter!=null){
                        showLoading();
                        mPresenter.getWorkOrders(JSON.toJSONString(map));
                    }
                }

            }
        });
        adapter.SetMyClickListner(this);
        srRefresh.setRefreshHeader(new ClassicsHeader(this));
        srRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                loaddata();
            }
        });
        loaddata();
        cbCheckAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(parrents!=null&&parrents.size()>0){
                    if(isChecked){
                        for(RepairScopeCase scop: parrents){
                            scop.setChecked(RepairScopeCase.CHECKED);
                            if(scop.getWorkOrders()!=null&&scop.getWorkOrders().size()>0){
                                for(RepairWorkOrder order:scop.getWorkOrders()){
                                    order.setChecked(RepairWorkOrder.CHECKED);
                                }
                            }
                        }
                    }else {
                        for(RepairScopeCase scop: parrents){
                            scop.setChecked(RepairScopeCase.UNCHECKED);
                            if(scop.getWorkOrders()!=null&&scop.getWorkOrders().size()>0){
                                for(RepairWorkOrder order:scop.getWorkOrders()){
                                    order.setChecked(RepairWorkOrder.UNCHECKED);
                                }
                            }
                        }
                    }
                    adapter.notifyDataSetChanged();
                }

            }
        });
    }

    //获取检修范围数据
    private void loaddata() {
        if(parrents!=null&&parrents.size()>0){
            for(int i = 0;i<parrents.size();i++){
                if(rlRecords.isGroupExpanded(i)){
                    rlRecords.collapseGroup(i);
                }
            }
        }
        showLoading();
        Map<String,Object> map = new HashMap<>();
        map.put("taskListIdx",idx);
        if(mPresenter!=null){
            mPresenter.getRepairScops(JSON.toJSONString(map));
        }

    }

    @Override
    public void showLoading() {
        ProgressDialogUtils.showProgressDialog(this,"加载中，请稍后...");
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
    public void OnLoadRepairScopeSuccess(List<RepairScopeCase> list) {
        srRefresh.finishRefresh();
        parrents.clear();
        if(list!=null){
            parrents.addAll(list);
            if(cbCheckAll.isChecked()){
                for(RepairScopeCase repairScopeCase:parrents){
                    repairScopeCase.setChecked(RepairScopeCase.CHECKED);
                }
            }else {
                for(RepairScopeCase repairScopeCase:parrents){
                    repairScopeCase.setChecked(RepairScopeCase.UNCHECKED);
                }
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void OnLoadOrderSuccess(List<RepairWorkOrder> list) {
        if(list!=null&&list.size()>0){
            if(parrents.get(expandPosition).getWorkOrders()==null){
                List<RepairWorkOrder> orders = new ArrayList<>();
                orders.addAll(list);
                parrents.get(expandPosition).setWorkOrders(orders);
            }else {
                parrents.get(expandPosition).getWorkOrders().clear();
                parrents.get(expandPosition).getWorkOrders().addAll(list);
            }
            if(parrents.get(expandPosition).getChecked()==RepairScopeCase.CHECKED){
                for(RepairWorkOrder order:parrents.get(expandPosition).getWorkOrders()){
                    order.setChecked(RepairWorkOrder.CHECKED);
                }
            }else {
                for(RepairWorkOrder order:parrents.get(expandPosition).getWorkOrders()){
                    order.setChecked(RepairWorkOrder.UNCHECKED);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void ConfirmAllSuccess() {
        loaddata();
    }

    @Override
    public void ConfirmSuccess() {
        loaddata();
    }

    //提交
    @OnClick(R.id.tvConfirm)
    void ConfirmAll(){
        Map<String,List<RepairWorkOrder>> map = new HashMap<>();
        if(parrents!=null&&parrents.size()>0){
            for(RepairScopeCase scopeCase: parrents){
                List<RepairWorkOrder> tempList = new ArrayList<>();
                if(scopeCase.getChecked()==RepairScopeCase.CHECKED&& scopeCase.getWclCount() != null && scopeCase.getWclCount() > 0){
                    if(scopeCase.getWorkOrders()!=null){
                        for(RepairWorkOrder order:scopeCase.getWorkOrders()){
                            if(order.getChecked()==RepairWorkOrder.CHECKED){
                                tempList.add(order);
                            }
                        }
                        if(tempList.size()>0){
                            map.put(scopeCase.getIdx(),tempList);
                        }
                    }else {
                        map.put(scopeCase.getIdx(),tempList);
                    }

                }
            }
        }
        if(map.size()>0){
            showLoading();
            mPresenter.ConfirmAll(JSON.toJSONString(map));
        }else {
            ToastUtils.showShort("还未勾选需要提交的工单");
        }
    }

    @Override
    public void OnParentClick(int parentPosition, boolean isExpand) {
        if(isExpand){
            rlRecords.collapseGroup(parentPosition);
        }else {
            rlRecords.expandGroup(parentPosition);
        }
    }

    @Override
    public void OnChildClick(int parentPosition, int ChildPosision) {
        if(parrents.get(parentPosition).getWorkOrders().get(ChildPosision).getChecked()==RepairWorkOrder.CHECKED){
            parrents.get(parentPosition).getWorkOrders().get(ChildPosision).setChecked(RepairWorkOrder.UNCHECKED);
            int temp = 0;
            for(int i = 0;i<parrents.get(parentPosition).getWorkOrders().size();i++){
                if(parrents.get(parentPosition).getWorkOrders().get(ChildPosision).getChecked()==RepairWorkOrder.CHECKED){
                    temp++;
                }
            }
            if(temp==0){
                parrents.get(parentPosition).setChecked(RepairScopeCase.UNCHECKED);
            }
        }else {
            parrents.get(parentPosition).setChecked(RepairScopeCase.CHECKED);
            parrents.get(parentPosition).getWorkOrders().get(ChildPosision).setChecked(RepairWorkOrder.CHECKED);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void OnConfirmClick(int parentPosition, int ChidPosision) {
        showLoading();
        if(mPresenter!=null){
            mPresenter.Confirm(parrents.get(parentPosition).getWorkOrders().get(ChidPosision).getIdx());
        }
    }

    @Override
    public void OnManageClick(int parentPosition, int ChidPosision) {
        RepairWorkOrder order = parrents.get(parentPosition).getWorkOrders().get(ChidPosision);
        Intent intent = new Intent(FwhRepaiScopsActivity.this,ManageWorkorderActivity.class);
        if(order!=null){
            intent.putExtra("workorder",order);
        }
        ArmsUtils.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showLoading();
        loaddata();
    }
}
