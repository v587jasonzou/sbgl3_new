package sbgl3.yunda.module.sbdj.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

import butterknife.BindView;
import sbgl3.yunda.R;
import sbgl3.yunda.module.sbdj.entity.EquipmentUnionRFID;
import sbgl3.yunda.module.sbdj.mvp.ui.holder.EquipHolder;

public class EquipAdapter extends DefaultAdapter<EquipmentUnionRFID> {

    Context context;
    public EquipAdapter(List<EquipmentUnionRFID> infos, Context context) {
        super(infos);
        this.context = context;
    }

    public EquipHolder holder;
    int type = 0;

    public void setType(int type) {
        this.type = type;
        holder.setType(type);
    }

    @Override
    public BaseHolder<EquipmentUnionRFID> getHolder(View v, int viewType) {
        holder = new EquipHolder(v,context);
        return holder;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_sbdj_equips;
    }
}
