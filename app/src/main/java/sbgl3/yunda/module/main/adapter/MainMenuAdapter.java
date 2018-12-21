package sbgl3.yunda.module.main.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.utils.utilcode.util.StringUtils;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import sbgl3.yunda.R;
import sbgl3.yunda.module.login.Entry.LoginReponsBody;
import sbgl3.yunda.module.main.Entry.MenuBean;
import sbgl3.yunda.module.main.viewHolder.MainMenuHolder;

public class MainMenuAdapter extends DefaultAdapter<MenuBean> implements ItemTouchHelperAdapter  {

    List<MenuBean> mData ;
    public MainMenuAdapter(List<MenuBean> infos) {
        super(infos);
        mData = infos;
    }

    @Override
    public BaseHolder<MenuBean> getHolder(View v, int viewType) {
        return new MainMenuHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_main_menu;
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        //交换位置
        Collections.swap(mData,fromPosition,toPosition);
        notifyItemMoved(fromPosition,toPosition);
    }

}
