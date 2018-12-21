package sbgl3.yunda.module.fwhjhx.mvp.ui.adapter;

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

import com.jess.arms.utils.utilcode.util.AppUtils;
import com.jess.arms.utils.utilcode.util.ImageUtils;
import com.jess.arms.utils.utilcode.util.ScreenUtils;
import com.jess.arms.utils.utilcode.util.SizeUtils;

import java.util.List;

import butterknife.BindView;
import sbgl3.yunda.R;
import sbgl3.yunda.module.fwhjhx.mvp.entity.RepairScopeCase;
import sbgl3.yunda.module.fwhjhx.mvp.entity.RepairWorkOrder;

public class ScopeWorkOrderAdapter extends BaseExpandableListAdapter {
    List<RepairScopeCase> parrents;
    Context context;
    MyOnItemclickListner myOnItemclickListner;
    public interface MyOnItemclickListner{
        void OnParentClick(int parentPosition,boolean isExpand);
        void OnChildClick(int parentPosition,int ChildPosision);
        void OnConfirmClick(int parentPosition,int ChidPosision);
        void OnManageClick(int parentPosition,int ChidPosision);
    }
    public void SetMyClickListner(MyOnItemclickListner myOnItemclickListner){
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
            view = LayoutInflater.from(context).inflate(R.layout.item_fwh_repairscope, null);
        } else {
            view = convertView;
        }
        CheckBox cbCheck = (CheckBox) view.findViewById(R.id.cbCheck);
        ImageView ivConfirm = (ImageView) view.findViewById(R.id.ivConfirm);
        TextView tvSeq = (TextView) view.findViewById(R.id.tvSeq);
        TextView tvInfo = (TextView) view.findViewById(R.id.tvInfo);
        ImageView ivArrow = (ImageView) view.findViewById(R.id.ivArrow);
        RepairScopeCase entity = parrents.get(groupPosition);
        CardView cardView = (CardView)view.findViewById(R.id.cvContent);
        ImageView ivTriangle = (ImageView)view.findViewById(R.id.ivTriangle);
        if (entity != null) {
            if (entity.getColor() != null && !"".equals(entity.getColor())) {
                ivTriangle.setVisibility(View.VISIBLE);
                String color = entity.getColor().toUpperCase();
                if(color.equals("#FF0000")){
                    ivTriangle.setImageResource(R.drawable.triangle_red_icon);
                }else if(color.equals("#FFC000")){
                    ivTriangle.setImageResource(R.drawable.triangle_yellow_icon);
                }else if(color.equals("#0070C0")){
                    ivTriangle.setImageResource(R.drawable.triangle_blue_icon);
                }else {
                    ivTriangle.setImageResource(R.drawable.triangle_green_icon);
                }
//                tvInfo.setBackgroundColor(Color.parseColor(entity.getColor()));
//                tvInfo.setTextColor(Color.parseColor("#ffffff"));

            } else {
//                tvInfo.setBackgroundColor(Color.parseColor("#00000000"));
//                tvInfo.setTextColor(Color.parseColor("#2D2D2D"));
                ivTriangle.setVisibility(View.GONE);
            }
        }
        cbCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(parrents.get(groupPosition).getChecked()==RepairScopeCase.CHECKED){
                    parrents.get(groupPosition).setChecked(RepairScopeCase.UNCHECKED);
                    if(parrents.get(groupPosition).getWorkOrders()!=null&&parrents.get(groupPosition)
                            .getWorkOrders().size()>0){
                        for(RepairWorkOrder order:parrents.get(groupPosition).getWorkOrders()){
                            order.setChecked(RepairWorkOrder.UNCHECKED);
                        }
                    }
                }else {
                    parrents.get(groupPosition).setChecked(RepairScopeCase.CHECKED);
                    if(parrents.get(groupPosition).getWorkOrders()!=null&&parrents.get(groupPosition)
                            .getWorkOrders().size()>0){
                        for(RepairWorkOrder order:parrents.get(groupPosition).getWorkOrders()){
                            order.setChecked(RepairWorkOrder.CHECKED);
                        }
                    }
                }
                notifyDataSetChanged();
            }
        });
        if (entity.getChecked() == RepairScopeCase.CHECKED) {
            cbCheck.setChecked(true);
        } else {
            cbCheck.setChecked(false);
        }
        tvInfo.setText(entity.getRepairItemName());
        if(entity.getSortNo()!=null){
            tvSeq.setText(entity.getSortNo().toString());
        }
        if (entity.getWclCount() == null || entity.getWclCount() <= 0) {
            cbCheck.setVisibility(View.GONE);
            ivConfirm.setVisibility(View.VISIBLE);
        } else {
            cbCheck.setVisibility(View.VISIBLE);
            ivConfirm.setVisibility(View.GONE);
        }
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myOnItemclickListner.OnParentClick(groupPosition,isExpanded);
            }
        });
        if(isExpanded){
            if (cardView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) cardView.getLayoutParams();
                p.setMargins(SizeUtils.dp2px(16), SizeUtils.dp2px(8), SizeUtils.dp2px(16),0);
                cardView.requestLayout();
            }
            ivArrow.setImageResource(R.mipmap.item_arrow_up);
        }else {
            ivArrow.setImageResource(R.mipmap.item_arrow_down);
            if (cardView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) cardView.getLayoutParams();
                p.setMargins(SizeUtils.dp2px(16), SizeUtils.dp2px(8), SizeUtils.dp2px(16), SizeUtils.dp2px(8));
                cardView.requestLayout();
            }
        }
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_fwh_workorder, null);
        } else {
            view = convertView;
        }
        CheckBox cbCheck = (CheckBox) view.findViewById(R.id.cbCheck);
        TextView tvSeq = (TextView) view.findViewById(R.id.tvSeq);
        TextView tvInfo = (TextView) view.findViewById(R.id.tvInfo);
        ImageView ivArrow = (ImageView) view.findViewById(R.id.ivConfirm);
        LinearLayout llfindStandard = (LinearLayout)view.findViewById(R.id.llfindStandard);
        LinearLayout llManageOrder = (LinearLayout)view.findViewById(R.id.llManageOrder);
        LinearLayout llConfirm = (LinearLayout)view.findViewById(R.id.llConfirm);
        CardView cvCard = (CardView)view.findViewById(R.id.cvCard);
        if(childPosition==parrents.get(groupPosition).getWorkOrders().size()-1){
            if (cvCard.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) cvCard.getLayoutParams();
                p.setMargins(SizeUtils.dp2px(16), 0, SizeUtils.dp2px(16), SizeUtils.dp2px(8));
                cvCard.requestLayout();
            }
        }else {
            if (cvCard.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) cvCard.getLayoutParams();
                p.setMargins(SizeUtils.dp2px(16), 0, SizeUtils.dp2px(16),0);
                cvCard.requestLayout();
            }
        }
        cvCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myOnItemclickListner.OnChildClick(groupPosition,childPosition);
            }
        });
        cbCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myOnItemclickListner.OnChildClick(groupPosition,childPosition);
            }
        });
        llConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myOnItemclickListner.OnConfirmClick(groupPosition,childPosition);
            }
        });
        llManageOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myOnItemclickListner.OnManageClick(groupPosition,childPosition);
            }
        });
        RepairWorkOrder entity = parrents.get(groupPosition).getWorkOrders().get(childPosition);
        if (entity != null) {
//            if (entity.getColor() != null && !"".equals(entity.getColor())) {
//                tvInfo.setBackgroundColor(Color.parseColor(entity.getColor()));
//                tvInfo.setTextColor(Color.parseColor("#ffffff"));
//            } else {
//                tvInfo.setBackgroundColor(Color.parseColor("#00000000"));
//                tvInfo.setTextColor(Color.parseColor("#603b07"));
//            }
        }
        if (entity.getChecked() == RepairScopeCase.CHECKED) {
            cbCheck.setChecked(true);
        } else {
            cbCheck.setChecked(false);
        }
        if (entity.getOrderStatus() != null && RepairWorkOrder.ORDER_STATUS_YCL == entity.getOrderStatus()) {
            cbCheck.setVisibility(View.GONE);
            ivArrow.setVisibility(View.VISIBLE);
        } else {
            cbCheck.setVisibility(View.VISIBLE);
            ivArrow.setVisibility(View.GONE);
        }
        tvInfo.setText(entity.getWorkContent());
        if(entity.getSortNo()!=null){
            tvSeq.setText(entity.getSortNo().toString());
        }
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
