package sbgl3.yunda.module.evaluate.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

import sbgl3.yunda.R;
import sbgl3.yunda.module.appraise.entry.AppraiseTemplateBean;
import sbgl3.yunda.module.evaluate.entry.EvaluateTemplateBean;
import sbgl3.yunda.module.evaluate.mvp.ui.viewHolder.TemplateHolder;

/**
 * <li>标题: 设备管理系统
 * <li>说明: 评定模板列表适配器
 * <li>创建人：刘欢
 * <li>创建日期：2018/9/6
 * <li>修改人:
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 *
 * @author 系统集成事业部设备管理系统项目组
 * @version 1.0
 */
public class TemplateAdapter extends DefaultAdapter<EvaluateTemplateBean> {


    TemplateHolder holder;
    TemplateHolder.OnAddTemplateClickListener onAddTemplateClickListener;
    public TemplateAdapter(List<EvaluateTemplateBean> infos, TemplateHolder.OnAddTemplateClickListener onAddTemplateClickListener) {
        super(infos);
        this.onAddTemplateClickListener = onAddTemplateClickListener;
    }

    @Override
    public BaseHolder<EvaluateTemplateBean> getHolder(View v, int viewType) {
        holder = new TemplateHolder(v, onAddTemplateClickListener);
        return holder;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_appraise_template;
    }


}
