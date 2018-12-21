package sbgl3.yunda.module.sbdj.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.utilcode.util.StringUtils;
import com.jess.arms.utils.utilcode.util.ToastUtils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.speedata.utils.ProgressDialogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sbgl3.yunda.R;
import sbgl3.yunda.entry.EquipInfoBean;
import sbgl3.yunda.entry.ImagesBean;
import sbgl3.yunda.globle.adapter.ImagesAdapter;
import sbgl3.yunda.module.sbdj.di.component.DaggerReadPhotoComponent;
import sbgl3.yunda.module.sbdj.di.module.ReadPhotoModule;
import sbgl3.yunda.module.sbdj.entity.EquipmentUnionRFID;
import sbgl3.yunda.module.sbdj.mvp.contract.ReadPhotoContract;
import sbgl3.yunda.module.sbdj.mvp.presenter.ReadPhotoPresenter;
import sbgl3.yunda.module.sbxj.mvp.ui.activity.TakephotoActivity;
import sbgl3.yunda.widget.PhotoReadActivity;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class ReadPhotoActivity extends BaseActivity<ReadPhotoPresenter> implements ReadPhotoContract.View {
    ArrayList<ImagesBean> imagess = new ArrayList<>();
    Integer position;
    ImagesAdapter imagesAdapter;
    public static int IMAGE_PICKER = 666;
    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.rlImages)
    RecyclerView rlImages;
    @BindView(R.id.tvConfirm)
    TextView tvConfirm;
    EquipmentUnionRFID equipBean;
    boolean isAdd = true;
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerReadPhotoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .readPhotoModule(new ReadPhotoModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_read_photo; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTranslucentStatus();
        setSupportActionBar(menuTp);
        menuTp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        if(intent!=null){
            equipBean = (EquipmentUnionRFID)intent.getSerializableExtra("equip");
            isAdd = intent.getBooleanExtra("isAdd",true);
        }
        if(equipBean!=null){
            mPresenter.getImages(equipBean.getIdx());
            showLoading();
        }
        if(isAdd){
            tvConfirm.setVisibility(View.VISIBLE);
        }else {
            tvConfirm.setVisibility(View.GONE);
        }
        ArmsUtils.configRecycleView(rlImages, new GridLayoutManager(this, 3, OrientationHelper.VERTICAL, false));
        imagesAdapter = new ImagesAdapter(imagess, this);
        rlImages.setAdapter(imagesAdapter);
        imagesAdapter.setOnItemClickListener(new DefaultAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int viewType, Object data, int position) {
                if(isAdd){
                    if("0".equals(imagess.get(position).getImagesName())){
                        Intent intent = new Intent(ReadPhotoActivity.this, ImageGridActivity.class);
                        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                        startActivityForResult(intent, IMAGE_PICKER);
                    }else {
                        Bundle bundle1 = new Bundle();
                        Intent intent = new Intent(ReadPhotoActivity.this, PhotoReadActivity.class);
                        bundle1.putSerializable("images", imagess);
                        bundle1.putInt("position",position);
                        bundle1.putString("state","order");
                        intent.putExtras(bundle1);
                        startActivityForResult(intent, ImagePicker.REQUEST_CODE_PREVIEW);
                    }
                }else {
                    Bundle bundle1 = new Bundle();
                    Intent intent = new Intent(ReadPhotoActivity.this, PhotoReadActivity.class);
                    bundle1.putSerializable("images", imagess);
                    bundle1.putInt("position",position);
                    bundle1.putString("state","1");
                    intent.putExtras(bundle1);
                    startActivityForResult(intent, ImagePicker.REQUEST_CODE_PREVIEW);
                }

            }
        });

    }

    @Override
    public void showLoading() {
        ProgressDialogUtils.showProgressDialog(this,"正在加载中,请稍后...");
    }

    @Override
    public void hideLoading() {
        ProgressDialogUtils.dismissProgressDialog();
    }


    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void getImagesSuccess(List<ImagesBean> images) {
        imagess.clear();
        if(images!=null){
            imagess.addAll(images);
        }
        if(isAdd){
            ImagesBean item = new ImagesBean();
            item.setImagesName("0");
            imagess.add(item);
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(!isAdd){
                    if(images==null||images.size()==0){
                        ToastUtils.showShort("此设备无相关照片信息");
                    }
                }
                hideLoading();
                imagesAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void UpLoadImagesSuccess(ImagesBean bean) {
        if(imagess.size()>0){
            imagess.remove(imagess.size() - 1);
        }
        imagess.add(bean);
        ImagesBean bean1 = new ImagesBean();
        bean1.setImagesName("0");
        imagess.add(bean1);
        imagesAdapter.notifyDataSetChanged();
    }

    @Override
    public void ConfirmImageSuccess() {
        ToastUtils.showShort("保存成功！");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {

            if (data != null && requestCode == IMAGE_PICKER) {
                ArrayList<ImageItem> images = data.getParcelableArrayListExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null && images.size() > 0) {
                    if(mPresenter!=null){
                        showLoading();
                        mPresenter.UploadImage(images.get(0));
                    }

                }
            } else if (requestCode == ImagePicker.REQUEST_CODE_PREVIEW) {
                if(data!=null){
                    ArrayList<ImagesBean> images = (ArrayList<ImagesBean>)data.getSerializableExtra("images");
                    imagess.clear();
                    if (images != null && images.size() > 0) {
                        imagess.addAll(images);
                        if(isAdd){
                            ImagesBean item = new ImagesBean();
                            item.setImagesName("0");
                            imagess.add(item);
                        }
                        imagesAdapter.notifyDataSetChanged();
                    } else {
                        if(isAdd){
                            ImagesBean item = new ImagesBean();
                            item.setImagesName("0");
                            imagess.add(item);
                        }
                        imagesAdapter.notifyDataSetChanged();
                    }
                }

            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @OnClick(R.id.tvConfirm)
    void CofirmImages(){
        String ids = equipBean.getIdx();
        List<String> paths = new ArrayList<>();
        if(imagess!=null&&imagess.size()>0){
            for(ImagesBean bean:imagess){
                if(!StringUtils.isTrimEmpty(bean.getImagesPath())){
                    paths.add(bean.getImagesPath());
                }
            }
        }
        if(mPresenter!=null){
            mPresenter.ConfirmRecord(ids,"e_equipment_info",JSON.toJSONString(paths));
        }
    }
}
