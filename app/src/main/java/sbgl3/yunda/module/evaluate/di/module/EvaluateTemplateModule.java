package sbgl3.yunda.module.evaluate.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import sbgl3.yunda.module.evaluate.mvp.contract.EvaluateTemplateContract;
import sbgl3.yunda.module.evaluate.mvp.model.EvaluateTemplateModel;


@Module
public class EvaluateTemplateModule {
    private EvaluateTemplateContract.View view;

    /**
     * 构建EvaluateTemplateModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public EvaluateTemplateModule(EvaluateTemplateContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    EvaluateTemplateContract.View provideEvaluateTemplateView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    EvaluateTemplateContract.Model provideEvaluateTemplateModel(EvaluateTemplateModel model) {
        return model;
    }
}