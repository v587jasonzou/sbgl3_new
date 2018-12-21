package sbgl3.yunda.widget;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lzy.imagepicker.bean.ImageItem;

import java.util.List;

import sbgl3.yunda.R;
import sbgl3.yunda.entry.ImagesBean;

/**
 * 拍照及照片查看相关类-照片列表适配器
 * @author 周雪巍
 * @time 2018/09/12
 * */
public class MyPhotoAdapter extends PagerAdapter {
    Context context;
    int posiotion;
    List<ImagesBean> list;
    String state;
    public MyPhotoAdapter(Context context, int posiotion, List<ImagesBean> list, String state){
        this.context = context;
        this.posiotion = posiotion;
        this.list = list;
        this.state = state;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        if(list!=null&&list.size()>0){
            return list.size();
        }else {
            return 0;
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
//        return super.instantiateItem(container, position);
        PhotoView imageView = new PhotoView(context);
        imageView.setPadding(0,20,0,20);
        imageView.enable();
//        if(state.equals("1")){
//            String base64 = list.get(position).path;
//            byte[] strbyte = Base64.decode(base64,Base64.DEFAULT);
////                    Bitmap bitmap = BitmapFactory.decodeByteArray(strbyte,0,strbyte.length);
//            Glide.with(context).load(strbyte).override(720,1000).into(imageView);
//        }else {
//            Glide.with(context).load(list.get(position).path).
//                    override(720,1000).into(imageView);
//            container.addView(imageView);
//        }
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.image_upload_icon)	//加载成功之前占位图
                .error(R.mipmap.image_upload_faild)	//加载错误之后的错误图
                .override(720,1000)	//指定图片的尺寸
                //指定图片的缩放类型为fitCenter （等比例缩放图片，宽或者是高等于ImageView的宽或者是高。）
                .fitCenter();
        if(list.get(position).getImagesLocalPath()!=null){
            Glide.with(context).load(list.get(position).getImagesLocalPath())
                    .apply(options)
                    .into(imageView);
        }

        container.addView(imageView);

        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }
}
