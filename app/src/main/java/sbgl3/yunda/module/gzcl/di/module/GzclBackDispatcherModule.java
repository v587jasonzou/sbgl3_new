package sbgl3.yunda.module.gzcl.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import sbgl3.yunda.module.gzcl.mvp.contract.GzclBackDispatcherContract;
import sbgl3.yunda.module.gzcl.mvp.model.GzclBackDispatcherModel;


@Module
public class GzclBackDispatcherModule {
    private GzclBackDispatcherContract.View view;

    /**
     * 构建GzclBackDispatcherModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public GzclBackDispatcherModule(GzclBackDispatcherContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    GzclBackDispatcherContract.View provideGzclBackDispatcherView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    GzclBackDispatcherContract.Model provideGzclBackDispatcherModel(GzclBackDispatcherModel model) {
        return model;
    }
}