package sbgl3.yunda.module.gzcl.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.utilcode.util.StringUtils;
import com.jess.arms.utils.utilcode.util.ToastUtils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.speedata.utils.ProgressDialogUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sbgl3.yunda.R;
import sbgl3.yunda.entry.ImagesBean;
import sbgl3.yunda.globle.adapter.ImagesAdapter;
import sbgl3.yunda.module.fwhjhx.mvp.entity.MateriaBean;
import sbgl3.yunda.module.fwhjhx.mvp.ui.activity.StuffActivity;
import sbgl3.yunda.module.gzcl.di.component.DaggerGzclEditComponent;
import sbgl3.yunda.module.gzcl.di.module.GzclEditModule;
import sbgl3.yunda.module.gzcl.entry.FaultOrderBean;
import sbgl3.yunda.module.gzcl.mvp.contract.GzclEditContract;
import sbgl3.yunda.module.gzcl.mvp.presenter.GzclEditPresenter;
import sbgl3.yunda.module.tpgzpg.entry.BackInfoBean;
import sbgl3.yunda.module.tpgzpg.mvp.ui.adapter.BackInfoAdapter;
import sbgl3.yunda.widget.PhotoReadActivity;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * <li>说明: 故障处理---故障处理详情界面
 * <li>创建人：刘欢
 * <li>创建日期：2018年10月12日
 * <li>修改人：
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 *
 * @author 信息系统事业部设备管理系统项目组
 * @version 3.0
 */
public class GzclEditActivity extends BaseActivity<GzclEditPresenter> implements GzclEditContract.View {

    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.tvEquipCode)
    TextView tvEquipCode;
    @BindView(R.id.tvEquipName)
    TextView tvEquipName;
    @BindView(R.id.tvModel)
    TextView tvModel;
    @BindView(R.id.tvFaultPlae)
    TextView tvFaultPlae;
    @BindView(R.id.tvFaultPhenomenon)
    TextView tvFaultPhenomenon;
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
    @BindView(R.id.cvNotImportant)
    CardView cvNotImportant;
    @BindView(R.id.tvReadMore)
    TextView tvReadMore;
    @BindView(R.id.etcauseAnalysis)
    EditText etcauseAnalysis;
    @BindView(R.id.etRepairContent)
    EditText etRepairContent;
    @BindView(R.id.rlImages)
    RecyclerView rlImages;
    @BindView(R.id.tvConfirm)
    TextView tvConfirm;
    @BindView(R.id.cvConfirm)
    CardView cvConfirm;
    @BindView(R.id.tvStuff)
    TextView tvStuff;
    @BindView(R.id.tvStuffNumber)
    TextView tvStuffNumber;
    @BindView(R.id.rlBackInfo)
    RecyclerView rlBackInfo;
    @BindView(R.id.cvBackInfo)
    CardView cvBackInfo;
    public static int IMAGE_PICKER = 666;
    List<String> ids = new ArrayList<String>();
    List<FaultOrderBean> mFaultOrderList = new ArrayList<>();
    ImagesAdapter imagesAdapter;
    ArrayList<ImagesBean> imagess = new ArrayList<>();
    List<BackInfoBean> backInfoList = new ArrayList<>();
    BackInfoAdapter backInfoAdapter;
    FaultOrderBean faultOrderBean;
    int position;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerGzclEditComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .gzclEditModule(new GzclEditModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_gzcl_edit; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
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
        if (getIntent() != null) {
            mFaultOrderList = (ArrayList<FaultOrderBean>) getIntent().getSerializableExtra("mFaultOrderList");
            position = getIntent().getIntExtra("position", 0);
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
                if ("0".equals(imagess.get(position).getImagesName())) {
                    Intent intent = new Intent(GzclEditActivity.this, ImageGridActivity.class);
                    intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                    startActivityForResult(intent, IMAGE_PICKER);
                } else {
                    Bundle bundle1 = new Bundle();
                    Intent intent = new Intent(GzclEditActivity.this, PhotoReadActivity.class);
                    bundle1.putSerializable("images", imagess);
                    bundle1.putInt("position", position);
                    bundle1.putString("state", "1");
                    intent.putExtras(bundle1);
                    startActivityForResult(intent, ImagePicker.REQUEST_CODE_PREVIEW);
                }
            }
        });
        // 加载退回详情
        loadBackInfo();
        ArmsUtils.configRecycleView(rlBackInfo, new LinearLayoutManager(this));
        backInfoAdapter = new BackInfoAdapter(backInfoList, this);
        rlBackInfo.setAdapter(backInfoAdapter);
        showLoading();
    }

    /**
     * <li>说明：联网获取照片
     * <li>创建人：刘欢
     * <li>创建日期：2018年10月12日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    private void loadImage() {
        if (null != mPresenter) {
            mPresenter.getImages(faultOrderBean.getIdx());
        }
    }

    /**
     * <li>说明：联网获取退回信息
     * <li>创建人：刘欢
     * <li>创建日期：2018年10月15日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    private void loadBackInfo() {
        if (null != mPresenter) {
            Map<String, Object> map = new HashMap<>();
            map.put("propName", "faultOrderIdx");
            map.put("propValue", faultOrderBean.getIdx());
            List<Map<String, Object>> list = new ArrayList<>();
            list.add(map);
            mPresenter.getBackInfo("createTime", JSON.toJSONString(list));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 绑定toobar跟menu
        getMenuInflater().inflate(R.menu.back_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 退回调度
        if (item.getItemId() == R.id.iv_right) {
            Intent intent = new Intent(GzclEditActivity.this, GzclBackDispatcherActivity.class);
            intent.putExtra("idx", faultOrderBean.getIdx());
            startActivityForResult(intent, 1);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * <li>说明：获取照片成功
     * <li>创建人：刘欢
     * <li>创建日期：2018年10月11日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    @Override
    public void getImagesSuccess(List<ImagesBean> images) {
        imagess.clear();
        if (images != null) {
            imagess.addAll(images);
        } else {
            ToastUtils.showShort("无相关照片信息！");
        }
        ImagesBean item = new ImagesBean();
        item.setImagesName("0");
        imagess.add(item);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hideLoading();
                imagesAdapter.notifyDataSetChanged();
            }
        });
        Map<String, String> map = new HashMap<>();
        map.put("repairWorkOrderIdx", faultOrderBean.getIdx());
        mPresenter.getStuffs(0, 200, JSON.toJSONString(map));
    }

    @Override
    public void getStuffSuccess(List<MateriaBean> list) {
        if (list != null && list.size() > 0) {
            int temp = 0;
            for (MateriaBean bean : list) {
                if (bean.getWaitStartTime() != null && bean.getWaitEndTime() == null) {
                    temp++;
                }
            }
            tvStuffNumber.setText("(" + temp + "/" + list.size() + ")");
        } else {
            tvStuffNumber.setText("(0)");
        }
    }

    @Override
    public void UpLoadImagesSuccess(ImagesBean bean) {
        if (imagess.size() > 0) {
            imagess.remove(imagess.size() - 1);
        }
        imagess.add(bean);
        ImagesBean bean1 = new ImagesBean();
        bean1.setImagesName("0");
        imagess.add(bean1);
        imagesAdapter.notifyDataSetChanged();
    }

    /**
     * <li>说明：获取退回信息成功
     * <li>创建人：刘欢
     * <li>创建日期：2018年10月15日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    @Override
    public void loadBackInfo(List<BackInfoBean> list) {
        backInfoList.clear();
        if (list.size() > 0) {
            backInfoList.addAll(list);
            backInfoAdapter.notifyDataSetChanged();
        } else {
            ToastUtils.showShort("无相关退回信息！");
            cvBackInfo.setVisibility(View.GONE);
        }
    }

    @Override
    public void confirmSuccess() {
        ToastUtils.showShort("提交成功!");
        refreshEquipmentList();
        finish();
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
    void hiddenCard() {
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
    void readMore() {
        cvNotImportant.setVisibility(View.VISIBLE);
        tvReadMore.setVisibility(View.GONE);
    }


    @OnClick(R.id.tvStuff)
    void readStuff() {
        Intent intent = new Intent(GzclEditActivity.this, StuffActivity.class);
        intent.putExtra("workorder", faultOrderBean);
        startActivityForResult(intent, 11);
    }

    /**
     * <li>说明：确认
     * <li>创建人：刘欢
     * <li>创建日期：2018年10月12日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    @OnClick(R.id.tvConfirm)
    void confirm() {
        String filePath = "";
        if (imagess != null && imagess.size() > 0) {
            List<String> paths = new ArrayList<>();
            for (ImagesBean bean : imagess) {
                if (!StringUtils.isTrimEmpty(bean.getImagesPath())) {
                    paths.add(bean.getImagesPath());
                }
            }
            filePath = JSON.toJSONString(paths);
        }
        if (!StringUtils.isEmpty(etcauseAnalysis.getText().toString())) {
            faultOrderBean.setCauseAnalysis(etcauseAnalysis.getText().toString());
        }
        if (!StringUtils.isEmpty(etRepairContent.getText().toString())) {
            faultOrderBean.setRepairContent(etRepairContent.getText().toString());
        }
        faultOrderBean.setFaultRecoverTime(System.currentTimeMillis());
        if (null != mPresenter) {
            mPresenter.submitOrder(faultOrderBean.getIdx(), JSON.toJSONString(faultOrderBean), filePath);
        }
    }

    /**
     * <li>说明：刷新列表数据
     * <li>创建人：刘欢
     * <li>创建日期：2018年10月11日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    private void refreshEquipmentList() {
        Intent intent = new Intent();
        intent.setAction("action.backRefreshList");
        sendBroadcast(intent);
    }

    /**
     * <li>说明：界面数据显示
     * <li>创建人：刘欢
     * <li>创建日期：2018年10月11日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    private void setData(FaultOrderBean bean) {
        if (null != bean) {
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
            String str = "";
            if (bean.getModel() != null) {
                str = str + bean.getModel();
            }
            if (bean.getSpecification() != null) {
                str = str + "," + bean.getSpecification();
            } else {
                str = str + "";
            }
            tvModel.setText(str);
            if (null != bean.getFaultPlace()) {
                tvFaultPlae.setText(bean.getFaultPlace());
            } else {
                tvFaultPlae.setText("");
            }
            if (null != bean.getFaultPhenomenon()) {
                tvFaultPhenomenon.setText(bean.getFaultPhenomenon());
            } else {
                tvFaultPhenomenon.setText("");
            }
            if (null != bean.getFaultOrderNo()) {
                tvFaultOrderNo.setText(bean.getFaultOrderNo());
            } else {
                tvFaultOrderNo.setText("");
            }
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String faultOccurTime = df.format(new Date(bean.getFaultOccurTime()));
            tvFaultOccurTime.setText(faultOccurTime);
            if (null != bean.getSubmitEmp()) {
                tvSubmitEmp.setText(bean.getSubmitEmp());
            } else {
                tvSubmitEmp.setText("");
            }
            if (null != bean.getFaultLevel()) {
                tvFaultLevel.setText(bean.getFaultLevel());
            } else {
                tvFaultLevel.setText("");
            }
            if (null != bean.getCauseAnalysis()) {
                etcauseAnalysis.setText(bean.getCauseAnalysis());
            } else {
                etcauseAnalysis.setText("");
            }
            if (null != bean.getRepairContent()) {
                etRepairContent.setText(bean.getRepairContent());
            } else {
                etRepairContent.setText("");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                ArrayList<ImageItem> images = data.getParcelableArrayListExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null && images.size() > 0) {
                    if (mPresenter != null) {
                        showLoading();
                        mPresenter.UploadImage(images.get(0));
                    }
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
        if (requestCode == 11) {
            if (faultOrderBean != null) {
                Map<String, String> map = new HashMap<>();
                map.put("repairWorkOrderIdx", faultOrderBean.getIdx());
                mPresenter.getStuffs(0, 200, JSON.toJSONString(map));
            }
        }
        if (resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
    }

}
