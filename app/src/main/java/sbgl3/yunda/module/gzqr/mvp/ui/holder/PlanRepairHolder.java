package sbgl3.yunda.module.gzqr.mvp.ui.holder;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.utils.utilcode.util.StringUtils;

import butterknife.BindView;
import sbgl3.yunda.R;
import sbgl3.yunda.module.gzqr.entry.PlanRepairEquipListBean;

public class PlanRepairHolder extends BaseHolder<PlanRepairEquipListBean> {
    @BindView(R.id.tvEquipCode)
    TextView tvEquipCode;
    @BindView(R.id.tvEquipName)
    TextView tvEquipName;
    @BindView(R.id.tvFaultDesc)
    TextView tvFaultDesc;
    @BindView(R.id.tvRepairClassnameO)
    TextView tvRepairClassnameO;
    @BindView(R.id.tvUsePlace)
    TextView tvUsePlace;
    @BindView(R.id.tvstatus)
    TextView tvstatus;
    @BindView(R.id.cvCard)
    CardView cvCard;
    public PlanRepairHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(PlanRepairEquipListBean data, int position) {
        if (null != data){
            if (!StringUtils.isTrimEmpty(data.getEquipmentCode())){
                tvEquipCode.setText(data.getEquipmentCode());
            } else {
                tvEquipCode.setText("");
            }
            if (!StringUtils.isTrimEmpty(data.getEquipmentName())){
                tvEquipName.setText(data.getEquipmentName());
            } else {
                tvEquipName.setText("");
            }
            if (!StringUtils.isTrimEmpty(data.getUsePlace())){
                tvFaultDesc.setText(data.getUsePlace());
            } else {
                tvFaultDesc.setText("");
            }
            if (!StringUtils.isTrimEmpty(data.getRepairClassName())){
                tvRepairClassnameO.setText(data.getRepairClassName());
            } else {
                tvRepairClassnameO.setText("");
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
            tvUsePlace.setText(dataTime);
        }
        tvstatus.setVisibility(View.GONE);
    }
}
