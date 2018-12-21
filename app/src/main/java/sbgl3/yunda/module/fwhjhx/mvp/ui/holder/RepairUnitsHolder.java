package sbgl3.yunda.module.fwhjhx.mvp.ui.holder;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.utils.utilcode.util.StringUtils;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import sbgl3.yunda.R;
import sbgl3.yunda.entry.ItemClickMessage;
import sbgl3.yunda.module.fwhjhx.mvp.entity.EosDictEntry;
import sbgl3.yunda.module.fwhjhx.mvp.entity.RepairTaskList;

public class RepairUnitsHolder extends BaseHolder<EosDictEntry> {
    @BindView(R.id.tvUnitName)
    TextView tvUnitName;


    public RepairUnitsHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(EosDictEntry data, int position) {
        if (data != null) {
            tvUnitName.setText(data.getDictname());
        }

    }
}
