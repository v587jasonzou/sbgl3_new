package sbgl3.yunda.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.utils.utilcode.util.ToastUtils;

import butterknife.BindView;
import sbgl3.yunda.R;
/**
 * 通用样式基础Dialog
 * @author 周雪巍
 * @time 2018/09/12
 * */
public class BaseDialog {
    public static AlertDialog dialog;
    public static void setDialog(Context context, String title, String confirmText, View.OnClickListener
            confirmlisnter, String cancelText, View.OnClickListener cancellisnter) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_base, null);
        dialog = new AlertDialog.Builder(context).setView(view).create();
        TextView tvTitle = (TextView)view.findViewById(R.id.tvTitle);
        TextView btCancel = (TextView)view.findViewById(R.id.btCancel);
        TextView btConfirm = (TextView)view.findViewById(R.id.btConfirm);
        dialog.show();
        if(title!=null){
            tvTitle.setText(title);
        }
        if(confirmText!=null){
            btConfirm.setText(confirmText);
        }
        if(confirmlisnter!=null){
            btConfirm.setOnClickListener(confirmlisnter);
        }
        if(cancelText!=null){
            btCancel.setText(cancelText);
        }
        if(cancellisnter!=null){
            btCancel.setOnClickListener(cancellisnter);
        }
    }
    public static void dissmiss(){
        if(dialog!=null){
            dialog.dismiss();
        }
    }
    public static void Show(){
        if(dialog!=null){
            dialog.show();
        }else {
            ToastUtils.showShort("请先设置弹出框内容");
        }
    }
    public static void clear(){
        dialog=null;
    }
}
