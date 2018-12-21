package sbgl3.yunda.module.equipinfo.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

import butterknife.BindView;
import sbgl3.yunda.R;
import sbgl3.yunda.module.equipinfo.entity.EquipmentPrimaryInfo;
import sbgl3.yunda.module.equipinfo.mvp.ui.holder.EquipListHolder;
import sbgl3.yunda.module.sbdj.mvp.ui.holder.EquipHolder;

public class EquipListAdapter extends DefaultAdapter<EquipmentPrimaryInfo> {



    public EquipListAdapter(List<EquipmentPrimaryInfo> infos) {
        super(infos);

    }

    public EquipListHolder holder;
    int type = 0;

    public void setType(int type) {
        this.type = type;
        holder.setType(type);
    }

    @Override
    public BaseHolder<EquipmentPrimaryInfo> getHolder(View v, int viewType) {
        holder = new EquipListHolder(v);
        return holder;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_equips_list;
    }
}
