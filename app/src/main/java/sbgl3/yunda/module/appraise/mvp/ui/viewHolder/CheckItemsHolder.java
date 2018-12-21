package sbgl3.yunda.module.appraise.mvp.ui.viewHolder;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.utils.utilcode.util.StringUtils;

import butterknife.BindView;
import sbgl3.yunda.R;
import sbgl3.yunda.module.appraise.entry.EquipmentAppraisePlanBean;
import sbgl3.yunda.module.appraise.entry.EquipmentAppraiserBean;
import sbgl3.yunda.module.appraise.mvp.ui.activity.EquipmentAppraiseCheckItemActivity;

/**
 * <li>标题: 设备管理系统
 * <li>说明:
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
public class CheckItemsHolder extends BaseHolder<EquipmentAppraisePlanBean>{
    @BindView(R.id.cardView)
    CardView cardView;
    @BindView(R.id.tvSeq)
    TextView tvSeq;
    @BindView(R.id.tvInfo)
    TextView tvInfo;
    @BindView(R.id.ivConfirm)
    ImageView ivConfirm;
    @BindView(R.id.ivTs)
    ImageView ivTs;
    View itemView;
    EquipmentAppraiseCheckItemActivity activity;
    public CheckItemsHolder (EquipmentAppraiseCheckItemActivity activity,View itemView){
        super(itemView);
        this.itemView = itemView;
        this.activity = activity;
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void setData(EquipmentAppraisePlanBean data, int position) {
        if (null != data){
            if (null!=data.getItemNo()){
                tvSeq.setText(data.getItemNo());
            }
            if (null!=data.getItemName()){
                tvInfo.setText(data.getItemName());
            }
            if (null!=data.getAfterAppraise()) {
                cardView.setBackground(ContextCompat.getDrawable(activity,R.drawable.shape_bg_done));
                ivConfirm.setVisibility(View.VISIBLE);
                ivTs.setVisibility(View.GONE);
            } else {
                cardView.setBackground(ContextCompat.getDrawable(activity,R.drawable.shape_bg_unfinished));
                ivConfirm.setVisibility(View.GONE);
                ivTs.setVisibility(View.VISIBLE);
            }
        }
    }
}
