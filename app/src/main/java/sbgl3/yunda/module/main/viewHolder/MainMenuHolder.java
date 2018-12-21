package sbgl3.yunda.module.main.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.utils.utilcode.util.StringUtils;

import butterknife.BindView;
import sbgl3.yunda.R;
import sbgl3.yunda.module.login.Entry.LoginReponsBody;
import sbgl3.yunda.module.main.Entry.MenuBean;

public class MainMenuHolder extends BaseHolder<MenuBean> {
    @BindView(R.id.ivMenuIcon)
    ImageView ivMenuIcon;
    @BindView(R.id.tvMenuName)
    TextView tvMenuName;
    @BindView(R.id.ivPoint)
    ImageView ivPoint;
    @BindView(R.id.tvTodoNum)
    TextView tvTodoNum;

    public MainMenuHolder(View itemView) {
        super(itemView);
    }
    @Override
    public void setData(MenuBean data, int position) {
        if (data != null) {
            if (!StringUtils.isTrimEmpty(data.getFuncname())) {
                tvMenuName.setText(data.getFuncname());
                if (data.getFuncname().equals("故障提票")) {
                    ivMenuIcon.setImageResource(R.mipmap.gztp_menu_icon);
                    ivPoint.setImageResource(R.mipmap.gztp_point);
                } else if (data.getFuncname().equals("设备停机登记")) {
                    ivMenuIcon.setImageResource(R.mipmap.sbxj_menu_icon);
                    ivPoint.setImageResource(R.mipmap.tjdj_point);
                } else if (data.getFuncname().equals("设备巡检")) {
                    ivMenuIcon.setImageResource(R.mipmap.sbxj_menu_icon);
                    ivPoint.setImageResource(R.mipmap.tjdj_point);
                } else if (data.getFuncname().equals("设备信息查看")) {
                    ivMenuIcon.setImageResource(R.mipmap.sbxxck_menu_icon);
                    ivPoint.setImageResource(R.mipmap.sbxj_point);
                } else if (data.getFuncname().equals("维修工长确认")) {
                    ivMenuIcon.setImageResource(R.mipmap.wxgzqr_menu_icon);
                    ivPoint.setImageResource(R.mipmap.wxgzqr_point);
                } else if (data.getFuncname().equals("设备登记")) {
                    ivMenuIcon.setImageResource(R.drawable.sbdjmenu_icon);
                    ivPoint.setImageResource(R.mipmap.sbxxck_point);
                } else if (data.getFuncname().equals("提票工长派工")) {
                    ivMenuIcon.setImageResource(R.mipmap.tpgzpg_menu_icon);
                    ivPoint.setImageResource(R.mipmap.sbxj_point);
                } else if (data.getFuncname().equals("计划检修")) {
                    ivMenuIcon.setImageResource(R.mipmap.jhjx_menu_icon);
                    ivPoint.setImageResource(R.mipmap.tjdj_point);
                } else if (data.getFuncname().equals("使用人确认")) {
                    ivMenuIcon.setImageResource(R.mipmap.syrqr_menu_icon);
                    ivPoint.setImageResource(R.mipmap.sbxxck_point);
                } else if (data.getFuncname().equals("验收人确认")) {
                    ivMenuIcon.setImageResource(R.mipmap.ysrqr_menu_icon);
                    ivPoint.setImageResource(R.mipmap.sbxj_point);
                } else if (data.getFuncname().equals("故障处理")) {
                    ivMenuIcon.setImageResource(R.mipmap.gzcl_menu_icon);
                    ivPoint.setImageResource(R.mipmap.sbxj_point);
                } else if (data.getFuncname().equals("设备点检")) {
                    ivMenuIcon.setImageResource(R.mipmap.sbdj_menu_icon);
                    ivPoint.setImageResource(R.mipmap.wxgzqr_point);
                } else if (data.getFuncname().equals("设备点检")) {
                    ivMenuIcon.setImageResource(R.mipmap.sbdj_menu_icon);
                    ivPoint.setImageResource(R.mipmap.wxgzqr_point);
                } else if (data.getFuncname().equals("设备鉴定")) {
                    ivMenuIcon.setImageResource(R.mipmap.sbjd_menu_icon);
                    ivPoint.setImageResource(R.mipmap.sbxj_point);
                } else if (data.getFuncname().equals("设备评定")) {
                    ivMenuIcon.setImageResource(R.mipmap.sbpd_menu_icon);
                    ivPoint.setImageResource(R.mipmap.tjdj_point);
                }
            }
            if(data.getTodoNum()!=null&&data.getTodoNum()>0){
                tvTodoNum.setVisibility(View.VISIBLE);
                tvTodoNum.setText(data.getTodoNum()+"");
            }else {
                tvTodoNum.setVisibility(View.GONE);
            }
        }
    }
}
