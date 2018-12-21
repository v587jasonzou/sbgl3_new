package sbgl3.yunda.globle.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

import butterknife.BindView;
import sbgl3.yunda.R;
import sbgl3.yunda.globle.viewHolder.MenuHolder;
import sbgl3.yunda.module.main.Entry.MenuBean;
import sbgl3.yunda.module.main.viewHolder.MainMenuHolder;
/**
 * <li>标题: 主页面菜单列表适配器
 * <li>创建人：周雪巍
 * <li>创建日期：2018-09-12
 * <li>修改人:
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 * @author 周雪巍
 * @version 1.0
 */
public class MenuAdapter extends DefaultAdapter<MenuBean> {
    String data;
    Context context;
    public void setNowData(String nowData){
        data = nowData;
    }
    public MenuAdapter(List<MenuBean> infos,Context context) {
        super(infos);
        this.context = context;
    }

    @Override
    public BaseHolder<MenuBean> getHolder(View v, int viewType) {
        MenuHolder holder = new MenuHolder(v,context);
        holder.setNowMenu(data);
        return holder;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_popup_menu;
    }
}
