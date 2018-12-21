package sbgl3.yunda.module.setting.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import sbgl3.yunda.module.setting.mvp.contract.ChangeServerContract;
import sbgl3.yunda.module.setting.mvp.model.ChangeServerModel;


@Module
public class ChangeServerModule {
    private ChangeServerContract.View view;

    /**
     * 构建ChangeServerModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ChangeServerModule(ChangeServerContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ChangeServerContract.View provideChangeServerView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ChangeServerContract.Model provideChangeServerModel(ChangeServerModel model) {
        return model;
    }
}