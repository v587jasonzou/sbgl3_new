package sbgl3.yunda.module.spot_check.mvp.ui.holder;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import sbgl3.yunda.R;
import sbgl3.yunda.entry.ItemClickMessage;
import sbgl3.yunda.module.spot_check.entity.PointCheckContent;

public class SpotCheckHolder extends BaseHolder<PointCheckContent> {
    @BindView(R.id.tvItemName)
    TextView tvItemName;
    @BindView(R.id.rbNice)
    RadioButton rbNice;
    @BindView(R.id.rbTerable)
    RadioButton rbTerable;
    @BindView(R.id.rbWaitFix)
    RadioButton rbWaitFix;
    @BindView(R.id.rbFixing)
    RadioButton rbFixing;
    @BindView(R.id.RgChoose)
    RadioGroup RgChoose;
    @BindView(R.id.cardView)
    CardView cardView;
    public SpotCheckHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(PointCheckContent data, int position) {
        if(data!=null){
            if(data.getCheckContent()!=null){
                tvItemName.setText(data.getCheckContent());
            }
            if(data.getTechnologyStateFlag()==null){
                RgChoose.clearCheck();
                rbFixing.setChecked(false);
                rbNice.setChecked(false);
                rbTerable.setChecked(false);
                rbWaitFix.setChecked(false);
            }else{
                String statu = data.getTechnologyStateFlag();
                if(PointCheckContent.STATE_FLAG_LH.equals(statu)){
                    rbNice.setChecked(true);
                    rbTerable.setChecked(false);
                    rbWaitFix.setChecked(false);
                    rbFixing.setChecked(false);
                }else if(PointCheckContent.STATE_FLAG_BL.equals(statu)){
                    rbNice.setChecked(false);
                    rbTerable.setChecked(true);
                    rbWaitFix.setChecked(false);
                    rbFixing.setChecked(false);
                }else if(PointCheckContent.STATE_FLAG_DX.equals(statu)){
                    rbNice.setChecked(false);
                    rbTerable.setChecked(false);
                    rbWaitFix.setChecked(true);
                    rbFixing.setChecked(false);
                }else {
                    rbNice.setChecked(false);
                    rbTerable.setChecked(false);
                    rbWaitFix.setChecked(false);
                    rbFixing.setChecked(true);
                }
            }
            RgChoose.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if(checkedId==R.id.rbNice){
                        data.setTechnologyStateFlag(PointCheckContent.STATE_FLAG_LH);
                    }else if(checkedId==R.id.rbTerable){
                        data.setTechnologyStateFlag(PointCheckContent.STATE_FLAG_BL);
                    }else if(checkedId==R.id.rbWaitFix){
                        data.setTechnologyStateFlag(PointCheckContent.STATE_FLAG_DX);
                    }else {
                        data.setTechnologyStateFlag(PointCheckContent.STATE_FLAG_XL);
                    }
                    ItemClickMessage message = new ItemClickMessage();
                    message.setType("SpotItemCheck");
                    message.setPosition(position);
                    EventBus.getDefault().post(message);
                }
            });
        }
    }
}
