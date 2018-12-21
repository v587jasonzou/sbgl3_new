package sbgl3.yunda.module.userconfirm.mvp.ui.holder;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;

import butterknife.BindView;
import sbgl3.yunda.R;
import sbgl3.yunda.module.userconfirm.entry.FaultOrderBean;

public class FaultOrderHolder extends BaseHolder<FaultOrderBean> {
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
    public FaultOrderHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(FaultOrderBean data, int position) {
        if (null != data){
            if (null!=data.getEquipmentCode()){
                tvEquipCode.setText(data.getEquipmentCode());
            } else {
                tvEquipCode.setText("");
            }
            if (null!=data.getEquipmentName()){
                tvEquipName.setText(data.getEquipmentName());
            } else {
                tvEquipName.setText("");
            }
            if (null!=data.getFaultPhenomenon()){
                tvFaultDesc.setText(data.getFaultPhenomenon());
            } else {
                tvFaultDesc.setText("");
            }
            if (null!=data.getFaultOrderNo()){
                tvRepairClassnameO.setText(data.getFaultOrderNo());
            } else {
                tvRepairClassnameO.setText("");
            }
            if (null!=data.getUsePlace()){
                tvUsePlace.setText(data.getUsePlace());
            } else {
                tvUsePlace.setText("");
            }
            if (null!=data.getState()){
                tvstatus.setText(data.getState());
                if (data.getState().equals("已处理")){
                    tvstatus.setBackgroundResource(R.drawable.statu_bg_green);
                } else {
                    tvstatus.setBackgroundResource(R.drawable.statu_bg_red);
                }
            } else {
                tvstatus.setText("");
            }
        }

    }
}
