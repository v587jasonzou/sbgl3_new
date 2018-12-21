package sbgl3.yunda.module.userconfirm.mvp.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import sbgl3.yunda.R;
import sbgl3.yunda.module.userconfirm.entry.RepairScopeCase;
import sbgl3.yunda.module.userconfirm.entry.RepairWorkOrder;

/**
 * <li>标题: 设备管理系统
 * <li>说明:  检修范围列表适配器
 * <li>创建人：刘欢
 * <li>创建日期：2018年9月20日
 * <li>修改人:
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 *
 * @author 系统集成事业部设备管理系统项目组
 * @version 1.0
 */
public class ScopeWorkOrderAdapter extends BaseExpandableListAdapter {
    List<RepairScopeCase> parrents;
    Context context;
    MyOnItemclickListner myOnItemclickListner;
    public interface MyOnItemclickListner{
        void OnParentClick(int parentPosition, boolean isExpand);
    }
    public void setMyClickListner(MyOnItemclickListner myOnItemclickListner){
        this.myOnItemclickListner = myOnItemclickListner;
    }
    public ScopeWorkOrderAdapter(Context context, List<RepairScopeCase> parrents) {
        this.context = context;
        this.parrents = parrents;
    }

    @Override
    public int getGroupCount() {
        if (parrents != null) {
            return parrents.size();
        } else {
            return 0;
        }
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        List<RepairWorkOrder> chiditems = parrents.get(groupPosition).getWorkOrders();
        if (chiditems != null) {
            return chiditems.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return parrents.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        List<RepairWorkOrder> chiditems = parrents.get(groupPosition).getWorkOrders();
        return chiditems.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_syrqr_planrepairscope, null);
        } else {
            view = convertView;
        }
        TextView tvSeq = (TextView) view.findViewById(R.id.tvSeq);
        TextView tvInfo = (TextView) view.findViewById(R.id.tvInfo);
        ImageView ivArrow = (ImageView) view.findViewById(R.id.ivArrow);
        RepairScopeCase entity = parrents.get(groupPosition);
        CardView cardView = (CardView)view.findViewById(R.id.cvContent);
        tvInfo.setText(entity.getRepairItemName());
        if(entity.getSortNo()!=null){
            tvSeq.setText(entity.getSortNo().toString());
        }
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myOnItemclickListner.OnParentClick(groupPosition,isExpanded);
            }
        });
        if(!isExpanded){
            ivArrow.setImageResource(R.mipmap.item_arrow_up);
        }else {
            ivArrow.setImageResource(R.mipmap.item_arrow_down);
        }
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_syrqr_workorder, null);
        } else {
            view = convertView;
        }
        TextView tvSeq = (TextView) view.findViewById(R.id.tvSeq);
        TextView tvInfo = (TextView) view.findViewById(R.id.tvInfo);
        TextView tvRepairRecord = (TextView)view.findViewById(R.id.tvRepairRecord);
        TextView tvRepairPerson = (TextView)view.findViewById(R.id.tvRepairPerson);
        RepairWorkOrder entity = parrents.get(groupPosition).getWorkOrders().get(childPosition);
        if(null!=entity.getSortNo()){
            tvSeq.setText("("+entity.getSortNo().toString()+")");
        } else {
            tvSeq.setText("");
        }
        if (null!=entity.getWorkContent()){
            tvInfo.setText(entity.getWorkContent());
        }else {
            tvInfo.setText("");
        }
        if (null!=entity.getRepairRecord()){
            tvRepairRecord.setText(entity.getRepairRecord());
        }else {
            tvRepairRecord.setText("");
        }
        if (null!=entity.getWorkerName()){
            tvRepairPerson.setText(entity.getWorkerName());
        }else {
            tvRepairPerson.setText("");
        }
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
