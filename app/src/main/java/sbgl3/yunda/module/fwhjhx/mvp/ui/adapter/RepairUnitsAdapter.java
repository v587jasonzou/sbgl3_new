package sbgl3.yunda.module.fwhjhx.mvp.ui.adapter;

import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

import butterknife.BindView;
import sbgl3.yunda.R;
import sbgl3.yunda.module.fwhjhx.mvp.entity.EosDictEntry;
import sbgl3.yunda.module.fwhjhx.mvp.ui.holder.RepairUnitsHolder;
import sbgl3.yunda.module.sbxj.mvp.ui.viewHolder.InspecRecordHolder;

public class RepairUnitsAdapter extends DefaultAdapter<EosDictEntry> {



    public RepairUnitsAdapter(List<EosDictEntry> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<EosDictEntry> getHolder(View v, int viewType) {
        return new RepairUnitsHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_repair_units;
    }
}
