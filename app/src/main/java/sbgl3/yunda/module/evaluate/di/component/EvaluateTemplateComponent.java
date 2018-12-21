package sbgl3.yunda.module.evaluate.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import sbgl3.yunda.module.evaluate.di.module.EvaluateTemplateModule;

import com.jess.arms.di.scope.ActivityScope;

import sbgl3.yunda.module.evaluate.mvp.ui.activity.EvaluateTemplateActivity;

@ActivityScope
@Component(modules = EvaluateTemplateModule.class, dependencies = AppComponent.class)
public interface EvaluateTemplateComponent {
    void inject(EvaluateTemplateActivity activity);
}