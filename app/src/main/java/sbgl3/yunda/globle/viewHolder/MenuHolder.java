package sbgl3.yunda.globle.viewHolder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.utilcode.util.StringUtils;

import butterknife.BindView;
import sbgl3.yunda.R;
import sbgl3.yunda.SysInfo;
import sbgl3.yunda.module.appraise.mvp.ui.activity.EquipmentAppraiseActivity;
import sbgl3.yunda.module.equipinfo.mvp.ui.activity.EquipListActivity;
import sbgl3.yunda.module.evaluate.mvp.ui.activity.EquipmentEvaluateActivity;
import sbgl3.yunda.module.fwhjhx.mvp.ui.activity.RepairTaskEquipActivity;
import sbgl3.yunda.module.gzcl.mvp.ui.activity.GzclListActivity;
import sbgl3.yunda.module.gzqr.mvp.ui.activity.GzqrEquipmentListActivity;
import sbgl3.yunda.module.gztp.mvp.ui.activity.FaultTicketActivity;
import sbgl3.yunda.module.main.Entry.MenuBean;
import sbgl3.yunda.module.main.mvp.ui.activity.MainActivity;
import sbgl3.yunda.module.sbdj.mvp.ui.activity.SbdjMainActivity;
import sbgl3.yunda.module.sbxj.mvp.ui.activity.SBXJActivity;
import sbgl3.yunda.module.spot_check.mvp.ui.activity.SpotCheckMainActivity;
import sbgl3.yunda.module.tpgzpg.mvp.ui.activity.TpgzpgListActivity;
import sbgl3.yunda.module.userconfirm.mvp.ui.activity.UserConfirmActivity;
import sbgl3.yunda.module.ysrqr.mvp.ui.activity.YsrqrEquipmentListActivity;

/**
 * <li>标题: 主页菜单列表控件holder
 * <li>创建人：周雪巍
 * <li>创建日期：2018-09-12
 * <li>修改人:
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 * @author 周雪巍
 * @version 1.0
 */
public class MenuHolder extends BaseHolder<MenuBean> {
    @BindView(R.id.ivMoreCircle)
    ImageView ivMoreCircle;
    @BindView(R.id.tvMenuName)
    TextView tvMenuName;
    @BindView(R.id.llMenu)
    LinearLayout llMenu;
    public String NowMenu;
    Context context;
    public MenuHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
    }
    @Override
    public void setData(MenuBean data, int position) {
        if (data != null) {
            if (!StringUtils.isTrimEmpty(data.getFuncname())) {
                tvMenuName.setText(data.getFuncname());
            }
            if(data.getTodoNum()!=null&&data.getTodoNum()>0){
                ivMoreCircle.setVisibility(View.VISIBLE);
            }else {
                ivMoreCircle.setVisibility(View.GONE);
            }
        }
        if(!StringUtils.isTrimEmpty(NowMenu)){
            if(data.getFuncname().equals(NowMenu)){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    llMenu.setBackground(ContextCompat.getDrawable(context,R.drawable.menu_item_click_bg));
                    tvMenuName.setTextColor(ContextCompat.getColor(context,R.color.colorPrimary));
                }
            }else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    llMenu.setBackground(ContextCompat.getDrawable(context,R.drawable.menu_item_unclick_bg));
                    tvMenuName.setTextColor(ContextCompat.getColor(context,R.color.textmenuview_bg));
                }
            }
        }
        llMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SysInfo.menus.get(position).getFuncname().equals("设备巡检")){
                    Intent intent = new Intent(context, SBXJActivity.class);
                    ArmsUtils.startActivity(intent);
                } else if (SysInfo.menus.get(position).getFuncname().equals("设备鉴定")){
                    Intent intent = new Intent(context, EquipmentAppraiseActivity.class);
                    ArmsUtils.startActivity(intent);
                } else if (SysInfo.menus.get(position).getFuncname().equals("设备评定")){
                    Intent intent = new Intent(context, EquipmentEvaluateActivity.class);
                    ArmsUtils.startActivity(intent);
                } else if(SysInfo.menus.get(position).getFuncname().equals("设备登记")){
                    Intent intent = new Intent(context, SbdjMainActivity.class);
                    ArmsUtils.startActivity(intent);
                } else if(SysInfo.menus.get(position).getFuncname().equals("设备信息查看")){
                    Intent intent = new Intent(context, EquipListActivity.class);
                    ArmsUtils.startActivity(intent);
                } else if(SysInfo.menus.get(position).getFuncname().equals("计划检修")){
                    Intent intent = new Intent(context, RepairTaskEquipActivity.class);
                    ArmsUtils.startActivity(intent);
                } else if (SysInfo.menus.get(position).getFuncname().equals("使用人确认")){
                    Intent intent = new Intent(context, UserConfirmActivity.class);
                    ArmsUtils.startActivity(intent);
                } else if (SysInfo.menus.get(position).getFuncname().equals("维修工长确认")){
                    Intent intent = new Intent(context, GzqrEquipmentListActivity.class);
                    ArmsUtils.startActivity(intent);
                } else if (SysInfo.menus.get(position).getFuncname().equals("验收人确认")){
                    Intent intent = new Intent(context, YsrqrEquipmentListActivity.class);
                    ArmsUtils.startActivity(intent);
                } else if(SysInfo.menus.get(position).getFuncname().equals("设备点检")){
                    Intent intent = new Intent(context, SpotCheckMainActivity.class);
                    ArmsUtils.startActivity(intent);
                } else if (SysInfo.menus.get(position).getFuncname().equals("提票工长派工")){
                    Intent intent = new Intent(context, TpgzpgListActivity.class);
                    ArmsUtils.startActivity(intent);
                } else if (SysInfo.menus.get(position).getFuncname().equals("故障处理")){
                    Intent intent = new Intent(context, GzclListActivity.class);
                    ArmsUtils.startActivity(intent);
                } else if (SysInfo.menus.get(position).getFuncname().equals("故障提票")){
                    Intent intent = new Intent(context, FaultTicketActivity.class);
                    ArmsUtils.startActivity(intent);
                }

            }
        });
    }

    public void setNowMenu(String menu){
        NowMenu = menu;
    }
}
