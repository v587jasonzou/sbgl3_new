package sbgl3.yunda.module.equipinfo.mvp.ui.holder;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.utils.utilcode.util.StringUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import sbgl3.yunda.R;
import sbgl3.yunda.entry.ItemClickMessage;
import sbgl3.yunda.module.equipinfo.entity.EquipmentPrimaryInfo;
import sbgl3.yunda.module.sbdj.entity.EquipmentUnionRFID;

public class EquipListHolder extends BaseHolder<EquipmentPrimaryInfo> {
    @BindView(R.id.tvEquipName)
    TextView tvEquipName;
    @BindView(R.id.tvType)
    TextView tvType;
    @BindView(R.id.tvEquipCode)
    TextView tvEquipCode;
    @BindView(R.id.tvMode)
    TextView tvMode;
    @BindView(R.id.tvUsePlace)
    TextView tvUsePlace;
    @BindView(R.id.cvCard)
    CardView cvCard;
    int type = 0;
    public void setType(int type) {
        this.type = type;
    }

    public EquipListHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(EquipmentPrimaryInfo data, int position) {
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
            if(!StringUtils.isTrimEmpty(data.getModel())&&!StringUtils.isTrimEmpty(data.getSpecification())){
                tvMode.setText(data.getSpecification()+","+data.getModel());
            }else {
                tvMode.setText("");
                if(!StringUtils.isTrimEmpty(data.getModel())){
                    tvMode.setText(data.getModel());
                }
                if(!StringUtils.isTrimEmpty(data.getSpecification())){
                    tvMode.setText(data.getSpecification());
                }
            }
            if (null != data.getTag() && data.getTag().equals("1")) {
                tvType.setVisibility(View.VISIBLE);
                tvType.setText("固资设备");
            }else {
                tvType.setVisibility(View.GONE);
                tvType.setText("非固资设备");
            }

            cvCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ItemClickMessage message = new ItemClickMessage();
                    message.setPosition(position);
                    message.setType("EquipClick");
                    EventBus.getDefault().post(message);
                }
            });
        }

    }
}
