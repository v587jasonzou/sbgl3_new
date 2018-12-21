package sbgl3.yunda.module.spot_check.mvp.ui.adapter;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

import butterknife.BindView;
import sbgl3.yunda.R;
import sbgl3.yunda.module.sbxj.mvp.ui.viewHolder.InspecPlanHolder;
import sbgl3.yunda.module.spot_check.entity.PointCheckContent;
import sbgl3.yunda.module.spot_check.mvp.ui.holder.SpotCheckHolder;

public class SpotCheckAdapter extends DefaultAdapter<PointCheckContent> {


    public SpotCheckAdapter(List<PointCheckContent> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<PointCheckContent> getHolder(View v, int viewType) {
        return new SpotCheckHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_spot_check;
    }
}
