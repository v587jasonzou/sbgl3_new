package sbgl3.yunda.globle.viewHolder;

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
import sbgl3.yunda.entry.UserInfo;
import sbgl3.yunda.module.sbxj.entry.InspectRecord;
/**
 * <li>标题: 用户添加控件列表控件holder
 * <li>创建人：周雪巍
 * <li>创建日期：2018-09-12
 * <li>修改人:
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 * @author 周雪巍
 * @version 1.0
 */
public class AddUserHolder extends BaseHolder<UserInfo> {
    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.tvEmpId)
    TextView tvEmpId;
    @BindView(R.id.cbCheck)
    CheckBox cbCheck;
    @BindView(R.id.cvCard)
    CardView cvCard;
    public AddUserHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(UserInfo data, int position) {
        if (data != null) {
            if (data.getEmpname() != null) {
                tvUserName.setText(data.getEmpname());
            }
            if (data.getEmpid() != null) {
                tvEmpId.setText(data.getUserid());
            }
            cbCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {
                        data.setChecked(UserInfo.CHECKED);
                    } else {
                        data.setChecked(UserInfo.UNCHECKED);
                    }
                    MessageBean messageBean = new MessageBean();
                    messageBean.setMsgType("UserSelected");
                    messageBean.setPosition(position);
                    messageBean.setSuccess(isChecked);
                    EventBus.getDefault().post(messageBean);
                }
            });
            cvCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(cbCheck.isChecked()){
                        cbCheck.setChecked(false);
                    }else {
                        cbCheck.setChecked(true);
                    }
                }
            });
            if (UserInfo.UNCHECKED == data.getChecked()) {
                cbCheck.setChecked(false);
            } else {
                cbCheck.setChecked(true);
            }
        }
    }
}
