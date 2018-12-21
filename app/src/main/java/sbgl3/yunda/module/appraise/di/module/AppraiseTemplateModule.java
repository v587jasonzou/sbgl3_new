package sbgl3.yunda.module.appraise.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import sbgl3.yunda.module.appraise.mvp.contract.AppraiseTemplateContract;
import sbgl3.yunda.module.appraise.mvp.model.AppraiseTemplateModel;


@Module
public class AppraiseTemplateModule {
    private AppraiseTemplateContract.View view;

    /**
     * 构建AppraiseTemplateModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public AppraiseTemplateModule(AppraiseTemplateContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    AppraiseTemplateContract.View provideAppraiseTemplateView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    AppraiseTemplateContract.Model provideAppraiseTemplateModel(AppraiseTemplateModel model) {
        return model;
    }
}