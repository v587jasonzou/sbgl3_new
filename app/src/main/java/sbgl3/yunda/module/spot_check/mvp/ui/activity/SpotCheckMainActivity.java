package sbgl3.yunda.module.spot_check.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.utilcode.util.StringUtils;
import com.jess.arms.utils.utilcode.util.ToastUtils;
import com.speedata.utils.ProgressDialogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sbgl3.yunda.R;
import sbgl3.yunda.entry.EpcDataBase;
import sbgl3.yunda.entry.ItemClickMessage;
import sbgl3.yunda.module.gztp.entry.FaultOrder;
import sbgl3.yunda.module.gztp.mvp.ui.activity.FaultTicketEditActivity;
import sbgl3.yunda.module.sbxj.mvp.ui.activity.SBXJDevActivity;
import sbgl3.yunda.module.sbxj.mvp.ui.adapter.InspectPlanEquipAdapter;
import sbgl3.yunda.module.spot_check.di.component.DaggerSpotCheckMainComponent;
import sbgl3.yunda.module.spot_check.di.module.SpotCheckMainModule;
import sbgl3.yunda.module.spot_check.entity.PointCheck;
import sbgl3.yunda.module.spot_check.entity.PointCheckContent;
import sbgl3.yunda.module.spot_check.mvp.contract.SpotCheckMainContract;
import sbgl3.yunda.module.spot_check.mvp.presenter.SpotCheckMainPresenter;
import sbgl3.yunda.module.spot_check.mvp.ui.adapter.SpotCheckAdapter;
import sbgl3.yunda.widget.BaseDialog;
import sbgl3.yunda.widget.MyLinearLayoutManager;
import sbgl3.yunda.widget.zxing.android.CaptureActivity;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class SpotCheckMainActivity extends BaseActivity<SpotCheckMainPresenter> implements SpotCheckMainContract.View {

    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.etEquipCode)
    EditText etEquipCode;
    @BindView(R.id.ivEditDelete)
    ImageView ivEditDelete;
    @BindView(R.id.tvNotFound)
    TextView tvNotFound;
    @BindView(R.id.ivSearch)
    ImageView ivSearch;
    @BindView(R.id.ivScan)
    ImageView ivScan;
    @BindView(R.id.tvEquipCode)
    TextView tvEquipCode;
    @BindView(R.id.tvEquipName)
    TextView tvEquipName;
    @BindView(R.id.tvUsePlace)
    TextView tvUsePlace;
    @BindView(R.id.llContent)
    LinearLayout llContent;
    @BindView(R.id.tvWorkUser)
    TextView tvWorkUser;
    @BindView(R.id.tvSpotStatus)
    TextView tvSpotStatus;
    @BindView(R.id.tvSpotCheckTime)
    TextView tvSpotCheckTime;
    @BindView(R.id.viewdashline)
    View viewdashline;
    @BindView(R.id.tvWorkTime)
    TextView tvWorkTime;
    @BindView(R.id.tvConfirm)
    TextView tvConfirm;
    @BindView(R.id.cvConfirm)
    CardView cvConfirm;
    public String SearchKey = "";
    @BindView(R.id.tvStartUse)
    TextView tvStartUse;
    @BindView(R.id.tvEndUse)
    TextView tvEndUse;
    @BindView(R.id.rlCheckItems)
    RecyclerView rlCheckItems;
    List<PointCheckContent> list = new ArrayList<>();
    SpotCheckAdapter adapter;
    Integer equipPosition = null;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSpotCheckMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .spotCheckMainModule(new SpotCheckMainModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_spot_check_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
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

        llContent.setVisibility(View.GONE);
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

        ArmsUtils.configRecycleView(rlCheckItems, new MyLinearLayoutManager(this));
        adapter = new SpotCheckAdapter(list);
        rlCheckItems.setAdapter(adapter);
        tvConfirm.setEnabled(false);
        tvConfirm.setBackgroundResource(R.drawable.btn_full_grey_bg);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEqiupCode(ArrayList<EpcDataBase> list) {
        if (list != null && list.size() > 0) {
            if (list.size() == 1) {
                SearchKey = list.get(0).equipmentCode;
                showLoading();
                etEquipCode.setText(list.get(0).equipmentCode);
                if (mPresenter != null)
                    mPresenter.getSpotCheck(SearchKey);
            } else {
                ToastUtils.showShort("扫描到多条标签，请重新扫描");
            }

        } else {
            ToastUtils.showShort("未扫描到标签，请重新扫描");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnItemcChecked(ItemClickMessage message) {
        if (message != null && message.getType() != null) {
            if (message.getType().equals("SpotItemCheck")) {
                int position = message.getPosition();
                equipPosition = position;
                PointCheckContent content = list.get(position);
                if (content != null) {
                    showLoading();
                    if (content.getTechnologyStateFlag() != null) {
                        if (mPresenter != null)
                            mPresenter.ChangeItemStatus(content.getIdx(), content.getTechnologyStateFlag());
                    } else {
                        if (mPresenter != null)
                            mPresenter.ChangeItemStatus(content.getIdx(), "");
                    }
                }
            }
        }
    }

    @Override
    public void showLoading() {
        ProgressDialogUtils.showProgressDialog(this, "加载中，请稍后...");
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
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (EventBus.getDefault().isRegistered(this)) {
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

    PointCheck entity;

    @Override
    public void getSpotCheckSuccess(PointCheck Entity) {
        if (Entity != null) {
            entity = Entity;
            llContent.setVisibility(View.VISIBLE);
            tvNotFound.setVisibility(View.GONE);
            if (entity.getEquipmentInfo() != null) {
                tvEquipCode.setText(entity.getEquipmentInfo().getEquipmentCode());
                tvEquipName.setText(entity.getEquipmentInfo().getEquipmentName());
                if (entity.getEquipmentInfo().getUsePlace() != null) {
                    tvUsePlace.setText(entity.getEquipmentInfo().getUsePlace());
                }
            }
            if (entity.getCheckEmp() != null) {
                tvWorkUser.setText(entity.getCheckEmp());
            }
            if (PointCheck.STATE_YCL.equals(entity.getState())) {
                tvSpotStatus.setText("已处理");
                tvSpotStatus.setTextColor(ContextCompat.getColor(this, R.color.text_color_green));
                tvSpotStatus.setBackgroundResource(R.drawable.radiobutton_empty_green_bg);
            } else {
                tvSpotStatus.setText("未处理");
                tvSpotStatus.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryLight));
                tvSpotStatus.setBackgroundResource(R.drawable.stuff_statu_nowait_bg);
            }
            if (entity.getCheckDate() != null) {
                tvSpotCheckTime.setText(entity.getCheckDate());
//                tvSpotCheckTime.setText(TimeUtils.date2String(TimeUtils.string2Date(entity.getCheckDate()),new SimpleDateFormat("yyyy/MM/dd")));
            }
            if (PointCheck.EQUIPMENT_STATE_TZ.equals(entity.getEquipmentState())) {
                tvStartUse.setBackgroundResource(R.drawable.btn_full_green_bg);
                tvStartUse.setEnabled(true);
                tvEndUse.setBackgroundResource(R.drawable.btn_full_grey_bg);
                tvEndUse.setEnabled(false);
            } else {
                tvStartUse.setBackgroundResource(R.drawable.btn_full_grey_bg);
                tvStartUse.setEnabled(false);
                tvEndUse.setBackgroundResource(R.drawable.btn_full_green_bg);
                tvEndUse.setEnabled(true);
            }
            list.clear();
            if (entity.getCheckContentList() != null && entity.getCheckContentList().size() > 0) {
                list.addAll(entity.getCheckContentList());
            }
//            ArmsUtils.setListViewHeightBasedOnChildren(rlCheckItems);
            adapter.notifyDataSetChanged();
            if (!isAllItemCheck()) {
                tvConfirm.setEnabled(false);
                tvConfirm.setBackgroundResource(R.drawable.btn_full_grey_bg);
            } else {
                tvConfirm.setEnabled(true);
                tvConfirm.setBackgroundResource(R.drawable.confirm_btn_bg);
            }
            String time = entity.getCalRunningTime() == null ? "0" : entity.getCalRunningTime().toString();
            if (entity.getRunningTime() != null) {
                time = entity.getRunningTime().toString();
            }
            tvWorkTime.setText(time);
        } else {
            tvNotFound.setVisibility(View.VISIBLE);
            ToastUtils.showShort("未查询到相关设备");
        }

    }

    @OnClick(R.id.tvStartUse)
    void Start() {
        if (mPresenter != null) {
            showLoading();
            mPresenter.StartEquip(entity.getIdx());
        }
    }

    @OnClick(R.id.tvEndUse)
    void End() {
        if (mPresenter != null) {
            showLoading();
            mPresenter.EndEquip(entity.getIdx());
        }
    }

    @Override
    public void OnItemStatusChangeSuccess() {
        ToastUtils.showShort("点检状态修改成功");
        if (!isAllItemCheck()) {
            tvConfirm.setEnabled(false);
            tvConfirm.setBackgroundResource(R.drawable.btn_full_grey_bg);
        } else {
            tvConfirm.setEnabled(true);
            tvConfirm.setBackgroundResource(R.drawable.confirm_btn_bg);
        }
        if (list.get(equipPosition).getTechnologyStateFlag() != null) {
            if (list.get(equipPosition).getTechnologyStateFlag().equals(PointCheckContent.STATE_FLAG_BL)) {
                BaseDialog.setDialog(this, "点检项状态为不良，是否发起故障提票", "确定"
                        , new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                BaseDialog.dissmiss();
                                FaultOrder order = new FaultOrder();
                                if (entity.getEquipmentInfo().getEquipmentCode() != null)
                                    order.setEquipmentCode(entity.getEquipmentInfo().getEquipmentCode());
                                if (entity.getEquipmentInfo().getEquipmentName() != null)
                                    order.setEquipmentName(entity.getEquipmentInfo().getEquipmentName());
                                if (entity.getEquipmentInfo().getIdx() != null)
                                    order.setEquipmentIdx(entity.getEquipmentInfo().getIdx());
                                if (entity.getEquipmentInfo().getSpecification() != null)
                                    order.setSpecification(entity.getEquipmentInfo().getSpecification());
                                if (entity.getEquipmentInfo().getModel() != null)
                                    order.setModel(entity.getEquipmentInfo().getModel());
                                order.setFaultOccurTime(System.currentTimeMillis());
                                order.setFaultPhenomenon("不良");
                                if (entity.getEquipmentInfo().getUsePlace() != null)
                                    order.setUsePlace(entity.getEquipmentInfo().getUsePlace());
                                if (list.get(equipPosition).getCheckContent() != null)
                                    order.setFaultPlace(list.get(equipPosition).getCheckContent());
                                Intent intent = new Intent(SpotCheckMainActivity.this, FaultTicketEditActivity.class);
                                intent.putExtra("FaultOrder", order);
                                intent.putExtra("businessEntity","com.yunda.sbgl.check.entity.PointCheckContent");
                                intent .putExtra("businessIdx",list.get(equipPosition).getIdx());
                                ArmsUtils.startActivity(intent);
                            }
                        }, "取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                BaseDialog.dissmiss();
                            }
                        });
                BaseDialog.Show();
            }
        }
    }

    @Override
    public void CofirmSpotSuccess() {
        ToastUtils.showShort("提交成功");
        tvConfirm.setEnabled(false);
        tvConfirm.setBackgroundResource(R.drawable.btn_full_grey_bg);
        entity = null;
        list.clear();
        adapter.notifyDataSetChanged();
        llContent.setVisibility(View.GONE);
    }

    @Override
    public void StartEquipSuccess() {
        ToastUtils.showShort("开始成功");
        entity.setEquipmentState(PointCheck.EQUIPMENT_STATE_QD);

        if (PointCheck.EQUIPMENT_STATE_TZ.equals(entity.getEquipmentState())) {
            tvStartUse.setBackgroundResource(R.drawable.btn_full_green_bg);
            tvStartUse.setEnabled(true);
            tvEndUse.setBackgroundResource(R.drawable.btn_full_grey_bg);
            tvEndUse.setEnabled(false);
        } else {
            tvStartUse.setBackgroundResource(R.drawable.btn_full_grey_bg);
            tvStartUse.setEnabled(false);
            tvEndUse.setBackgroundResource(R.drawable.btn_full_green_bg);
            tvEndUse.setEnabled(true);
        }
    }

    @Override
    public void EndEquipSuccess(PointCheck check) {
        ToastUtils.showShort("结束成功");
        entity.setEquipmentState(PointCheck.EQUIPMENT_STATE_TZ);
        if (check != null) {
            entity = check;
            String time = check.getCalRunningTime() == null ? "0" : check.getCalRunningTime().toString();
            if (check.getRunningTime() != null) {
                time = check.getRunningTime().toString();
            }
            tvWorkTime.setText(time);
        }
        if (PointCheck.EQUIPMENT_STATE_TZ.equals(entity.getEquipmentState())) {
            tvStartUse.setBackgroundResource(R.drawable.btn_full_green_bg);
            tvStartUse.setEnabled(true);
            tvEndUse.setBackgroundResource(R.drawable.btn_full_grey_bg);
            tvEndUse.setEnabled(false);
        } else {
            tvStartUse.setBackgroundResource(R.drawable.btn_full_grey_bg);
            tvStartUse.setEnabled(false);
            tvEndUse.setBackgroundResource(R.drawable.btn_full_green_bg);
            tvEndUse.setEnabled(true);
        }
    }

    @OnClick(R.id.ivScan)
    void OnScanStart() {
        Intent intent = new Intent(SpotCheckMainActivity.this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SCAN);
    }

    @OnClick(R.id.tvConfirm)
    void Confirm() {
        BaseDialog.setDialog(this,
                "确定提交当前点检任务",
                "确定",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BaseDialog.dissmiss();
                        if (mPresenter != null) {
                            showLoading();
                            mPresenter.ConfirmSpot(JSON.toJSONString(entity));
                        }
                    }
                }, "取消",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BaseDialog.dissmiss();
                    }
                });
    }

    public boolean isAllItemCheck() {
        if (list.size() == 0) {
            return false;
        } else {
            for (PointCheckContent content : list) {
                if (StringUtils.isTrimEmpty(content.getTechnologyStateFlag())) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(DECODED_CONTENT_KEY);
                SearchKey = content;
                showLoading();
                etEquipCode.setText(content);
                if (mPresenter != null)
                    mPresenter.getSpotCheck(SearchKey);
            }
        }
    }
}
