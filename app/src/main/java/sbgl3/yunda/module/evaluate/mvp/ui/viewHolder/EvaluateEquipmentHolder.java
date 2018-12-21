package sbgl3.yunda.module.evaluate.mvp.ui.viewHolder;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.utils.utilcode.util.StringUtils;

import butterknife.BindView;
import sbgl3.yunda.R;
import sbgl3.yunda.module.evaluate.entry.EquipmentEvaluateBean;

/**
 * <li>标题: 设备管理系统
 * <li>说明:  设备鉴定列表适配器
 * <li>创建人：刘欢
 * <li>创建日期：2018年8月24日
 * <li>修改人:
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 * @author 系统集成事业部设备管理系统项目组
 * @version 1.0
 */
public class EvaluateEquipmentHolder extends BaseHolder<EquipmentEvaluateBean> {

    @BindView(R.id.tvEquipName)
    TextView tvEquipName;
    @BindView(R.id.tvIsAppraise)
    TextView tvIsAppraise;
    @BindView(R.id.tvEquipCode)
    TextView tvEquipCode;
    @BindView(R.id.tvSpecificationModel)
    TextView tvSpecificationModel;
    @BindView(R.id.tvUsePlace)
    TextView tvUsePlace;

    public EvaluateEquipmentHolder(View itemView) {
        super(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void setData(EquipmentEvaluateBean data, int position) {
        if (null != data){
            if (null!=data.getEquipmentName()){
                tvEquipName.setText(data.getEquipmentName());
            } else {
                tvEquipName.setText("");
            }
            if (null!=data.getEquipmentCode()){
                tvEquipCode.setText(data.getEquipmentCode());
            } else {
                tvEquipCode.setText("");
            }
            String str = "";
            if(data.getModel()!=null){
                str = str+data.getModel();
            }
            if(data.getSpecification()!=null){
                str = str+","+data.getSpecification();
            }else {
                str = str+"";
            }
            tvSpecificationModel.setText(str);

            if (null!=data.getUsePlace()){
                tvUsePlace.setText(data.getUsePlace());
            } else {
                tvUsePlace.setText("");
            }

            GradientDrawable myGrad = (GradientDrawable)tvIsAppraise.getBackground();
            myGrad.setColor(Color.BLUE);
            if (data.getPlanStatus() == 1){
                tvIsAppraise.setText("计划中");
                myGrad.setColor(Color.parseColor("#9E9E9E"));
            } else if(data.getPlanStatus() == 2){
                tvIsAppraise.setText("进行中");
                myGrad.setColor(Color.parseColor("#E6BA23"));
            } else {
                tvIsAppraise.setText("已完成");
                myGrad.setColor(Color.parseColor("#24BD36"));
            }
        }
    }
}
