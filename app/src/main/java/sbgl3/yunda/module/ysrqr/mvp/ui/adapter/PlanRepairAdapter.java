package sbgl3.yunda.module.ysrqr.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

import sbgl3.yunda.R;
import sbgl3.yunda.module.ysrqr.entry.PlanRepairEquipListBean;
import sbgl3.yunda.module.ysrqr.mvp.ui.holder.PlanRepairHolder;

/**
 * <li>标题: 设备管理系统
 * <li>说明:  工长确认列表适配器
 * <li>创建人：刘欢
 * <li>创建日期：2018年9月18日
 * <li>修改人:
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 *
 * @author 系统集成事业部设备管理系统项目组
 * @version 1.0
 */
public class PlanRepairAdapter extends DefaultAdapter<PlanRepairEquipListBean> {
    PlanRepairHolder holder;
    public PlanRepairAdapter(List<PlanRepairEquipListBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<PlanRepairEquipListBean> getHolder(View v, int viewType) {
        holder = new PlanRepairHolder(v);
        return holder;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_syrqr_equipment;
    }
}
