package sbgl3.yunda.module.userconfirm.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.utilcode.util.StringUtils;
import com.jess.arms.utils.utilcode.util.ToastUtils;
import com.lzy.imagepicker.ImagePicker;
import com.speedata.utils.ProgressDialogUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sbgl3.yunda.R;
import sbgl3.yunda.entry.ImagesBean;
import sbgl3.yunda.globle.adapter.ImagesAdapter;
import sbgl3.yunda.module.userconfirm.di.component.DaggerFaultOrderConfirmEditComponent;
import sbgl3.yunda.module.userconfirm.di.module.FaultOrderConfirmEditModule;
import sbgl3.yunda.module.userconfirm.entry.FaultOrderBean;
import sbgl3.yunda.module.userconfirm.mvp.contract.FaultOrderConfirmEditContract;
import sbgl3.yunda.module.userconfirm.mvp.presenter.FaultOrderConfirmEditPresenter;
import sbgl3.yunda.widget.PhotoReadActivity;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * <li>标题: 设备管理系统
 * <li>说明: 使用人确认---故障处理明细界面
 * <li>创建人：刘欢
 * <li>创建日期：2018年9月18日
 * <li>修改人:
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 *
 * @author 系统集成事业部设备管理系统项目组
 * @version 3.0
 */
public class FaultOrderConfirmEditActivity extends BaseActivity<FaultOrderConfirmEditPresenter> implements FaultOrderConfirmEditContract.View {

    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.tvEquipCode)
    TextView tvEquipCode;
    @BindView(R.id.tvEquipName)
    TextView tvEquipName;
    @BindView(R.id.tvModel)
    TextView tvModel;
    @BindView(R.id.tvFaultPhenomenon)
    TextView tvFaultPhenomenon;
    @BindView(R.id.tvFaultPlae)
    TextView tvFaultPlae;
    @BindView(R.id.tvcauseAnalysis)
    TextView tvcauseAnalysis;
    @BindView(R.id.tvRepairContent)
    TextView tvRepairContent;
    @BindView(R.id.rlImages)
    RecyclerView rlImages;
    @BindView(R.id.tvConfirm)
    TextView tvConfirm;
    @BindView(R.id.cvConfirm)
    CardView cvConfirm;
    @BindView(R.id.tvFaultOrderNo)
    TextView tvFaultOrderNo;
    @BindView(R.id.tvFaultOccurTime)
    TextView tvFaultOccurTime;
    @BindView(R.id.tvSubmitEmp)
    TextView tvSubmitEmp;
    @BindView(R.id.tvFaultLevel)
    TextView tvFaultLevel;
    @BindView(R.id.tvHidden)
    TextView tvHidden;
    @BindView(R.id.tvReadMore)
    TextView tvReadMore;
    @BindView(R.id.cvNotImportant)
    CardView cvNotImportant;
    List<FaultOrderBean> mFaultOrderList = new ArrayList<>();
    ImagesAdapter imagesAdapter;
    ArrayList<ImagesBean> imagess = new ArrayList<>();
    FaultOrderBean faultOrderBean;
    FaultOrderBean nextFaultOrderBean;
    int position;
    int currentPoistion = 0;
    /** 记录未确认工单数*/
    int UnconfirmNum=0;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerFaultOrderConfirmEditComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .faultOrderConfirmEditModule(new FaultOrderConfirmEditModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_fault_order_confirm_edit; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
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
        if (getIntent() != null) {
            mFaultOrderList = (ArrayList<FaultOrderBean>)getIntent().getSerializableExtra("allFaultOrderList");
            UnconfirmNum = mFaultOrderList.size();
            position = getIntent().getIntExtra("position",0);
            faultOrderBean = mFaultOrderList.get(position);
            setData(faultOrderBean);
        }
        loadImage();
        ArmsUtils.configRecycleView(rlImages, new GridLayoutManager(this, 3, OrientationHelper.VERTICAL, false));
        imagesAdapter = new ImagesAdapter(imagess, this);
        rlImages.setAdapter(imagesAdapter);
        imagesAdapter.setOnItemClickListener(new DefaultAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int viewType, Object data, int position) {
                Bundle bundle1 = new Bundle();
                Intent intent = new Intent(FaultOrderConfirmEditActivity.this, PhotoReadActivity.class);
                bundle1.putSerializable("images", imagess);
                bundle1.putInt("position", position);
                bundle1.putString("state", "1");
                intent.putExtras(bundle1);
                startActivityForResult(intent, ImagePicker.REQUEST_CODE_PREVIEW);
            }
        });
        showLoading();
    }

    /**
     * <li>说明：联网获取照片
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月19日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    private void loadImage(){
        if (null != mPresenter) {
            mPresenter.getImages(faultOrderBean.getIdx());
        }
    }

    @Override
    public void showLoading() {
        ProgressDialogUtils.showProgressDialog(this, "正处理，请稍等...");
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
     * <li>说明：获取照片成功
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月19日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    @Override
    public void getImagesSuccess(List<ImagesBean> images) {
        imagess.clear();
        if (images != null) {
            imagess.addAll(images);
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hideLoading();
                imagesAdapter.notifyDataSetChanged();
            }
        });
    }


    /**
     * <li>说明：隐藏提票单号等信息
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月19日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    @OnClick(R.id.tvHidden)
    void hiddenCard(){
        cvNotImportant.setVisibility(View.GONE);
        tvReadMore.setVisibility(View.VISIBLE);
    }

    /**
     * <li>说明：查看提票单号等信息
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月19日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    @OnClick(R.id.tvReadMore)
    void readMore(){
        cvNotImportant.setVisibility(View.VISIBLE);
        tvReadMore.setVisibility(View.GONE);
    }



    /**
     * <li>说明：确认
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月12日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    @OnClick(R.id.tvConfirm)
    void confirm(){
        if (null != mPresenter){
            mPresenter.faultConfirm(JSON.toJSONString(new String[]{faultOrderBean.getIdx()}));
            refreshEquipmentList();
        }

    }

    @Override
    public void getNextDataSuccess(FaultOrderBean bean) {
        if (null!=bean){
            faultOrderBean = bean;
            setData(faultOrderBean);
            loadImage();
        } else {
            ToastUtils.showShort("所有工单已处理完成！");
            refreshEquipmentList();
            finish();
        }
    }

    /**
     * <li>说明：获取下一条待确认数据
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月20日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    @Override
    public FaultOrderBean loadNextData() {
        faultOrderBean.setConfirm(true);
        UnconfirmNum--;
        for (int i = currentPoistion; i < mFaultOrderList.size(); i++){
            if (!mFaultOrderList.get(i).isConfirm()){
                nextFaultOrderBean = mFaultOrderList.get(i);
                break;
            } else {
                continue;
            }
        }
        currentPoistion++;
        if (UnconfirmNum ==0){
            return null;
        } else {
            return nextFaultOrderBean;
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
    private void refreshEquipmentList(){
        Intent intent = new Intent();
        intent.setAction("action.refreshFaultEuipmentList");
        sendBroadcast(intent);
    }

    /**
     * <li>说明：界面数据显示
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月18日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    private void setData(FaultOrderBean bean){
        if (null!=bean){
            if (null!=bean.getEquipmentCode()) {
                tvEquipCode.setText(bean.getEquipmentCode());
            } else {
                tvEquipCode.setText("");
            }
            if (null!=bean.getEquipmentName()) {
                tvEquipName.setText(bean.getEquipmentName());
            } else {
                tvEquipName.setText("");
            }
            String str = "";
            if(bean.getModel()!=null){
                str = str+bean.getModel();
            }
            if(bean.getSpecification()!=null){
                str = str+","+bean.getSpecification();
            }else {
                str = str+"";
            }
            tvModel.setText(str);
            if (null!=bean.getFaultPlace()) {
                tvFaultPlae.setText(bean.getFaultPlace());
            } else {
                tvFaultPlae.setText("");
            }
            if (null!=bean.getFaultPhenomenon()) {
                tvFaultPhenomenon.setText(bean.getFaultPhenomenon());
            } else {
                tvFaultPhenomenon.setText("");
            }
            if (null!=bean.getFaultOrderNo()){
                tvFaultOrderNo.setText(bean.getFaultOrderNo());
            } else {
                tvFaultOrderNo.setText("");
            }
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String faultOccurTime = df.format(new Date(bean.getFaultOccurTime()));
            tvFaultOccurTime.setText(faultOccurTime);
            if (null!=bean.getSubmitEmp()){
                tvSubmitEmp.setText(bean.getSubmitEmp());
            } else {
                tvSubmitEmp.setText("");
            }
            if (null!=bean.getFaultLevel()){
                tvFaultLevel.setText(bean.getFaultLevel());
            } else {
                tvFaultLevel.setText("");
            }
            if (null!=bean.getCauseAnalysis()) {
                tvcauseAnalysis.setText(bean.getCauseAnalysis());
            } else {
                tvcauseAnalysis.setText("");
            }
            if (null!=bean.getRepairContent()) {
                tvRepairContent.setText(bean.getRepairContent());
            } else {
                tvRepairContent.setText("");
            }
        }
    }
}
