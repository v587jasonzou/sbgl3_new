package sbgl3.yunda.module.gzcl.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import sbgl3.yunda.module.gzcl.mvp.contract.GzclEditContract;
import sbgl3.yunda.module.gzcl.mvp.model.GzclEditModel;


@Module
public class GzclEditModule {
    private GzclEditContract.View view;

    /**
     * 构建GzclEditModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public GzclEditModule(GzclEditContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    GzclEditContract.View provideGzclEditView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    GzclEditContract.Model provideGzclEditModel(GzclEditModel model) {
        return model;
    }
}