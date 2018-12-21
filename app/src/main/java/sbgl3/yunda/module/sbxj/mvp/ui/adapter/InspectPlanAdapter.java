package sbgl3.yunda.module.sbxj.mvp.ui.adapter;

import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

import butterknife.BindView;
import sbgl3.yunda.R;
import sbgl3.yunda.module.main.viewHolder.MainMenuHolder;
import sbgl3.yunda.module.sbxj.entry.InspectPlanBean;
import sbgl3.yunda.module.sbxj.mvp.ui.viewHolder.InspecPlanHolder;

public class InspectPlanAdapter extends DefaultAdapter<InspectPlanBean> {

    public InspectPlanAdapter(List<InspectPlanBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<InspectPlanBean> getHolder(View v, int viewType) {
        return new InspecPlanHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_inspect_plan;
    }
}
