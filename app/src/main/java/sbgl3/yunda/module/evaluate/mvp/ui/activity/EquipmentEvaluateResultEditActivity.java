package sbgl3.yunda.module.evaluate.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.utilcode.util.StringUtils;
import com.jess.arms.utils.utilcode.util.ToastUtils;
import com.jess.arms.widget.autolayout.AutoScrollView;
import com.speedata.utils.ProgressDialogUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sbgl3.yunda.R;
import sbgl3.yunda.SysInfo;
import sbgl3.yunda.module.appraise.mvp.ui.activity.AppraiseTemplateActivity;
import sbgl3.yunda.module.appraise.mvp.ui.activity.EquipmentAppraiseCheckItemActivity;
import sbgl3.yunda.module.appraise.mvp.ui.activity.EquipmentAppraiseResultEditActivity;
import sbgl3.yunda.module.evaluate.mvp.ui.window.SelectLevelPopupWindow;
import sbgl3.yunda.module.evaluate.di.component.DaggerEquipmentEvaluateResultEditComponent;
import sbgl3.yunda.module.evaluate.di.module.EquipmentEvaluateResultEditModule;
import sbgl3.yunda.module.evaluate.entry.EquipmentEvaluateBean;
import sbgl3.yunda.module.evaluate.mvp.contract.EquipmentEvaluateResultEditContract;
import sbgl3.yunda.module.evaluate.mvp.presenter.EquipmentEvaluateResultEditPresenter;
import sbgl3.yunda.widget.DateText;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * <li>标题: 设备管理系统
 * <li>说明: 设备评定结果界面
 * <li>创建人：刘欢
 * <li>创建日期：2018/9/13
 * <li>修改人:
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 *
 * @author 系统集成事业部设备管理系统项目组
 * @version 3.0
 */
public class EquipmentEvaluateResultEditActivity extends BaseActivity<EquipmentEvaluateResultEditPresenter> implements EquipmentEvaluateResultEditContract.View {

    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.gray_layout)
    View grayLayout;
    @BindView(R.id.tvEquipName)
    TextView tvEquipName;
    @BindView(R.id.tvPlanStatus)
    TextView tvPlanStatus;
    @BindView(R.id.tvEquipCode)
    TextView tvEquipCode;
    @BindView(R.id.tvOrgName)
    TextView tvOrgName;
    @BindView(R.id.tvUsePlace)
    TextView tvUsePlace;
    @BindView(R.id.tvJxRepairPerson)
    TextView tvJxRepairPerson;
    @BindView(R.id.tvDqRepairPerson)
    TextView tvDqRepairPerson;
    @BindView(R.id.tvPlanYear)
    TextView tvPlanYear;
    @BindView(R.id.tvPlanAppraiDate)
    TextView tvPlanAppraiDate;
    @BindView(R.id.tvTianbaoUser)
    EditText tvTianbaoUser;
    @BindView(R.id.tvLevelA)
    TextView tvLevelA;
    @BindView(R.id.tvLevelB)
    TextView tvLevelB;
    @BindView(R.id.cvLevel)
    CardView cvLevel;
    @BindView(R.id.tvJianDinPerson)
    EditText tvJianDinPerson;
    @BindView(R.id.tvJianDinDate)
    DateText tvJianDinDate;
    @BindView(R.id.tvRemark)
    EditText tvRemark;
    @BindView(R.id.scrollView)
    AutoScrollView scrollView;
    @BindView(R.id.tvTemplate)
    TextView tvTemplate;
    @BindView(R.id.tvConfirm)
    TextView tvConfirm;
    @BindView(R.id.cvConfirm)
    CardView cvConfirm;

    SelectLevelPopupWindow menuWindow;
    public static EquipmentEvaluateBean equipmentEvaluateBean;
    View view;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerEquipmentEvaluateResultEditComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .equipmentEvaluateResultEditModule(new EquipmentEvaluateResultEditModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_equipment_evaluate_result_edit; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTranslucentStatus();
        menuTp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (null != getIntent()) {
            equipmentEvaluateBean = (EquipmentEvaluateBean) getIntent().getSerializableExtra("equipment");
            setData();
        }
    }

    private String replaceLevels(String level) {
        String text = "";
        if ("1".equals(level)) {
            text = "一级";
        } else if ("2".equals(level)) {
            text = "二级";
        } else if ("3".equals(level)) {
            text = "三级";
        } else if ("4".equals(level)) {
            text = "四级";
        }
        return text;
    }

    /*private void setEmptyStyle(TextView textView) {
        textView.setText("无");
        textView.setTextColor(Color.parseColor("#595959"));
    }*/

    @Override
    public void showLoading() {
        ProgressDialogUtils.showProgressDialog(this,"正在处理，请稍后！");
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
        view = LayoutInflater.from(this).inflate(R.layout.view_level_popup_menu2,null);
    }

    /**
     * <li>说明：评定等级设置
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月13日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    @OnClick(R.id.cvLevel)
    void setLevels() {
        setPupUpMenu();
        grayLayout.setVisibility(View.VISIBLE);
    }

    /**
     * <li>说明：界面数据显示
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月13日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    private void setData(){
        if (null != equipmentEvaluateBean) {
            if (null!=equipmentEvaluateBean.getEquipmentName()) {
                tvEquipName.setText(equipmentEvaluateBean.getEquipmentName());
            } else {
                tvEquipName.setText("");
            }
            GradientDrawable myGrad = (GradientDrawable) tvPlanStatus.getBackground();
            if (null!=equipmentEvaluateBean.getPlanStatus().toString()) {
                switch (equipmentEvaluateBean.getPlanStatus()) {
                    case 1:
                        tvPlanStatus.setText("计划中");
                        myGrad.setColor(Color.parseColor("#9E9E9E"));
                        break;
                    case 2:
                        tvPlanStatus.setText("进行中");
                        myGrad.setColor(Color.parseColor("#E6BA23"));
                        break;
                    case 3:
                        tvPlanStatus.setText("已完成");
                        myGrad.setColor(Color.parseColor("#24BD36"));
                        break;
                    default:
                }
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
            if (null!=equipmentEvaluateBean.getMechanicalRepairPerson()) {
                tvJxRepairPerson.setText(equipmentEvaluateBean.getMechanicalRepairPerson());
            } else {
                tvJxRepairPerson.setText("");
            }
            if (null!=equipmentEvaluateBean.getElectricRepairPerson()) {
                tvDqRepairPerson.setText(equipmentEvaluateBean.getElectricRepairPerson());
            } else {
                tvDqRepairPerson.setText("");
            }
            if (null!=equipmentEvaluateBean.getPlanYear().toString()) {
                tvPlanYear.setText(equipmentEvaluateBean.getPlanYear().toString());
            } else {
                tvPlanYear.setText("");
            }
            if (null!=equipmentEvaluateBean.getAppraiseDate()) {
                tvPlanAppraiDate.setText(equipmentEvaluateBean.getAppraiseDate());
            } else {
                tvPlanAppraiDate.setText("");
            }
            if (null!=equipmentEvaluateBean.getInfomant()) {
                tvTianbaoUser.setText(equipmentEvaluateBean.getInfomant());
            } else {
                tvTianbaoUser.setText(SysInfo.empname);
            }
            if (null!=equipmentEvaluateBean.getBeforeRepairLevel()) {
                String textA = replaceLevels(equipmentEvaluateBean.getBeforeRepairLevel());
                tvLevelA.setText(textA);
            } else {
                tvLevelA.setText("");
            }
            if (null!=equipmentEvaluateBean.getAfterRepairLevel()) {
                String textB = replaceLevels(equipmentEvaluateBean.getAfterRepairLevel());
                tvLevelB.setText(textB);
            } else {
                tvLevelB.setText("");
            }

            if (null!=equipmentEvaluateBean.getAdjuster()) {
                tvJianDinPerson.setText(equipmentEvaluateBean.getAdjuster());
            } else {
                tvJianDinPerson.setText("");
            }
            if (null!=equipmentEvaluateBean.getEvaluateDate()) {
                tvJianDinDate.setText(equipmentEvaluateBean.getEvaluateDate());
            } else {
                tvJianDinDate.setText("");
            }
            if (null!=equipmentEvaluateBean.getRemark()) {
                tvRemark.setText(equipmentEvaluateBean.getRemark());
            } else {
                tvRemark.setText("");
            }
            if (null!=equipmentEvaluateBean.getTemplateName()) {
                tvTemplate.setText(equipmentEvaluateBean.getTemplateName() + "【" + equipmentEvaluateBean.getTemplateNo() + "】");
                tvTemplate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(EquipmentEvaluateResultEditActivity.this,EquipmentEvaluateCheckItemActivity.class);
                        ArmsUtils.startActivity(intent);
                    }
                });
            } else {
                tvTemplate.setText("无评定模板，点击添加！");
                tvTemplate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(EquipmentEvaluateResultEditActivity.this,EvaluateTemplateActivity.class);
                        ArmsUtils.startActivity(intent);
                        //startActivityForResult(intent,200);
                    }
                });
            }
        }
    }

    /**
     * <li>说明：提交
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月13日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    @OnClick(R.id.tvConfirm)
    void submit(){
        if (isAllWrite()){
            Map<String,Object> map = new HashMap<>();
            map.put("idx", equipmentEvaluateBean.getIdx());
            map.put("beforeRepairLevel", getTextViewLevelData(tvLevelA));
            map.put("afterRepairLevel", getTextViewLevelData(tvLevelB));
            map.put("evaluateDate", tvJianDinDate.getText().toString().trim());
            map.put("infomant", tvTianbaoUser.getText().toString().trim());
            map.put("adjuster", tvJianDinPerson.getText().toString().trim());
            if(!StringUtils.isTrimEmpty(tvRemark.getText().toString().trim())){
                map.put("remark", tvRemark.getText().toString().trim());
            }
            if (null != mPresenter){
                showLoading();
                mPresenter.submitEvaluateResult(JSON.toJSONString(map));
            }
            Intent intent = new Intent();
            intent.setAction("action.refreshEquiEvaluateList");
            sendBroadcast(intent);
            finish();
        }

    }

    private String getTextViewLevelData( TextView textView){
        String level;
        if (textView.getText().toString().trim().equals("一级")){
            level = "1";
        } else if (textView.getText().toString().trim().equals("二级")){
            level = "2";
        } else if (textView.getText().toString().trim().equals("三级")){
            level = "3";
        } else if (textView.getText().toString().trim().equals("四级")){
            level = "4";
        } else {
            level = "";
        }
        return level;
    }


    /**
     * <li>说明：判断是否必填
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月13日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    private boolean isAllWrite() {
        if(StringUtils.isTrimEmpty(tvTianbaoUser.getText().toString())){
            ToastUtils.showShort("请输入填报人!");
            return false;
        }
        if(StringUtils.isTrimEmpty(tvLevelA.getText().toString()) || tvLevelA.getText().toString().trim().equals("无")){
            ToastUtils.showShort("请选择整修前数据!");
            return false;
        }
        if(StringUtils.isTrimEmpty(tvLevelB.getText().toString()) || tvLevelB.getText().toString().trim().equals("无")){
            ToastUtils.showShort("请选择整修后数据!");
            return false;
        }

        if(StringUtils.isTrimEmpty(tvJianDinPerson.getText().toString())){
            ToastUtils.showShort("请输入鉴定人!");
            return false;
        }
        if(StringUtils.isTrimEmpty(tvJianDinDate.getText().toString())){
            ToastUtils.showShort("请选择评定日期!");
            return false;
        }
        return true;
    }

    /**
     * <li>说明：菜单窗口
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月13日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    private void setPupUpMenu() {
        View.OnClickListener itemsOnClick = new View.OnClickListener(){

            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.tvConfirm:
                        if (null != menuWindow.getJiaxiang()){
                            tvLevelA.setText(menuWindow.getJiaxiang());
                        }
                        if (null != menuWindow.getYixiang()){
                            tvLevelB.setText(menuWindow.getYixiang());
                        }
                        menuWindow.dismiss();
                        grayLayout.setVisibility(View.GONE);
                        break;
                    case R.id.ivClose:
                        menuWindow.dismiss();
                        grayLayout.setVisibility(View.GONE);
                }
            }

        };
        menuWindow = new SelectLevelPopupWindow(EquipmentEvaluateResultEditActivity.this, itemsOnClick, equipmentEvaluateBean);
        menuWindow.showAtLocation(EquipmentEvaluateResultEditActivity.this.findViewById(R.id.scrollView), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
    }

    @Override
    protected void onResume() {
        super.onResume();
        setData();
    }

}
