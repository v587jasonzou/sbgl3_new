package sbgl3.yunda.module.sbxj.mvp.ui.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.utils.utilcode.util.StringUtils;
import com.jess.arms.utils.utilcode.util.TimeUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import sbgl3.yunda.R;
import sbgl3.yunda.module.main.Entry.MenuBean;
import sbgl3.yunda.module.sbxj.entry.InspectPlanBean;

public class InspecPlanHolder extends BaseHolder<InspectPlanBean> {
    @BindView(R.id.tvplanType)
    TextView tvplanType;
    @BindView(R.id.tvIslate)
    TextView tvIslate;
    @BindView(R.id.tvCreatedTime)
    TextView tvCreatedTime;
    @BindView(R.id.tvTodoNum)
    TextView tvTodoNum;
    public InspecPlanHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(InspectPlanBean data, int position) {
        if (data != null) {
            if (!StringUtils.isTrimEmpty(data.getRouteName())) {
                tvplanType.setText(data.getRouteName());
            }
            if(data.getPlanEndDate()!=null){
                DateFormat sf = new SimpleDateFormat("yyyy/MM/dd");
                tvCreatedTime.setText(TimeUtils.date2String(new Date(data.getPlanEndDate()),sf));
                if (new Date(data.getPlanEndDate()).before(new Date())) {
                    tvIslate.setVisibility(View.VISIBLE);
                }else {
                    tvIslate.setVisibility(View.GONE);
                }
            }else {
                tvIslate.setVisibility(View.GONE);
            }
            if(data.getWxjCount()!=null&&data.getWxjCount()>0){
                tvTodoNum.setVisibility(View.VISIBLE);
                tvTodoNum.setText(data.getWxjCount()+"");
            }else {
                tvTodoNum.setVisibility(View.GONE);
            }
        }
    }
}
