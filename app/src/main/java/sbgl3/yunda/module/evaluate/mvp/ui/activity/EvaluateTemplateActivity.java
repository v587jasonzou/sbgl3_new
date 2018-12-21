package sbgl3.yunda.module.evaluate.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.utilcode.util.StringUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.speedata.utils.ProgressDialogUtils;

import net.lemonsoft.lemonhello.LemonHello;
import net.lemonsoft.lemonhello.LemonHelloAction;
import net.lemonsoft.lemonhello.LemonHelloInfo;
import net.lemonsoft.lemonhello.LemonHelloView;
import net.lemonsoft.lemonhello.interfaces.LemonHelloActionDelegate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import sbgl3.yunda.R;
import sbgl3.yunda.module.evaluate.di.component.DaggerEvaluateTemplateComponent;
import sbgl3.yunda.module.evaluate.di.module.EvaluateTemplateModule;
import sbgl3.yunda.module.evaluate.entry.EvaluateTemplateBean;
import sbgl3.yunda.module.evaluate.mvp.contract.EvaluateTemplateContract;
import sbgl3.yunda.module.evaluate.mvp.presenter.EvaluateTemplatePresenter;
import sbgl3.yunda.module.evaluate.mvp.ui.adapter.TemplateAdapter;
import sbgl3.yunda.module.evaluate.mvp.ui.viewHolder.TemplateHolder;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static sbgl3.yunda.module.evaluate.mvp.ui.activity.EquipmentEvaluateResultEditActivity.equipmentEvaluateBean;


/**
 * <li>标题: 设备管理系统
 * <li>说明: 设备评定模板列表界面
 * <li>创建人：刘欢
 * <li>创建日期：2018/9/14
 * <li>修改人:
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 *
 * @author 系统集成事业部设备管理系统项目组
 * @version 3.0
 */
public class EvaluateTemplateActivity extends BaseActivity<EvaluateTemplatePresenter> implements EvaluateTemplateContract.View, TemplateHolder.OnAddTemplateClickListener {

    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.ivSearch)
    ImageView ivSearch;
    @BindView(R.id.rlTemplates)
    RecyclerView rlTemplates;
    @BindView(R.id.srRefresh)
    SmartRefreshLayout srRefresh;
    @BindView(R.id.llNoData)
    LinearLayout llNoData;
    int start = 0;
    int limit = 8;
    boolean isLoadMore = false;
    /**
     * 模板类型propValue - 评定模板【0】
     */
    public static final int TEMPLATE_TYPE_PDMB = 0;
    /**
     * 模板类型propName
     */
    public static final String templateType = "templateType";
    /**
     * 按类别编号排序
     */
    public static final String sort = "classCode";
    public static String queryKey = null;
    List<EvaluateTemplateBean> mList = new ArrayList();
    TemplateAdapter adapter;
    EvaluateTemplateBean templateBean = null;
    @BindView(R.id.ivEditDelete)
    LinearLayout ivEditDelete;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerEvaluateTemplateComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .evaluateTemplateModule(new EvaluateTemplateModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_appraise_template; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
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
        menuTp.setTitle("选择评定模板");
        etSearch.setText(equipmentEvaluateBean.getClassCode());
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start = 0;
                isLoadMore = false;
                loadData();
            }
        });
        ivEditDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSearch.setText("");
                start = 0;
                isLoadMore = false;
                loadData();
            }
        });
        adapter = new TemplateAdapter(mList, this);
        ArmsUtils.configRecycleView(rlTemplates, new LinearLayoutManager(this));
        rlTemplates.setAdapter(adapter);
        loadData();
        setSrRefresh();
    }

    /**
     * <li>说明：联网获取数据
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月14日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    private void loadData() {
        showLoading();
        List<Map> whereList = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("propName", templateType);
        map1.put("propValue", TEMPLATE_TYPE_PDMB);
        map1.put("stringLike", false);
        whereList.add(map1);
        Map<String, Object> map2 = new HashMap<>();
        if (!StringUtils.isTrimEmpty(etSearch.getText().toString())) {
            queryKey = etSearch.getText().toString();
            map2.put("compare", 30);
            map2.put("sql", "CLASS_CODE LIKE " + "'" + queryKey + "%'" + " OR CLASS_NAME LIKE " + "'%" + queryKey + "%'");
            whereList.add(map2);
        }
        if (null != mPresenter) {
            mPresenter.getTemplate(start, limit, sort, JSON.toJSONString(whereList), isLoadMore);
        }
    }

    /**
     * <li>说明：下拉刷新上拉加载更多
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月14日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    private void setSrRefresh() {
        srRefresh.setRefreshHeader(new ClassicsHeader(this));
        srRefresh.setRefreshFooter(new ClassicsFooter(this));
        srRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                start = 0;
                isLoadMore = false;
                if (!StringUtils.isTrimEmpty(etSearch.getText().toString())) {
                    queryKey = (etSearch.getText().toString());
                }
                loadData();
            }
        });
        srRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                start = start + limit;
                isLoadMore = true;
                loadData();
            }
        });
        srRefresh.setEnableAutoLoadMore(false);
    }

    @Override
    public void showLoading() {
        ProgressDialogUtils.showProgressDialog(this, "正在加载中，请稍后！");
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
     * <li>说明：加载列表数据
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月14日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    @Override
    public void LoadData(List<EvaluateTemplateBean> list) {
        srRefresh.finishRefresh();
        mList.clear();
        if (list.size() > 0) {
            mList.addAll(list);
        } else {
            loadFail();
        }
        adapter.notifyDataSetChanged();
        if (list.size() < limit) {
            srRefresh.setNoMoreData(true);
        } else {
            srRefresh.setNoMoreData(false);
        }
    }

    /**
     * <li>说明：加载更多
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月14日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    @Override
    public void LoadMoreData(List<EvaluateTemplateBean> list) {
        srRefresh.finishLoadMore();
        if (list.size() > 0) {
            mList.addAll(list);
        }
        adapter.notifyDataSetChanged();
        Observable.timer(1500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        if (list.size() < limit) {
                            srRefresh.setNoMoreData(true);
                        } else {
                            srRefresh.setNoMoreData(false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void addTemplateClick(int position) {
        templateBean = mList.get(position);
        LemonHello.getInformationHello("温馨提示", "是否选择【" + templateBean.getTemplateName() + "】评定模板？")
                .addAction(new LemonHelloAction("确定", new LemonHelloActionDelegate() {
                    @Override
                    public void onClick(LemonHelloView lemonHelloView, LemonHelloInfo lemonHelloInfo, LemonHelloAction lemonHelloAction) {
                        addAppraiseTemplate();
                        lemonHelloView.hide();
                    }
                })).addAction(new LemonHelloAction("取消", new LemonHelloActionDelegate() {
            @Override
            public void onClick(LemonHelloView lemonHelloView, LemonHelloInfo lemonHelloInfo, LemonHelloAction lemonHelloAction) {
                lemonHelloView.hide();
            }
        })).show(this);

    }

    /**
     * <li>说明：添加模板
     * <li>创建人：刘欢
     * <li>创建日期：2018年9月14日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    public void addAppraiseTemplate() {
        if (null != mPresenter) {
            // 添加模板
            mPresenter.addEvaluateTemplate(equipmentEvaluateBean.getIdx(), templateBean.getIdx());
        }
    }

    @Override
    public void loadFail() {
        srRefresh.setVisibility(View.GONE);
        llNoData.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadSuccess() {
        srRefresh.setVisibility(View.VISIBLE);
        llNoData.setVisibility(View.GONE);
    }

    @Override
    public void addSuccess() {
        equipmentEvaluateBean.setTemplateIdx(templateBean.getIdx());
        equipmentEvaluateBean.setTemplateName(templateBean.getTemplateName());
        equipmentEvaluateBean.setTemplateNo(templateBean.getTemplateNo());
        // 刷新设备评定列表
        Intent intent = new Intent();
        intent.setAction("action.refreshEquiEvaluateList");
        sendBroadcast(intent);
        // 初始化评定数据
        if (null != mPresenter) {
            mPresenter.startUp(equipmentEvaluateBean.getAppraisePlanIdx(), 0);
        }
        finish();
        Intent intent1 = new Intent(EvaluateTemplateActivity.this, EquipmentEvaluateCheckItemActivity.class);
        ArmsUtils.startActivity(intent1);
    }
}
