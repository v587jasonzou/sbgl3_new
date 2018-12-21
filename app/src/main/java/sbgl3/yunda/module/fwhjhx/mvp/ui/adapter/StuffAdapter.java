package sbgl3.yunda.module.fwhjhx.mvp.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.utilcode.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import sbgl3.yunda.R;
import sbgl3.yunda.module.fwhjhx.mvp.entity.MateriaBean;
import sbgl3.yunda.module.fwhjhx.mvp.entity.StuffParams;

public class StuffAdapter extends BaseAdapter {
    Context context;
    List<MateriaBean> list;
    ViewHolder holder;
    OnItemEditListner onItemEditListner;
    List<StuffParams> stuffParams = new ArrayList<>();
    PopupMenu popupMenu;
    Toolbar view;
    public StuffAdapter(Context context, List<MateriaBean> list) {
        this.context = context;
        this.list = list;
    }
    public void setView(Toolbar view){
        this.view = view;
    }
    public void setStuffParams(List<StuffParams> params){
        stuffParams.clear();
        if(params!=null&&params.size()>0){
            stuffParams.addAll(params);
        }
    }

    public interface OnItemEditListner{
        void OnCancelClick(int position);
        void OnConfirmClick(int position);
        void OnEditclick(int position);
        void OnWaitClick(String type,int position);
        void OnStuffSelected(int paramsPosition,int EditPosition);
    }
    public void setOnItemEditLisner(OnItemEditListner onItemEditListner){
        this.onItemEditListner = onItemEditListner;
    }
    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_stuff_add, null);
            holder = new ViewHolder();
            holder.tvReadStuffName = (TextView)convertView.findViewById(R.id.tvReadStuffName);
            holder.tvReadStuffOnePrice = (TextView)convertView.findViewById(R.id.tvReadStuffOnePrice);
            holder.tvReadStuffNumber = (TextView)convertView.findViewById(R.id.tvReadStuffNumber);
            holder.tvReadStuffTotalPrice = (TextView)convertView.findViewById(R.id.tvReadStuffTotalPrice);
            holder.tvReadStaus = (TextView)convertView.findViewById(R.id.tvReadStaus);
            holder.cvReadContent = (CardView)convertView.findViewById(R.id.cvReadContent);
            holder.llReadContent = (LinearLayout)convertView.findViewById(R.id.llReadContent);
            holder.tvEditAddTitle = (TextView)convertView.findViewById(R.id.tvEditAddTitle);
            holder.tvEditStuffName = (EditText)convertView.findViewById(R.id.tvEditStuffName);
            holder.etEditStuffOnePrice = (EditText)convertView.findViewById(R.id.etEditStuffOnePrice);
            holder.ivMinus = (ImageView)convertView.findViewById(R.id.ivMinus);
            holder.etEditStuffNumber = (EditText)convertView.findViewById(R.id.etEditStuffNumber);
            holder.ivAdd = (ImageView)convertView.findViewById(R.id.ivAdd);
            holder.etEditStuffTotalPrice = (TextView)convertView.findViewById(R.id.etEditStuffTotalPrice);
            holder.tvEditCancel = (TextView)convertView.findViewById(R.id.tvEditCancel);
            holder.tvEditConfirm = (TextView)convertView.findViewById(R.id.tvEditConfirm);
            holder.cvEditContent = (CardView)convertView.findViewById(R.id.cvEditContent);
            holder.llEditContent = (LinearLayout)convertView.findViewById(R.id.llEditContent);
            holder.ivEditStuffName = (ImageView)convertView.findViewById(R.id.ivEditStuffName);
            holder.ivEditStuff = (ImageView)convertView.findViewById(R.id.ivEditStuff);
            holder.rlEditNumber = (RelativeLayout)convertView.findViewById(R.id.rlEditNumber);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        final MateriaBean bean = list.get(position);
        if(bean!=null){
            if(bean.isEdit()){
                holder.llEditContent.setVisibility(View.VISIBLE);
                holder.llReadContent.setVisibility(View.GONE);
            }else {
                holder.llEditContent.setVisibility(View.GONE);
                holder.llReadContent.setVisibility(View.VISIBLE);

            }
            holder.ivEditStuffName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(stuffParams!=null&&stuffParams.size()>0){
                        popupMenu = new PopupMenu(context, view);
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                onItemEditListner.OnStuffSelected(item.getItemId(),position);
                                bean.setStuffName(item.getTitle().toString());
                                bean.setStuffUnitPrice(stuffParams.get(item.getItemId()).getStuffUnitPrice());
                                InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
                                if (imm != null) {
                                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                }
                                popupMenu.dismiss();
                                notifyDataSetChanged();
                                return false;
                            }
                        });
                        Menu menu_more = popupMenu.getMenu();
                        menu_more.clear();
                        int size = stuffParams.size();
                        for (int i = 0; i < size; i++) {
                            menu_more.add(Menu.NONE, i, i, stuffParams.get(i).getStuffName());
                        }
                        popupMenu.show();
                    }else {
                        ToastUtils.showShort("无可选择物料信息请手动输入");
                    }
                }
            });
            if(bean.getStatus()!=null){
                if(bean.getStatus().equals("添加")){
                    holder.tvEditAddTitle.setText("添加物料信息");
                }else {
                    holder.tvEditAddTitle.setText("修改物料信息");
                }
            }
            if(bean.getStuffName()!=null){
                holder.tvEditStuffName.setText(bean.getStuffName());
            }else {
                holder.tvEditStuffName.setText("");
            }
            if(bean.getStuffNumberStr()!=null){
                holder.etEditStuffNumber.setText(bean.getStuffNumberStr());
            }else {
                holder.etEditStuffNumber.setText("0");
            }

            holder.etEditStuffNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    switch(actionId){
                        case EditorInfo.IME_NULL:
                            break;
                        case EditorInfo.IME_ACTION_SEND:
                            break;
                        case EditorInfo.IME_ACTION_DONE:
                            notifyDataSetChanged();
                            ((InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(((Activity)context).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                            break;
                    }
                    return true;
                }
            });
            holder.etEditStuffNumber.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() == 2) {
                        if (s.charAt(0) == '0' && s.charAt(1) == '0') {
                            bean.setStuffNumberStr("0");
                            bean.setStuffNumber(0.0);
                        }
                    } else if (s.length() == 1) {
                        if (!"0123456789".contains(s)) {
                            bean.setStuffNumberStr("");
                            bean.setStuffNumber(0.0);
                        }
                    }
                    bean.setStuffNumberStr(s.toString());
                    if(s.toString().equals("")){
                        bean.setStuffNumber(0.0);
                    }else {
                        bean.setStuffNumber(Double.parseDouble(s.toString()));
                    }
                    if (bean.getStuffUnitPrice()!= null ) {
                        double temp;
                        if(s.toString().equals("")){
                            bean.setStuffNumber(0.0);
                            temp = 0.0;
                        }else {
                            bean.setStuffNumber(Double.parseDouble(s.toString()));
                            temp = Double.parseDouble(s.toString());
                        }
                        bean.setStuffTotalMoney(ArmsUtils.mul(temp, bean.getStuffUnitPrice()));

                    }

                }
            });
            holder.ivAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(bean.getStuffNumber()!=null&&bean.getStuffNumberStr()!=null&&!bean.getStuffNumberStr().equals("")){
                        if(bean.getStuffNumberStr().contains(".")){
                            Double num = Double.parseDouble(bean.getStuffNumberStr());
                            num = num+1;
                            bean.setStuffNumber(num);
                            bean.setStuffNumberStr(num+"");
                        }else {
                            Integer num = Integer.parseInt(bean.getStuffNumberStr());
                            num = num+1;
                            bean.setStuffNumber((double)num);
                            bean.setStuffNumberStr(num+"");
                        }
                    }else {
                        bean.setStuffNumber(1.0);
                        bean.setStuffNumberStr("1");
                    }
                    notifyDataSetChanged();
                }
            });
            holder.ivMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(bean.getStuffNumber()!=null&&bean.getStuffNumberStr()!=null&&!bean.getStuffNumberStr().equals("")){
                        if(bean.getStuffNumberStr().contains(".")){
                            Double num = Double.parseDouble(bean.getStuffNumberStr());
                            if(num>1){
                                num = num-1;
                                bean.setStuffNumber(num);
                                bean.setStuffNumberStr(num+"");
                            }else {
                                bean.setStuffNumber(0.0);
                                bean.setStuffNumberStr("0");
                            }

                        }else {
                            Integer num = Integer.parseInt(bean.getStuffNumberStr());
                            if(num>1){
                                num = num-1;
                                bean.setStuffNumber((double)num);
                                bean.setStuffNumberStr(num+"");
                            }else {
                                bean.setStuffNumber(0.0);
                                bean.setStuffNumberStr("0");
                            }

                        }
                    }
                    notifyDataSetChanged();
                }
            });
            if(bean.getStuffUnitPrice()!=null){
                holder.etEditStuffOnePrice.setText(bean.getStuffUnitPrice().toString());
            }else {
                holder.etEditStuffOnePrice.setText("");
            }
            holder.etEditStuffOnePrice.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    switch(actionId){
                        case EditorInfo.IME_NULL:
                            break;
                        case EditorInfo.IME_ACTION_SEND:
                            break;
                        case EditorInfo.IME_ACTION_DONE:
                            notifyDataSetChanged();
                            ((InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(((Activity)context).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                            break;
                    }
                    return true;
                }
            });
            holder.etEditStuffOnePrice.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() == 2) {
                        if (s.charAt(0) == '0' && s.charAt(1) == '0') {
                            bean.setStuffUnitPrice(0.0);
                        }
                    } else if (s.length() == 1) {
                        if (!"0123456789".contains(s)) {
                            bean.setStuffUnitPrice(0.0);
                        }
                    }
                    if(s.toString().equals("")){
                        bean.setStuffUnitPrice(0.0);
                    }else {
                        bean.setStuffUnitPrice(Double.parseDouble(s.toString()));
                    }
                    if (bean.getStuffNumber()!= null ) {
                        double temp;
                        if(s.toString().equals("")){
                            bean.setStuffUnitPrice(0.0);
                            temp = 0.0;
                        }else {
                            bean.setStuffUnitPrice(Double.parseDouble(s.toString()));
                            temp = Double.parseDouble(s.toString());
                        }
                        bean.setStuffTotalMoney(ArmsUtils.mul(temp, bean.getStuffNumber()));
                    }

                }
            });
            if(bean.getStuffTotalMoney()!=null){
                holder.etEditStuffTotalPrice.setText(bean.getStuffTotalMoney().toString());
            }else {
                holder.etEditStuffTotalPrice.setText("");
            }
            holder.tvEditCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemEditListner.OnCancelClick(position);
                }
            });
            holder.tvEditConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(bean.getStuffName()==null||bean.getStuffName().equals("")){
                        ToastUtils.showShort("还未选择/输入物料名称");
                        return;
                    }
                    if(bean.getStuffUnitPrice()==null||bean.getStuffUnitPrice()<=0){
                        ToastUtils.showShort("还未选择/输入物料单价");
                        return;
                    }
                    if(bean.getStuffNumber()==null||bean.getStuffNumber()<=0){
                        ToastUtils.showShort("还未选择/输入物料数量");
                        return;
                    }
//                    bean.setStuffNumber(Double.parseDouble(holder.etEditStuffNumber.getText().toString()));
//                    bean.setStuffName(holder.tvEditStuffName.getText().toString());
//                    bean.setStuffUnitPrice(Double.parseDouble(holder.etEditStuffOnePrice.getText().toString()));
//                    bean.setStuffTotalMoney(Double.parseDouble(holder.etEditStuffTotalPrice.getText().toString()));
                    onItemEditListner.OnConfirmClick(position);
                }
            });
            if(bean.getStuffName()!=null){
                holder.tvReadStuffName.setText(bean.getStuffName());
            }else {
                holder.tvReadStuffName.setText("");
            }
            if(bean.getStuffNumberStr()!=null){
                holder.tvReadStuffNumber.setText(bean.getStuffNumberStr());
            }else {
                holder.tvReadStuffNumber.setText("");
            }
            if(bean.getStuffUnitPrice()!=null){
                holder.tvReadStuffOnePrice.setText(bean.getStuffUnitPrice().toString()+"¥");
            }else {
                holder.tvReadStuffOnePrice.setText("");
            }
            if(bean.getStuffTotalMoney()!=null){
                holder.tvReadStuffTotalPrice.setText(bean.getStuffTotalMoney().toString()+"¥");
            }else {
                holder.tvReadStuffTotalPrice.setText("");
            }
            if (list.get(position).getWaitStartTime() == null && list.get(position).getWaitEndTime() == null) {
                holder.tvReadStaus.setText("准备待料");
                holder.tvReadStaus.setTextColor(ContextCompat.getColor(context,R.color.colorPrimaryLight));
                holder.tvReadStaus.setBackgroundResource(R.drawable.stuff_statu_nowait_bg);
                holder.tvReadStuffName.setTextColor(ContextCompat.getColor(context,R.color.colorPrimaryLight));
            } else if (list.get(position).getWaitStartTime() != null && list.get(position).getWaitEndTime() == null) {
                holder.tvReadStaus.setText("正在待料");
                holder.tvReadStaus.setTextColor(ContextCompat.getColor(context,R.color.white));
                holder.tvReadStaus.setBackgroundResource(R.drawable.confirm_btn_bg);
                holder.tvReadStuffName.setTextColor(ContextCompat.getColor(context,R.color.colorPrimaryLight));
            } else {
                holder.tvReadStaus.setText("完成待料");
                holder.tvReadStaus.setTextColor(ContextCompat.getColor(context,R.color.green));
                holder.tvReadStaus.setBackgroundResource(R.drawable.radiobutton_empty_green_bg);
                holder.tvReadStuffName.setTextColor(ContextCompat.getColor(context,R.color.title_text_color));
            }
            holder.ivEditStuff.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemEditListner.OnEditclick(position);
                }
            });
            holder.tvReadStaus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(list.get(position).getWaitStartTime() == null && list.get(position).getWaitEndTime() == null){
                        onItemEditListner.OnWaitClick("开始待料",position);
                    }else if (list.get(position).getWaitStartTime() != null && list.get(position).getWaitEndTime() == null){
                        onItemEditListner.OnWaitClick("正在待料",position);
                    }
                }
            });
        }
        return convertView;
    }

    class ViewHolder {
        TextView tvReadStuffName;
        TextView tvReadStuffOnePrice;
        TextView tvReadStuffNumber;
        TextView tvReadStuffTotalPrice;
        TextView tvReadStaus;
        CardView cvReadContent;
        LinearLayout llReadContent;
        TextView tvEditAddTitle;
        EditText tvEditStuffName;
        EditText etEditStuffOnePrice;
        ImageView ivMinus;
        EditText etEditStuffNumber;
        ImageView ivAdd;
        TextView etEditStuffTotalPrice;
        TextView tvEditCancel;
        TextView tvEditConfirm;
        CardView cvEditContent;
        LinearLayout llEditContent;
        ImageView ivEditStuffName;
        ImageView ivEditStuff;
        RelativeLayout  rlEditNumber;
    }
}
