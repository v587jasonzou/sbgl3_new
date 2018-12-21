package sbgl3.yunda.module.fwhjhx.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import sbgl3.yunda.module.fwhjhx.mvp.contract.FwhRepaiScopsContract;
import sbgl3.yunda.module.fwhjhx.mvp.model.FwhRepaiScopsModel;


@Module
public class FwhRepaiScopsModule {
    private FwhRepaiScopsContract.View view;

    /**
     * 构建FwhRepaiScopsModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public FwhRepaiScopsModule(FwhRepaiScopsContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    FwhRepaiScopsContract.View provideFwhRepaiScopsView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    FwhRepaiScopsContract.Model provideFwhRepaiScopsModel(FwhRepaiScopsModel model) {
        return model;
    }
}