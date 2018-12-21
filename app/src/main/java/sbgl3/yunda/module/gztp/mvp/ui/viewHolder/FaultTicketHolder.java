package sbgl3.yunda.module.gztp.mvp.ui.viewHolder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.utils.utilcode.util.StringUtils;

import butterknife.BindView;
import retrofit2.http.POST;
import sbgl3.yunda.R;
import sbgl3.yunda.module.gztp.entry.FaultOrder;
import sbgl3.yunda.module.sbxj.entry.InspectPlanEquipmentBean;

public class FaultTicketHolder extends BaseHolder<FaultOrder> {
    @BindView(R.id.tvEquipCode)
    TextView tvEquipCode;
    @BindView(R.id.tvEquipName)
    TextView tvEquipName;
    @BindView(R.id.tvFaultContent)
    TextView tvFaultContent;
    @BindView(R.id.tvFaultOrderNo)
    TextView tvFaultOrderNo;
    @BindView(R.id.tvUsePlace)
    TextView tvUsePlace;
    @BindView(R.id.tvStatus)
    TextView tvStatus;

    public FaultTicketHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(FaultOrder data, int position) {
        if (data != null) {
            if(!StringUtils.isTrimEmpty(data.getEquipmentName())){
                tvEquipName.setText(data.getEquipmentName());
            }else {
                tvEquipName.setText("");
            }
            if(!StringUtils.isTrimEmpty(data.getEquipmentCode())){
                tvEquipCode.setText(data.getEquipmentCode());
            }else {
                tvEquipCode.setText("");
            }
            if(!StringUtils.isTrimEmpty(data.getFaultOrderNo())){
                tvFaultOrderNo.setText(data.getFaultOrderNo());
            }else {
                tvFaultOrderNo.setText("");
            }
            if(!StringUtils.isTrimEmpty(data.getFaultPhenomenon())){
                tvFaultContent.setText(data.getFaultPhenomenon());
            }else {
                tvFaultContent.setText("");
            }
            if(!StringUtils.isTrimEmpty(data.getUsePlace())){
                tvUsePlace.setText(data.getUsePlace());
            }else {
                tvUsePlace.setText("");
            }
            if(!StringUtils.isTrimEmpty(data.getState())){
                tvStatus.setText(data.getState());
                if(data.getState().equals(FaultOrder.STATE_XJ)||data.getState().contains(FaultOrder.STATE_TH)){
                    tvStatus.setBackgroundResource(R.drawable.btn_full_red_bg);
                }else if(data.getState().equals(FaultOrder.STATE_YCL)){
                    tvStatus.setBackgroundResource(R.drawable.btn_full_green_bg);
                }else {
                    tvStatus.setBackgroundResource(R.drawable.btn_full_yellow_bg);
                }
            }else {
                tvStatus.setVisibility(View.GONE);
            }
        }
    }
}
