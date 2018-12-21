package sbgl3.yunda.module.tpgzpg.mvp.ui.activity;

import android.app.Dialog;
import android.content.Context;
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
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.utilcode.util.ToastUtils;
import com.jess.arms.widget.autolayout.MarqueeView;
import com.lzy.imagepicker.ImagePicker;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.speedata.utils.ProgressDialogUtils;
import com.zhy.autolayout.AutoLinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
import sbgl3.yunda.entry.MessageBean;
import sbgl3.yunda.entry.UserInfo;
import sbgl3.yunda.globle.adapter.AddUserAdapter;
import sbgl3.yunda.globle.adapter.ImagesAdapter;
import sbgl3.yunda.module.tpgzpg.di.component.DaggerTpgzpgEditComponent;
import sbgl3.yunda.module.tpgzpg.di.module.TpgzpgEditModule;
import sbgl3.yunda.module.tpgzpg.entry.BackInfoBean;
import sbgl3.yunda.module.tpgzpg.entry.FaultOrderBean;
import sbgl3.yunda.module.tpgzpg.mvp.contract.TpgzpgEditContract;
import sbgl3.yunda.module.tpgzpg.mvp.presenter.TpgzpgEditPresenter;
import sbgl3.yunda.module.tpgzpg.mvp.ui.adapter.BackInfoAdapter;
import sbgl3.yunda.widget.PhotoReadActivity;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * <li>说明: 提票工长派工---提票详情界面
 * <li>创建人：刘欢
 * <li>创建日期：2018年10月9日
 * <li>修改人：
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 *
 * @author 信息系统事业部设备管理系统项目组
 * @version 3.0
 */
public class TpgzpgEditActivity extends BaseActivity<TpgzpgEditPresenter> implements TpgzpgEditContract.View {

    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.tvFaultOrderNo)
    TextView tvFaultOrderNo;
    @BindView(R.id.tvEquipCode)
    TextView tvEquipCode;
    @BindView(R.id.tvEquipName)
    TextView tvEquipName;
    @BindView(R.id.tvEquipModel)
    TextView tvEquipModel;
    @BindView(R.id.tvFaultOccurTime)
    TextView tvFaultOccurTime;
    @BindView(R.id.tvFaultLevel)
    TextView tvFaultLevel;
    @BindView(R.id.tvFaultPlae)
    TextView tvFaultPlae;
    @BindView(R.id.tvFaultPhenomenon)
    TextView tvFaultPhenomenon;
    @BindView(R.id.tvSubmitEmp)
    TextView tvSubmitEmp;
    @BindView(R.id.rlImages)
    RecyclerView rlImages;
    @BindView(R.id.tvConfirm)
    TextView tvConfirm;
    @BindView(R.id.cvConfirm)
    CardView cvConfirm;
    @BindView(R.id.rlBackInfo)
    RecyclerView rlBackInfo;
    @BindView(R.id.cvImages)
    CardView cvImages;
    @BindView(R.id.cvBackInfo)
    CardView cvBackInfo;
    List<FaultOrderBean> mFaultOrderList = new ArrayList<>();
    FaultOrderBean faultOrderBean;
    int position;
    ImagesAdapter imagesAdapter;
    ArrayList<ImagesBean> imagess = new ArrayList<>();
    List<BackInfoBean> backInfoList = new ArrayList<>();
    BackInfoAdapter backInfoAdapter;
    Dialog userDialog;
    TextView tvUserBack;
    EditText etUserSearch;
    AutoLinearLayout ivEditDelete;
    ImageView ivUserSearch;
    RecyclerView rlChooseUsers;
    SmartRefreshLayout srUserRefresh;
    MarqueeView tvChooseUser;
    TextView tvSelecNumbs, tvUserClear;
    TextView tvUserConfirm;
    CardView cvUserConfirm;
    List<UserInfo> mUsers = new ArrayList<>();
    List<UserInfo> selectedUsers = new ArrayList<>();
    AddUserAdapter userAdapter;
    String queryUserKey = "";

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerTpgzpgEditComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .tpgzpgEditModule(new TpgzpgEditModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_tpgzpg_edit; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
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
        if (null != getIntent()) {
            mFaultOrderList = (ArrayList<FaultOrderBean>) getIntent().getSerializableExtra("mFaultOrderList");
            position = getIntent().getIntExtra("position", 0);
            faultOrderBean = mFaultOrderList.get(position);
            setData(faultOrderBean);
        }
        // 加载照片
        loadImage();
        ArmsUtils.configRecycleView(rlImages, new GridLayoutManager(this, 3, OrientationHelper.VERTICAL, false));
        imagesAdapter = new ImagesAdapter(imagess, this);
        rlImages.setAdapter(imagesAdapter);
        imagesAdapter.setOnItemClickListener(new DefaultAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int viewType, Object data, int position) {
                Bundle bundle1 = new Bundle();
                Intent intent = new Intent(TpgzpgEditActivity.this, PhotoReadActivity.class);
                bundle1.putSerializable("images", imagess);
                bundle1.putInt("position", position);
                bundle1.putString("state", "1");
                intent.putExtras(bundle1);
                startActivityForResult(intent, ImagePicker.REQUEST_CODE_PREVIEW);
            }
        });
        // 加载退回详情
        loadBackInfo();
        ArmsUtils.configRecycleView(rlBackInfo, new LinearLayoutManager(this));
        backInfoAdapter = new BackInfoAdapter(backInfoList, this);
        rlBackInfo.setAdapter(backInfoAdapter);
        setDialog();
        showLoading();
    }

    /**
     * <li>说明：联网获取照片
     * <li>创建人：刘欢
     * <li>创建日期：2018年10月9日
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
     * <li>创建日期：2018年10月9日
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

    /**
     * <li>说明：人员选择全屏对话框
     * <li>创建人：刘欢
     * <li>创建日期：2018年10月9日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    private void setDialog() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentview = inflater.inflate(R.layout.dialog_add_repair_user, null);
        userDialog = new Dialog(this, R.style.FullScreenStyle);
        Display defaultDisplay = userDialog.getWindow().getWindowManager().getDefaultDisplay();
        int height = defaultDisplay.getHeight();
        int width = defaultDisplay.getWidth();
        contentview.setMinimumHeight(height);
        contentview.setMinimumWidth(width);
        tvUserBack = (TextView) contentview.findViewById(R.id.tvUserBack);
        etUserSearch = (EditText) contentview.findViewById(R.id.etUserSearch);
        ivUserSearch = (ImageView) contentview.findViewById(R.id.ivUserSearch);
        ivEditDelete = (AutoLinearLayout) contentview.findViewById(R.id.ivEditDelete);
        rlChooseUsers = (RecyclerView) contentview.findViewById(R.id.rlChooseUsers);
        srUserRefresh = (SmartRefreshLayout) contentview.findViewById(R.id.srUserRefresh);
        tvChooseUser = (MarqueeView) contentview.findViewById(R.id.tvChooseUser);
        tvSelecNumbs = (TextView) contentview.findViewById(R.id.tvSelecNumbs);
        tvUserConfirm = (TextView) contentview.findViewById(R.id.tvUserConfirm);
        cvUserConfirm = (CardView) contentview.findViewById(R.id.cvUserConfirm);
        tvUserClear = (TextView) contentview.findViewById(R.id.tvUserClear);
        tvUserClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearDialogDatas();
            }
        });
        userDialog.setContentView(contentview);
        ArmsUtils.configRecycleView(rlChooseUsers, new LinearLayoutManager(this));
        userAdapter = new AddUserAdapter(mUsers);
        rlChooseUsers.setAdapter(userAdapter);
        // 刷新
        srUserRefresh.setNoMoreData(true);
        srUserRefresh.setRefreshHeader(new ClassicsHeader(this));
        srUserRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                showLoading();
                if (mPresenter != null) {
                    mPresenter.getSelectUsers(queryUserKey);
                }

            }
        });
        // 返回
        tvUserBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearDialogDatas();
                userDialog.dismiss();
            }
        });
        //查询（搜索）人员
        ivUserSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading();
                if (etUserSearch.getText() != null) {
                    queryUserKey = etUserSearch.getText().toString();
                } else {
                    queryUserKey = "";
                }
                if (mPresenter != null) {
                    mPresenter.getSelectUsers(queryUserKey);
                }
            }
        });
        // 清空搜索框并刷新
        ivEditDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etUserSearch.setText("");
                showLoading();
                if (etUserSearch.getText() != null) {
                    queryUserKey = etUserSearch.getText().toString();
                } else {
                    queryUserKey = "";
                }
                if (mPresenter != null) {
                    mPresenter.getSelectUsers(queryUserKey);
                }
            }
        });
        // 派工
        tvUserConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedUsers == null || selectedUsers.size() <= 0) {
                    ToastUtils.showShort("修理人不能为空！");
                    return;
                }
                FaultOrderBean paramFo = new FaultOrderBean();
                StringBuffer empNameTemp = new StringBuffer();
                StringBuffer empIdTemp = new StringBuffer();
                int i = 0;
                for (UserInfo emp : selectedUsers) {
                    empNameTemp.append(emp.getEmpname());
                    empIdTemp.append(emp.getEmpid());
                    if (i < selectedUsers.size() - 1) {
                        empNameTemp.append(",");
                        empIdTemp.append(",");
                    }
                    i++;
                }
                paramFo.setRepairEmp(empNameTemp.toString());
                paramFo.setRepairEmpId(empIdTemp.toString());
                if (null != mPresenter) {
                    mPresenter.confirm(JSON.toJSONString(new String[]{faultOrderBean.getIdx()}), JSON.toJSONString(paramFo));
                }
                refreshEquipmentList();
                clearDialogDatas();
                userDialog.dismiss();
                finish();
            }
        });
    }

    /**
     * <li>说明：清除dialog数据
     * <li>创建人：刘欢
     * <li>创建日期：2018年10月10日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    public void clearDialogDatas() {
        tvChooseUser.setText("");
        tvSelecNumbs.setText("0");
        selectedUsers.clear();
        for (UserInfo user : mUsers) {
            user.setChecked(UserInfo.UNCHECKED);
        }
        userAdapter.notifyDataSetChanged();
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
        ButterKnife.bind(this);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 绑定toobar跟menu
        getMenuInflater().inflate(R.menu.back_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.iv_right) {
            Intent intent = new Intent(TpgzpgEditActivity.this, BackDispatcherActivity.class);
            intent.putExtra("idx", faultOrderBean.getIdx());
            startActivityForResult(intent, 1);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
    }

    /**
     * <li>说明：获取照片成功
     * <li>创建人：刘欢
     * <li>创建日期：2018年10月9日
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
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hideLoading();
                imagesAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * <li>说明：获取退回信息成功
     * <li>创建人：刘欢
     * <li>创建日期：2018年10月9日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    @Override
    public void loadBackInfo(List<BackInfoBean> list) {
        backInfoList.clear();
        if (list.size()>0) {
            backInfoList.addAll(list);
            backInfoAdapter.notifyDataSetChanged();
        } else {
            ToastUtils.showShort("无相关退回信息！");
            cvBackInfo.setVisibility(View.GONE);
        }
    }

    /**
     * <li>说明：获取施修人员成功
     * <li>创建人：刘欢
     * <li>创建日期：2018年10月9日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    @Override
    public void getFixUserSuccess(List<UserInfo> list) {
        srUserRefresh.finishRefresh();
        mUsers.clear();
        if (list != null && list.size() > 0) {
            mUsers.addAll(list);
        }
        if (!userDialog.isShowing()) {
            userDialog.show();
        }
        if (selectedUsers.size() > 0) {
            for (UserInfo muer : mUsers) {
                for (UserInfo selectuser : selectedUsers) {
                    if (muer.getEmpid().equals(selectuser.getEmpid())) {
                        muer.setChecked(UserInfo.CHECKED);
                    }
                }
            }
        }
        userAdapter.notifyDataSetChanged();
    }

    @Subscribe
    public void getChecked(MessageBean messageBean) {
        if (messageBean != null) {
            if (messageBean.getMsgType().equals("UserSelected")) {
                int position = messageBean.getPosition();
                boolean isChecked = messageBean.getSuccess();
                if (isChecked) {
                    int temp = 0;
                    for (UserInfo user : selectedUsers) {
                        if (mUsers.get(position).getEmpid().equals(user.getEmpid())) {
                            temp++;
                            break;
                        }
                    }
                    if (temp == 0) {
                        selectedUsers.add(mUsers.get(position));
                    }
                } else {
                    Long empid = mUsers.get(position).getEmpid();
                    for (UserInfo user : selectedUsers) {
                        if (user.getEmpid().equals(empid)) {
                            selectedUsers.remove(user);
                            break;
                        }
                    }
                }
                if (selectedUsers != null & selectedUsers.size() > 0) {
                    String str = "";
                    for (int i = 0; i < selectedUsers.size(); i++) {
                        if (i == 0) {
                            str = str + selectedUsers.get(i).getEmpname();
                        } else {
                            str = str + " " + selectedUsers.get(i).getEmpname();
                        }
                    }
                    tvChooseUser.setText(str);
                    tvSelecNumbs.setText(selectedUsers.size() + "");
                } else {
                    tvChooseUser.setText("");
                    tvSelecNumbs.setText("0");
                }
            }
        }
    }

    /**
     * <li>说明：选择维修人
     * <li>创建人：刘欢
     * <li>创建日期：2018年10月8日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    @OnClick(R.id.tvConfirm)
    void confirm() {
        if (mUsers.size() == 0) {
            if (mPresenter != null) {
                showLoading();
                mPresenter.getSelectUsers("");
            }
        } else {
            userDialog.show();
        }
    }

    /**
     * <li>说明：界面数据显示
     * <li>创建人：刘欢
     * <li>创建日期：2018年10月8日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    private void setData(FaultOrderBean bean) {
        if (null != bean) {
            if (null != bean.getFaultOrderNo()) {
                tvFaultOrderNo.setText(bean.getFaultOrderNo());
            } else {
                tvFaultOrderNo.setText("");
            }
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
            if (null != bean.getSpecification()) {
                if (null != bean.getModel()) {
                    tvEquipModel.setText(bean.getSpecification() + "/" + bean.getModel());
                } else {
                    tvEquipModel.setText(bean.getSpecification().toString());
                }
            } else {
                if (null != bean.getModel()) {
                    tvEquipModel.setText(bean.getModel().toString());
                } else {
                    tvEquipModel.setText("");
                }
            }
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String faultOccurTime = df.format(new Date(bean.getFaultOccurTime()));
            tvFaultOccurTime.setText(faultOccurTime);
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
            if (null != bean.getFaultLevel()) {
                tvFaultLevel.setText(bean.getFaultLevel());
            } else {
                tvFaultLevel.setText("");
            }
            if (null != bean.getSubmitEmp()) {
                tvSubmitEmp.setText(bean.getSubmitEmp());
            } else {
                tvSubmitEmp.setText("");
            }
        }
    }

    // 刷新设备列表
    private void refreshEquipmentList() {
        Intent intent = new Intent();
        intent.setAction("action.refreshList");
        sendBroadcast(intent);
    }
}
