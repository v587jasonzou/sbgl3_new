package sbgl3.yunda.module.setting.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import sbgl3.yunda.module.setting.di.module.SettingModule;

import com.jess.arms.di.scope.ActivityScope;

import sbgl3.yunda.module.setting.mvp.ui.activity.SettingActivity;

@ActivityScope
@Component(modules = SettingModule.class, dependencies = AppComponent.class)
public interface SettingComponent {
    void inject(SettingActivity activity);
}