package sbgl3.yunda.module.fwhjhx.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import sbgl3.yunda.module.fwhjhx.mvp.contract.StuffContract;
import sbgl3.yunda.module.fwhjhx.mvp.model.StuffModel;


@Module
public class StuffModule {
    private StuffContract.View view;

    /**
     * 构建StuffModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public StuffModule(StuffContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    StuffContract.View provideStuffView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    StuffContract.Model provideStuffModel(StuffModel model) {
        return model;
    }
}