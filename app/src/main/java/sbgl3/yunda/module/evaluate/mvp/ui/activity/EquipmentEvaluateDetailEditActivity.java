package sbgl3.yunda.module.evaluate.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
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
import sbgl3.yunda.module.appraise.entry.EquipmentAppraisePlanBean;
import sbgl3.yunda.module.appraise.mvp.ui.activity.EquipmentAppraiseCheckItemActivity;
import sbgl3.yunda.module.evaluate.di.component.DaggerEquipmentEvaluateDetailEditComponent;
import sbgl3.yunda.module.evaluate.di.module.EquipmentEvaluateDetailEditModule;
import sbgl3.yunda.module.evaluate.entry.EquipmentEvaluatePlanBean;
import sbgl3.yunda.module.evaluate.mvp.contract.EquipmentEvaluateDetailEditContract;
import sbgl3.yunda.module.evaluate.mvp.presenter.EquipmentEvaluateDetailEditPresenter;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static sbgl3.yunda.module.evaluate.mvp.ui.activity.EquipmentEvaluateResultEditActivity.equipmentEvaluateBean;


/**
 * <li>标题: 设备管理系统
 * <li>说明: 设备评定明细录入界面
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
public class EquipmentEvaluateDetailEditActivity extends BaseActivity<EquipmentEvaluateDetailEditPresenter> implements EquipmentEvaluateDetailEditContract.View {

    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.gray_layout)
    View grayLayout;
    @BindView(R.id.tvEquipCode)
    TextView tvEquipCode;
    @BindView(R.id.tvEquipName)
    TextView tvEquipName;
    @BindView(R.id.tvUsePlace)
    TextView tvUsePlace;
    @BindView(R.id.tvTemplate)
    TextView tvTemplate;
    @BindView(R.id.tvCheckItemCode)
    TextView tvCheckItemCode;
    @BindView(R.id.tvCheckItemName)
    TextView tvCheckItemName;
    @BindView(R.id.etRealRepairBefore)
    EditText etRealRepairBefore;
    @BindView(R.id.etRealRepairAfter)
    EditText etRealRepairAfter;
    @BindView(R.id.scrollView)
    AutoScrollView scrollView;
    @BindView(R.id.tvConfirm)
    TextView tvConfirm;
    @BindView(R.id.cvConfirm)
    CardView cvConfirm;
    EquipmentEvaluatePlanBean equipmentEvaluatePlanBean;
    EquipmentEvaluatePlanBean nextEquipmentEvaluatePlanBean;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerEquipmentEvaluateDetailEditComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .equipmentEvaluateDetailEditModule(new EquipmentEvaluateDetailEditModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_equipment_evaluate_detail_edit; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTranslucentStatus();
        menuTp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (null!=getIntent()){
            equipmentEvaluatePlanBean = (EquipmentEvaluatePlanBean) getIntent().getSerializableExtra("evaluatePlan");
            if (null != equipmentEvaluatePlanBean){
                if (null!=equipmentEvaluateBean.getEquipmentName()){
                    tvEquipName.setText(equipmentEvaluateBean.getEquipmentName());
                } else {
                    tvEquipName.setText("");
                }
                if (null!=equipmentEvaluateBean.getEquipmentCode()){
                    tvEquipCode.setText(equipmentEvaluateBean.getEquipmentCode());
                } else {
                    tvEquipCode.setText("");
                }
                if (null!=equipmentEvaluateBean.getUsePlace()){
                    tvUsePlace.setText(equipmentEvaluateBean.getUsePlace());
                } else {
                    tvUsePlace.setText("");
                }
                if (null!=equipmentEvaluateBean.getTemplateName()){
                    tvTemplate.setText(equipmentEvaluateBean.getTemplateName() + "【" + equipmentEvaluateBean.getTemplateNo() + "】");
                } else {
                    tvTemplate.setText("");
                }
            }
            setData(equipmentEvaluatePlanBean);
        }
    }

    /**
     * <li>说明：鉴定明细提交
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月13日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    @OnClick(R.id.cvConfirm)
    void submit(){
        Map<String,String> map = new HashMap<>();
        map.put("idx",equipmentEvaluatePlanBean.getIdx());
        if(StringUtils.isTrimEmpty(etRealRepairBefore.getText().toString())){
            ToastUtils.showShort("请输入整修前数据!");
            return;
        }
        if(StringUtils.isTrimEmpty(etRealRepairAfter.getText().toString())){
            ToastUtils.showShort("请输入整修后数据!");
            return;
        }
        map.put("beforeAppraise",etRealRepairBefore.getText().toString().trim());
        map.put("afterAppraise",etRealRepairAfter.getText().toString().trim());
        if (null != mPresenter){
            showLoading();
            mPresenter.submitEvaluateDetail(JSON.toJSONString(map), equipmentEvaluatePlanBean.getIdx());
            Intent intent = new Intent();
            intent.setAction("action.refreshCheckItemsList");
            sendBroadcast(intent);
        }
    }

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

    /**
     * <li>说明：获取下一条数据
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月13日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    @Override
    public void loadNextData(EquipmentEvaluatePlanBean bean) {
        if (null != bean){
            equipmentEvaluatePlanBean = bean;
            this.nextEquipmentEvaluatePlanBean = bean;
            setData(nextEquipmentEvaluatePlanBean);
        } else {
            ToastUtils.showShort("所有检查项已评定完成！");
            // 全部提交完成，刷新检查项列表
            Intent intent = new Intent();
            intent.setAction("action.refreshCheckItemsList");
            sendBroadcast(intent);
            finish();
            return;
        }
    }

    /**
     * <li>说明：界面数据显示
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月13日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    private void setData (EquipmentEvaluatePlanBean bean){
        if (null != bean){
            if (null!=bean.getItemNo()){
                tvCheckItemCode.setText(bean.getItemNo());
            } else {
                tvCheckItemCode.setText("");
            }
            if (null!=bean.getItemName()){
                tvCheckItemName.setText(bean.getItemName());
            } else {
                tvCheckItemName.setText("");
            }
            /*判断设备评定、附属电器评定
             * 设备评定：4
             * 附属电器评定：5
             */
            switch (EquipmentEvaluateCheckItemActivity.itemType){
                case 4:
                    menuTp.setTitle("设备评定");
                    break;
                case 5:
                    menuTp.setTitle("附属电器评定");
                    break;
                default:
            }
            if (null!=bean.getBeforeAppraise()){
                etRealRepairBefore.setText(bean.getBeforeAppraise());
            } else {
                etRealRepairBefore.setText("");
            }
            if (null!=bean.getAfterAppraise()){
                etRealRepairAfter.setText(bean.getAfterAppraise());
            } else {
                etRealRepairAfter.setText("");
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
}
