package sbgl3.yunda.module.appraise.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.zhy.autolayout.AutoLinearLayout;

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
import retrofit2.http.POST;
import sbgl3.yunda.R;
import sbgl3.yunda.module.appraise.di.component.DaggerEquipmentAppraiseDetailEditComponent;
import sbgl3.yunda.module.appraise.di.module.EquipmentAppraiseDetailEditModule;
import sbgl3.yunda.module.appraise.entry.EquipmentAppraisePlanBean;
import sbgl3.yunda.module.appraise.mvp.contract.EquipmentAppraiseDetailEditContract;
import sbgl3.yunda.module.appraise.mvp.presenter.EquipmentAppraiseDetailEditPresenter;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static sbgl3.yunda.module.appraise.mvp.ui.activity.EquipmentAppraiseResultEditActivity.equipmentAppraiserBean;

/**
 * <li>标题: 设备管理系统
 * <li>说明: 设备鉴定明细提交界面
 * <li>创建人：刘欢
 * <li>创建日期：2018年9月4日
 * <li>修改人:
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 *
 * @author 系统集成事业部设备管理系统项目组
 * @version 3.0
 */
public class EquipmentAppraiseDetailEditActivity extends BaseActivity<EquipmentAppraiseDetailEditPresenter> implements EquipmentAppraiseDetailEditContract.View {

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
    @BindView(R.id.tvStandardJ)
    TextView tvStandardJ;
    @BindView(R.id.tvStandardY)
    TextView tvStandardY;
    @BindView(R.id.tvStandardB)
    TextView tvStandardB;
    @BindView(R.id.llStandard)
    AutoLinearLayout llStandard;
    @BindView(R.id.etRealRepairBefore)
    EditText etRealRepairBefore;
    @BindView(R.id.etRealRepairAfter)
    EditText etRealRepairAfter;
    @BindView(R.id.etResultRepairBefore)
    EditText etResultRepairBefore;
    @BindView(R.id.etResultRepairAfter)
    EditText etResultRepairAfter;
    @BindView(R.id.llAppraiseResult)
    AutoLinearLayout llAppraiseResult;
    @BindView(R.id.scrollView)
    AutoScrollView scrollView;
    @BindView(R.id.tvConfirm)
    TextView tvConfirm;
    @BindView(R.id.cvConfirm)
    CardView cvConfirm;

    EquipmentAppraisePlanBean equipmentAppraisePlanBean;
    EquipmentAppraisePlanBean nextEquipmentAppraisePlanBean;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerEquipmentAppraiseDetailEditComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .equipmentAppraiseDetailEditModule(new EquipmentAppraiseDetailEditModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_equipment_appraise_detail_edit; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
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
        if (null != getIntent()){
            equipmentAppraisePlanBean = (EquipmentAppraisePlanBean) getIntent().getSerializableExtra("appraisePlan");
            if (null != equipmentAppraiserBean){
                if (null!=equipmentAppraiserBean.getEquipmentName()){
                    tvEquipName.setText(equipmentAppraiserBean.getEquipmentName());
                } else {
                    tvEquipName.setText("");
                }
                if (null!=equipmentAppraiserBean.getEquipmentCode()){
                    tvEquipCode.setText(equipmentAppraiserBean.getEquipmentCode());
                } else {
                    tvEquipCode.setText("");
                }
                if (null!=equipmentAppraiserBean.getUsePlace()){
                    tvUsePlace.setText(equipmentAppraiserBean.getUsePlace());
                } else {
                    tvUsePlace.setText("");
                }
                if (null!=equipmentAppraiserBean.getTemplateName()){
                    tvTemplate.setText(equipmentAppraiserBean.getTemplateName() + "【" + equipmentAppraiserBean.getTemplateNo() + "】");
                } else {
                    tvTemplate.setText("");
                }
            }
            setData(equipmentAppraisePlanBean);
        }
    }

    /**
     * <li>说明：鉴定明细提交
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月4日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    @OnClick(R.id.cvConfirm)
    void submit(){
        Map<String,String> map = new HashMap<>();
        map.put("idx",equipmentAppraisePlanBean.getIdx());
        // 根据鉴定项提交（甲：1；乙：2；附属电器：3）
        if (EquipmentAppraiseCheckItemActivity.itemType == 1){
            if(StringUtils.isTrimEmpty(etRealRepairBefore.getText().toString())){
                ToastUtils.showShort("请输入实测整修前数据");
                return;
            }
            if(StringUtils.isTrimEmpty(etRealRepairAfter.getText().toString())){
                ToastUtils.showShort("请输入实测整修后数据");
                return;
            }
            if(StringUtils.isTrimEmpty(etResultRepairBefore.getText().toString())){
                ToastUtils.showShort("请输入鉴定结果整修前数据");
                return;
            }
            if(StringUtils.isTrimEmpty(etResultRepairAfter.getText().toString())){
                ToastUtils.showShort("请输入鉴定结果整修后数据");
                return;
            }
            map.put("beforeMeasured",etRealRepairBefore.getText().toString().trim());
            map.put("afterMeasured",etRealRepairAfter.getText().toString().trim());
            map.put("beforeAppraise",etResultRepairBefore.getText().toString().trim());
            map.put("afterAppraise",etResultRepairAfter.getText().toString().trim());
        } else if (EquipmentAppraiseCheckItemActivity.itemType == 2 || EquipmentAppraiseCheckItemActivity.itemType == 3){
            if(StringUtils.isTrimEmpty(etRealRepairBefore.getText().toString())){
                ToastUtils.showShort("请输入实测整修前数据");
                return;
            }
            if(StringUtils.isTrimEmpty(etRealRepairAfter.getText().toString())){
                ToastUtils.showShort("请输入实测整修后数据");
                return;
            }
            map.put("beforeAppraise",etRealRepairBefore.getText().toString().trim());
            map.put("afterAppraise",etRealRepairAfter.getText().toString().trim());
        }
        if (null != mPresenter){
            showLoading();
            mPresenter.submitAppraiseDetail(JSON.toJSONString(map),equipmentAppraisePlanBean.getIdx());
            Intent intent = new Intent();
            intent.setAction("action.refreshCheckItemsList");
            sendBroadcast(intent);
        }
    }

    @Override
    public void showLoading() {
        ProgressDialogUtils.showProgressDialog(this,"正在提交，请稍等.....");
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
     * <li>说明：获取下一条数据
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月4日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    @Override
    public void loadNextData(EquipmentAppraisePlanBean bean) {
        if (null != bean){
            equipmentAppraisePlanBean = bean;
            this.nextEquipmentAppraisePlanBean = bean;
            setData(nextEquipmentAppraisePlanBean);
        } else {
            ToastUtils.showShort("所有检查项已鉴定完成！");
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
     * <li>创建日期：2018年9月4日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    private void setData (EquipmentAppraisePlanBean bean){
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
            /*判断甲、乙、附属电器鉴定
             * 甲项：1
             * 乙项：2
             * 附属电器：3
             */
            switch (EquipmentAppraiseCheckItemActivity.itemType){
                case 1:
                    menuTp.setTitle("甲项鉴定");
                    if (null!=bean.getRepairStandardA()){
                        tvStandardJ.setText(bean.getRepairStandardA());
                    } else {
                        tvStandardJ.setText("");
                    }
                    if (null!=bean.getRepairStandardB()){
                        tvStandardY.setText(bean.getRepairStandardB());
                    } else {
                        tvStandardY.setText("");
                    }
                    if (null!=bean.getRepairStandardC()){
                        tvStandardB.setText(bean.getRepairStandardC());
                    } else {
                        tvStandardB.setText("");
                    }
                    if (null!=bean.getBeforeMeasured()){
                        etRealRepairBefore.setText(bean.getBeforeMeasured());
                    } else {
                        etRealRepairBefore.setText("");
                    }
                    if (null!=bean.getAfterMeasured()){
                        etRealRepairAfter.setText(bean.getAfterMeasured());
                    } else {
                        etRealRepairAfter.setText("");
                    }
                    if (null!=bean.getBeforeAppraise()){
                        etResultRepairBefore.setText(bean.getBeforeAppraise());
                    } else {
                        etResultRepairBefore.setText("");
                    }
                    if (null!=bean.getAfterAppraise()){
                        etResultRepairAfter.setText(bean.getAfterAppraise());
                    } else {
                        etResultRepairAfter.setText("");
                    }
                    break;
                case 2:
                    menuTp.setTitle("乙项鉴定");
                    llAppraiseResult.setVisibility(View.INVISIBLE);
                    if (null!=bean.getRepairStandardA()){
                        tvStandardJ.setText(bean.getRepairStandardA());
                    } else {
                        tvStandardJ.setText("");
                    }
                    if (null!=bean.getRepairStandardB()){
                        tvStandardY.setText(bean.getRepairStandardB());
                    } else {
                        tvStandardY.setText("");
                    }
                    if (null!=bean.getRepairStandardC()){
                        tvStandardB.setText(bean.getRepairStandardC());
                    } else {
                        tvStandardB.setText("");
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
                    break;
                case 3:
                    menuTp.setTitle("附属电器鉴定");
                    llStandard.setVisibility(View.GONE);
                    llAppraiseResult.setVisibility(View.INVISIBLE);
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
                    break;
                default:
            }
        }
    }

}
