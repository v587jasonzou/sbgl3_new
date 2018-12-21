package sbgl3.yunda.module.spot_check.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import sbgl3.yunda.module.spot_check.mvp.contract.SpotCheckMainContract;
import sbgl3.yunda.module.spot_check.mvp.model.SpotCheckMainModel;


@Module
public class SpotCheckMainModule {
    private SpotCheckMainContract.View view;

    /**
     * 构建SpotCheckMainModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public SpotCheckMainModule(SpotCheckMainContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SpotCheckMainContract.View provideSpotCheckMainView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    SpotCheckMainContract.Model provideSpotCheckMainModel(SpotCheckMainModel model) {
        return model;
    }
}