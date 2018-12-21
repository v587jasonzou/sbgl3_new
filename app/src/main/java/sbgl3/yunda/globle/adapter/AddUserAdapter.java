package sbgl3.yunda.globle.adapter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

import butterknife.BindView;
import sbgl3.yunda.R;
import sbgl3.yunda.entry.UserInfo;
import sbgl3.yunda.globle.viewHolder.AddUserHolder;
import sbgl3.yunda.module.sbxj.entry.InspectRecord;
import sbgl3.yunda.module.sbxj.mvp.ui.viewHolder.InspecRecordHolder;
/**
 * <li>标题: 用户添加控件列表适配器
 * <li>创建人：周雪巍
 * <li>创建日期：2018-09-12
 * <li>修改人:
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 * @author 周雪巍
 * @version 1.0
 */
public class AddUserAdapter extends DefaultAdapter<UserInfo> {

    public AddUserAdapter(List<UserInfo> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<UserInfo> getHolder(View v, int viewType) {
        return new AddUserHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_user_add_dialog;
    }
}
