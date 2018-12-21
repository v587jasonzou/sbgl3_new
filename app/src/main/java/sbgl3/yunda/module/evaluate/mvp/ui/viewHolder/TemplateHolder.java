package sbgl3.yunda.module.evaluate.mvp.ui.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.utils.utilcode.util.StringUtils;

import butterknife.BindView;
import sbgl3.yunda.R;
import sbgl3.yunda.module.appraise.entry.AppraiseTemplateBean;
import sbgl3.yunda.module.evaluate.entry.EvaluateTemplateBean;

/**
 * <li>标题: 设备管理系统
 * <li>说明:
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
public class TemplateHolder extends BaseHolder<EvaluateTemplateBean>{
    @BindView(R.id.tvTemplateName)
    TextView tvTemplateName;
    @BindView(R.id.tvClassCode)
    TextView tvClassCode;
    @BindView(R.id.ivAdd)
    ImageView ivAdd;
    OnAddTemplateClickListener onAddTemplateClickListener;

    public TemplateHolder(View itemView, OnAddTemplateClickListener onAddTemplateClickListener) {
        super(itemView);
        this.onAddTemplateClickListener = onAddTemplateClickListener;
    }

    public interface OnAddTemplateClickListener{
        void addTemplateClick(int position);
    }

    @Override
    public void setData(EvaluateTemplateBean data, int position) {
        if (null != data){
            if (null!=data.getTemplateName()){
                tvTemplateName.setText(data.getTemplateName());
            }
            if (null!=data.getClassCode()){
                tvClassCode.setText(data.getClassCode());
            }
            ivAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onAddTemplateClickListener.addTemplateClick(position);
                }
            });
            ivAdd.setTag(position);
        }
    }
}
