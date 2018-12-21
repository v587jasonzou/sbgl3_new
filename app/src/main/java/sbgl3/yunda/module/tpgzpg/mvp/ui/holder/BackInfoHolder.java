package sbgl3.yunda.module.tpgzpg.mvp.ui.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import sbgl3.yunda.R;
import sbgl3.yunda.module.tpgzpg.entry.BackInfoBean;

public class BackInfoHolder extends BaseHolder<BackInfoBean> {
    Context context;
    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.line)
    TextView line;
    @BindView(R.id.tvBackUser)
    TextView tvBackUser;
    @BindView(R.id.tvBackInfo)
    TextView tvBackInfo;
    public BackInfoHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
    }
    @Override
    public void setData(BackInfoBean data, int position) {
        if (data != null) {
            if (data.getUpdateTime() != null && data.getUpdateTime() > 0) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String date = df.format(new Date(data.getUpdateTime()));
                SimpleDateFormat df1 = new SimpleDateFormat("HH:mm");
                String time = df1.format(new Date(data.getUpdateTime()));
                tvDate.setText(date);
                tvTime.setText(time);
            }else {
                tvDate.setText("");
                tvTime.setText("");
            }
            if(data.getBackPerson()!=null){
                if(data.getBackPersonDuty()!=null){
                    tvBackUser.setText(data.getBackPerson()+"("+data.getBackPersonDuty()+")");
                }else {
                    tvBackUser.setText(data.getBackPerson());
                }
            }else {
                if(data.getBackPersonDuty()!=null){
                    tvBackUser.setText(data.getBackPersonDuty());
                }else {
                    tvBackUser.setText("");
                }
            }
            if(data.getBackReason()!=null){
                tvBackInfo.setText(data.getBackReason());
            }else {
                tvBackInfo.setText("");
            }

        }

    }
}
