package sbgl3.yunda.module.sbxj.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import sbgl3.yunda.module.sbxj.di.module.SBXJModule;

import com.jess.arms.di.scope.ActivityScope;

import sbgl3.yunda.module.sbxj.mvp.ui.activity.SBXJActivity;

@ActivityScope
@Component(modules = SBXJModule.class, dependencies = AppComponent.class)
public interface SBXJComponent {
    void inject(SBXJActivity activity);
}