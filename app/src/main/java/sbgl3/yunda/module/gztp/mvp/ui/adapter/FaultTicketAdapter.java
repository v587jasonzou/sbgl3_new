package sbgl3.yunda.module.gztp.mvp.ui.adapter;

import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

import butterknife.BindView;
import sbgl3.yunda.R;
import sbgl3.yunda.module.gztp.entry.FaultOrder;
import sbgl3.yunda.module.gztp.mvp.ui.viewHolder.FaultTicketHolder;

public class FaultTicketAdapter extends DefaultAdapter<FaultOrder> {

    public FaultTicketAdapter(List<FaultOrder> infos) {
        super(infos);
    }

    FaultTicketHolder holder;

    @Override
    public BaseHolder<FaultOrder> getHolder(View v, int viewType) {
        holder = new FaultTicketHolder(v);
        return holder;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_fault_ticket;
    }
}
