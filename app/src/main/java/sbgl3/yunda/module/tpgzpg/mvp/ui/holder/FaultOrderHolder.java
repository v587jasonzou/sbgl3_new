package sbgl3.yunda.module.tpgzpg.mvp.ui.holder;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import sbgl3.yunda.R;
import sbgl3.yunda.entry.MessageBean;
import sbgl3.yunda.module.tpgzpg.entry.FaultOrderBean;

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
    @BindView(R.id.cbCheck)
    CheckBox cbCheck;
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
                tvUsePlace.setText(data.getUsePlace().toString());
            } else {
                tvUsePlace.setText("");
            }
            if (null!=data.getState()){
                if (data.getState().equals("已派工")){
                    tvstatus.setText("未派工");
                    tvstatus.setBackgroundResource(R.drawable.statu_bg_red);
                }
            } else {
                tvstatus.setText("");
            }
            cbCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        data.setChecked(FaultOrderBean.CHECKED);
                    }
                    else {
                        data.setChecked(FaultOrderBean.UNCHECKED);
                    }
                    MessageBean messageBean = new MessageBean();
                    messageBean.setMsgType("EquipmentsSelected");
                    messageBean.setPosition(position);
                    messageBean.setSuccess(isChecked);
                    EventBus.getDefault().post(messageBean);
                }
            });
            if (FaultOrderBean.UNCHECKED == data.getChecked()) {
                cbCheck.setChecked(false);
            } else {
                cbCheck.setChecked(true);
            }
        }

    }
}
