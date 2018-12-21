package sbgl3.yunda.module.sbxj.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import sbgl3.yunda.module.sbxj.mvp.contract.TakephotoContract;
import sbgl3.yunda.module.sbxj.mvp.model.TakephotoModel;


@Module
public class TakephotoModule {
    private TakephotoContract.View view;

    /**
     * 构建TakephotoModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public TakephotoModule(TakephotoContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    TakephotoContract.View provideTakephotoView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    TakephotoContract.Model provideTakephotoModel(TakephotoModel model) {
        return model;
    }
}