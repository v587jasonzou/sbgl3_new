package sbgl3.yunda.module.main.adapter;

import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.jess.arms.utils.utilcode.util.TimeUtils;

import java.util.Timer;

public class SimpleItemTouchHelperCallback extends  ItemTouchHelper.Callback {
    private ItemTouchHelperAdapter mAdapter;
    public int start = 0;
    public int end =0;
    public boolean isFirst = true;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1){
                start = 0;
                end = 0;
                mAdapter.onItemMove(msg.arg1,msg.arg2);
            }
        }
    };
    public SimpleItemTouchHelperCallback(ItemTouchHelperAdapter adapter){
        mAdapter = adapter;
    }
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
//        int swipeFlags = ItemTouchHelper.LEFT;
        return makeFlag(ItemTouchHelper.ACTION_STATE_DRAG, dragFlags);
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//        if(end!=target.getAdapterPosition()){
//            end = target.getAdapterPosition();
//            if(isFirst){
//                start = viewHolder.getAdapterPosition();
//                Message message = new Message();
//                message.what = 1;
//                message.arg1 = start;
//                message.arg2 = end;
//                isFirst = false;
//                mHandler.sendMessageDelayed(message,1000);
//            }else {
//                mHandler.removeMessages(1);
//                end = target.getAdapterPosition();
//                Message message = new Message();
//                message.what = 1;
//                message.arg1 = start;
//                message.arg2 =  end;
//                mHandler.sendMessageDelayed(message,1000);
//            }
//        }
        mAdapter.onItemMove( viewHolder.getAdapterPosition(),target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }
    //拖拽停止时 调用
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        Log.i("zyb", "clearView");
        super.clearView(recyclerView, viewHolder);
    }

    //按下和松开item时 调用
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        Log.i("zyb", "onSelectedChanged");
        super.onSelectedChanged(viewHolder, actionState);
        if (actionState==2){
            isFirst = true;
        }
    }


}
