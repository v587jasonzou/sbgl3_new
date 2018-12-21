package sbgl3.yunda.module.sbxj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import sbgl3.yunda.module.sbxj.mvp.contract.SBXJRecordContract;
import sbgl3.yunda.module.sbxj.mvp.model.SBXJRecordModel;


@Module
public class SBXJRecordModule {
    private SBXJRecordContract.View view;

    /**
     * 构建SBXJRecordModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public SBXJRecordModule(SBXJRecordContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SBXJRecordContract.View provideSBXJRecordView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    SBXJRecordContract.Model provideSBXJRecordModel(SBXJRecordModel model) {
        return model;
    }
}