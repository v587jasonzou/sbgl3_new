package sbgl3.yunda.module.gzcl.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import sbgl3.yunda.module.gzcl.mvp.contract.GzclListContract;
import sbgl3.yunda.module.gzcl.mvp.model.GzclListModel;


@Module
public class GzclListModule {
    private GzclListContract.View view;

    /**
     * 构建GzclListModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public GzclListModule(GzclListContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    GzclListContract.View provideGzclListView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    GzclListContract.Model provideGzclListModel(GzclListModel model) {
        return model;
    }
}