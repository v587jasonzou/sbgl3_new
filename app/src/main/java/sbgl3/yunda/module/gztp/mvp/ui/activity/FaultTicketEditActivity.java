package sbgl3.yunda.module.gztp.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.utilcode.util.StringUtils;
import com.jess.arms.utils.utilcode.util.TimeUtils;
import com.jess.arms.utils.utilcode.util.ToastUtils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.speedata.utils.ProgressDialogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sbgl3.yunda.R;
import sbgl3.yunda.entry.EpcDataBase;
import sbgl3.yunda.entry.ImagesBean;
import sbgl3.yunda.globle.adapter.ImagesAdapter;
import sbgl3.yunda.module.gztp.di.component.DaggerFaultTicketEditComponent;
import sbgl3.yunda.module.gztp.di.module.FaultTicketEditModule;
import sbgl3.yunda.module.gztp.entry.EquipmentPrimaryInfo;
import sbgl3.yunda.module.gztp.entry.FaultOrder;
import sbgl3.yunda.module.gztp.mvp.contract.FaultTicketEditContract;
import sbgl3.yunda.module.gztp.mvp.presenter.FaultTicketEditPresenter;
import sbgl3.yunda.widget.PhotoReadActivity;
import sbgl3.yunda.widget.zxing.android.CaptureActivity;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class FaultTicketEditActivity extends BaseActivity<FaultTicketEditPresenter> implements FaultTicketEditContract.View {
    public static int IMAGE_PICKER = 666;
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
    EditText tvEquipCode;
    @BindView(R.id.tvEquipName)
    EditText tvEquipName;
    @BindView(R.id.tvUsePlace)
    EditText tvUsePlace;
    @BindView(R.id.tvFindTime)
    TextView tvFindTime;
    @BindView(R.id.rbCommon)
    RadioButton rbCommon;
    @BindView(R.id.rbBig)
    RadioButton rbBig;
    @BindView(R.id.rbVeryBig)
    RadioButton rbVeryBig;
    @BindView(R.id.etFaultLocation)
    EditText etFaultLocation;
    @BindView(R.id.etFaultDes)
    EditText etFaultDes;
    @BindView(R.id.rlImages)
    RecyclerView rlImages;
    @BindView(R.id.tvFaultOrderNo)
    TextView tvFaultOrderNo;
    @BindView(R.id.tvRefreshNo)
    TextView tvRefreshNo;
    @BindView(R.id.tvConfirm)
    TextView tvConfirm;
    @BindView(R.id.cvConfirm)
    CardView cvConfirm;
    @BindView(R.id.rgLevel)
    RadioGroup rgLevel;
    ArrayList<ImagesBean> imagess = new ArrayList<>();
    ImagesAdapter imagesAdapter;
    FaultOrder order;
    boolean isEditEquip;
    String equipType = "";
    @BindView(R.id.ivEdit1)
    ImageView ivEdit1;
    @BindView(R.id.ivEdit2)
    ImageView ivEdit2;
    @BindView(R.id.ivEdit3)
    ImageView ivEdit3;
    EquipmentPrimaryInfo equipInfo;
    String businessIdx = "";
    String businessEntity = "";
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerFaultTicketEditComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .faultTicketEditModule(new FaultTicketEditModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_fault_ticket_edit; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
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
        if (getIntent().getSerializableExtra("FaultOrder") != null) {
            isScan = false;
            order = (FaultOrder) getIntent().getSerializableExtra("FaultOrder");
            businessIdx = intent.getStringExtra("businessIdx");
            businessEntity = intent.getStringExtra("businessEntity");
            isEditEquip = false;
            equipType = order.getEquipmentType();
            tvEquipCode.setEnabled(false);
            tvEquipName.setEnabled(false);
            tvUsePlace.setEnabled(false);
           if(order.getEquipmentCode()!=null){
               tvEquipCode.setText(order.getEquipmentCode());
           }
           if(order.getEquipmentName()!=null){
               tvEquipName.setText(order.getEquipmentName());
           }
           if(order.getUsePlace()!=null){
               tvUsePlace.setText(order.getUsePlace());
           }
           if(order.getFaultOccurTime()!=null){
               tvFindTime.setText(TimeUtils.date2String(new Date(order.getFaultOccurTime()),new SimpleDateFormat("yyyy/MM/dd HH:mm")));
           }
           if(order.getFaultPlace()!=null){
               etFaultLocation.setText(order.getFaultPlace());
           }
           if(order.getFaultPhenomenon()!=null){
               etFaultDes.setText(order.getFaultPhenomenon());
           }
        } else {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this);
            }
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
            Long time = System.currentTimeMillis();
            tvFindTime.setText(TimeUtils.date2String(new Date(time), new SimpleDateFormat("yyyy/MM/dd HH:mm")));
            order = new FaultOrder();
            order.setFaultOccurTime(time);
            ivEdit1.setVisibility(View.VISIBLE);
            ivEdit2.setVisibility(View.VISIBLE);
            ivEdit3.setVisibility(View.VISIBLE);
            equipType = intent.getStringExtra("equipType");
            if (!StringUtils.isTrimEmpty(equipType)) {
                order.setEquipmentType(equipType);
                if (equipType.equals("设施") || equipType.equals("其他设备")) {
                    isEditEquip = true;
                    tvEquipCode.setEnabled(true);
                    tvEquipName.setEnabled(true);
                    tvUsePlace.setEnabled(true);
                    tvEquipCode.setHint("请输入");
                    tvEquipName.setHint("请输入");
                    tvUsePlace.setHint("请输入");
                } else {
                    isEditEquip = false;
                    tvEquipCode.setHint("请扫描设备获取相关信息");
                    tvEquipName.setHint("请扫描设备获取相关信息");
                    tvUsePlace.setHint("请扫描设备获取相关信息");
                    tvEquipCode.setEnabled(false);
                    tvEquipName.setEnabled(false);
                    tvUsePlace.setEnabled(false);
                }
            }
        }
        if (!StringUtils.isTrimEmpty(equipType)) {
            menuTp.setTitle(equipType + "提票");
        } else {
            menuTp.setTitle("故障提票");
        }

        ImagesBean item = new ImagesBean();
        item.setImagesName("0");
        imagess.add(item);
        ArmsUtils.configRecycleView(rlImages, new GridLayoutManager(FaultTicketEditActivity.this, 3, OrientationHelper.VERTICAL, false));
        imagesAdapter = new ImagesAdapter(imagess, this);
        rlImages.setAdapter(imagesAdapter);
        imagesAdapter.setOnItemClickListener(new DefaultAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int viewType, Object data, int position) {
                if ("0".equals(imagess.get(position).getImagesName())) {
                    Intent intent = new Intent(FaultTicketEditActivity.this, ImageGridActivity.class);
                    intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                    startActivityForResult(intent, IMAGE_PICKER);
                } else {
                    Bundle bundle1 = new Bundle();
                    Intent intent = new Intent(FaultTicketEditActivity.this, PhotoReadActivity.class);
                    bundle1.putSerializable("images", imagess);
                    bundle1.putInt("position", position);
                    bundle1.putString("state", "order");
                    intent.putExtras(bundle1);
                    startActivityForResult(intent, ImagePicker.REQUEST_CODE_PREVIEW);
                }
            }
        });
        if (mPresenter != null) {
            showLoading();
            mPresenter.getFaultTicketNo();
        }
        rbCommon.setChecked(true);
        order.setFaultLevel("一般");
        rgLevel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rbCommon:
                        order.setFaultLevel("一般");
                        break;
                    case R.id.rbBig:
                        order.setFaultLevel("重大");
                        break;
                    case R.id.rbVeryBig:
                        order.setFaultLevel("极大");
                        break;
                }
            }
        });
    }

    @Override
    public void showLoading() {
        ProgressDialogUtils.showProgressDialog(this, "加载中...");
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                ArrayList<ImageItem> images = data.getParcelableArrayListExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null && images.size() > 0) {
                    if(mPresenter!=null){
                        showLoading();
                        mPresenter.UploadImage(images.get(0));
                    }

//                    FileUtils.copyFile(images.get(0).path, AppConstant.IMAGE_PATH+"12345"+AppConstant.JPG_EXTNAME);
//                    if(imagess.size()>0){
//                        imagess.remove(imagess.size() - 1);
//                    }
//                    for (int i = 0; i < images.size(); i++) {
//                        ImagesBean bean = new ImagesBean();
//                        bean.setImagesLocalPath(images.get(i).path);
//                        imagess.add(bean);
//                    }
//                    ImagesBean bean = new ImagesBean();
//                    bean.setImagesName("0");
//                    imagess.add(bean);
//                    imagesAdapter.notifyDataSetChanged();
                }
            } else if (requestCode == ImagePicker.REQUEST_CODE_PREVIEW) {
                if (data != null) {
                    ArrayList<ImagesBean> images = (ArrayList<ImagesBean>) data.getSerializableExtra("images");
                    imagess.clear();
                    if (images != null && images.size() > 0) {
                        imagess.addAll(images);
                        ImagesBean bean = new ImagesBean();
                        bean.setImagesName("0");
                        imagess.add(bean);
                        imagesAdapter.notifyDataSetChanged();
                    } else {
                        ImagesBean bean = new ImagesBean();
                        bean.setImagesName("0");
                        imagess.add(bean);
                        imagesAdapter.notifyDataSetChanged();
                    }
                }

            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(DECODED_CONTENT_KEY);
                if(mPresenter!=null){
                    showLoading();
                    mPresenter.getEquipInfo(content,equipType);
                }
            }
        }
    }

    @Override
    public void getFaultNoSuccess(String num) {
        if (!StringUtils.isTrimEmpty(num)) {
            tvFaultOrderNo.setText(num);
            order.setFaultOrderNo(num);
        }
    }

    @Override
    public void onLoadFaild(String type, String msg) {

    }

    @Override
    public void UpLoadImagesSuccess(ImagesBean bean) {
        if(imagess.size()>0){
            imagess.remove(imagess.size() - 1);
        }
        imagess.add(bean);
        ImagesBean bean1 = new ImagesBean();
        bean1.setImagesName("0");
        imagess.add(bean1);
        imagesAdapter.notifyDataSetChanged();
    }

    @Override
    public void addOrderSuccess() {
        ToastUtils.showShort("提票成功！");
        finish();
    }

    @Override
    public void getEquipSuccess(EquipmentPrimaryInfo equip) {
        if (equip != null) {
            equipInfo = equip;
            if (!StringUtils.isTrimEmpty(equipInfo.getEquipmentName())) {
                tvEquipName.setText(equipInfo.getEquipmentName());
            } else {
                tvEquipName.setText("无");
            }
            if (!StringUtils.isTrimEmpty(equipInfo.getEquipmentCode())) {
                tvEquipCode.setText(equipInfo.getEquipmentCode());
                etEquipCode.setText(equipInfo.getEquipmentCode());
            } else {
                tvEquipCode.setText("无");
            }
            if (!StringUtils.isTrimEmpty(equipInfo.getUsePlace())) {
                tvUsePlace.setText(equipInfo.getUsePlace());
            } else {
                tvUsePlace.setText("无");
            }
            if(!StringUtils.isTrimEmpty(equipInfo.getIdx())){
                order.setEquipmentIdx(equipInfo.getIdx());
            }
        } else {
            ToastUtils.showShort("无相关设备信息");
        }
    }

    @OnClick(R.id.tvRefreshNo)
    void RefreshFaultNo() {
        if (mPresenter != null) {
            showLoading();
            mPresenter.getFaultTicketNo();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEqiupCode(ArrayList<EpcDataBase> list) {
        if (list != null && list.size() > 0) {
            if (list.size() == 1) {
                if (mPresenter != null) {
                    showLoading();
                    mPresenter.getEquipInfo(list.get(0).equipmentCode, equipType);
                }
            } else {
                ToastUtils.showShort("扫描到多条标签，请重新扫描");
            }

        } else {
            ToastUtils.showShort("未扫描到标签，请重新扫描");
        }

    }

    @OnClick(R.id.ivSearch)
    void SearchEquip() {
        if (etEquipCode.getText() != null && !StringUtils.isTrimEmpty(etEquipCode.getText().toString())) {
            if (mPresenter != null){
                mPresenter.getEquipInfo(etEquipCode.getText().toString(), equipType);
                showLoading();
            }
        } else {
            ToastUtils.showShort("还未输入设备编码！");
        }
    }

    @OnClick(R.id.ivScan)
    void OnScanStart() {
        Intent intent = new Intent(FaultTicketEditActivity.this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SCAN);
    }
    @OnClick(R.id.tvConfirm)
    void Confirm(){
        if(StringUtils.isTrimEmpty(order.getFaultOrderNo())){
            ToastUtils.showShort("未获取提票单号信息！");
            return;
        }
        if(!isEditEquip){
            if (StringUtils.isTrimEmpty(order.getEquipmentIdx())) {
                ToastUtils.showShort("无有效设备信息！");
                return;
            }
        }
        if(tvEquipCode.getText()==null||StringUtils.isTrimEmpty(tvEquipCode.getText().toString())){
            ToastUtils.showShort("还未输入设备编码信息");
            return;
        }else {
            order.setEquipmentCode(tvEquipCode.getText().toString());
        }
        if(tvEquipName.getText()==null||StringUtils.isTrimEmpty(tvEquipName.getText().toString())){
            ToastUtils.showShort("还未输入设备名称信息");
            return;
        }else {
            order.setEquipmentName(tvEquipName.getText().toString());
        }
        if(etFaultLocation.getText()==null||StringUtils.isTrimEmpty(etFaultLocation.getText().toString())){
            ToastUtils.showShort("还未输入地点部位信息");
            return;
        }else {
            order.setFaultPlace(etFaultLocation.getText().toString());
        }
        if(etFaultDes.getText()==null||StringUtils.isTrimEmpty(etFaultDes.getText().toString())){
            ToastUtils.showShort("还未输入现象描述信息");
            return;
        }else {
            order.setFaultPhenomenon(etFaultDes.getText().toString());
        }
        if(tvUsePlace.getText()!=null&&!StringUtils.isTrimEmpty(tvUsePlace.getText().toString())){
            order.setUsePlace(tvUsePlace.getText().toString());
        }
        if(!StringUtils.isTrimEmpty(businessIdx)){
            order.setBusinessIdx(businessIdx);
        }
        if(!StringUtils.isTrimEmpty(businessEntity)){
            order.setBusinessEntity(businessEntity);
        }
        List<String> paths = new ArrayList<>();
        if(imagess!=null&&imagess.size()>0){
            for(ImagesBean bean:imagess){
                if(!StringUtils.isTrimEmpty(bean.getImagesPath())){
                    paths.add(bean.getImagesPath());
                }
            }
        }
        if(mPresenter!=null){
            showLoading();
            mPresenter.AddFaultTicket(JSON.toJSONString(paths),JSON.toJSONString(order));
        }
    }
}
