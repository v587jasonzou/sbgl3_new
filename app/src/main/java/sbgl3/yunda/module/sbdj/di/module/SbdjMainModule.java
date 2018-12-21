package sbgl3.yunda.module.sbdj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import sbgl3.yunda.module.sbdj.mvp.contract.SbdjMainContract;
import sbgl3.yunda.module.sbdj.mvp.model.SbdjMainModel;


@Module
public class SbdjMainModule {
    private SbdjMainContract.View view;

    /**
     * 构建SbdjMainModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public SbdjMainModule(SbdjMainContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SbdjMainContract.View provideSbdjMainView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    SbdjMainContract.Model provideSbdjMainModel(SbdjMainModel model) {
        return model;
    }
}