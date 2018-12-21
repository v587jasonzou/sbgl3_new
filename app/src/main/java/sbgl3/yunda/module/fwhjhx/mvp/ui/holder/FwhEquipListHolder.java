package sbgl3.yunda.module.fwhjhx.mvp.ui.holder;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.utils.utilcode.util.StringUtils;
import com.jess.arms.utils.utilcode.util.TimeUtils;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import sbgl3.yunda.R;
import sbgl3.yunda.entry.ItemClickMessage;
import sbgl3.yunda.module.equipinfo.entity.EquipmentPrimaryInfo;
import sbgl3.yunda.module.fwhjhx.mvp.entity.RepairTaskList;

public class FwhEquipListHolder extends BaseHolder<RepairTaskList> {
    @BindView(R.id.tvEquipCode)
    TextView tvEquipCode;
    @BindView(R.id.tvEquipName)
    TextView tvEquipName;
    @BindView(R.id.tvUsePlace)
    TextView tvUsePlace;
    @BindView(R.id.tvRepairClassname)
    TextView tvRepairClassname;
    @BindView(R.id.tvPlanData)
    TextView tvPlanData;
    @BindView(R.id.tvstatus)
    TextView tvstatus;
    @BindView(R.id.cvCard)
    CardView cvCard;


    public FwhEquipListHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(RepairTaskList data, int position) {
        if (data != null) {
            if (!StringUtils.isTrimEmpty(data.getEquipmentName())) {
                tvEquipName.setText(data.getEquipmentName());
            }
            if (!StringUtils.isTrimEmpty(data.getEquipmentCode())) {
                tvEquipCode.setText(data.getEquipmentCode());
            }
            if (!StringUtils.isTrimEmpty(data.getUsePlace())) {
                tvUsePlace.setText(data.getUsePlace());
            }else {
                tvUsePlace.setText("");
            }
            if(!StringUtils.isTrimEmpty(data.getRepairClassName())){
                tvRepairClassname.setText(data.getRepairClassName());
            }else {
                tvRepairClassname.setText("");
            }
            Date date = null;
            if(data.getEndTime()!=null){
                SimpleDateFormat sr = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    date = sr.parse(data.getEndTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (date.before(new Date()) && data.getOrgWclCount() != null && data.getOrgWclCount() > 0) {
                    tvstatus.setText("已延期");
                    tvstatus.setBackgroundResource(R.drawable.statu_bg_red);
                } else if (data.getOrgWclCount() == null || data.getOrgWclCount() <= 0) {
                    tvstatus.setText("已处理");
                    tvstatus.setBackgroundResource(R.drawable.statu_bg_green);
                } else {
                    tvstatus.setText(data.getOrgWclCount().toString());
                    tvstatus.setBackgroundResource(R.drawable.circle_blue);
                }
            }
            String dataTime  = "";
            if(data.getBeginTime()!=null){
                dataTime = "("+data.getBeginTime();
            }
            if(data.getEndTime()!=null){
                String endDate = data.getEndTime();
                if(dataTime.equals("")){
                    dataTime = "("+endDate;
                }else {
                    dataTime = dataTime+"至"+endDate;
                }
            }
            if(!dataTime.equals("")){
                dataTime = dataTime+")";
            }
           tvPlanData.setText(dataTime);
            cvCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ItemClickMessage message = new ItemClickMessage();
                    message.setPosition(position);
                    message.setType("FwhEquipClick");
                    EventBus.getDefault().post(message);
                }
            });
        }

    }
}
