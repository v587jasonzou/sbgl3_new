package sbgl3.yunda.module.gztp.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.utilcode.util.StringUtils;
import com.jess.arms.utils.utilcode.util.TimeUtils;
import com.jess.arms.utils.utilcode.util.ToastUtils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.speedata.utils.ProgressDialogUtils;

import net.lemonsoft.lemonbubble.LemonBubble;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import sbgl3.yunda.R;
import sbgl3.yunda.entry.ImagesBean;
import sbgl3.yunda.globle.adapter.ImagesAdapter;
import sbgl3.yunda.module.gztp.di.component.DaggerNewBuildTicketComponent;
import sbgl3.yunda.module.gztp.di.module.NewBuildTicketModule;
import sbgl3.yunda.module.gztp.entry.FaultOrder;
import sbgl3.yunda.module.gztp.mvp.contract.NewBuildTicketContract;
import sbgl3.yunda.module.gztp.mvp.presenter.NewBuildTicketPresenter;
import sbgl3.yunda.module.sbxj.mvp.ui.activity.TakephotoActivity;
import sbgl3.yunda.module.tpgzpg.entry.BackInfoBean;
import sbgl3.yunda.module.tpgzpg.mvp.ui.adapter.BackInfoAdapter;
import sbgl3.yunda.widget.PhotoReadActivity;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class NewBuildTicketActivity extends BaseActivity<NewBuildTicketPresenter> implements NewBuildTicketContract.View {
    public static int IMAGE_PICKER = 666;
    @BindView(R.id.menu_tp)
    Toolbar menuTp;
    @BindView(R.id.etEquipCode)
    EditText etEquipCode;
    @BindView(R.id.ivEditDelete)
    ImageView ivEditDelete;
    @BindView(R.id.tvNotFound)
    TextView tvNotFound;
    @BindView(R.id.ivSearch)
    ImageView ivSearch;
    @BindView(R.id.ivScan)
    ImageView ivScan;
    @BindView(R.id.tvFaultOrderNo)
    TextView tvFaultOrderNo;
    @BindView(R.id.tvEquipCode)
    TextView tvEquipCode;
    @BindView(R.id.tvEquipName)
    TextView tvEquipName;
    @BindView(R.id.tvUsePlace)
    TextView tvUsePlace;
    @BindView(R.id.tvFindTime)
    TextView tvFindTime;
    @BindView(R.id.rbCommon)
    RadioButton rbCommon;
    @BindView(R.id.rbBig)
    RadioButton rbBig;
    @BindView(R.id.rbVeryBig)
    RadioButton rbVeryBig;
    @BindView(R.id.rgLevel)
    RadioGroup rgLevel;
    @BindView(R.id.etFaultLocation)
    EditText etFaultLocation;
    @BindView(R.id.etFaultDes)
    EditText etFaultDes;
    @BindView(R.id.rlImages)
    RecyclerView rlImages;
    @BindView(R.id.tvConfirm)
    TextView tvConfirm;
    @BindView(R.id.cvConfirm)
    CardView cvConfirm;
    ArrayList<ImagesBean> imagess = new ArrayList<>();
    ImagesAdapter imagesAdapter;
    FaultOrder order;
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerNewBuildTicketComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .newBuildTicketModule(new NewBuildTicketModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_new_build_ticket; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
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
        ArmsUtils.configRecycleView(rlImages, new GridLayoutManager(NewBuildTicketActivity.this, 3, OrientationHelper.VERTICAL, false));
        imagesAdapter = new ImagesAdapter(imagess, this);
        rlImages.setAdapter(imagesAdapter);
        imagesAdapter.setOnItemClickListener(new DefaultAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int viewType, Object data, int position) {
                if("0".equals(imagess.get(position).getImagesName())){
                    Intent intent = new Intent(NewBuildTicketActivity.this, ImageGridActivity.class);
                    intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                    startActivityForResult(intent, IMAGE_PICKER);
                }else {
                    Bundle bundle1 = new Bundle();
                    Intent intent = new Intent(NewBuildTicketActivity.this, PhotoReadActivity.class);
                    bundle1.putSerializable("images", imagess);
                    bundle1.putInt("position",position);
                    bundle1.putString("state","order");
                    intent.putExtras(bundle1);
                    startActivityForResult(intent, ImagePicker.REQUEST_CODE_PREVIEW);
                }
            }
        });
        if(intent!=null){
            order = (FaultOrder) intent.getSerializableExtra("order");
            if(order!=null){
                if(order.getFaultOrderNo()!=null){
                    tvFaultOrderNo.setText(order.getFaultOrderNo());
                }
                if(order.getEquipmentCode()!=null){
                    tvEquipCode.setText(order.getEquipmentCode());
                }
                if(order.getEquipmentName()!=null){
                    tvEquipName.setText(order.getEquipmentName());
                }
                if(order.getUsePlace()!=null){
                    tvUsePlace.setText(order.getUsePlace());
                }
                if(order.getFaultOccurTime()!=null&&order.getFaultOccurTime()>0){
                    tvFindTime.setText(TimeUtils.date2String(new Date(order.getFaultOccurTime()),new SimpleDateFormat("yyyy/MM/dd HH:mm")));
                }
                if(order.getFaultLevel()!=null){
                    if(order.getFaultLevel().equals("一般")){
                        rbCommon.setChecked(true);
                    }else if(order.getFaultLevel().equals("重大")){
                        rbBig.setChecked(true);
                    }else {
                        rbVeryBig.setChecked(true);
                    }
                }
                if(order.getFaultPlace()!=null){
                    etFaultLocation.setText(order.getFaultPlace());
                }
                if(order.getFaultPhenomenon()!=null){
                    etFaultDes.setText(order.getFaultPhenomenon());
                }
                if(mPresenter!=null){
                    showLoading();
                    mPresenter.getImages(order.getIdx());
                }
            }
        }
        rgLevel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rbCommon:
                        order.setFaultLevel("一般");
                        break;
                    case R.id.rbBig:
                        order.setFaultLevel("重大");
                        break;
                    case R.id.rbVeryBig:
                        order.setFaultLevel("极大");
                        break;
                }
            }
        });
    }

    @Override
    public void showLoading() {
//        ProgressDialogUtils.showProgressDialog(this,"加载中...");

//        LemonBubble.showError(this, "出错啦", 2000);
        LemonBubble.showRoundProgress(this, "加载中...");
    }

    @Override
    public void hideLoading() {
//        ProgressDialogUtils.dismissProgressDialog();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LemonBubble.showRight(NewBuildTicketActivity.this, "成功啦！", 2000);
            }
        });

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
        }else {
            LemonBubble.showError(NewBuildTicketActivity.this, "未获取到相关照片！", 2000);
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
//        ToastUtils.showShort("提票成功！");
        Observable.just(1).observeOn(AndroidSchedulers.mainThread())
                .delay(2500, TimeUnit.MILLISECONDS)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

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
    void Confirm(){
        if(etFaultLocation.getText()==null||StringUtils.isTrimEmpty(etFaultLocation.getText().toString())){
            ToastUtils.showShort("还未输入地点部位信息");
            return;
        }else {
            order.setFaultPlace(etFaultLocation.getText().toString());
        }
        if(etFaultDes.getText()==null||StringUtils.isTrimEmpty(etFaultDes.getText().toString())){
            ToastUtils.showShort("还未输入现象描述信息");
            return;
        }else {
            order.setFaultPhenomenon(etFaultDes.getText().toString());
        }
        if(tvUsePlace.getText()!=null&&!StringUtils.isTrimEmpty(tvUsePlace.getText().toString())){
            order.setUsePlace(tvUsePlace.getText().toString());
        }
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
            mPresenter.AddFaultTicket(JSON.toJSONString(paths),JSON.toJSONString(order));
        }
    }
}
