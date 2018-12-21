package sbgl3.yunda.module.sbxj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import sbgl3.yunda.module.sbxj.mvp.contract.SBXJDevContract;
import sbgl3.yunda.module.sbxj.mvp.model.SBXJDevModel;


@Module
public class SBXJDevModule {
    private SBXJDevContract.View view;

    /**
     * 构建SBXJDevModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public SBXJDevModule(SBXJDevContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SBXJDevContract.View provideSBXJDevView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    SBXJDevContract.Model provideSBXJDevModel(SBXJDevModel model) {
        return model;
    }
}