package sbgl3.yunda.module.tpgzpg.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import sbgl3.yunda.module.tpgzpg.mvp.contract.TpgzpgListContract;
import sbgl3.yunda.module.tpgzpg.mvp.model.TpgzpgListModel;


@Module
public class TpgzpgListModule {
    private TpgzpgListContract.View view;

    /**
     * 构建TpgzpgListActivityModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public TpgzpgListModule(TpgzpgListContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    TpgzpgListContract.View provideTpgzpgListView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    TpgzpgListContract.Model provideTpgzpgListModel(TpgzpgListModel model) {
        return model;
    }
}