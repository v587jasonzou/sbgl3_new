package sbgl3.yunda.module.sbxj.mvp.ui.adapter;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

import butterknife.BindView;
import sbgl3.yunda.R;
import sbgl3.yunda.module.sbxj.entry.InspectPlanEquipmentBean;
import sbgl3.yunda.module.sbxj.mvp.ui.viewHolder.InspecPlanEquipHolder;
import sbgl3.yunda.module.sbxj.mvp.ui.viewHolder.InspecPlanHolder;

public class InspectPlanEquipAdapter extends DefaultAdapter<InspectPlanEquipmentBean> {

    public InspectPlanEquipAdapter(List<InspectPlanEquipmentBean> infos) {
        super(infos);
    }
    InspecPlanEquipHolder holder;
    int type=0;
    public void setType(int type){
        this.type = type;
        holder.setType(type);
    }
    @Override
    public BaseHolder<InspectPlanEquipmentBean> getHolder(View v, int viewType) {
        holder = new InspecPlanEquipHolder(v);
        return holder;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_inspect_dev;
    }
}
