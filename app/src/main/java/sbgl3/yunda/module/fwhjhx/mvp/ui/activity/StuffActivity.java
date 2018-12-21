package sbgl3.yunda.module.fwhjhx.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.utilcode.util.ToastUtils;
import com.jess.arms.widget.MySwipeMenuListView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.speedata.utils.ProgressDialogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sbgl3.yunda.R;
import sbgl3.yunda.module.fwhjhx.di.component.DaggerStuffComponent;
import sbgl3.yunda.module.fwhjhx.di.module.StuffModule;
import sbgl3.yunda.module.fwhjhx.mvp.contract.StuffContract;
import sbgl3.yunda.module.fwhjhx.mvp.entity.MateriaBean;
import sbgl3.yunda.module.fwhjhx.mvp.entity.RepairWorkOrder;
import sbgl3.yunda.module.fwhjhx.mvp.entity.StuffParams;
import sbgl3.yunda.module.fwhjhx.mvp.presenter.StuffPresenter;
import sbgl3.yunda.module.fwhjhx.mvp.ui.adapter.StuffAdapter;
import sbgl3.yunda.module.gzcl.entry.FaultOrderBean;
import sbgl3.yunda.widget.BaseDialog;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.scwang.smartrefresh.layout.util.DensityUtil.dp2px;


public class StuffActivity extends BaseActivity<StuffPresenter> implements StuffContract.View, StuffAdapter.OnItemEditListner {

    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.rlStuffs)
    MySwipeMenuListView rlStuffs;

    @BindView(R.id.tvAddStuff)
    TextView tvAddStuff;
    @BindView(R.id.root_layout)
    LinearLayout lin;
    List<MateriaBean> stuffs = new ArrayList<>();
    StuffAdapter adapter;
    RepairWorkOrder order;
    FaultOrderBean faultOrder;
    Object object;
    List<StuffParams> params = new ArrayList<>();

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerStuffComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .stuffModule(new StuffModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_stuff; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        Intent intent = getIntent();
        if (intent != null) {
            object = intent.getSerializableExtra("workorder");
            if (object instanceof RepairWorkOrder){
                order = (RepairWorkOrder)object;
            } else if (object instanceof FaultOrderBean){
                faultOrder = (FaultOrderBean) object;
            }
        }
        setTranslucentStatus();
        setSupportActionBar(menuTp);
        menuTp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        adapter = new StuffAdapter(this, stuffs);
        adapter.setView(menuTp);
        rlStuffs.setAdapter(adapter);
        adapter.setOnItemEditLisner(this);
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                SwipeMenuItem openItem2 = new SwipeMenuItem(
                        StuffActivity.this);
                // set item background
                openItem2.setBackground(new ColorDrawable(Color.rgb(0xff,
                        0x37, 0x37)));
                // set item width
                openItem2.setWidth(dp2px(90));
                // set item title
                openItem2.setTitle("删除");
                // set item title fontsize
                openItem2.setTitleSize(18);
                // set item title font color
                openItem2.setTitleColor(Color.WHITE);

                menu.addMenuItem(openItem2);

            }
        };
        rlStuffs.setMenuCreator(creator);
        rlStuffs.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                BaseDialog.setDialog(StuffActivity.this, "确定删除当前物料?", "确定",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showLoading();
                                List<String> ids = new ArrayList<>();
                                ids.add(stuffs.get(position).getIdx());
                                mPresenter.DeleteStuff(JSON.toJSONString(ids));
                                BaseDialog.dissmiss();
                            }
                        },
                        "取消"
                        , new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                BaseDialog.dissmiss();
                            }
                        });
                return false;
            }
        });
        rlStuffs.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);

        if (order != null) {
            if (mPresenter != null) {
                showLoading();
                Map<String, String> map = new HashMap<>();
                map.put("repairWorkOrderIdx", order.getIdx());
                mPresenter.getStuffs(0, 200, JSON.toJSONString(map));
            }
        } else if (faultOrder!=null){
            if (mPresenter != null) {
                showLoading();
                Map<String, String> map = new HashMap<>();
                map.put("repairWorkOrderIdx", faultOrder.getIdx());
                mPresenter.getStuffs(0, 200, JSON.toJSONString(map));
            }
        }

    }

    @Override
    public void showLoading() {
        ProgressDialogUtils.showProgressDialog(this, "加载中,请稍后");
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
    public void getStuffsSucceess(List<MateriaBean> list) {
        stuffs.clear();
        if (list != null && list.size() > 0) {
            stuffs.addAll(list);
        }
        ArmsUtils.setListViewHeightBasedOnChildren(rlStuffs);
        adapter.notifyDataSetChanged();
        if (params.size() == 0) {
            Map<String, Object> map = new HashMap<>();
            mPresenter.getStuffParams(0, 200, JSON.toJSONString(map));
        } else {
            hideLoading();
        }
    }

    @Override
    public void getStuffsParamsSuccess(List<StuffParams> list) {
        if (list != null && list.size() > 0) {
            params.clear();
            params.addAll(list);
            adapter.setStuffParams(list);
        }

    }

    @Override
    public void UpdataStuffSuccess(String type) {
        if(type.equals("添加")){
            ToastUtils.showShort("添加物料信息成功");
        }else {
            ToastUtils.showShort("修改物料信息成功");
        }
        isEdit = false;
        showLoading();
        Map<String, String> map = new HashMap<>();
        if (order!=null){
            map.put("repairWorkOrderIdx", order.getIdx());
        } else if (faultOrder!=null){
            map.put("repairWorkOrderIdx", faultOrder.getIdx());
        }
     //   map.put("repairWorkOrderIdx", order.getIdx());
        mPresenter.getStuffs(0, 200, JSON.toJSONString(map));
    }

    @Override
    public void StartStuffWaitSuccess() {
        ToastUtils.showShort("开始待料成功！");
        showLoading();
        Map<String, String> map = new HashMap<>();
        if (order!=null){
            map.put("repairWorkOrderIdx", order.getIdx());
        } else if (faultOrder!=null){
            map.put("repairWorkOrderIdx", faultOrder.getIdx());
        }
     //   map.put("repairWorkOrderIdx", order.getIdx());
        mPresenter.getStuffs(0, 200, JSON.toJSONString(map));
    }

    @Override
    public void EndStuffWaitSuccess() {
        ToastUtils.showShort("结束待料成功！");
        showLoading();
        Map<String, String> map = new HashMap<>();
        if (order!=null){
            map.put("repairWorkOrderIdx", order.getIdx());
        } else if (faultOrder!=null){
            map.put("repairWorkOrderIdx", faultOrder.getIdx());
        }
    //    map.put("repairWorkOrderIdx", order.getIdx());
        mPresenter.getStuffs(0, 200, JSON.toJSONString(map));
    }

    @Override
    public void DeleteStuffSuccess() {
        ToastUtils.showShort("删除物料成功");
        showLoading();
        Map<String, String> map = new HashMap<>();
        if (order!=null){
            map.put("repairWorkOrderIdx", order.getIdx());
        } else if (faultOrder!=null){
            map.put("repairWorkOrderIdx", faultOrder.getIdx());
        }
     //   map.put("repairWorkOrderIdx", order.getIdx());
        mPresenter.getStuffs(0, 200, JSON.toJSONString(map));
    }

    boolean isEdit = false;
    boolean isstup = false;
    @OnClick(R.id.tvAddStuff)
    void addStuffs() {
        if (isEdit) {
            ToastUtils.showShort("还有正在编辑的物料信息未处理，请编辑完成后再添加");
        } else {
            MateriaBean materiaBean = new MateriaBean();
            materiaBean.setEdit(true);
            materiaBean.setStatus("添加");
            stuffs.add(materiaBean);
            ArmsUtils.setListViewHeightBasedOnChildren(rlStuffs);
            adapter.notifyDataSetChanged();
            isEdit = true;
            lin.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
        }

    }

    @Override
    public void OnCancelClick(int position) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            lin.getViewTreeObserver().removeOnGlobalLayoutListener(onGlobalLayoutListener);
        }
        if (stuffs.get(position).getStatus().equals("添加")) {
            stuffs.remove(position);
        } else {
            stuffs.get(position).setEdit(false);
        }
        isEdit = false;
        ArmsUtils.setListViewHeightBasedOnChildren(rlStuffs);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void OnConfirmClick(int position) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            lin.getViewTreeObserver().removeOnGlobalLayoutListener(onGlobalLayoutListener);
        }
        MateriaBean bean = stuffs.get(position);
        if (bean != null) {
            if (bean.getStatus().equals("添加")) {
                Map<String, Object> map = new HashMap<>();
                if (order!=null){
                    map.put("repairWorkOrderIdx", order.getIdx());
                } else if (faultOrder!=null){
                    map.put("repairWorkOrderIdx", faultOrder.getIdx());
                }
            //    map.put("repairWorkOrderIdx", order.getIdx());
                map.put("stuffName", bean.getStuffName());
                map.put("stuffNumber", bean.getStuffNumber());
                map.put("stuffTotalMoney", bean.getStuffTotalMoney());
                map.put("stuffUnitPrice", bean.getStuffUnitPrice());
                mPresenter.UpdataStuffs(JSON.toJSONString(map), "添加");
            }else {
                Map<String, Object> map = new HashMap<>();
                map.put("idx", bean.getIdx());
                if (order!=null){
                    map.put("repairWorkOrderIdx", order.getIdx());
                } else if (faultOrder!=null){
                    map.put("repairWorkOrderIdx", faultOrder.getIdx());
                }
            //    map.put("repairWorkOrderIdx", order.getIdx());
                map.put("stuffName", bean.getStuffName());
                map.put("stuffNumber", bean.getStuffNumber());
                map.put("stuffTotalMoney", bean.getStuffTotalMoney());
                map.put("stuffUnitPrice", bean.getStuffUnitPrice());
                mPresenter.UpdataStuffs(JSON.toJSONString(map), "修改");
            }
        }
    }
    ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            //监听到了就注销监听

            Rect rect = new Rect();
            lin.getWindowVisibleDisplayFrame(rect);
            int rootInvisibleHeight = lin.getRootView().getHeight() - rect.bottom;
            if (rootInvisibleHeight <= 100) {
                //软键盘隐藏啦
                Log.e("软键盘","隐藏");
                if(isstup){
                    adapter.notifyDataSetChanged();
                    isstup = false;
                }
            } else {
                ////软键盘弹出啦
                isstup = true;
                Log.e("软键盘","弹出");
            }
        }
    };
    @Override
    public void OnEditclick(int position) {
        if (isEdit) {
            ToastUtils.showShort("还有正在编辑的物料信息未处理，请编辑完成后再添加");
        } else {
            stuffs.get(position).setEdit(true);
            stuffs.get(position).setStatus("修改");
            ArmsUtils.setListViewHeightBasedOnChildren(rlStuffs);
            adapter.notifyDataSetChanged();
            isEdit = true;
            lin.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
        }
    }

    @Override
    public void OnWaitClick(String type, final int position) {
        if(type.equals("开始待料")){
            BaseDialog.setDialog(this, "确定开始待料?", "确定",
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showLoading();
                            mPresenter.StartWaitStuff(stuffs.get(position).getIdx());
                            BaseDialog.dissmiss();
                        }
                    },
                    "取消"
                    , new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            BaseDialog.dissmiss();
                        }
                    });

        }else if(type.equals("正在待料")){
            BaseDialog.setDialog(this, "确定结束待料?", "确定",
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showLoading();
                            mPresenter.EndtWaitStuff(stuffs.get(position).getIdx());
                            BaseDialog.dissmiss();
                        }
                    },
                    "取消"
                    , new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            BaseDialog.dissmiss();
                        }
                    });
        }
    }

    int EditPosition;
    int paramsPosition;

    @Override
    public void OnStuffSelected(int paramsPosition, int EditPosition) {
        this.EditPosition = EditPosition;
        this.paramsPosition = paramsPosition;
//        stuffs.get(EditPosition).setStuffName(params.get(paramsPosition).getStuffName());
//        stuffs.get(EditPosition).setStuffUnitPrice(params.get(paramsPosition).getStuffUnitPrice());
//        if(stuffs.get(EditPosition).getStuffNumber()!=null){
//            stuffs.get(EditPosition).setStuffTotalMoney(ArmsUtils.mul(stuffs.get(EditPosition).getStuffNumber(),stuffs.get(EditPosition).getStuffUnitPrice()));
//        }
//        adapter.notifyDataSetChanged();
    }


}
