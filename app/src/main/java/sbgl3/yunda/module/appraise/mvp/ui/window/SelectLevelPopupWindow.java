package sbgl3.yunda.module.appraise.mvp.ui.window;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import com.jess.arms.utils.utilcode.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import sbgl3.yunda.R;
import sbgl3.yunda.module.appraise.entry.EquipmentAppraiserBean;

/**
 * <li>标题: 设备管理系统
 * <li>说明: 选择鉴定等级底部菜单控件
 * <li>创建人：刘欢
 * <li>创建日期：2018/8/30
 * <li>修改人:
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 *
 * @author 系统集成事业部设备管理系统项目组
 * @version 1.0
 */

public class SelectLevelPopupWindow extends PopupWindow implements OnClickListener{
    ImageView ivClose;
    RadioButton rbJwu;
    RadioButton rbJ1;
    RadioButton rbJ2;
    RadioButton rbJ3;
    RadioButton rbJ4;
    RadioButton rbYwu;
    RadioButton rbY1;
    RadioButton rbY2;
    RadioButton rbY3;
    RadioButton rbY4;
    RadioButton rbZhwu;
    RadioButton rbZh1;
    RadioButton rbZh2;
    RadioButton rbZh3;
    RadioButton rbZh4;
    TextView tvConfirm;

    private View mMenuView;
    String textLevelA = null;
    String textLevelB = null;
    String textLevelC = null;

    public SelectLevelPopupWindow(Activity context, OnClickListener itemsOnClick, EquipmentAppraiserBean bean) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.view_level_popup_menu, null);
        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        initView();
        setJiaxiang(bean.getEvaluateLevelA());
        setYixiang(bean.getEvaluateLevelB());
        setZonghe(bean.getSynthesizeLevel());
        ivClose.setOnClickListener(itemsOnClick);
        rbJwu.setOnClickListener(this);
        rbJ1.setOnClickListener(this);
        rbJ2.setOnClickListener(this);
        rbJ3.setOnClickListener(this);
        rbJ4.setOnClickListener(this);
        rbYwu.setOnClickListener(this);
        rbY1.setOnClickListener(this);
        rbY2.setOnClickListener(this);
        rbY3.setOnClickListener(this);
        rbY4.setOnClickListener(this);
        rbZhwu.setOnClickListener(this);
        rbZh1.setOnClickListener(this);
        rbZh2.setOnClickListener(this);
        rbZh3.setOnClickListener(this);
        rbZh4.setOnClickListener(this);
        tvConfirm.setOnClickListener(itemsOnClick);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.FILL_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        //设置当前的PopupWindow不获取获取焦点
        this.setFocusable(false);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        //this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        /*mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.getTop();
                int y=(int) event.getY();
                if(event.getAction()== MotionEvent.ACTION_UP){
                    if(y<height){
                        dismiss();
                        View view1 = (View)view.findViewById(R.id.gray_layout);
                        view1.setVisibility(View.GONE);
                    }
                }
                return true;
            }
        });*/
    }

    private void setJiaxiang(String msg){
        if(msg!=null){
          if (msg.equals("1") || msg.equals("一级")){
              rbJ1.setChecked(true);
          } else if (msg.equals("2") || msg.equals("二级")){
              rbJ2.setChecked(true);
          } else  if (msg.equals("3") || msg.equals("三级")){
              rbJ3.setChecked(true);
          } else if (msg.equals("4") || msg.equals("四级")){
              rbJ4.setChecked(true);
          } else {
              rbJwu.setChecked(true);
          }
        } else {
            rbJwu.setChecked(true);
        }
    }
    private void setYixiang(String msg){
        if(msg!=null){
            if (msg.equals("1") || msg.equals("一级")){
                rbY1.setChecked(true);
            } else if (msg.equals("2") || msg.equals("二级")){
                rbY2.setChecked(true);
            } else  if (msg.equals("3") || msg.equals("三级")){
                rbY3.setChecked(true);
            } else if (msg.equals("4") || msg.equals("四级")){
                rbY4.setChecked(true);
            } else {
                rbYwu.setChecked(true);
            }
        } else {
            rbYwu.setChecked(true);
        }
    }
    private void setZonghe(String msg){
        if(msg!=null){
            if (msg.equals("1") || msg.equals("一级")){
                rbZh1.setChecked(true);
            } else if (msg.equals("2") || msg.equals("二级")){
                rbZh2.setChecked(true);
            } else  if (msg.equals("3") || msg.equals("三级")){
                rbZh3.setChecked(true);
            } else if (msg.equals("4") || msg.equals("四级")){
                rbZh4.setChecked(true);
            } else {
                rbZhwu.setChecked(true);
            }
        } else {
            rbZhwu.setChecked(true);
        }
    }

    private void initView() {
        ivClose = (ImageView) mMenuView.findViewById(R.id.ivClose);
        rbJwu = (RadioButton) mMenuView.findViewById(R.id.rb_jwu);
        rbJ1 = (RadioButton) mMenuView.findViewById(R.id.rb_j1);
        rbJ2 = (RadioButton) mMenuView.findViewById(R.id.rb_j2);
        rbJ3 = (RadioButton) mMenuView.findViewById(R.id.rb_j3);
        rbJ4 = (RadioButton) mMenuView.findViewById(R.id.rb_j4);
        rbYwu = (RadioButton) mMenuView.findViewById(R.id.rb_ywu);
        rbY1 = (RadioButton) mMenuView.findViewById(R.id.rb_y1);
        rbY2 = (RadioButton) mMenuView.findViewById(R.id.rb_y2);
        rbY3 = (RadioButton) mMenuView.findViewById(R.id.rb_y3);
        rbY4 = (RadioButton) mMenuView.findViewById(R.id.rb_y4);
        rbZhwu = (RadioButton) mMenuView.findViewById(R.id.rb_zhwu);
        rbZh1 = (RadioButton) mMenuView.findViewById(R.id.rb_zh1);
        rbZh2 = (RadioButton) mMenuView.findViewById(R.id.rb_zh2);
        rbZh3 = (RadioButton) mMenuView.findViewById(R.id.rb_zh3);
        rbZh4 = (RadioButton) mMenuView.findViewById(R.id.rb_zh4);
        tvConfirm = (TextView) mMenuView.findViewById(R.id.tvConfirm);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rb_jwu:
                textLevelA = "无";
                break;
            case R.id.rb_j1:
                textLevelA = "一级";
                break;
            case R.id.rb_j2:
                textLevelA = "二级";
                break;
            case R.id.rb_j3:
                textLevelA = "三级";
                break;
            case R.id.rb_j4:
                textLevelA = "四级";
                break;
            case R.id.rb_ywu:
                textLevelB = "无";
                break;
            case R.id.rb_y1:
                textLevelB = "一级";
                break;
            case R.id.rb_y2:
                textLevelB = "二级";
                break;
            case R.id.rb_y3:
                textLevelB = "三级";
                break;
            case R.id.rb_y4:
                textLevelB = "四级";
                break;
            case R.id.rb_zhwu:
                textLevelC = "无";
                break;
            case R.id.rb_zh1:
                textLevelC = "一级";
                break;
            case R.id.rb_zh2:
                textLevelC = "二级";
                break;
            case R.id.rb_zh3:
                textLevelC = "三级";
                break;
            case R.id.rb_zh4:
                textLevelC = "四级";
                break;
            default:
        }
    }
   /* public List<String> getChooseText(){
        textLevelList.add(textLevelA);
        textLevelList.add(textLevelB);
        textLevelList.add(textLevelC);
        return textLevelList;
    }*/
    public String getJiaxiang(){
        setJiaxiang(textLevelA);
        return textLevelA;
    }
    public String getYixiang(){
        setYixiang(textLevelB);
        return textLevelB;
    }
    public String getZonghe(){
        setZonghe(textLevelC);
        return textLevelC;
    }
}
