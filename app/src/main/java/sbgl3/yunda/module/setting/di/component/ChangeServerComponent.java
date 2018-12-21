package sbgl3.yunda.module.setting.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import sbgl3.yunda.module.setting.di.module.ChangeServerModule;

import com.jess.arms.di.scope.ActivityScope;

import sbgl3.yunda.module.setting.mvp.ui.activity.ChangeServerActivity;

@ActivityScope
@Component(modules = ChangeServerModule.class, dependencies = AppComponent.class)
public interface ChangeServerComponent {
    void inject(ChangeServerActivity activity);
}