package sbgl3.yunda.module.sbxj.mvp.ui.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.utilcode.util.StringUtils;
import com.jess.arms.utils.utilcode.util.ToastUtils;
import com.loopj.android.http.RequestParams;
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
import sbgl3.yunda.module.sbxj.di.component.DaggerSBXJRecordComponent;
import sbgl3.yunda.module.sbxj.di.module.SBXJRecordModule;
import sbgl3.yunda.module.sbxj.entry.InspectPlanEquipmentBean;
import sbgl3.yunda.module.sbxj.entry.InspectRecord;
import sbgl3.yunda.module.sbxj.mvp.contract.SBXJRecordContract;
import sbgl3.yunda.module.sbxj.mvp.presenter.SBXJRecordPresenter;
import sbgl3.yunda.module.sbxj.mvp.ui.adapter.InspectPlanEquipAdapter;
import sbgl3.yunda.module.sbxj.mvp.ui.adapter.InspectRecordAdapter;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class SBXJRecordActivity extends BaseActivity<SBXJRecordPresenter> implements SBXJRecordContract.View {

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
    RecyclerView rlRecords;
    @BindView(R.id.srRefresh)
    SmartRefreshLayout srRefresh;
    @BindView(R.id.cbCheckAll)
    CheckBox cbCheckAll;
    @BindView(R.id.tvConfirm)
    TextView tvConfirm;
    @BindView(R.id.cvConfirm)
    CardView cvConfirm;
    InspectPlanEquipmentBean inspectPlanEquipmentBean;
    ArrayList<InspectRecord> mList = new ArrayList<>();
    InspectRecordAdapter adapter;
    AlertDialog confirmDialog;
    int hasCommit = 0;
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSBXJRecordComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .sBXJRecordModule(new SBXJRecordModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_sbxjrecord; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
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
        ArmsUtils.configRecycleView(rlRecords, new LinearLayoutManager(this));
        adapter = new InspectRecordAdapter(mList);
        rlRecords.setAdapter(adapter);
        setRefresh();
        if( getIntent()!=null){
            inspectPlanEquipmentBean = (InspectPlanEquipmentBean) getIntent().getSerializableExtra("Equip");
        }
        if(inspectPlanEquipmentBean!=null){
            if(inspectPlanEquipmentBean.getRealBeginTime()==null){
                if(mPresenter!=null){
                    mPresenter.UpLoadRecordTime(JSON.toJSONString(new String[]{inspectPlanEquipmentBean.getIdx()}));
                }
            }
            if("已巡检".equals(inspectPlanEquipmentBean.getCheckResult())){
                cvConfirm.setVisibility(View.GONE);
            }else {
                cvConfirm.setVisibility(View.VISIBLE);
            }
            if(!StringUtils.isTrimEmpty(inspectPlanEquipmentBean.getEquipmentCode())){
                tvEquipCode.setText(inspectPlanEquipmentBean.getEquipmentCode());
            }else {
                tvEquipCode.setText("");
            }
            if(!StringUtils.isTrimEmpty(inspectPlanEquipmentBean.getEquipmentName())){
                tvEquipName.setText(inspectPlanEquipmentBean.getEquipmentName());
            }else {
                tvEquipName.setText("");
            }
            if(!StringUtils.isTrimEmpty(inspectPlanEquipmentBean.getUsePlace())){
                tvUsePlace.setText(inspectPlanEquipmentBean.getUsePlace());
            }else {
                tvUsePlace.setText("");
            }
            if(!StringUtils.isTrimEmpty(inspectPlanEquipmentBean.getSpecification())){
                if(!StringUtils.isTrimEmpty(inspectPlanEquipmentBean.getModel())){
                    tvEquipModel.setText(inspectPlanEquipmentBean.getSpecification()+"/"+inspectPlanEquipmentBean.getModel());
                }else {
                    tvEquipModel.setText(inspectPlanEquipmentBean.getSpecification());
                }
            }else {
                if(!StringUtils.isTrimEmpty(inspectPlanEquipmentBean.getModel())){
                    tvEquipModel.setText(inspectPlanEquipmentBean.getModel());
                }else {
                    tvEquipModel.setText("");
                }
            }
            adapter.setOnItemClickListener(new DefaultAdapter.OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, int viewType, Object data, int position) {
                    Intent intent = new Intent(SBXJRecordActivity.this,TakephotoActivity.class);
                    intent.putExtra("inspectRecordArrayList",mList);
                    intent.putExtra("position",position);
                    ArmsUtils.startActivity(intent);
                }
            });
            showLoading();
            loadData();
            setConfirmDialog();
            cbCheckAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        for(InspectRecord record:mList){
                            if(record.getChecked()!=null){
                                record.setChecked(0);
                            }
                        }
                    }else {
                        for(InspectRecord record:mList){
                            if(record.getChecked()!=null){
                                record.setChecked(1);
                            }
                        }
                    }
                    if(adapter!=null)
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }

    TextView tvTitle;
    EditText etInfo;
    TextView tvCancle;
    TextView tvconfirm;
    private void setConfirmDialog() {
        if(confirmDialog == null){
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_confirm_insepect,null);
            tvTitle = (TextView)view.findViewById(R.id.tvTitle);
            etInfo = (EditText) view.findViewById(R.id.etInfo);
            tvCancle = (TextView)view.findViewById(R.id.tvCancle);
            tvconfirm = (TextView)view.findViewById(R.id.tvconfirm);
            confirmDialog = new AlertDialog.Builder(this).setView(view).create();
            tvCancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    confirmDialog.dismiss();
                }
            });
            tvconfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(etInfo.getText()==null||StringUtils.isTrimEmpty(etInfo.getText().toString())){
                        ToastUtils.showShort("还未输入巡检情况");
                        return;
                    }
                    Map<String,Object> map = new HashMap<>();
                    map.put("planEquipmentIdx",inspectPlanEquipmentBean.getIdx());
                    map.put("checkResultDesc",etInfo.getText().toString());
                    if(mPresenter!=null){
                        mPresenter.ConfirmEquipPlan(JSON.toJSONString(map));
                    }
                    confirmDialog.dismiss();
                }
            });
        }
    }

    private void setRefresh() {
        srRefresh.setRefreshHeader(new ClassicsHeader(this));
        srRefresh.setRefreshFooter(new ClassicsFooter(this));
        srRefresh.setNoMoreData(true);
        srRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                loadData();
            }
        });
    }

    private void loadData() {
        InspectRecord rsc = new InspectRecord();
        rsc.setPlanEquipmentIdx(inspectPlanEquipmentBean.getIdx());
        rsc.setChecked(null);
        if(mPresenter!=null){
            mPresenter.getInspectRecords(0,100,JSON.toJSONString(rsc),false);
        }
    }

    @Override
    public void showLoading() {
        ProgressDialogUtils.showProgressDialog(this,"正在加载中，请稍后...");
    }

    @Override
    public void hideLoading() {
        ProgressDialogUtils.dismissProgressDialog();
    }


    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ToastUtils.showShort( message);
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
    List<String> ids = new ArrayList<>();
    @OnClick(R.id.tvConfirm)
    void Confirm(){
        // 组装批量上传的设备巡检记录主键数组
        ids.clear();
        // 此变量用于统计已经提交过的巡检项
        hasCommit = 0;
        for (InspectRecord r : mList) {
            if (!InspectRecord.CHECK_RESULT_HG.equals(r.getCheckResult()) && InspectRecord.CHECKED == r.getChecked()) {
                ids.add(r.getIdx());
            } else if (InspectRecord.CHECK_RESULT_HG.equals(r.getCheckResult())) {
                hasCommit++;
            }
        }

        // 若巡检项已全部提交，直接弹出提交设备巡检结果的窗口
        if (mList.size() > 0 && hasCommit == mList.size()) {
            confirmDialog.show();
            return;
        }

        if (mList.size() > 0 && ids.size() <= 0) {
            showMessage("请先勾选巡检项目！");
            return;
        }
        if(mPresenter!=null){
            mPresenter.ConfirmRecords(JSON.toJSONString(ids),InspectRecord.CHECK_RESULT_HG);
        }
    }

    @Override
    public void LoadData(List<InspectRecord> list) {
        hideLoading();
        mList.clear();
        if(list!=null&&list.size()>0){
            mList.addAll(list);
        }
        srRefresh.finishRefresh();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void LoadMoreData(List<InspectRecord> list) {

    }

    @Override
    public void SubmitRecordSuccess() {
// 如果巡检项没有完全提交，则不提交巡检结果描述
        if (hasCommit + ids.size() < mList.size()) {
            loadData();
        } else {
            confirmDialog.show();
        }
    }

    @Override
    public void SubmitPlanSuccess() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
}
