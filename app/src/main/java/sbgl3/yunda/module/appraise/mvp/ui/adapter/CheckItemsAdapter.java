package sbgl3.yunda.module.appraise.mvp.ui.adapter;

import android.support.v7.widget.CardView;
import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

import butterknife.BindView;
import sbgl3.yunda.R;
import sbgl3.yunda.module.appraise.entry.EquipmentAppraisePlanBean;
import sbgl3.yunda.module.appraise.mvp.ui.activity.EquipmentAppraiseCheckItemActivity;
import sbgl3.yunda.module.appraise.mvp.ui.viewHolder.CheckItemsHolder;

/**
 * <li>标题: 设备管理系统
 * <li>说明: 检查项列表适配器
 * <li>创建人：刘欢
 * <li>创建日期：2018/9/3
 * <li>修改人:
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 *
 * @author 系统集成事业部设备管理系统项目组
 * @version 1.0
 */
public class CheckItemsAdapter extends DefaultAdapter<EquipmentAppraisePlanBean> {

    CheckItemsHolder holder;
    EquipmentAppraiseCheckItemActivity activity;

    public CheckItemsAdapter(EquipmentAppraiseCheckItemActivity activity, List<EquipmentAppraisePlanBean> list) {
        super(list);
        this.activity = activity;
    }

    @Override
    public BaseHolder<EquipmentAppraisePlanBean> getHolder(View v, int viewType) {
        holder = new CheckItemsHolder(activity, v);
        holder.setIsRecyclable(false);
        return holder;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_check_items;
    }
}
