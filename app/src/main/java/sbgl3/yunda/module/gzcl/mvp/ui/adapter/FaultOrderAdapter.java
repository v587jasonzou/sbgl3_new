package sbgl3.yunda.module.gzcl.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

import sbgl3.yunda.R;
import sbgl3.yunda.module.gzcl.entry.FaultOrderBean;
import sbgl3.yunda.module.gzcl.mvp.ui.holder.FaultOrderHolder;

/**
 * <li>标题: 设备管理系统
 * <li>说明:  故障处理列表适配器
 * <li>创建人：刘欢
 * <li>创建日期：2018年10月11日
 * <li>修改人:
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 *
 * @author 系统集成事业部设备管理系统项目组
 * @version 1.0
 */
public class FaultOrderAdapter extends DefaultAdapter<FaultOrderBean> {
    FaultOrderHolder holder;
    public FaultOrderAdapter(List<FaultOrderBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<FaultOrderBean> getHolder(View v, int viewType) {
        holder = new FaultOrderHolder(v);
        return holder;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_syrqr_equipment;
    }
}
