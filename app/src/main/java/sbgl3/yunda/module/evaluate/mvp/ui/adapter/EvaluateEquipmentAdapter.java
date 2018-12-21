package sbgl3.yunda.module.evaluate.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

import sbgl3.yunda.R;
import sbgl3.yunda.module.evaluate.entry.EquipmentEvaluateBean;
import sbgl3.yunda.module.evaluate.mvp.ui.viewHolder.EvaluateEquipmentHolder;

/**
 * <li>标题: 设备管理系统
 * <li>说明:  设备评定列表适配器
 * <li>创建人：刘欢
 * <li>创建日期：2018年8月24日
 * <li>修改人:
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 * @author 系统集成事业部设备管理系统项目组
 * @version 1.0
 */
public class EvaluateEquipmentAdapter extends DefaultAdapter<EquipmentEvaluateBean> {

    EvaluateEquipmentHolder holder;
    public EvaluateEquipmentAdapter(List<EquipmentEvaluateBean> list){
        super(list);
    }
    @Override
    public BaseHolder<EquipmentEvaluateBean> getHolder(View v, int viewType) {
        holder = new EvaluateEquipmentHolder(v);
        return holder;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_appraise_equipment;
    }
}
