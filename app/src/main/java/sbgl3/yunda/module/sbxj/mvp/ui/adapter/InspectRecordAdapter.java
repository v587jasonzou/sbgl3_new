package sbgl3.yunda.module.sbxj.mvp.ui.adapter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

import butterknife.BindView;
import sbgl3.yunda.R;
import sbgl3.yunda.module.sbxj.entry.InspectRecord;
import sbgl3.yunda.module.sbxj.mvp.ui.viewHolder.InspecPlanHolder;
import sbgl3.yunda.module.sbxj.mvp.ui.viewHolder.InspecRecordHolder;

public class InspectRecordAdapter extends DefaultAdapter<InspectRecord> {



    public InspectRecordAdapter(List<InspectRecord> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<InspectRecord> getHolder(View v, int viewType) {
        return new InspecRecordHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_inspect_record;
    }
}
