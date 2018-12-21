package sbgl3.yunda.module.sbdj.mvp.ui.holder;

import android.content.Context;
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
import sbgl3.yunda.module.sbdj.entity.EquipmentUnionRFID;
import sbgl3.yunda.module.sbxj.entry.InspectPlanEquipmentBean;

public class EquipHolder extends BaseHolder<EquipmentUnionRFID> {
    @BindView(R.id.tvEquipName)
    TextView tvEquipName;
    @BindView(R.id.tvType)
    TextView tvType;
    @BindView(R.id.tvEquipCode)
    TextView tvEquipCode;
    @BindView(R.id.tvMode)
    TextView tvMode;
    @BindView(R.id.ivPhoto)
    ImageView ivPhoto;
    @BindView(R.id.cvContent)
    LinearLayout cvContent;
    @BindView(R.id.cvCard)
    CardView cvCard;
    int type = 0;
    Context context;

    public void setType(int type) {
        this.type = type;
    }

    public EquipHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
    }

    @Override
    public void setData(EquipmentUnionRFID data, int position) {
        if (data != null) {
            if (!StringUtils.isTrimEmpty(data.getEquipmentName())) {
                tvEquipName.setText(data.getEquipmentName());
            }
            if (!StringUtils.isTrimEmpty(data.getEquipmentCode())) {
                tvEquipCode.setText(data.getEquipmentCode());
            }
            if (!StringUtils.isTrimEmpty(data.getUsePlace())) {
                tvMode.setText(data.getUsePlace());
            }else {
                tvMode.setText("");
            }
            if (StringUtils.isTrimEmpty(data.getRfidCode())) {
                tvType.setText("未登记");
                tvType.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.type_bg_red));
                ivPhoto.setImageResource(R.mipmap.photo_grey);
            } else {
                tvType.setText("已登记");
                tvType.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.type_bg_green));
                ivPhoto.setImageResource(R.mipmap.photo_blue);
            }
            if (data.getStatus() == 1) {
                tvEquipName.setTextColor(ContextCompat.getColor(context,R.color.colorPrimary));
                cvContent.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.item_white_bg_click));
            } else {
                tvEquipName.setTextColor(ContextCompat.getColor(context,R.color.title_text_color));
                cvContent.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.item_white_bg));
            }
            cvCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ItemClickMessage message = new ItemClickMessage();
                    message.setPosition(position);
                    message.setType("content");
                    EventBus.getDefault().post(message);
                }
            });
            ivPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ItemClickMessage message = new ItemClickMessage();
                    message.setPosition(position);
                    message.setType("image");
                    EventBus.getDefault().post(message);
                }
            });
        }

    }
}
