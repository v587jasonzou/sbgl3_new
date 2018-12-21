package sbgl3.yunda.module.tpgzpg.mvp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

import butterknife.BindView;
import sbgl3.yunda.R;
import sbgl3.yunda.module.tpgzpg.entry.BackInfoBean;
import sbgl3.yunda.module.tpgzpg.mvp.ui.holder.BackInfoHolder;

/**
 * <li>标题: 设备管理系统
 * <li>说明:  退回详情适配器
 * <li>创建人：刘欢
 * <li>创建日期：2018年10月9日
 * <li>修改人:
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 *
 * @author 系统集成事业部设备管理系统项目组
 * @version 1.0
 */
public class BackInfoAdapter extends DefaultAdapter<BackInfoBean> {
    Context context;
    public BackInfoAdapter(List<BackInfoBean> infos, Context context) {
        super(infos);
        this.context = context;
    }

    @Override
    public BaseHolder<BackInfoBean> getHolder(View v, int viewType) {
        return new BackInfoHolder(v, context);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_back_info;
    }
}
