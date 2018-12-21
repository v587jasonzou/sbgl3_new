package sbgl3.yunda.module.fwhjhx.mvp.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.alibaba.fastjson.JSON;
import com.jess.arms.base.AppConstant;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.utilcode.util.BarUtils;
import com.jess.arms.utils.utilcode.util.SnackbarUtils;
import com.jess.arms.utils.utilcode.util.StringUtils;
import com.jess.arms.utils.utilcode.util.ToastUtils;
import com.jess.arms.widget.autolayout.MarqueeView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.speedata.utils.ProgressDialogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
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
import sbgl3.yunda.module.fwhjhx.di.component.DaggerManageWorkorderComponent;
import sbgl3.yunda.module.fwhjhx.di.module.ManageWorkorderModule;
import sbgl3.yunda.module.fwhjhx.mvp.contract.ManageWorkorderContract;
import sbgl3.yunda.module.fwhjhx.mvp.entity.EosDictEntry;
import sbgl3.yunda.module.fwhjhx.mvp.entity.MateriaBean;
import sbgl3.yunda.module.fwhjhx.mvp.entity.RepairWorkOrder;
import sbgl3.yunda.module.fwhjhx.mvp.presenter.ManageWorkorderPresenter;
import sbgl3.yunda.module.fwhjhx.mvp.ui.adapter.RepairUnitsAdapter;
import sbgl3.yunda.widget.PhotoReadActivity;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class ManageWorkorderActivity extends BaseActivity<ManageWorkorderPresenter> implements ManageWorkorderContract.View {
    public static int IMAGE_PICKER = 666;
    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.tvNumber)
    TextView tvNumber;
    @BindView(R.id.tvOrderContent)
    TextView tvOrderContent;
    @BindView(R.id.tvProccess)
    TextView tvProccess;
    @BindView(R.id.ivDelete)
    ImageView ivDelete;
    @BindView(R.id.ivHistory)
    ImageView ivHistory;
    @BindView(R.id.rlUnit)
    RecyclerView rlUnit;
    @BindView(R.id.rlImages)
    RecyclerView rlImages;
    @BindView(R.id.tvFixUser)
    TextView tvFixUser;
    @BindView(R.id.cvChangeServer)
    CardView cvChangeServer;
    @BindView(R.id.tvStuff)
    TextView tvStuff;
    @BindView(R.id.tvStuffNumber)
    TextView tvStuffNumber;
    @BindView(R.id.tvConfirm)
    TextView tvConfirm;
    @BindView(R.id.cvConfirm)
    CardView cvConfirm;
    @BindView(R.id.etSuggess)
    EditText etSuggess;
    @BindView(R.id.ivTriangle)
    ImageView ivTriangle;
    RepairWorkOrder order;
    ArrayList<ImagesBean> imagess = new ArrayList<>();
    Integer position;
    ImagesAdapter imagesAdapter;
    RepairUnitsAdapter unitsAdapter;
    List<EosDictEntry> units = new ArrayList<>();
    List<String> historyRepairRecords = new ArrayList<>();
    PopupMenu popupMenu;
    Dialog userDialog;

    TextView tvUserBack;
    EditText etUserSearch;
    ImageView ivUserSearch;
    RecyclerView rlChooseUsers;
    SmartRefreshLayout srUserRefresh;
    MarqueeView tvChooseUser;
    TextView tvSelecNumbs,tvUserClear;
    TextView tvUserConfirm;
    CardView cvUserConfirm;
    List<UserInfo> mUsers = new ArrayList<>();
    List<UserInfo> selectedUsers = new ArrayList<>();
    AddUserAdapter userAdapter;
    String queryUserKey = "";
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerManageWorkorderComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .manageWorkorderModule(new ManageWorkorderModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_manage_workorder; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        setTranslucentStatus();
        setSupportActionBar(menuTp);
        menuTp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (mPresenter != null) {
            showLoading();
            mPresenter.getUnits();
        }
        Intent intent = getIntent();
        if (intent != null) {
            order = (RepairWorkOrder) intent.getSerializableExtra("workorder");
        }
        setData();
        ArmsUtils.configRecycleView(rlUnit, new GridLayoutManager(ManageWorkorderActivity.this, 4, OrientationHelper.VERTICAL, false));
        unitsAdapter = new RepairUnitsAdapter(units);
        rlUnit.setAdapter(unitsAdapter);
        unitsAdapter.setOnItemClickListener(new DefaultAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int viewType, Object data, int position) {
                String str = "";
                if (etSuggess.getText() == null) {
                    str = units.get(position).getDictname();
                } else {
                    str = etSuggess.getText().toString() + units.get(position).getDictname();
                }
                etSuggess.setText(str);
            }
        });
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etSuggess != null) {
                    etSuggess.setText("");
                }
            }
        });
        popupMenu = new PopupMenu(this, etSuggess);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                etSuggess.setText(item.getTitle());
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(etSuggess.getWindowToken(), 0);
                }
                popupMenu.dismiss();
                return false;
            }
        });
        ArmsUtils.configRecycleView(rlImages, new GridLayoutManager(ManageWorkorderActivity.this, 3, OrientationHelper.VERTICAL, false));
        imagesAdapter = new ImagesAdapter(imagess, this);
        rlImages.setAdapter(imagesAdapter);
        imagesAdapter.setOnItemClickListener(new DefaultAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int viewType, Object data, int position) {
                if ("0".equals(imagess.get(position).getImagesName())) {
                    Intent intent = new Intent(ManageWorkorderActivity.this, ImageGridActivity.class);
                    intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                    startActivityForResult(intent, IMAGE_PICKER);
                } else {
                    Bundle bundle1 = new Bundle();
                    Intent intent = new Intent(ManageWorkorderActivity.this, PhotoReadActivity.class);
                    bundle1.putSerializable("images", imagess);
                    bundle1.putInt("position", position);
                    bundle1.putString("state", "order");
                    intent.putExtras(bundle1);
                    startActivityForResult(intent, ImagePicker.REQUEST_CODE_PREVIEW);
                }
            }
        });
        setDialog();
    }

    private void setData(){
        if (order != null) {
            showLoading();
            mPresenter.getImages(order.getIdx());
            ivTriangle.setVisibility(View.VISIBLE);
            if(!StringUtils.isTrimEmpty(order.getColor())){
                String color = order.getColor().toUpperCase();
                ivTriangle.setVisibility(View.VISIBLE);
                if(color.equals("#FF0000")){
                    ivTriangle.setImageResource(R.drawable.triangle_red_icon);
                }else if(color.equals("#FFC000")){
                    ivTriangle.setImageResource(R.drawable.triangle_yellow_icon);
                }else if(color.equals("#0070C0")){
                    ivTriangle.setImageResource(R.drawable.triangle_blue_icon);
                }else {
                    ivTriangle.setImageResource(R.drawable.triangle_green_icon);
                }
            }else {
                ivTriangle.setVisibility(View.GONE);
            }

            if (order.getWorkContent() != null) {
                tvOrderContent.setText(order.getWorkContent());
            }
            if (order.getProcessStandard() != null) {
                tvProccess.setText(order.getProcessStandard());
            }
            tvNumber.setText(order.getSortNo().toString());
            if(order.getRepairRecord()!=null){
                etSuggess.setText(order.getRepairRecord());
            }else {
                etSuggess.setText("合格");
            }
            if(order.getOtherWorkerName()!=null){
                tvFixUser.setText(order.getOtherWorkerName());
            }
        }
    }
    private void setDialog() {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentview = inflater.inflate(R.layout.dialog_add_user, null);

        userDialog = new Dialog(this, R.style.FullScreenStyle);
        Display defaultDisplay = userDialog.getWindow().getWindowManager()
                .getDefaultDisplay();
        int height = defaultDisplay.getHeight();
        int width = defaultDisplay.getWidth();
        contentview.setMinimumHeight(height);
        contentview.setMinimumWidth(width);
        tvUserBack = (TextView) contentview.findViewById(R.id.tvUserBack);
        etUserSearch = (EditText) contentview.findViewById(R.id.etUserSearch);
        ivUserSearch = (ImageView) contentview.findViewById(R.id.ivUserSearch);
        rlChooseUsers = (RecyclerView) contentview.findViewById(R.id.rlChooseUsers);
        srUserRefresh = (SmartRefreshLayout) contentview.findViewById(R.id.srUserRefresh);
        tvChooseUser = (MarqueeView) contentview.findViewById(R.id.tvChooseUser);
        tvSelecNumbs = (TextView) contentview.findViewById(R.id.tvSelecNumbs);
        tvUserConfirm = (TextView) contentview.findViewById(R.id.tvUserConfirm);
        cvUserConfirm = (CardView) contentview.findViewById(R.id.cvUserConfirm);
        tvUserClear = (TextView)contentview.findViewById(R.id.tvUserClear);
        tvUserClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvChooseUser.setText("");
                tvSelecNumbs.setText("0");
                selectedUsers.clear();
                for(UserInfo user : mUsers){
                    user.setChecked(UserInfo.UNCHECKED);
                }
                userAdapter.notifyDataSetChanged();
            }
        });
        userDialog.setContentView(contentview);
        ArmsUtils.configRecycleView(rlChooseUsers, new LinearLayoutManager(this));
        userAdapter = new AddUserAdapter(mUsers);
        rlChooseUsers.setAdapter(userAdapter);
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
        tvUserBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDialog.dismiss();
            }
        });
        ivUserSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading();
                if(etUserSearch.getText()!=null){
                    queryUserKey = etUserSearch.getText().toString();
                }else {
                    queryUserKey = "";
                }
                if (mPresenter != null) {
                    mPresenter.getSelectUsers(queryUserKey);
                }
            }
        });
        tvUserConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedUsers != null & selectedUsers.size() > 0) {
                    String str = "";
                    for (int i = 0; i < selectedUsers.size(); i++) {
                        if (i == 0) {
                            str = str + selectedUsers.get(i).getEmpname();
                        } else {
                            str = str + "," + selectedUsers.get(i).getEmpname();
                        }
                    }
                    tvFixUser.setText(str);
                } else {
                    tvFixUser.setText("选择辅修人员");
                }
                userDialog.dismiss();
            }
        });
    }

    @Override
    public void showLoading() {
        ProgressDialogUtils.showProgressDialog(this,"加载中，请稍后");
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
    public void getImagesSuccess(List<ImagesBean> images) {
        imagess.clear();
        if (images != null) {
            imagess.addAll(images);
        }
        ImagesBean item = new ImagesBean();
        item.setImagesName("0");
        imagess.add(item);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imagesAdapter.notifyDataSetChanged();
                showLoading();
            }
        });
        Map<String, String> map = new HashMap<>();
        map.put("repairWorkOrderIdx", order.getIdx());
        mPresenter.getStuffs(0, 200, JSON.toJSONString(map));
    }

    @OnClick(R.id.ivHistory)
    void getHistory() {
        if (mPresenter != null) {
            if (historyRepairRecords.size() == 0) {
                showLoading();
                mPresenter.getHistorys(0, 200, order.getDefineIdx());
            } else {
                popupMenu.show();
            }
        }

    }

    @OnClick(R.id.cvChangeServer)
    void getUsers() {
        if (mUsers.size() == 0) {
            if (mPresenter != null) {
                showLoading();
                mPresenter.getSelectUsers("");
            }
        } else {
            userDialog.show();
        }
    }

    @Override
    public void confirmSuccess() {
        ToastUtils.showShort("提交工单成功");
        if(mPresenter!=null){
            showLoading();
            mPresenter.getNextOrder(order.getIdx());
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

    @Override
    public void getUnitSuccess(List<EosDictEntry> list) {
        units.clear();
        if (list != null && list.size() > 0) {
            units.addAll(list);
        }
        unitsAdapter.notifyDataSetChanged();
    }

    @Override
    public void getHistorysSuccess(List<String> list) {
        historyRepairRecords.clear();
        if (list != null && list.size() > 0) {
            historyRepairRecords.addAll(list);
        }
        if (historyRepairRecords.size() > 0) {
            Menu menu_more = popupMenu.getMenu();
            menu_more.clear();
            int size = historyRepairRecords.size();
            for (int i = 0; i < size; i++) {
                menu_more.add(Menu.NONE, i, i, historyRepairRecords.get(i));
            }
            popupMenu.show();
        }
    }

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
        if(selectedUsers.size()>0){
            for(UserInfo muer:mUsers){
                for(UserInfo selectuser:selectedUsers){
                    if(muer.getEmpid().equals(selectuser.getEmpid())){
                        muer.setChecked(UserInfo.CHECKED);
                    }
                }
            }
        }
        if(tvFixUser.getText()!=null&&!tvFixUser.getText().toString().equals("")){
            for(UserInfo muer:mUsers){
                if(tvFixUser.getText().toString().contains(muer.getEmpname())){
                    muer.setChecked(UserInfo.CHECKED);
                }
            }
        }
        userAdapter.notifyDataSetChanged();
    }

    @Override
    public void getStuffSuccess(List<MateriaBean> list) {
        if(list!=null&&list.size()>0){
            int temp = 0;
            for(MateriaBean bean:list){
                if (bean.getWaitStartTime() != null && bean.getWaitEndTime() == null) {
                    temp++;
                }
            }
            tvStuffNumber.setText("("+temp+"/"+list.size()+")");
        }else {
            tvStuffNumber.setText("(0)");
        }
    }

    @Override
    public void getNextOrderSuccess(RepairWorkOrder order) {
        if(order!=null){
            this.order = order;
            setData();
        }else {
            ToastUtils.showShort("已完成所有工单提交");
            finish();
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
        if(requestCode==11){
            if(order!=null){
                Map<String, String> map = new HashMap<>();
                map.put("repairWorkOrderIdx", order.getIdx());
                mPresenter.getStuffs(0, 200, JSON.toJSONString(map));
            }
        }
    }

    @Subscribe
    public void getChecked(MessageBean messageBean) {
        if (messageBean != null) {
            if (messageBean.getMsgType().equals("UserSelected")) {
                int position = messageBean.getPosition();
                boolean isChecked = messageBean.getSuccess();
                if (isChecked) {
                    int temp = 0;
                    for(UserInfo user:selectedUsers){
                        if(mUsers.get(position).getEmpid().equals(user.getEmpid())){
                            temp++;
                            break;
                        }
                    }
                    if(temp==0){
                        selectedUsers.add(mUsers.get(position));
                    }
                } else {
                    Long empid = mUsers.get(position).getEmpid();
                    for(UserInfo user:selectedUsers){
                        if(user.getEmpid().equals(empid)){
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
    @OnClick(R.id.tvStuff)
    void ReadStuff(){
        Intent intent = new Intent(this,StuffActivity.class);
        intent.putExtra("workorder",order);
        startActivityForResult(intent,11);
    }
    @OnClick(R.id.tvConfirm)
    void ConfirmOrder(){
        String repari = "";
        String Filepath = "";
        String otherworker = "";
        if(etSuggess.getText()!=null&&!"".equals(etSuggess.getText().toString())){
            repari = etSuggess.getText().toString();
        }
        if(tvFixUser.getText()!=null&&!tvFixUser.getText().toString().equals("")
                &&!tvFixUser.getText().toString().equals("选择辅修人员")){
            otherworker = tvFixUser.getText().toString();
        }
        if(imagess!=null&&imagess.size()>0){
            List<String> paths = new ArrayList<>();
            for(ImagesBean bean:imagess){
                if(!StringUtils.isTrimEmpty(bean.getImagesPath())){
                    paths.add(bean.getImagesPath());
                }
            }
            Filepath = JSON.toJSONString(paths);
        }
        if(mPresenter!=null){
            showLoading();
            mPresenter.ConfirmOrder(order.getIdx(),otherworker,repari,Filepath);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
