package sbgl3.yunda.module.appraise.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import sbgl3.yunda.module.appraise.di.module.AppraiseTemplateModule;

import com.jess.arms.di.scope.ActivityScope;

import sbgl3.yunda.module.appraise.mvp.ui.activity.AppraiseTemplateActivity;

@ActivityScope
@Component(modules = AppraiseTemplateModule.class, dependencies = AppComponent.class)
public interface AppraiseTemplateComponent {
    void inject(AppraiseTemplateActivity activity);
}