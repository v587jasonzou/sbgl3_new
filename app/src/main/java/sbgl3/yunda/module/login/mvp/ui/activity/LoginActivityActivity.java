package sbgl3.yunda.module.login.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.utilcode.util.CacheDiskUtils;
import com.jess.arms.utils.utilcode.util.ScreenUtils;
import com.jess.arms.utils.utilcode.util.StringUtils;
import com.jess.arms.utils.utilcode.util.ToastUtils;
import com.speedata.utils.ProgressDialogUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sbgl3.yunda.R;
import sbgl3.yunda.SysInfo;
import sbgl3.yunda.module.login.di.component.DaggerLoginActivityComponent;
import sbgl3.yunda.module.login.di.module.LoginActivityModule;
import sbgl3.yunda.module.login.mvp.contract.LoginActivityContract;
import sbgl3.yunda.module.login.mvp.presenter.LoginActivityPresenter;
import sbgl3.yunda.module.main.mvp.ui.activity.MainActivity;
import sbgl3.yunda.module.setting.mvp.ui.activity.SettingActivity;
import sbgl3.yunda.widget.ClearEditText;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class LoginActivityActivity extends BaseActivity<LoginActivityPresenter> implements LoginActivityContract.View {

    @Inject
    LoginActivityPresenter mPresenter;
    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.textView1)
    TextView textView1;
    @BindView(R.id.usernameText)
    ClearEditText usernameText;
    @BindView(R.id.ivDelete)
    ImageView ivDelete;
    @BindView(R.id.input_account)
    LinearLayout inputAccount;
    @BindView(R.id.passwordText)
    ClearEditText passwordText;
    @BindView(R.id.loginBtn)
    Button loginBtn;
    @BindView(R.id.rememberAccount)
    CheckBox rememberAccount;
    @BindView(R.id.settingTxv)
    TextView settingTxv;
    @BindView(R.id.version)
    TextView version;
    List<String> users = new ArrayList<>();
    PopupMenu popupMenu;
    @BindView(R.id.ivSpring)
    ImageView ivSpring;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLoginActivityComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .loginActivityModule(new LoginActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_login; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTranslucentStatus();
        Log.e("尺寸", ScreenUtils.getScreenWidth() + "" + ScreenUtils.getScreenHeight() + ScreenUtils.getScreenDensityDpi());
        popupMenu = new PopupMenu(this, inputAccount);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                usernameText .setText(item.getTitle());
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(inputAccount.getWindowToken(), 0);
                }
                popupMenu.dismiss();
                return false;
            }
        });
    }

    @OnClick(R.id.loginBtn)
    void login() {
        if (usernameText.getText() == null || StringUtils.isTrimEmpty(usernameText.getText().toString())) {
            ToastUtils.showShort("请输入用户名！");
            return;
        }
        if (passwordText.getText() == null || StringUtils.isTrimEmpty(passwordText.getText().toString())) {
            ToastUtils.showShort("请输入密码！");
            return;
        }
        if (rememberAccount.isChecked()) {
            mPresenter.Login(usernameText.getText().toString(), passwordText.getText().toString(), true);
        } else {
            mPresenter.Login(usernameText.getText().toString(), passwordText.getText().toString(), false);
        }

    }

    @Override
    public void showLoading() {
        ProgressDialogUtils.showProgressDialog(this, "正在登录中，请稍后");
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

    @OnClick(R.id.settingTxv)
    void toSetting() {
        ArmsUtils.startActivity(new Intent(this, SettingActivity.class));
    }
    @OnClick(R.id.ivSpring)
    void ShowPopup(){
        if(users.size()>0){
            android.view.Menu menu_more = popupMenu.getMenu();
            menu_more.clear();
            int size = users.size();
            for (int i = 0; i < size; i++) {
                menu_more.add(android.view.Menu.NONE,  i, i, users.get(i));
            }
            popupMenu.show();
        }else {
            ToastUtils.showShort("无保存用户信息");
        }
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
        if((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0){
            finish();
            return;
        }
    }

    @Override
    public void toMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        ArmsUtils.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SysInfo.menus != null && SysInfo.menus.size() > 0) {
            SysInfo.menus.clear();
        }
        users.clear();
        ArrayList<String> list = (ArrayList<String>) CacheDiskUtils.getInstance().getSerializable("users");
        if (list != null && list.size() > 0) {
            users.addAll(list);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        if((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0){
            finish();
            return;
        }
    }
}
