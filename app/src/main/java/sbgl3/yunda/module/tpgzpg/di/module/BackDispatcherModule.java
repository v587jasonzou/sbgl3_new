package sbgl3.yunda.module.tpgzpg.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import sbgl3.yunda.module.tpgzpg.mvp.contract.BackDispatcherContract;
import sbgl3.yunda.module.tpgzpg.mvp.model.BackDispatcherModel;


@Module
public class BackDispatcherModule {
    private BackDispatcherContract.View view;

    /**
     * 构建BackDispatcherModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public BackDispatcherModule(BackDispatcherContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    BackDispatcherContract.View provideBackDispatcherView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    BackDispatcherContract.Model provideBackDispatcherModel(BackDispatcherModel model) {
        return model;
    }
}