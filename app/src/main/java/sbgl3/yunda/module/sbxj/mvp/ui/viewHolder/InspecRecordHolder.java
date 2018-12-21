package sbgl3.yunda.module.sbxj.mvp.ui.viewHolder;

import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
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
import sbgl3.yunda.module.sbxj.entry.InspectRecord;

public class InspecRecordHolder extends BaseHolder<InspectRecord> {
    @BindView(R.id.cbCheck)
    CheckBox cbCheck;
    @BindView(R.id.ivConfirm)
    ImageView ivConfirm;
    @BindView(R.id.tvInfo)
    TextView tvInfo;
    @BindView(R.id.tvSeq)
    TextView tvSeq;
    public InspecRecordHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(InspectRecord data, int position) {
        if (data != null) {
            if(data.getSeqNo()!=null){
                tvSeq.setText(data.getSeqNo()+"");
            }
            if(data.getCheckItem()!=null){
                tvInfo.setText(data.getCheckItem());
            }
            cbCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        data.setChecked(InspectRecord.CHECKED);
                    }
                    else {
                        data.setChecked(InspectRecord.UNCHECKED);
                    }
                }
            });
            if (!TextUtils.isEmpty(data.getCheckResult())) {
                cbCheck.setVisibility(View.GONE);
                ivConfirm.setVisibility(View.VISIBLE);
            } else {
                cbCheck.setVisibility(View.VISIBLE);
                ivConfirm.setVisibility(View.GONE);

                if (InspectRecord.UNCHECKED == data.getChecked()) {
                    cbCheck.setChecked(false);
                } else {
                    cbCheck.setChecked(true);
                }
            }
        }
    }
}
