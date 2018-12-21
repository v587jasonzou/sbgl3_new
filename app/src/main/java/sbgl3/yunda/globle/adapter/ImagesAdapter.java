package sbgl3.yunda.globle.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.lzy.imagepicker.bean.ImageItem;

import java.util.List;

import butterknife.BindView;
import sbgl3.yunda.R;
import sbgl3.yunda.entry.ImagesBean;
import sbgl3.yunda.globle.viewHolder.ImagesHolder;
import sbgl3.yunda.globle.viewHolder.MenuHolder;
/**
 * <li>标题: 图片显示列表适配器
 * <li>创建人：周雪巍
 * <li>创建日期：2018-09-12
 * <li>修改人:
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 * @author 周雪巍
 * @version 1.0
 */
public class ImagesAdapter extends DefaultAdapter<ImagesBean> {

    Context context;



    public ImagesAdapter(List<ImagesBean> infos, Context context) {
        super(infos);
        this.context = context;
    }

    @Override
    public BaseHolder<ImagesBean> getHolder(View v, int viewType) {
        return new ImagesHolder(v,context);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.images_item;
    }
}
