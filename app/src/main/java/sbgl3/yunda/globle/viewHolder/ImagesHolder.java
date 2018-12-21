package sbgl3.yunda.globle.viewHolder;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.utils.utilcode.util.ScreenUtils;
import com.jess.arms.utils.utilcode.util.SizeUtils;
import com.jess.arms.utils.utilcode.util.StringUtils;
import com.lzy.imagepicker.bean.ImageItem;

import butterknife.BindView;
import sbgl3.yunda.R;
import sbgl3.yunda.entry.ImagesBean;
import sbgl3.yunda.module.main.Entry.MenuBean;
/**
 * <li>标题: 照片查看列表控件holder
 * <li>创建人：周雪巍
 * <li>创建日期：2018-09-12
 * <li>修改人:
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 * @author 周雪巍
 * @version 1.0
 */
public class ImagesHolder extends BaseHolder<ImagesBean> {
    @BindView(R.id.ivImages)
    ImageView ivImages;
    Context context;
    public ImagesHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
    }
    @Override
    public void setData(ImagesBean data, int position) {
        if (data != null) {
            RequestOptions options = new RequestOptions()
                    .placeholder(R.mipmap.image_upload_icon)	//加载成功之前占位图
                    .error(R.mipmap.image_upload_faild)	//加载错误之后的错误图
                    .override(SizeUtils.dp2px(108),SizeUtils.dp2px(108))	//指定图片的尺寸
                    //指定图片的缩放类型为fitCenter （等比例缩放图片，宽或者是高等于ImageView的宽或者是高。）
                    .centerCrop();
            if("0".equals(data.getImagesName())){
                Glide.with(context)
                        .load(R.mipmap.add_images_icon)
                        .thumbnail(0.2f)
                        .apply(options)
                        .into(ivImages);
            }else {
                if(data.getImagesLocalPath()!=null){
                    Glide.with(context)
                            .load(data.getImagesLocalPath())
                            .thumbnail(0.2f)
                            .apply(options)
                            .into(ivImages);
                }
            }


        }

    }

}
