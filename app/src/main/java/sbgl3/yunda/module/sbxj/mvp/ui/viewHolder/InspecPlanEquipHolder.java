package sbgl3.yunda.module.sbxj.mvp.ui.viewHolder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.utils.utilcode.util.StringUtils;
import com.jess.arms.utils.utilcode.util.TimeUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import sbgl3.yunda.R;
import sbgl3.yunda.module.sbxj.entry.InspectPlanBean;
import sbgl3.yunda.module.sbxj.entry.InspectPlanEquipmentBean;

public class InspecPlanEquipHolder extends BaseHolder<InspectPlanEquipmentBean> {
    @BindView(R.id.tvEquipCode)
    TextView tvEquipCode;
    @BindView(R.id.tvOrgName)
    TextView tvOrgName;
    @BindView(R.id.tvFixUser)
    TextView tvFixUser;
    @BindView(R.id.tvEquipName)
    TextView tvEquipName;
    @BindView(R.id.tvWclCount)
    TextView tvWclCount;
    @BindView(R.id.llUpload)
    LinearLayout llUpload;
    int type = 0;
    public void setType(int type){
        this.type = type;
    }
    public InspecPlanEquipHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(InspectPlanEquipmentBean data, int position) {
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
            if(data.getWclCount()!=null&&data.getWclCount()>0){
                tvWclCount.setVisibility(View.VISIBLE);
                tvWclCount.setText(data.getWclCount().toString());
            }else {
                tvWclCount.setVisibility(View.GONE);
            }
            if(!StringUtils.isTrimEmpty(data.getUsePlace())){
                tvOrgName.setText(data.getUsePlace());
            }else {
                tvOrgName.setText("");
            }
            if(data.getMechanicalRepairPerson()==null||data.getMechanicalCoefficient()==null||data.getMechanicalCoefficient()==0||"null".equals(data.getMechanicalRepairPerson())||"".equals(data.getMechanicalRepairPerson())){
                if(data.getElectricRepairPerson()==null||data.getElectricCoefficient()==null||data.getElectricCoefficient()==0||data.getElectricRepairPerson().equals("null")||data.getElectricRepairPerson().equals("")){
                   tvFixUser.setText("");
                }else {
                    tvFixUser.setText(data.getElectricRepairPerson());
                }
            }else {
                if(data.getElectricRepairPerson()==null||data.getElectricCoefficient()==null||data.getElectricCoefficient()==0||data.getElectricRepairPerson().equals("null")||data.getElectricRepairPerson().equals("")){
                    tvFixUser.setText(data.getMechanicalRepairPerson());
                }else {
                    tvFixUser.setText(data.getMechanicalRepairPerson()+" "+data.getElectricRepairPerson());
                }
            }
            if(type==2){
                llUpload.setVisibility(View.VISIBLE);
            }else {
                llUpload.setVisibility(View.GONE);
            }
        }
    }
}
