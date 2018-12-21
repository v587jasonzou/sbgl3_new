package sbgl3.yunda.module.main.mvp.presenter;

import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.utilcode.util.ToastUtils;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import sbgl3.yunda.SysInfo;
import sbgl3.yunda.module.login.Entry.LoginReponsBody;
import sbgl3.yunda.module.main.Entry.MenuBean;
import sbgl3.yunda.module.main.mvp.contract.MainContract;


@ActivityScope
public class MainPresenter extends BasePresenter<MainContract.Model, MainContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public MainPresenter(MainContract.Model model, MainContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
    public void getmenus(){
        mRootView.showLoading();
        if(SysInfo.menus==null||SysInfo.menus.size()==0){
            mModel.getMenus().observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<MenuBean>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(List<MenuBean> menuBeans) {
                            if(menuBeans!=null&&menuBeans.size()>0){
                                SysInfo.menus.clear();
                                SysInfo.menus.addAll(menuBeans);
                                mModel.getTotos().observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Observer<LoginReponsBody>() {
                                            @Override
                                            public void onSubscribe(Disposable d) {

                                            }

                                            @Override
                                            public void onNext(LoginReponsBody loginReponsBody) {
                                                if(loginReponsBody!=null){
                                                    if(loginReponsBody.getTodoJob()!=null&&loginReponsBody
                                                            .getTodoJob().size()>0){
                                                        List<LoginReponsBody.TodoJobBean> todos = loginReponsBody.getTodoJob();
                                                        for(int i = 0;i<todos.size();i++){
                                                            for(int j = 0;j<SysInfo.menus.size();j++){
                                                                if(todos.get(i).getJobType()!=null){
                                                                    if(todos.get(i).getJobType().equals(SysInfo.menus.get(j).getFuncname())){
                                                                        if(todos.get(i).getJobNum()!=null&&todos.get(i).getJobNum()>0){
                                                                            SysInfo.menus.get(j).setTodoNum(todos.get(i).getJobNum());
                                                                            break;
                                                                        }
                                                                    }
                                                                }

                                                            }
                                                        }
                                                        mRootView.hideLoading();
                                                        mRootView.notifyAdapter();
                                                    } else {
                                                        ToastUtils.showShort("该用户没有待办项！");
                                                        mRootView.notifyAdapter();
                                                        mRootView.hideLoading();
                                                    }
                                                }else {
                                                    mRootView.hideLoading();
                                                    mRootView.notifyAdapter();
                                                    ToastUtils.showShort("获取用户待办失败");
                                                }
                                            }

                                            @Override
                                            public void onError(Throwable e) {
                                                ToastUtils.showShort("获取用户待办失败"+e.getMessage());
                                                mRootView.hideLoading();
                                                mRootView.notifyAdapter();
                                            }

                                            @Override
                                            public void onComplete() {

                                            }
                                        });
                            }else {
                                mRootView.hideLoading();
                                ToastUtils.showShort("当前用户无任何权限，请管理员分配权限后才重试");
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            mRootView.hideLoading();
                            ToastUtils.showShort("获取菜单权限失败"+e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }else {
            mModel.getTotos().observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<LoginReponsBody>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(LoginReponsBody loginReponsBody) {
                            if(loginReponsBody!=null){
                                if(loginReponsBody.getTodoJob()!=null&&loginReponsBody
                                        .getTodoJob().size()>0){
                                    List<LoginReponsBody.TodoJobBean> todos = loginReponsBody.getTodoJob();
                                    for(int i = 0;i<todos.size();i++){
                                        for(int j = 0;j<SysInfo.menus.size();j++){
                                            if(todos.get(i).getJobType()!=null){
                                                if(todos.get(i).getJobType().equals(SysInfo.menus.get(j).getFuncname())){
                                                    if(todos.get(i).getJobNum()!=null&&todos.get(i).getJobNum()>0){
                                                        SysInfo.menus.get(j).setTodoNum(todos.get(i).getJobNum());
                                                        break;
                                                    }
                                                }
                                            }

                                        }
                                    }
                                    mRootView.hideLoading();
                                    mRootView.notifyAdapter();
                                }
                            }else {
                                mRootView.hideLoading();
                                mRootView.notifyAdapter();
                                ToastUtils.showShort("获取用户待办失败");
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            ToastUtils.showShort("获取用户待办失败"+e.getMessage());
                            mRootView.hideLoading();
                            mRootView.notifyAdapter();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }
}
