package sbgl3.yunda.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.PopupMenu;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.utils.utilcode.util.StringUtils;
import com.jess.arms.utils.utilcode.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import sbgl3.yunda.R;
/**
 * 自定义控件-搜索框
 * @author 周雪巍
 * @time 2018/09/12
 * */
public class MySearchEditText extends LinearLayout {
    Context context;
    ClearEditText etSearch;
    TextView tvChoose;
    List<String> datas;
    String nowData;
    PopupMenu popupMenu;
    onTypeClickListner onTypeClickListner;
    public interface onTypeClickListner{
        void OnTypeClick(String type);
    }
    public void setOnTypeClickListner(onTypeClickListner onTypeClickListner){
        this.onTypeClickListner = onTypeClickListner;
    }
    public MySearchEditText(Context context) {
        super(context);
    }

    public MySearchEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initdata();
    }

    public void setTypes(List<String> list){
        datas.clear();
        datas.addAll(list);
        nowData = datas.get(0);
        tvChoose.setText(datas.get(0));
    }
    public void setText(String text){
        if(text!=null)
        etSearch.setText(text);
    }
    public String getSerachData(){
        if(etSearch.getText()!=null){
            return etSearch.getText().toString();
        }else {
            return "";
        }

    }
    public String getType(){
        return tvChoose.getText().toString();
    }
    public String getSearch(){
        if(etSearch!=null&&etSearch.getText()!=null&& !StringUtils.isTrimEmpty(etSearch.getText().toString())){
            return etSearch.getText().toString();
        }else {
//            ToastUtils.showShort("还未输入查询内容");
            return null;
        }
    }
    private void initdata() {
        LayoutInflater.from(context).inflate(R.layout.view_myedittext,this);
        etSearch = (ClearEditText) findViewById(R.id.etMySearch);
        tvChoose = (TextView) findViewById(R.id.tvChoose);
        popupMenu = new PopupMenu(context, tvChoose);
        datas = new ArrayList<>();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                tvChoose.setText(item.getTitle());
                nowData = item.getTitle().toString();
                InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(tvChoose.getWindowToken(), 0);
                }
                popupMenu.dismiss();
                onTypeClickListner.OnTypeClick(item.getTitle().toString());
                return false;
            }
        });
        tvChoose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (datas.size() > 0) {
                    android.view.Menu menu_more = popupMenu.getMenu();
                    menu_more.clear();
                    int size = datas.size();
                    for (int i = 0; i < size; i++) {
                        menu_more.add(android.view.Menu.NONE, android.view.Menu.FIRST + i, i, datas.get(i));
                    }
                    popupMenu.show();
                }
            }
        });
    }
}
