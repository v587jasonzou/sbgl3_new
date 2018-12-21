package sbgl3.yunda.module.userconfirm.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import sbgl3.yunda.module.userconfirm.mvp.contract.UserConfirmContract;
import sbgl3.yunda.module.userconfirm.mvp.model.UserConfirmModel;


@Module
public class UserConfirmModule {
    private UserConfirmContract.View view;

    /**
     * 构建UserConfirmModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public UserConfirmModule(UserConfirmContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    UserConfirmContract.View provideUserConfirmView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    UserConfirmContract.Model provideUserConfirmModel(UserConfirmModel model) {
        return model;
    }
}