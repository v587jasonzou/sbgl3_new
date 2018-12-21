package sbgl3.yunda.module.equipinfo.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.utilcode.util.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sbgl3.yunda.R;
import sbgl3.yunda.module.equipinfo.di.component.DaggerEquipInfoComponent;
import sbgl3.yunda.module.equipinfo.di.module.EquipInfoModule;
import sbgl3.yunda.module.equipinfo.entity.EquipmentPrimaryInfo;
import sbgl3.yunda.module.equipinfo.mvp.contract.EquipInfoContract;
import sbgl3.yunda.module.equipinfo.mvp.presenter.EquipInfoPresenter;
import sbgl3.yunda.module.sbdj.entity.EquipmentUnionRFID;
import sbgl3.yunda.module.sbdj.mvp.ui.activity.ReadPhotoActivity;
import sbgl3.yunda.module.sbdj.mvp.ui.activity.SbdjMainActivity;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 设备信息查看-设备详细信息查看
 * @author 周雪巍
 * @time 2018/09/12
 * */
public class EquipInfoActivity extends BaseActivity<EquipInfoPresenter> implements EquipInfoContract.View {

    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.tvEquipName)
    TextView tvEquipName;
    @BindView(R.id.tvEquipCode)
    TextView tvEquipCode;
    @BindView(R.id.tvWeight)
    TextView tvWeight;
    @BindView(R.id.tvEquipModel)
    TextView tvEquipModel;
    @BindView(R.id.tvTotalPower)
    TextView tvTotalPower;
    @BindView(R.id.tvEquipSpecification)
    TextView tvEquipSpecification;
    @BindView(R.id.tvMechanicalCoefficient)
    TextView tvMechanicalCoefficient;
    @BindView(R.id.tvElectricCoefficient)
    TextView tvElectricCoefficient;
    @BindView(R.id.tvFixedAssetValue)
    TextView tvFixedAssetValue;
    @BindView(R.id.tvUsePlace)
    TextView tvUsePlace;
    @BindView(R.id.tvClassCode)
    TextView tvClassCode;
    @BindView(R.id.tvClassName)
    TextView tvClassName;
    @BindView(R.id.tvMechanicalRepairPerson)
    TextView tvMechanicalRepairPerson;
    @BindView(R.id.tvElectricRepairPerson)
    TextView tvElectricRepairPerson;
    @BindView(R.id.tvBuyData)
    TextView tvBuyData;
    @BindView(R.id.tvMakeFactory)
    TextView tvMakeFactory;
    EquipmentPrimaryInfo EquipEntity;
    @BindView(R.id.ivPhoto)
    ImageView ivPhoto;
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerEquipInfoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .equipInfoModule(new EquipInfoModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_equip_info; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
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
        Intent intent = getIntent();
        if(intent!=null){
            EquipEntity = (EquipmentPrimaryInfo) intent.getSerializableExtra("Equip");
        }
        if(EquipEntity!=null){
            if(EquipEntity.getEquipmentName()!=null){
                tvEquipName.setText(EquipEntity.getEquipmentName());
            }
            if(EquipEntity.getEquipmentCode()!=null){
                tvEquipCode.setText(EquipEntity.getEquipmentCode());
            }
            if(EquipEntity.getWeight()!=null){
                tvWeight.setText(EquipEntity.getWeight().toString()+" t");
            }
            if(EquipEntity.getBuyDate()!=null){
                tvBuyData.setText(TimeUtils.date2String(new Date(EquipEntity.getBuyDate()),new SimpleDateFormat("yyyy-MM-dd")));
            }
            if(EquipEntity.getClassCode()!=null){
                tvClassCode.setText(EquipEntity.getClassCode());
            }
            if(EquipEntity.getClassName()!=null){
                tvClassName.setText(EquipEntity.getClassName());
            }
            if(EquipEntity.getElectricCoefficient()!=null){
                tvElectricCoefficient.setText(EquipEntity.getElectricCoefficient().toString());
            }
            if(EquipEntity.getMechanicalCoefficient()!=null){
                tvMechanicalCoefficient.setText(EquipEntity.getMechanicalCoefficient().toString());
            }
            if(EquipEntity.getElectricRepairPerson()!=null){
                tvElectricRepairPerson.setText(EquipEntity.getElectricRepairPerson());
            }
            if(EquipEntity.getMechanicalRepairPerson()!=null){
                tvMechanicalRepairPerson.setText(EquipEntity.getMechanicalRepairPerson());
            }
            if(EquipEntity.getModel()!=null){
                tvEquipModel.setText(EquipEntity.getModel());
            }

            if(EquipEntity.getSpecification()!=null){
                tvEquipSpecification.setText(EquipEntity.getSpecification());
            }
            if(EquipEntity.getUsePlace()!=null){
                tvUsePlace.setText(EquipEntity.getUsePlace());
            }
            if(EquipEntity.getFixedAssetValue()!=null){
                tvFixedAssetValue.setText(EquipEntity.getFixedAssetValue().toString());
            }
            if(EquipEntity.getMakeFactory()!=null){
                tvMakeFactory.setText(EquipEntity.getMakeFactory());
            }
            if(EquipEntity.getEletricTotalPower()!=null){
                tvTotalPower.setText(EquipEntity.getEletricTotalPower()+" kw");
            }
        }
    }
    @OnClick(R.id.ivPhoto)
    void toPhoto(){
        EquipmentUnionRFID bean = new EquipmentUnionRFID();
        bean.setIdx(EquipEntity.getIdx());
        Intent intent = new Intent(EquipInfoActivity.this,ReadPhotoActivity.class);
        intent.putExtra("equip",bean);
        intent.putExtra("isAdd",false);
        ArmsUtils.startActivity(intent);
    }
    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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
}
