package sbgl3.yunda.module.tpgzpg.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import sbgl3.yunda.module.tpgzpg.mvp.contract.TpgzpgEditContract;
import sbgl3.yunda.module.tpgzpg.mvp.model.TpgzpgEditModel;


@Module
public class TpgzpgEditModule {
    private TpgzpgEditContract.View view;

    /**
     * 构建TpgzpgEditModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public TpgzpgEditModule(TpgzpgEditContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    TpgzpgEditContract.View provideTpgzpgEditView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    TpgzpgEditContract.Model provideTpgzpgEditModel(TpgzpgEditModel model) {
        return model;
    }
}