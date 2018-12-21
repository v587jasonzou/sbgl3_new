package sbgl3.yunda.module.sbxj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import sbgl3.yunda.module.sbxj.mvp.contract.SBXJContract;
import sbgl3.yunda.module.sbxj.mvp.model.SBXJModel;


@Module
public class SBXJModule {
    private SBXJContract.View view;

    /**
     * 构建SBXJModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public SBXJModule(SBXJContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SBXJContract.View provideSBXJView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    SBXJContract.Model provideSBXJModel(SBXJModel model) {
        return model;
    }
}