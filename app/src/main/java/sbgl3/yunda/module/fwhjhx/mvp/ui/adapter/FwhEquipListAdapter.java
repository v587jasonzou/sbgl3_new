package sbgl3.yunda.module.fwhjhx.mvp.ui.adapter;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

import butterknife.BindView;
import sbgl3.yunda.R;
import sbgl3.yunda.module.equipinfo.mvp.ui.holder.EquipListHolder;
import sbgl3.yunda.module.fwhjhx.mvp.entity.RepairTaskList;
import sbgl3.yunda.module.fwhjhx.mvp.ui.holder.FwhEquipListHolder;

public class FwhEquipListAdapter extends DefaultAdapter<RepairTaskList> {

    public FwhEquipListAdapter(List<RepairTaskList> infos) {
        super(infos);
    }

    public FwhEquipListHolder holder;


    @Override
    public BaseHolder<RepairTaskList> getHolder(View v, int viewType) {
        holder = new FwhEquipListHolder(v);
        return holder;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_repair_task_equip;
    }
}
