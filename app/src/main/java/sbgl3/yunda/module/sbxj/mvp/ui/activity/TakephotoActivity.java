package sbgl3.yunda.module.sbxj.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
import sbgl3.yunda.entry.ImagesBean;
import sbgl3.yunda.globle.adapter.ImagesAdapter;
import sbgl3.yunda.module.sbxj.di.component.DaggerTakephotoComponent;
import sbgl3.yunda.module.sbxj.di.module.TakephotoModule;
import sbgl3.yunda.module.sbxj.entry.InspectRecord;
import sbgl3.yunda.module.sbxj.mvp.contract.TakephotoContract;
import sbgl3.yunda.module.sbxj.mvp.presenter.TakephotoPresenter;
import sbgl3.yunda.widget.PhotoReadActivity;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class TakephotoActivity extends BaseActivity<TakephotoPresenter> implements TakephotoContract.View {
    public static int IMAGE_PICKER = 666;
    InspectRecord inspectRecord;
    ArrayList<InspectRecord> inspectRecordArrayList;
    ArrayList<ImagesBean> imagess = new ArrayList<>();
    Integer position;
    ImagesAdapter imagesAdapter;
    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.tvNowNum)
    TextView tvNowNum;
    @BindView(R.id.tvItemInfo)
    TextView tvItemInfo;
    @BindView(R.id.tvRule)
    TextView tvRule;
    @BindView(R.id.rlImages)
    RecyclerView rlImages;
    @BindView(R.id.tvConfirm)
    TextView tvConfirm;
    @BindView(R.id.cvConfirm)
    CardView cvConfirm;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerTakephotoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .takephotoModule(new TakephotoModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_takephoto; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        inspectRecordArrayList = (ArrayList<InspectRecord>)getIntent().getSerializableExtra("inspectRecordArrayList");
        position = getIntent().getIntExtra("position",0);
        setTranslucentStatus();
        setSupportActionBar(menuTp);
        menuTp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ArmsUtils.configRecycleView(rlImages, new GridLayoutManager(TakephotoActivity.this, 3, OrientationHelper.VERTICAL, false));
        imagesAdapter = new ImagesAdapter(imagess, this);
        rlImages.setAdapter(imagesAdapter);
        imagesAdapter.setOnItemClickListener(new DefaultAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int viewType, Object data, int position) {
                if("0".equals(imagess.get(position).getImagesName())){
                    Intent intent = new Intent(TakephotoActivity.this, ImageGridActivity.class);
                    intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                    startActivityForResult(intent, IMAGE_PICKER);
                }else {
                    Bundle bundle1 = new Bundle();
                    Intent intent = new Intent(TakephotoActivity.this, PhotoReadActivity.class);
                    bundle1.putSerializable("images", imagess);
                    bundle1.putInt("position",position);
                    bundle1.putString("state","order");
                    intent.putExtras(bundle1);
                    startActivityForResult(intent, ImagePicker.REQUEST_CODE_PREVIEW);
                }
            }
        });
        showLoading();
        initdata();
//        ProgressManager.getInstance().setRefreshTime(100);
//        ProgressManager.getInstance().addResponseListener("http://10.2.4.49:8080/SBGL3/pdaAttachment/uploadImage.do", new ProgressListener() {
//            @Override
//            public void onProgress(ProgressInfo progressInfo) {
//                ToastUtils.showShort( progressInfo.getPercent()+"");
//            }
//
//            @Override
//            public void onError(long id, Exception e) {
//                ToastUtils.showShort("监听错误"+e.getMessage());
//            }
//        });
    }

    private void initdata() {
        if(inspectRecordArrayList!=null&&inspectRecordArrayList.size()>0){
            inspectRecord = inspectRecordArrayList.get(position);
            mPresenter.getImages(inspectRecord.getIdx());
        }
        if (inspectRecord != null) {
            if(inspectRecord.getSeqNo()!=null){
                tvNowNum.setVisibility(View.VISIBLE);
                tvNowNum.setText(inspectRecord.getSeqNo().toString());
            }else {
                tvNowNum.setVisibility(View.GONE);
            }
            if(inspectRecord.getCheckItem()!=null){
                tvItemInfo.setText(inspectRecord.getCheckItem());
            }
            if(!StringUtils.isTrimEmpty(inspectRecord.getCheckStandard())){
                tvRule.setText(inspectRecord.getCheckStandard());
            }else {
                tvRule.setText("无");
            }

        }
    }

    @Override
    public void showLoading() {
        ProgressDialogUtils.showProgressDialog(this,"处理中...");
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void getImagesSuccess(List<ImagesBean> images) {
        imagess.clear();
        if(images!=null){
            imagess.addAll(images);
        }
        ImagesBean item = new ImagesBean();
        item.setImagesName("0");
        imagess.add(item);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imagesAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void confirmSuccess() {
        inspectRecordArrayList.get(position).setCheckResult("合格");
        boolean allOver = true;
        for (InspectRecord record : inspectRecordArrayList) {
            if (TextUtils.isEmpty(record.getCheckResult())) {
                allOver = false;
                break;
            }
        }
        if(!allOver){
            if(position==inspectRecordArrayList.size()-1){
                position=0;
            }else {
                position++;
            }
            showLoading();
            imagess.clear();
            imagesAdapter.notifyDataSetChanged();
            initdata();
        }else {
            ToastUtils.showShort("已完成所有巡检项！");
            finish();
        }
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

//                    FileUtils.copyFile(images.get(0).path, AppConstant.IMAGE_PATH+"12345"+AppConstant.JPG_EXTNAME);
//                    if(imagess.size()>0){
//                        imagess.remove(imagess.size() - 1);
//                    }
//                    for (int i = 0; i < images.size(); i++) {
//                        ImagesBean bean = new ImagesBean();
//                        bean.setImagesLocalPath(images.get(i).path);
//                        imagess.add(bean);
//                    }
//                    ImagesBean bean = new ImagesBean();
//                    bean.setImagesName("0");
//                    imagess.add(bean);
//                    imagesAdapter.notifyDataSetChanged();
                }
            } else if (requestCode == ImagePicker.REQUEST_CODE_PREVIEW) {
                if(data!=null){
                    ArrayList<ImagesBean> images = (ArrayList<ImagesBean>)data.getSerializableExtra("images");
                    imagess.clear();
                    if (images != null && images.size() > 0) {
                        imagess.addAll(images);
                        ImagesBean bean = new ImagesBean();
                        bean.setImagesName("0");
                        imagess.add(bean);
                        imagesAdapter.notifyDataSetChanged();
                    } else {
                        ImagesBean bean = new ImagesBean();
                        bean.setImagesName("0");
                        imagess.add(bean);
                        imagesAdapter.notifyDataSetChanged();
                    }
                }

            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @OnClick(R.id.tvConfirm)
    void Corfirm(){
        String ids = JSON.toJSONString(new String[] { inspectRecord.getIdx() });
        List<String> paths = new ArrayList<>();
        if(imagess!=null&&imagess.size()>0){
            for(ImagesBean bean:imagess){
                if(!StringUtils.isTrimEmpty(bean.getImagesPath())){
                    paths.add(bean.getImagesPath());
                }
            }
        }
        if(mPresenter!=null){
            showLoading();
            mPresenter.ConfirmRecord(ids,"合格",JSON.toJSONString(paths));
        }

    }
}
